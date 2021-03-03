
package ku.cs.model.sa;


public class Main {
    
    public static void main(String args[]){
        
        Polygon pl = new Polygon();
        pl.readPolygonXML("E:\\Projects\\Projects 2020\\CS final project\\Model - SA\\Model_SA\\test_cases\\testcase1.xml");
        System.out.println(pl.isIsHoled());
        pl.plotPolygon();
        
    }
    
}
