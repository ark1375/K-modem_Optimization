
package ku.cs.model.sa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

public class GeneticsAlgorithm {
    
    private Polygon ply;
    private int monteCarloItterations = 10000;
    private int geneticPopulation = 1000;
    private int numberOfGenerations = 100;
    private int numberOfModems = 1;
    private int defaultKValue = 2;
    private int numberOfAllowedCollisions = 0;
    private double mutationRate = 0.1;
    public boolean multiThreading = false;
    public boolean showTextWhenRunning = false;
    
    private ArrayList<Chromosome> population = new ArrayList<>();
    private ArrayList<Double> history = new ArrayList<>();
    
    public void initializeAlgorithm(){
        
        if(showTextWhenRunning)
            System.out.println("\n ***** Initializing the Algorithim *****\n");

        for (int i = 0 ; i < geneticPopulation ; i++)
            population.add(new Chromosome(true));
            
    }
    
    public GeneticsAlgorithm( 
            Polygon ply ,
            int numberOfModems,
            int defaultKValue,
            int numberOfAllowedCollisions,
            int monteCarloItterations , 
            int geneticPopulation , 
            double mutationRate , 
            int numberOfGenerations,
            boolean multiThreading,
            boolean showTextWhenRunning ){
        
        this.ply = ply;
        this.numberOfModems = numberOfModems;
        this.defaultKValue = defaultKValue;
        this.numberOfAllowedCollisions = numberOfAllowedCollisions;
        this.monteCarloItterations = monteCarloItterations;
        this.geneticPopulation = geneticPopulation;
        this.mutationRate = mutationRate;
        this.numberOfGenerations = numberOfGenerations;
        this.population = new ArrayList<>();
        this.multiThreading = multiThreading;
        this.showTextWhenRunning = showTextWhenRunning;
        
        initializeAlgorithm();
        
    }
    
    public GeneticsAlgorithm(Polygon ply, GA_Config configs){
        
        this.ply = ply;
        this.numberOfModems = configs.getNumberOfModems();
        this.defaultKValue = configs.getDefaultKValue();
        this.numberOfAllowedCollisions = configs.getNumberOfAllowedCollisions();
        this.monteCarloItterations = configs.getMonteCarloItterations();
        this.geneticPopulation = configs.getGeneticPopulation();
        this.mutationRate = configs.getMutationRate();
        this.numberOfGenerations = configs.getNumberOfGenerations();
        this.multiThreading = configs.isMultiThreading();
        this.showTextWhenRunning = configs.isShowTextWhenRunning();
        this.population = new ArrayList<>();
        
        initializeAlgorithm();
        
        
    }
    
    private void sortChromosomes(){
        
        //might be a problem in accsending or decssending problem
        
        population.sort(Comparator.comparing(Chromosome::getFitness));
        Collections.reverse(population);
        if(showTextWhenRunning)
            System.out.printf("Best Chromosome:%f\tWorst Chromosome:%f\n" , population.get(0).getFitness() , population.get(population.size()-1).getFitness());
        
    }
    
    private void selection(){
        
        sortChromosomes();
        population = new ArrayList<>(population.subList(0, geneticPopulation));
//        Collections.reverse(population);
        
    }
    
    public void runGenetics(double threshold){
        
        
        for (int i = 0 ; i < numberOfGenerations ; i++){
            if(showTextWhenRunning)
                System.out.printf("\nItteration: %d\nCrossover Started\n" , i+1 );
            
            crossoverThePopulation();
            
            if(showTextWhenRunning)
                System.out.println("Crossover Done \nMutation Started");
            
            mutatePopulation();
            
            if(showTextWhenRunning)
                System.out.println("Mutation Done \nSelection Started");
            
            selection();
            
            if(showTextWhenRunning)
                System.out.printf("Selection Done \nBCF: %f \n\n**********************\n" , population.get(0).getFitness());
            
            history.add(population.get(0).getFitness());
            
            if (population.get(0).getFitness() >= threshold)
                break;
//            System.out.printf("\nItteration: %d\nMutation Started\n" , i+1 );
//            mutatePopulation();
//            System.out.println("Mutation Done \nCrossover Started");
//            crossoverThePopulation();
//            System.out.println("Crossover Done \nSelection Started");
//            selection();
//            System.out.printf("Selection Done \nBCF: %f \n\n**********************\n" , population.get(0).getFitness());
            
        }
        
        if(showTextWhenRunning){
            for (int i=0 ; i < 10 ; i++)
                System.out.printf("Chromosome %d Fitness: %f\n" , i , population.get(i).getFitness());
            System.out.println("\n************ End **********\n\n");
        }
            
    
    }
    
    private void crossoverThePopulation(){
        
        Collections.shuffle(population);
        int realSize = population.size();
        for (int i = 1 ; i < realSize ; i++)
            population.add( population.get(i).crossOver( population.get(i - 1) ) );
        
    }
    
