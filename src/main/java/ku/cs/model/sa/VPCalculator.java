
package ku.cs.model.sa;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.shape.random.RandomPointsInGridBuilder;

public class VPCalculator {
    
    public static org.locationtech.jts.geom.Polygon unholedVisibilityPolygon(Polygon poly , Modem modem){
        
        LineString outterBoundery = poly.getPoly().getExteriorRing();
  
        /***** Finding the Critical Vertices *****/
        ArrayList<Coordinate> criticalVertices = criticalVerticesCalc(outterBoundery, modem);

        System.out.println(Arrays.toString(criticalVertices.toArray()));
        
        for (Coordinate cr : outterBoundery.getCoordinates() )
            System.out.println(cr);
        
        return null;
    
    }
    
    private static ArrayList<Coordinate> criticalVerticesCalc(LineString outterBoundery , Modem modem){
    
        ArrayList<Coordinate> criticalVertices = new ArrayList<>();
        int numberOfPoints = outterBoundery.getNumPoints();
        
        for( int i = 0 ; i < outterBoundery.getNumPoints() ; i++ ){
            
            Coordinate lineSegmentPoints[] = new Coordinate[2];
            lineSegmentPoints[0] = modem.getCordinates();
            lineSegmentPoints[1] = outterBoundery.getCoordinateN(i);
            
            LineSegment lineSegment = new LineSegment( lineSegmentPoints[0] , lineSegmentPoints[1]);
            int firstOrientation , secondOrientation;

            if ( i == 0 ){
                
               firstOrientation = lineSegment.orientationIndex( outterBoundery.getCoordinateN( numberOfPoints - 2 ));
               secondOrientation = lineSegment.orientationIndex( outterBoundery.getCoordinateN( i + 1 ) );
                          
            }
            
            else if ( i == numberOfPoints - 1){
                
               firstOrientation = lineSegment.orientationIndex( outterBoundery.getCoordinateN( i - 1 ));
               secondOrientation = lineSegment.orientationIndex( outterBoundery.getCoordinateN( 0 ) );
                
            }
            
            else{
                
               firstOrientation = lineSegment.orientationIndex( outterBoundery.getCoordinateN( i - 1 ));
               secondOrientation = lineSegment.orientationIndex( outterBoundery.getCoordinateN( i + 1 ) );
                
            }
            
            if ( firstOrientation == secondOrientation)
                   criticalVertices.add( outterBoundery.getCoordinateN(i) );
            
        }
        
        return criticalVertices;
        
    }

