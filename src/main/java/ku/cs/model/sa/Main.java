
package ku.cs.model.sa;
import java.util.Random;

public class Main {
    
    public static void main(String args[]){
        
        Polygon pl = new Polygon();
        pl.readPolygonXML("E:\\Projects\\Projects 2020\\CS final project\\Model - SA\\Model_SA\\test_cases\\testcase3.xml");
//        VPCalculator.unholedVisibilityPolygon(pl, new Modem(210 , 130));
//        pl.plotPolygon();
//      
//        System.out.println(VPCalculator.monteCarloVP(10000 , pl , new Modem[]{ new Modem(130 , 42 , 8) , new Modem(210 , 380 , 4) } ));
        GeneticsAlgorithm gna = new GeneticsAlgorithm(pl, 2, 2, 500, 500, 0, 20);
        gna.runGenetics();
        System.out.println(gna.getPopulation().subList(0, 10));
    }
    
}
