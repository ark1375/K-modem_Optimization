
package ku.cs.model.sa;
import java.util.Random;

public class Main {
    
    public static void main(String args[]){
        
        Polygon pl = new Polygon();
        pl.readPolygonXML("E:\\Projects\\Projects 2020\\CS final project\\Model - SA\\Model_SA\\test_cases\\testcase3.xml");
//        VPCalculator.unholedVisibilityPolygon(pl, new Modem(210 , 130));
//        pl.plotPolygon();
//      
        VPCalculator.monteCarloVP(100000 , pl , new Modem[]{ new Modem(130 , 42 , 2) , new Modem(210 , 380 , 2) } );

    }
    
}
