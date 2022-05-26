
package ku.cs.model.sa;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.locationtech.jts.geom.Envelope;
import java.lang.Math;

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

    public static void benchmarkChromatic(String benchmarkName , int defaultK, int monteCarloItter, double acceptedFitness){

        double mutationRate = 0.25;
        int geneticPopulation = 300;
        int owSelectedPopulation = 100;
        int generationDefault = 30;
        int owMovingThreshold = 20;
        double owAgentPace = 15;
        double owDeteriationRate = 0.05;



        String savePathBenchmark = ".\\"+ benchmarkName +".csv";
        String path = ".\\dataset\\picked";
        File file = new File(path);
        String[] names = file.list();
//        ArrayList<ArrayList<Double>> history = new ArrayList<>();

        for(String name: names){

            System.out.println("Benchmarking on Poly #" + name);
            System.out.println("______________________________");
            ArrayList<Double> row = new ArrayList<>();
            ArrayList<Double> bestFitCordinate = new ArrayList<>();

            Polygon poly = new Polygon();
            poly.readPolygonWKT(path + "\\" + name);

            row.add(
                    (double)poly.getPoly().getNumPoints()
            );

            for (int numberOfModem = 1 ; numberOfModem <= poly.getPoly().getNumPoints() / 3  ; numberOfModem++ ){
                System.out.println("Current Modem Test = " + Integer.toString(numberOfModem));
                double bestFitness = 0;

                if (numberOfModem == 1){
                    OW_Config config = new OW_Config(
                            defaultK,
                            owMovingThreshold,
                            owAgentPace,
                            owDeteriationRate,
                            monteCarloItter,
                            5000,
                            owSelectedPopulation,
                            0.01,
                            true ,
                            true
                    );



                    OptimizedWalk ow = new OptimizedWalk(poly, config);
                    ow.optimizePopulation();

                    bestFitness = Math.max( ow.getTopTenAgents().get(0).currentFitness , ow.getTopTenAgents().get(9).currentFitness) ;

                }


                else {

                    GA_Config config = new GA_Config(
                            monteCarloItter,
                            geneticPopulation,
                            generationDefault,
                            numberOfModem,
                            defaultK,
                            0,
                            mutationRate,
                            true,
                            true);

                    GeneticsAlgorithm ga = new GeneticsAlgorithm(poly , config);
                    ga.runGenetics(acceptedFitness);
                    bestFitness = ga.getRunFitness();

                }

                row.add(bestFitness);
            }

//            history.add(row);

            try {
                FileWriter fw = new FileWriter(new File(savePathBenchmark),true);
                String out = name + ",";

                String rowOut = row.toString();
                rowOut = rowOut.substring(1,rowOut.length() - 1);
                out += rowOut + "\n";
                fw.append(out);
                fw.close();

            } catch (IOException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }

        }



    }

    private static void benchmarkMonteCarloSingleIter(int itterations, String benchmarkName){
        
        String path = ".\\dataset\\MonteCarloBenchmark";
        File file = new File(path);
        String[] names = file.list();
        ArrayList<ArrayList<Double>> history = new ArrayList<>();
        
        
        
        for(String name : names){
            try{
                
//                System.out.println(name);
                Polygon pl = new Polygon();
                pl.readPolygonWKT(path + "\\" + name);
                Envelope exteriorBound = pl.getPoly().getEnvelope().getEnvelopeInternal();
                
                double actualRatio = pl.getPoly().getArea() / exteriorBound.getArea();
                
                double monteCarloRatio = VPCalculator.monteCarloBenchmark(pl, itterations);
                
                ArrayList<Double> row = new ArrayList<>();
                row.add(actualRatio);
                row.add(monteCarloRatio);
                history.add(row);
                
            } catch(Exception ex){
                System.out.println(ex);
            }
        }
        
        
        String savePath = ".\\"+ benchmarkName +".csv";
        
        try {
            FileWriter fw = new FileWriter(new File(savePath));
            String out = "";
            for(ArrayList<Double> hs : history){
                String rowOut = hs.toString();
                rowOut = rowOut.substring(1,rowOut.length() - 1);
                out += rowOut + "\n";
            }
            fw.write(out);
            fw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static void benchmarkMonteCarlo(String resultName){

        int[] itterations = {10,100,1000,10000,100000,1000000};

        for( int iter : itterations){
            System.out.println("Benchmarking MonteCarlo itteraion: " + Integer.toString(iter));
            benchmarkMonteCarloSingleIter(iter, resultName + "_" + Integer.toString(iter));
        }



    }

    public static void benchmarkRapidMonteCarlo(String benchmarkName, int bound, int step , int repeats){

        String path = ".\\dataset\\MonteCarloBenchmark";
        File file = new File(path);
        String[] names = file.list();
        ArrayList<Double> history = new ArrayList<>();


        for(int itterations = 100; itterations <= bound ; itterations+=step ) {

            System.out.println("Benchmarking on itteration: " + Integer.toString(itterations));
            int sampleCounter = 0;
            double totalFitness = 0;

            for (String name : names) {

                double averageCalculatedRatio = 0;

                for(int i = 0 ; i < repeats ; i++){
                    try {

                        //                System.out.println(name);
                        Polygon pl = new Polygon();
                        pl.readPolygonWKT(path + "\\" + name);
                        Envelope exteriorBound = pl.getPoly().getEnvelope().getEnvelopeInternal();

                        double actualRatio = pl.getPoly().getArea() / exteriorBound.getArea();
                        double monteCarloRatio = VPCalculator.monteCarloBenchmark(pl, itterations);

                        averageCalculatedRatio += Math.abs(actualRatio - monteCarloRatio);

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
                averageCalculatedRatio /= repeats;

                totalFitness += averageCalculatedRatio;
                sampleCounter++;

            }

            totalFitness /= sampleCounter;
            history.add(totalFitness);

        }

        String savePath = ".\\"+ benchmarkName +".csv";

        try {
            FileWriter fw = new FileWriter(new File(savePath));
            String out = "";
            for(Double hs : history){
                String rowOut = Double.toString(hs) + ",";
                out += rowOut + "\n";
            }
            fw.write(out);
            fw.close();

        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void timeMonteCarlo(String benchmarkName , int bound , int step , int repeats){

        String path = ".\\dataset\\MonteCarloBenchmark";
        File file = new File(path);
        String[] names = file.list();
        ArrayList<Double> history = new ArrayList<>();


        for(int itterations = 10 ; itterations <= bound ; itterations *= step ) {

            System.out.println("Benchmarking on itteration: " + Integer.toString(itterations));

            double totalAverageTime = 0;
            int counts = 0;

            for (String name : names) {

                double averageTime = 0;

                for(int i = 0 ; i < repeats ; i++){
                    try {

                        Polygon pl = new Polygon();
                        pl.readPolygonWKT(path + "\\" + name);
                        Envelope exteriorBound = pl.getPoly().getEnvelope().getEnvelopeInternal();

                        double actualRatio = pl.getPoly().getArea() / exteriorBound.getArea();

                        long startTime = System.nanoTime();
                        double monteCarloRatio = VPCalculator.monteCarloBenchmark(pl, itterations);
                        long duration = System.nanoTime() - startTime;

                        averageTime += duration;

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }

                }
                averageTime /= repeats;


                totalAverageTime += averageTime;
                counts++;

            }

            totalAverageTime /= counts;
            history.add(totalAverageTime);

        }

        String savePath = ".\\"+ benchmarkName +".csv";

        try {
            FileWriter fw = new FileWriter(new File(savePath));
            String out = "";
            for(Double hs : history){
                String rowOut = Double.toString(hs) + ",";
                out += rowOut + "\n";
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
