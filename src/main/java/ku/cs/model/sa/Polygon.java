
package ku.cs.model.sa;
import ku.cs.model.sa.graphics.PlotFrame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.awt.PolygonShape;
import java.util.ArrayList;

public class Polygon {

    private org.locationtech.jts.geom.Polygon poly;
    private boolean isHoled;
    private double  area = -1;

    public Polygon(){
        
    }
    
    public Polygon(String path){
        
        readPolygonXML(path);
        
    }
    
    public boolean readPolygonWKT(String path){
        
        WKTReader wktr = new WKTReader();
        try {
            
            Geometry gm = wktr.read(new FileReader(path));
            
            if(gm.getGeometryType() == "Polygon"){
                poly = (org.locationtech.jts.geom.Polygon) gm;
                area = poly.getArea();
                isHoled = !(poly.getNumInteriorRing() == 0);
            }
            
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }
    
    public boolean readPolygonXML(String path){
    
        String data = readXMLFile(path);
        
        if (data == null) return false;
        
        WKTReader wktr = new WKTReader();
        
        try {
            Geometry gm = wktr.read(data);
            poly = (org.locationtech.jts.geom.Polygon) gm;
            area = poly.getArea();
            isHoled = !(poly.getNumInteriorRing() == 0);
            
            return true;
            
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }        
    
    }
    
    private static String readXMLFile(String path){
            File XML_File = new File(path);

            if( XML_File.exists() ){

                String fileExtension = XML_File.getName().substring(XML_File.getName().length() - 3 , XML_File.getName().length());

                if ( fileExtension.equals("xml") ){
                    try {
                        BufferedReader bfr = new BufferedReader(new FileReader(XML_File));
                        String data = "";

                        while (!bfr.readLine().equals("  <a>"))
                            continue;

                        while(true){
                            
                            String buffer = bfr.readLine();
                            if (buffer.equals("    </a>"))
                                break;
                            
                            data += buffer + "\n";
                        }

                        return data;
                    } catch (Exception ex){
                            System.out.println(ex);
                    }
                }
            }
            
        return null;
        
    }

    public static boolean convertXMLFile(String path){
        
        File XML_File = new File(path);
        String data = readXMLFile(path);
        
        if ( data != null ){

            String newFileName = XML_File.getParent() +"\\"+ XML_File.getName().replace("xml", "wkt");
            try{
            File newWKTFile = new File(newFileName);
            newWKTFile.createNewFile();

            FileWriter fw = new FileWriter(newWKTFile);
            fw.write(data);
            fw.close();

            return true;
            }catch (Exception ex){
                System.out.println(ex);
            }

        }
        
        
        return false;
    }
    
    /*********************Plot*********************************************/
    
    public boolean plotPolygon(){
        
        if (poly != null){
            org.locationtech.jts.geom.Polygon pl_test = (org.locationtech.jts.geom.Polygon) poly.copy();
            
            pl_test.apply(new org.locationtech.jts.geom.util.AffineTransformation().setToReflection(1, 0));
            pl_test.apply(new org.locationtech.jts.geom.util.AffineTransformation().setToTranslation(30, 500));
            
            ArrayList<Coordinate[]> innerHoles = new ArrayList<>();
            
            for(int i=0 ; i <  pl_test.getNumInteriorRing() ; i++)
                innerHoles.add( pl_test.getInteriorRingN(i).getCoordinates());
            
            PolygonShape ps = new PolygonShape( pl_test.getExteriorRing().getCoordinates() , innerHoles);
            
            PlotFrame plt = new PlotFrame(ps);
            
            return true;
            
        }
        
        return false;
        
    }
    
    /*********************Setters and Getters******************************/
    
    public org.locationtech.jts.geom.Polygon getPoly() {
        return poly;
    }

    public boolean isIsHoled() {
        return !(poly.getNumInteriorRing() == 0);
    }

    public double getArea() {
        return poly.getArea();
    }

    public void setPoly(org.locationtech.jts.geom.Polygon poly) {
        this.poly = poly;
    }
    
}

