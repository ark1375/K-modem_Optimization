
package ku.cs.model.sa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import java.util.Random;
import java.lang.Math;
import org.locationtech.jts.geom.Point;

/* Test this Class*/

public class OptimizedWalk {
    
    private Polygon poly;
    private int defaultK;
    private int movingThreshold;
    private double agentPace;
    private double deteriorationRate;
    private int monteCarloItterations;
    private int numberOfPopulation;
    private double monteCarloErrorThreshold ;
    private ArrayList<Agent> population = new ArrayList<>();

    public OptimizedWalk(
            Polygon poly,
            int defaultK,
            int movingThreshold,
            double agentPace,
            double deteriorationRate,
            int monteCarloItterations,
            int numberOfPopulation,
            double monteCarloErrorThreshold){
        
        this.poly = poly;
        this.defaultK = defaultK;
        this.movingThreshold = movingThreshold;
        this.agentPace = agentPace;
        this.deteriorationRate = deteriorationRate;
        this.monteCarloItterations = monteCarloItterations;
        this.numberOfPopulation = numberOfPopulation;
        this.monteCarloErrorThreshold = monteCarloErrorThreshold;
        
        for(int i = 0; i < numberOfPopulation ; i++){
            population.add(new Agent());
//            System.out.printf("Agent #%d Added\n",i);
        }
    }
    
    public void optimizePopulation(){
        System.out.println("Begin Optimization with OptimizedWalk");
        for (int i = 0 ; i < this.numberOfPopulation ; i++){
            System.out.printf("Optimizing Agent #%d\n  Starting fitness: %f" , i+1 ,population.get(i).currentFitness );
            population.get(i).optimize();
            System.out.printf(" -> End Fitness %f\n_________________________\n\n",population.get(i).currentFitness);            
        }
        
    }
    
    public ArrayList<Modem> getTopTen(){
        population.sort(Comparator.comparing(OptimizedWalk.Agent::getCurrentFitness));
        Collections.reverse(population);

        ArrayList<Modem> topTen = new ArrayList<>();
        
        for(int i=0 ; i < 10 ; i++)
            topTen.add(population.get(i).toModem());
        
        return topTen;
        
    }
    
    public ArrayList<Agent> getTopTenAgents(){
        population.sort(Comparator.comparing(OptimizedWalk.Agent::getCurrentFitness));
        Collections.reverse(population);

        ArrayList<Agent> topTen = new ArrayList<>();
        
        for(int i=0 ; i < 10 ; i++)
            topTen.add(population.get(i));
        
        return topTen;
        
    }

    public class Agent{
        
        Coordinate cord;
        double currentFitness = 0;
        int numberOfBadMoves = 0;
        boolean isActive = true;
        double currentPace = agentPace;
        
        ArrayList<Coordinate> history = new ArrayList<>();
        
        public Agent(){
            initialize();
        }
        
        private void initialize(){
            this.cord = poly.randomPoint();
            currentFitness = VPCalculator.monteCarloVP_MT(monteCarloItterations, poly, new Modem[]{new Modem(cord.x , cord.y , defaultK)}, 0);
            history.add(cord);
        }
       
        
        private void walk(){
            
            if (isActive){
                
                boolean validMove = false;
                
                while(!validMove){
                    double direction = new Random().nextDouble() * 360;
                    
                    double moveInX = currentPace * Math.cos(Math.toRadians(direction));
                    double moveInY = currentPace * Math.sin(Math.toRadians(direction));
                    Coordinate newCord = new Coordinate(cord.x + moveInX , cord.y + moveInY);
                    
                    Point radnomPoint = new GeometryFactory().createPoint(newCord);
                    if( !poly.getPoly().contains(radnomPoint) )
                        continue;
                    
                    else{
                        validMove = true;
                        double movedFitness = VPCalculator.monteCarloVP_MT(
                            monteCarloItterations, poly, new Modem[]{new Modem(newCord.x , newCord.y , defaultK)}, 0);
                        
                        if (movedFitness - monteCarloErrorThreshold >= currentFitness || movedFitness + monteCarloErrorThreshold >= currentFitness){
                            currentFitness = movedFitness;
                            cord = newCord;
                            numberOfBadMoves = 0;
                            history.add(newCord);
                            deteriate();
//                            System.out.printf("%f   %f,\n" , cord.x , cord.y);
                        }
                        
                        else{
                            numberOfBadMoves ++;
                            checkDeactivation();
                        }
                            
                    }
                
                }
            }
            
        }
        
        public void optimize(){
            while(this.isActive)
                walk();
        }
        
        public void checkDeactivation(){
            if (isActive && numberOfBadMoves >= movingThreshold)
                isActive = false;
        }
        
        public void resetAgent(){
            history.clear();
            initialize();
        }
        
        private void deteriate(){
            this.currentPace -= deteriorationRate * this.currentPace;
        }
        
        public double getCurrentFitness(){
            return currentFitness;
        }
        
        public Modem toModem(){
            return new Modem(cord.x, cord.y, defaultK);
        }
        
    }
    




}