    public static double monteCarloVP_MT(int itterations , Polygon poly , Modem[] modem){
        
        Envelope exteriorBound = poly.getPoly().getEnvelope().getEnvelopeInternal();
        
        RandomPointsInGridBuilder rpigb = new RandomPointsInGridBuilder();
        rpigb.setNumPoints(1);
        rpigb.setExtent( exteriorBound );
        
        ArrayList<Coordinate> vPoly = new ArrayList<>();
        
        int numberOfInnerPoints = 0;
        long t = System.currentTimeMillis();

        ExecutorService exs = Executors.newFixedThreadPool(4);
        
        Callable<Integer> t1 = () -> {
            
            for(int i = 0 ; i < itterations / 4 ; i++ ){

                Coordinate radnomCord = rpigb.getGeometry().getCoordinate();
                Point radnomPoint = new GeometryFactory().createPoint(radnomCord);

                int numberOfVisibleModems = 0;

                for(Modem m : modem){

                    LineSegment ray = new LineSegment(m.getCordinates() , radnomCord);
                    LineString rayString = ray.toGeometry(new GeometryFactory());

                    Geometry gm = poly.getPoly().intersection(rayString);

                    if ( gm.getCoordinates().length - 2 <= m.getK() && poly.getPoly().contains(radnomPoint) )
                        numberOfVisibleModems++;

                }

                if (numberOfVisibleModems == 1 ) 
                    vPoly.add(new Coordinate( (int)radnomCord.x ,(int)radnomCord.y ) );
            }            
            
            return 0;
        };
        
        Callable<Integer> t2 = () -> {
            
            for(int i = itterations / 4 ; i < itterations / 2 ; i++ ){

                Coordinate radnomCord = rpigb.getGeometry().getCoordinate();
                Point radnomPoint = new GeometryFactory().createPoint(radnomCord);

                int numberOfVisibleModems = 0;

                for(Modem m : modem){

                    LineSegment ray = new LineSegment(m.getCordinates() , radnomCord);
                    LineString rayString = ray.toGeometry(new GeometryFactory());

                    Geometry gm = poly.getPoly().intersection(rayString);

                    if ( gm.getCoordinates().length - 2 <= m.getK() && poly.getPoly().contains(radnomPoint) )
                        numberOfVisibleModems++;

                }

                if (numberOfVisibleModems == 1 ) 
                    vPoly.add(new Coordinate( (int)radnomCord.x ,(int)radnomCord.y ) );
            }            
            
            return 0;
        };
        
        Callable<Integer> t3 = () -> {
            
            for(int i = itterations / 2 ; i < (3 * itterations / 4) ; i++ ){

                Coordinate radnomCord = rpigb.getGeometry().getCoordinate();
                Point radnomPoint = new GeometryFactory().createPoint(radnomCord);

                int numberOfVisibleModems = 0;

                for(Modem m : modem){

                    LineSegment ray = new LineSegment(m.getCordinates() , radnomCord);
                    LineString rayString = ray.toGeometry(new GeometryFactory());

                    Geometry gm = poly.getPoly().intersection(rayString);

                    if ( gm.getCoordinates().length - 2 <= m.getK() && poly.getPoly().contains(radnomPoint) )
                        numberOfVisibleModems++;

                }

                if (numberOfVisibleModems == 1 ) 
                    vPoly.add(new Coordinate( (int)radnomCord.x ,(int)radnomCord.y ) );
            }            
            
            return 0;
        };
        
        Callable<Integer> t4 = () -> {
            
            for(int i = (3 * itterations / 4) ; i < itterations ; i++ ){

                Coordinate radnomCord = rpigb.getGeometry().getCoordinate();
                Point radnomPoint = new GeometryFactory().createPoint(radnomCord);

                int numberOfVisibleModems = 0;

                for(Modem m : modem){

                    LineSegment ray = new LineSegment(m.getCordinates() , radnomCord);
                    LineString rayString = ray.toGeometry(new GeometryFactory());

                    Geometry gm = poly.getPoly().intersection(rayString);

                    if ( gm.getCoordinates().length - 2 <= m.getK() && poly.getPoly().contains(radnomPoint) )
                        numberOfVisibleModems++;

                }

                if (numberOfVisibleModems == 1 ) 
                    vPoly.add(new Coordinate( (int)radnomCord.x ,(int)radnomCord.y ) );
            }            
            
            return 0;
        };
        
        Future<Integer> f1 = exs.submit(t1);
        Future<Integer> f2 = exs.submit(t2);
        Future<Integer> f3 = exs.submit(t3);
        Future<Integer> f4 = exs.submit(t4);
        
        try{
            f1.get();
            f2.get();
            f3.get();
            f4.get();
            if (f1.isDone()) System.out.println("f1 done");
            if (f2.isDone()) System.out.println("f2 done");
            if (f3.isDone()) System.out.println("f3 done");
            if (f4.isDone()) System.out.println("f4 done");
            System.out.println((System.currentTimeMillis() - t) / 1000f);

        }
        catch(Exception ex){
            System.out.println(ex);
        }
        
        try {
            System.out.println("attempt to shutdown executor");
            exs.shutdown();
            exs.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        
        finally {
            if (!exs.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            exs.shutdownNow();
            System.out.println("shutdown finished");
        }
        
        String str = "MULTIPOINT(";
        for(Coordinate cr : vPoly)
            str += "(" + cr.x + " " + cr.y + "), ";
        
        str = str.substring(0, str.length() -3 ) + "))";
        System.out.println(str);
//        try {
//            BufferedWriter br = new BufferedWriter(new FileWriter("e:\\file.csv"));
//            br.write(str);
//            br.close();
//        } catch (IOException ex) {
//            Logger.getLogger(VPCalculator.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        return 0f;
    }

    public static double monteCarloVP(int itterations , Polygon poly , Modem[] modem){
        
//        Envelope exteriorBound = poly.getPoly().getEnvelope().getEnvelopeInternal();
//        
//        RandomPointsInGridBuilder rpigb = new RandomPointsInGridBuilder();
//        rpigb.setNumPoints(1);
//        rpigb.setExtent( exteriorBound );
        
        ArrayList<Coordinate> vPoly = new ArrayList<>();
        
        long t = System.currentTimeMillis();
        
        int nPointsInPolygon = 0 , nPointsInVisibilityPolygon = 0;
        
        for(int i = 0 ; i < itterations ; i++ ){

                Coordinate radnomCord = poly.randomPoint();
                Point radnomPoint = new GeometryFactory().createPoint(radnomCord);

                int numberOfVisibleModems = 0;
                if (poly.getPoly().contains(radnomPoint)){
                    nPointsInPolygon++ ;
                    
                    for(Modem m : modem){

                        LineSegment ray = new LineSegment(m.getCordinates() , radnomCord);
                        LineString rayString = ray.toGeometry(new GeometryFactory());

                        Geometry gm = poly.getPoly().intersection(rayString);

                        if ( gm.getCoordinates().length - 2 <= m.getK())
                            numberOfVisibleModems++;
    //
    //                    if ( numberOfIntersections(poly, rayString) <= m.getK() && poly.getPoly().contains(radnomPoint) )
    //                        numberOfVisibleModems++;

                    }

                    if (numberOfVisibleModems >= 1 ){ 
//                        vPoly.add(new Coordinate( (int)radnomCord.x ,(int)radnomCord.y ) );
                        nPointsInVisibilityPolygon++ ;
                    }
                }
        }
        
//        System.out.println((System.currentTimeMillis() - t) / 1000f);
//
//        String str = "MULTIPOINT(";
//        for(Coordinate cr : vPoly)
//            str += "(" + cr.x + " " + cr.y + "), ";
//        
//        str = str.substring(0, str.length() -3 ) + "))";
//        System.out.println(str);
//        
//        try {
//            BufferedWriter br = new BufferedWriter(new FileWriter("e:\\file.csv"));
//            br.write(str);
//            br.close();
//        } catch (IOException ex) {
//            Logger.getLogger(VPCalculator.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        return (nPointsInVisibilityPolygon / (double) nPointsInPolygon);
        
    }
    
    private static int numberOfIntersections(Polygon poly , LineString string){
        
        LineString shells[] = new LineString[ poly.getPoly().getNumInteriorRing() + 1 ];
        int count = 0;
        
        shells[0] = poly.getPoly().getExteriorRing();
        
        for (int i = 1 ; i < poly.getPoly().getNumInteriorRing() + 1 ; i++)
            shells[i] = poly.getPoly().getInteriorRingN(i-1);
        
        for (LineString ls : shells)
            if(ls.intersects(string))
                count++;
        
        return count;
    }

    public static double rayTracingVP(double minDegre , Polygon poly , Modem[] modem){
        
        
        
        
        return 0f;
    }
}
