
package ku.cs.model.sa;
import java.util.Arrays;
import java.util.Random;

public class Main {
    
    public static void main(String args[]){
        
        Polygon pl = new Polygon();
        String projectPath = "E:\\Projects\\Projects 2020\\CS final project\\Model - SA\\Model_SA\\test_cases";
        pl.readPolygonXML(projectPath + "\\obviouscase.xml");
        
        
        //(Polygon , NumberOfModems , DefaultKValue , MonteCarloItterations , Population , MutationRate , Generations)
        
        GeneticsAlgorithm gna = new GeneticsAlgorithm(pl, 3, 0, 500, 1000, 0.15, 30);
        gna.runGenetics();
        System.out.println(gna.getPopulation().get(0));
        System.out.println(gna.getPopulation().get(gna.getPopulation().size() -1));
        
        VPCalculator.monteCarloVP_SavePoints(10000 , pl , gna.getBestGene() , projectPath , true);
        
        for (Modem[] modems : gna.getTopTenResults())
            System.out.println(VPCalculator.monteCarloVP(10000 , pl , modems));

        System.out.println(Arrays.toString(gna.getBestGene()));
    }
    
}
