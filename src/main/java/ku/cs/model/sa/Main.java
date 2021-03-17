
package ku.cs.model.sa;
import java.util.Random;

public class Main {
    
    public static void main(String args[]){
        
        Polygon pl = new Polygon();
        pl.readPolygonXML("E:\\Projects\\Projects 2020\\CS final project\\Model - SA\\Model_SA\\test_cases\\testcase1.xml");
//        VPCalculator.unholedVisibilityPolygon(pl, new Modem(210 , 130));
//        pl.plotPolygon();

        System.out.println(VPCalculator.monteCarloVP(10000 , pl , new Modem[]{ new Modem(196.666959 , 213.300185 , 2) , new Modem(403.612889 , 6.173115 , 2) }));

//
//        GeneticsAlgorithm gna = new GeneticsAlgorithm(pl, 2, 2, 100, 500, 0.05, 10);
//        gna.runGenetics();
//        System.out.println(gna.getPopulation().get(0));
//        System.out.println(gna.getPopulation().get(gna.getPopulation().size() -1));
    }
    
}
