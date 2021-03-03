
package ku.cs.model.sa;


public class Main {
    
    public static void main(String args[]){
        
        Polygon pl = new Polygon();
        pl.readPolygonXML("E:\\Projects\\Projects 2020\\CS final project\\Model - SA\\Model_SA\\test_cases\\articleTest.xml");
        VPCalculator.unholedVisibilityPolygon(pl, new Modem(210 , 130));
        pl.plotPolygon();
        
    }
    
}
