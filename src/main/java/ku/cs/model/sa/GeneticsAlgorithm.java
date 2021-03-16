
package ku.cs.model.sa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import org.locationtech.jts.geom.Coordinate;

public class GeneticsAlgorithm {
    
    private Polygon ply;
    private int monteCarloItterations = 10000;
    private int geneticPopulation = 1000;
    private int numberOfGenerations = 100;
    private int numberOfModems = 1;
    private int defaultKValue = 2;
    private double mutationRate = 0.1;
    private ArrayList<Chromosome> population = new ArrayList<>();
    
    public void initializeAlgorithm(){
        
        System.out.println("\n ***** Initializing the Algorithim *****\n");

        for (int i = 0 ; i < geneticPopulation ; i++)
            population.add(new Chromosome(true));
            
    }
    
    public GeneticsAlgorithm( 
            Polygon ply ,
            int numberOfModems,
            int defalutKValue,
            int monteCarloItterations , 
            int geneticPopulation , 
            double mutationRate , 
            int numberOfGenerations ){
        
        this.ply = ply;
        this.monteCarloItterations = monteCarloItterations;
        this.geneticPopulation = geneticPopulation;
        this.numberOfGenerations = numberOfGenerations;
        this.numberOfModems = numberOfModems;
        this.defaultKValue = defalutKValue;
        this.mutationRate = mutationRate;
        this.population = new ArrayList<>();
        
        initializeAlgorithm();
        
    }
    
    private void sortChromosomes(){
        
        //might be a problem in accsending or decssending problem
        
        population.sort(Comparator.comparing(Chromosome::getFitness));
//        Collections.reverse(population);
        
    }
    
    private void selection(){
        
        sortChromosomes();
        population = new ArrayList<Chromosome>(population.subList(0, geneticPopulation));
        Collections.reverse(population);
        
    }
    
    public void runGenetics(){
        
        for (int i = 0 ; i < numberOfGenerations ; i++){
            System.out.printf("Itteration: %d\nCrossover Started\n" , i+1 );
            crossoverThePopulation();
            System.out.println("Crossover Done \nSelection Started");
            //mutate line
            selection();
            System.out.printf("Selection Done \nBCF: %f \n**********************\n" , population.get(0).getFitness());
        }
    
    }
    
    private void crossoverThePopulation(){
        
        Collections.shuffle(population);
        int realSize = population.size();
        for (int i = 1 ; i < realSize ; i++)
            population.add( population.get(i).crossOver( population.get(i - 1) ) );
        
    }
    
    private void mutatePopulation(){}

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
            fitness = VPCalculator.monteCarloVP(monteCarloItterations, ply, modemList);
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
            
            for (int i = 0 ; i < chr1.modemList.length ; i++)
                if ( i % 2 == 0 )
                    modemList[i] = chr1.modemList[i];
                else 
                    modemList[i] = this.modemList[i];
            
            newChr.modemList = modemList;
            newChr.setFitness();
                   
            return newChr;
            
        }
        
    }
    
    
    
}