    private void mutatePopulation(){
    
        int numberOfMutation = (int) (mutationRate * geneticPopulation);
        Collections.shuffle(population);
        
        for (int i = 0 ; i < numberOfMutation ; i++){
            Coordinate crdRandom = ply.randomPoint();
            Modem randomModem = new Modem(crdRandom.x , crdRandom.y , defaultKValue);
            int whichModem = new Random().nextInt(numberOfModems);
            
            population.get(i).modemList[whichModem] = randomModem;  
            population.get(i).setFitness();
            
        }
    
    }
    
    public double getRunFitness(){
        return population.get(0).getFitness();
    }

    
    
    
    
    public Modem[] getBestGene(){
        
        return population.get(0).modemList;
        
    }
    
    public ArrayList<Modem[]> getTopTenResults(){
        
        ArrayList<Modem[]> topTen = new ArrayList<>();
        
        for (int i = 0 ; i < 10 ; i++)
            topTen.add( population.get(i).modemList );
        
        return topTen;
    
    }   
    
    public Polygon getPly() {
        return ply;
    }

    public int getMonteCarloItterations() {
        return monteCarloItterations;
    }

    public int getGeneticPopulation() {
        return geneticPopulation;
    }

    public int getNumberOfGenerations() {
        return numberOfGenerations;
    }

    public int getNumberOfModems() {
        return numberOfModems;
    }

    public int getDefaultKValue() {
        return defaultKValue;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public ArrayList<Chromosome> getPopulation() {
        return population;
    }

    public ArrayList<Double> getHistory(){
        return history;
    }
    
    
    
    
    
    public void setPly(Polygon ply) {
        this.ply = ply;
    }

    public void setMonteCarloItterations(int monteCarloItterations) {
        this.monteCarloItterations = monteCarloItterations;
    }

    public void setGeneticPopulation(int geneticPopulation) {
        this.geneticPopulation = geneticPopulation;
    }

    public void setNumberOfGenerations(int numberOfGenerations) {
        this.numberOfGenerations = numberOfGenerations;
    }

    public void setNumberOfModems(int numberOfModems) {
        this.numberOfModems = numberOfModems;
    }

    public void setDefaultKValue(int defaultKValue) {
        this.defaultKValue = defaultKValue;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public void setPopulation(ArrayList<Chromosome> population) {
        this.population = population;
    }
    
    
    
    
    public class Chromosome{
        
        Modem[] modemList = new Modem[numberOfModems];
        double fitness;
        
        public Chromosome(boolean randomizeIt){
            
            if (randomizeIt)
                randomize();    
            
        }
        
        public void randomize(){
            
            for (int i = 0 ; i < numberOfModems ; i++ ){
                Coordinate temp = ply.randomPoint();
                modemList[i] = new Modem(temp.x, temp.y, defaultKValue);
            }
            
            setFitness();
            
        }
        
        public void setFitness(){
            if (multiThreading)
                fitness = VPCalculator.monteCarloVP_MT(monteCarloItterations, ply, modemList , numberOfAllowedCollisions);
            else
                fitness = VPCalculator.monteCarloVP(monteCarloItterations, ply, modemList , numberOfAllowedCollisions);
        }
        
        @Override
        public String toString(){
            String s = "";
            for (Modem m : modemList){
                s += String.format("X: %f  ,  Y:%f\n" , m.getCordinates().x , m.getCordinates().y);
            }
            return s;
        }
         
        public void mutate(){
            
        }
        
        public double getFitness(){
            return fitness;
        }
        
        public Chromosome crossOver(Chromosome chr1){
            
            //number of swaped modems can be random
            //int randNumber = new Random().nextInt(chr1.modemList.length);
            Chromosome newChr = new Chromosome(false);
            Modem[] modemList = new Modem[chr1.modemList.length];
            
            for (int i = 0 ; i < chr1.modemList.length ; i++){
            
                if ( i % 2 == 0 ){
                    //For getting it back to normal just remove all the line till firs else
                    
//                    Coordinate crd = new Coordinate();
//                    crd.x = (chr1.modemList[i].getCordinates().x + this.modemList[i].getCordinates().x) /2f;
//                    crd.y = (chr1.modemList[i].getCordinates().y + this.modemList[i].getCordinates().y) /2f;
//                    
//                    if( ply.getPoly().contains(new GeometryFactory().createPoint(crd)) )
//                        modemList[i] = new Modem(crd.x, crd.y ,defaultKValue);
//                    else
                        modemList[i] = chr1.modemList[i];
                    
                }
                else 
                    modemList[i] = this.modemList[i];
            
            }
            
            newChr.modemList = modemList;
            newChr.setFitness();
                   
            return newChr;
            
        }
        
    }
    
    
    
}
