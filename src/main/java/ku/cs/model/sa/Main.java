
package ku.cs.model.sa;
import java.util.Arrays;
import java.util.Random;
import org.locationtech.jts.geom.Coordinate;

public class Main {
    
    public static void main(String args[]){
        
//      ###################     Test Genetic Algorithm Section    ###################
        

        Polygon pl = new Polygon();
        String repoPath = ".\\dataset\\Picked";
        pl.readPolygonWKT(repoPath + "\\87.wkt");
//        OW_Config owconf = new OW_Config(0, 10, 20 , 0.2, 1000, 100, 20, 0.015, true);
//        System.out.println(owconf + "\n");
//        
//        OptimizedWalk op = new OptimizedWalk(pl , owconf);
//        
//        op.optimizePopulation();
//        VPCalculator.monteCarloVP_SavePoints(10000, pl, new Modem[]{ op.getTopTen().get(0)} , 0 , "./" , false);
//        System.out.printf("Best Agent Fitness %f\n\n" , op.getTopTenAgents().get(0).getCurrentFitness());
//        System.out.printf("MULTIPOINT(");
//        for (Coordinate crd : op.getTopTenAgents().get(0).history){
//            System.out.printf("(%f %f),\n" , crd.x ,crd.y);
//        }
//        
//        VPCalculator.monteCarloVP_MT(1000000, pl, new Modem[]{new Modem(178, 750 , 0)});          ::::DEBUG LINE::::
//        VPCalculator.monteCarloVP_SavePoints(10000, pl, new Modem[]{new Modem(178, 750 , 0) , new Modem(45, 368 , 0)} , 0 , "./" , false);  ::::DEBUG LINE::::
//        
//        //(Polygon , NumberOfModems , DefaultKValue , MonteCarloItterations , Population , MutationRate , Generations)
//        
//        GeneticsAlgorithm gna = new GeneticsAlgorithm(pl, 2, 0, 0 , 1000, 200, 0.25, 10,true);
//        gna.runGenetics(0.98);
//        System.out.println(gna.getPopulation().get(0));
//        System.out.println(gna.getPopulation().get(gna.getPopulation().size() -1));
////        
//        VPCalculator.monteCarloVP_SavePoints(10000 , pl , gna.getBestGene() , repoPath , true);
//        
//        for ( int i = 0 ; i < 10 ; i++){
//            double coverage = VPCalculator.monteCarloVP(10000 , pl , gna.getTopTenResults().get(i));
//            System.out.printf("Acurate Coverage Chromosome %d: %f \n", i , coverage);
//        
//        }
//        
//        String bestGene  = Arrays.toString(gna.getBestGene());
//        System.out.println("\nMULTIPOINT("+ bestGene.substring(1 , bestGene.length() -2) +")");
//        

    //      ###################  Benchmark Section   ###################
    
//        Util.benchmarkGenetics(2 , 0 , 0 , 1000 , 0.25 , 200 , 20 , true , 0.96);
//        Util.benchmarkMonteCarlo(10000);
        
        
    }
    
}
