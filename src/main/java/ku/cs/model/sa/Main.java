
package ku.cs.model.sa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;


public class Main {
    
    public static void main(String args[]){
        
        Polygon pl = new Polygon();
        pl.readPolygonXML("E:\\Projects\\Projects 2020\\CS final project\\Model - SA\\Model_SA\\test_cases\\testcase1.xml");
        System.out.println(pl.isIsHoled());
        
    }
    
}
