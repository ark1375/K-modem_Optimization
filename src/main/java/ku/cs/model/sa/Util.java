
package ku.cs.model.sa;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.locationtech.jts.geom.Envelope;


public class Util {
    
    public static void benchmarkGenetics(
            int numberOfModems ,
            int penRate ,
            int numberOfAllowedCollisions ,
            int monteCarloItter ,
            double mutRate ,
            int population ,
            int generations ,
            boolean multiThreading,
            double threshold){
        
        String path = ".\\dataset\\wkt\\picked";
        File file = new File(path);
        String[] names = file.list();
        ArrayList<ArrayList<Double>> history = new ArrayList<>();
        
        
        
        for(String name : names){
            try{
                System.out.println(name);
                Polygon pl = new Polygon();
                pl.readPolygonWKT(path + "\\" + name);
    //            pl.plotPolygon();
                GeneticsAlgorithm gna;
                gna = new GeneticsAlgorithm(
                        pl, numberOfModems, penRate, numberOfAllowedCollisions , monteCarloItter, population, mutRate, generations , multiThreading , true
                );
                
                gna.runGenetics(threshold);
                history.add(gna.getHistory());
                
            } catch(Exception ex){
                System.out.println(ex);
            }
        }
        
        
        String savePath = ".\\becnhmark.csv";
        
        try {
            FileWriter fw = new FileWriter(new File(savePath));
            String out = "";
            for(ArrayList<Double> hs : history){
                out += hs.toString();
                out = out.substring(1, out.length() - 1);
                out += "\n";
            }
            fw.write(out);
            fw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public static void benchmarkMonteCarlo(int itterations){
        
        String path = ".\\dataset\\wkt\\picked";
        File file = new File(path);
        String[] names = file.list();
        ArrayList<ArrayList<Double>> history = new ArrayList<>();
        
        
        
        for(String name : names){
            try{
                
                System.out.println(name);
                Polygon pl = new Polygon();
                pl.readPolygonWKT(path + "\\" + name);
                Envelope exteriorBound = pl.getPoly().getEnvelope().getEnvelopeInternal();
                
                double actualRate = pl.getPoly().getArea() / exteriorBound.getArea();
                
                double monteCarloRate = VPCalculator.monteCarloBenchmark(pl, itterations);
                
                ArrayList<Double> row = new ArrayList<>();
                row.add(actualRate);
                row.add(monteCarloRate);
                history.add(row);
                
            } catch(Exception ex){
                System.out.println(ex);
            }
        }
        
        
        String savePath = ".\\montecarlo_becnhmark.csv";
        
        try {
            FileWriter fw = new FileWriter(new File(savePath));
            String out = "";
            for(ArrayList<Double> hs : history){
                out += hs.toString();
                out = out.substring(1, out.length() - 1);
                out += "\n";
            }
            fw.write(out);
            fw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static ArrayList<Modem[]> K_ModmSolver(
            Polygon ply,
            OW_Config ow_configs,
            GA_Config ga_configs,
            double coverageThreshold,
            int minNumberOfModem,
            int maxNumberOfModem,
            boolean allowCollision,
            boolean haltIfFitnessDecrease){
        
        ArrayList<Modem[]> history = new ArrayList<>();
        
        double previousFitness = 0;
        
        System.out.println("\n\n::: Starting K-Modem Solve  :::");
        for(int i = minNumberOfModem ; i < maxNumberOfModem ; i++){
            
            double currentFitness = 0;
            
            System.out.printf("\nRun for %d Modems\n" , i);
            
            if(i == 1){
                OptimizedWalk ow = new OptimizedWalk(ply, ow_configs);
                ow.optimizePopulation();
                currentFitness =  ow.getTopTenAgents().get(0).getCurrentFitness();
                history.add( new Modem[]{ ow.getTopTen().get(0) } );
            }
            
            else{
                
                ga_configs.setNumberOfModems(i);
                
                if (allowCollision)
                    ga_configs.setNumberOfAllowedCollisions(i);
                else
                    ga_configs.setNumberOfAllowedCollisions(0);
                
                GeneticsAlgorithm ga = new GeneticsAlgorithm(ply, ga_configs);
                ga.runGenetics(coverageThreshold);
                currentFitness = ga.getRunFitness();
                
                if (haltIfFitnessDecrease && previousFitness > currentFitness){
                    System.out.println("\n\nFitness Decreased, Algorithm Ended!!");
                    return history;
                }
                
                history.add( ga.getBestGene() );
                
            }
            
            System.out.printf("Fitness: %f\n======================" , currentFitness);
            
            if(currentFitness >= coverageThreshold)
                return history;
            
            previousFitness = currentFitness;
            
        }
        
        System.out.println("Algorithm Ended Witout Finding Suitable Cordinates!!");
        return history;
        
    }
    
    
}
