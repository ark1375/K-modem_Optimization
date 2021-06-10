
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
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.locationtech.jts.shape.random.RandomPointsInGridBuilder;

public class VPCalculator {
    
    static int totalNumberOfPoints = 0;
    
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
    
    
    public static double monteCarloVP_MT(int itterations , Polygon poly , Modem[] modem , int numberOfAllowedCollisions){

        int totalVisiblePoints = 0;
        
        long t = System.currentTimeMillis();

        ExecutorService exs = Executors.newFixedThreadPool(4);
        
        Callable<Integer> t1 = () -> {
            
            int nPointsInVisibilityPolygon = 0;
            
            for(int i = 0 ; i < itterations / 4 ; i++ ){

                Coordinate radnomCord = poly.randomPoint();
                Point radnomPoint = new GeometryFactory().createPoint(radnomCord);
//                totalNumberOfPoints++;
                
                int numberOfVisibleModems = 0;

                for(Modem m : modem){

                    LineSegment ray = new LineSegment(m.getCordinates() , radnomCord);
                    LineString rayString = ray.toGeometry(new GeometryFactory());
//
//                        Geometry gm = poly.getPoly().intersection(rayString);
//
//                        if ( gm.getCoordinates().length - 2 <= m.getK())
//                            numberOfVisibleModems++;
//    
                    if ( numberOfIntersections(poly, ray) <= m.getK() )
                        numberOfVisibleModems++;

                }

                if (numberOfVisibleModems > 0 && numberOfVisibleModems <= numberOfAllowedCollisions + 1 )
                    nPointsInVisibilityPolygon++ ;

            }            
            
            return nPointsInVisibilityPolygon;
        };
        
        Callable<Integer> t2 = () -> {
            
            int nPointsInVisibilityPolygon = 0 , numberOfInvestigatedPoint = 0;
            
            for(int i = itterations / 4 ; i < itterations / 2 ; i++ ){

                Coordinate radnomCord = poly.randomPoint();
                Point radnomPoint = new GeometryFactory().createPoint(radnomCord);
//                totalNumberOfPoints++;
                
                int numberOfVisibleModems = 0;

                for(Modem m : modem){

                    LineSegment ray = new LineSegment(m.getCordinates() , radnomCord);
                    LineString rayString = ray.toGeometry(new GeometryFactory());
//
//                        Geometry gm = poly.getPoly().intersection(rayString);
//
//                        if ( gm.getCoordinates().length - 2 <= m.getK())
//                            numberOfVisibleModems++;
//    
                    if ( numberOfIntersections(poly, ray) <= m.getK() )
                        numberOfVisibleModems++;

                }

                if (numberOfVisibleModems > 0 && numberOfVisibleModems <= numberOfAllowedCollisions + 1)
                    nPointsInVisibilityPolygon++ ;

            }            
            
            return nPointsInVisibilityPolygon;
            
        };
        
        Callable<Integer> t3 = () -> {
            
            int nPointsInVisibilityPolygon = 0 , numberOfInvestigatedPoint = 0;
            
            for(int i = itterations / 2 ; i < 3 * itterations / 4 ; i++ ){

                Coordinate radnomCord = poly.randomPoint();
                Point radnomPoint = new GeometryFactory().createPoint(radnomCord);
//                totalNumberOfPoints++;
                
                int numberOfVisibleModems = 0;

                for(Modem m : modem){

                    LineSegment ray = new LineSegment(m.getCordinates() , radnomCord);
                    LineString rayString = ray.toGeometry(new GeometryFactory());
//
//                        Geometry gm = poly.getPoly().intersection(rayString);
//
//                        if ( gm.getCoordinates().length - 2 <= m.getK())
//                            numberOfVisibleModems++;
//    
                    if ( numberOfIntersections(poly, ray) <= m.getK() )
                        numberOfVisibleModems++;

                }

                if (numberOfVisibleModems > 0 && numberOfVisibleModems <= numberOfAllowedCollisions + 1 )
                    nPointsInVisibilityPolygon++ ;

            }            
            
            return nPointsInVisibilityPolygon;
            
        };
        
        Callable<Integer> t4 = () -> {
            
            int nPointsInVisibilityPolygon = 0 , numberOfInvestigatedPoint = 0;
            
            for(int i = 3 * itterations / 4 ; i < itterations ; i++ ){

                Coordinate radnomCord = poly.randomPoint();
                Point radnomPoint = new GeometryFactory().createPoint(radnomCord);
//                totalNumberOfPoints++;
                
                int numberOfVisibleModems = 0;

                for(Modem m : modem){

                    LineSegment ray = new LineSegment(m.getCordinates() , radnomCord);
                    LineString rayString = ray.toGeometry(new GeometryFactory());
//
//                        Geometry gm = poly.getPoly().intersection(rayString);
//
//                        if ( gm.getCoordinates().length - 2 <= m.getK())
//                            numberOfVisibleModems++;
//    
                    if ( numberOfIntersections(poly, ray) <= m.getK() )
                        numberOfVisibleModems++;

                }

                if (numberOfVisibleModems > 0 && numberOfVisibleModems <= numberOfAllowedCollisions + 1 )
                    nPointsInVisibilityPolygon++ ;

            }            
            
            return nPointsInVisibilityPolygon;
            
        };
        
        Future<Integer> f1 = exs.submit(t1);
        Future<Integer> f2 = exs.submit(t2);
        Future<Integer> f3 = exs.submit(t3);
        Future<Integer> f4 = exs.submit(t4);
        
        try{
            totalVisiblePoints += f1.get();
            totalVisiblePoints += f2.get();
            totalVisiblePoints += f3.get();
            totalVisiblePoints += f4.get();
        }
        catch(Exception ex){
        }
        
        try {
            exs.shutdown();
            exs.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
        }
        
        finally {
            if (!exs.isTerminated()) {
            }
            exs.shutdownNow();
        }
//        System.out.println(itterations);
//        System.out.println(totalVisiblePoints);
//        System.out.println(totalNumberOfPoints);
        
        double returnedValue = totalVisiblePoints / (double) itterations;
//        totalNumberOfPoints = 0;
        
        return returnedValue;
        
    }

    public static double monteCarloVP(int itterations , Polygon poly , Modem[] modem , int numberOfAllowedCollisions){
                
        long t = System.currentTimeMillis();
        
        int nPointsInPolygon = 0 , nPointsInVisibilityPolygon = 0;
        
        for(int i = 0 ; i < itterations ; i++ ){

                Coordinate radnomCord = poly.randomPoint();
                Point radnomPoint = new GeometryFactory().createPoint(radnomCord);

                int numberOfVisibleModems = 0;
                
                nPointsInPolygon++ ;

                for(Modem m : modem){

                    LineSegment ray = new LineSegment(m.getCordinates() , radnomCord);
                    LineString rayString = ray.toGeometry(new GeometryFactory());

//                    Geometry gm = poly.getPoly().intersection(rayString);
//
//                    if ( gm.getCoordinates().length - 2 <= m.getK())
//                        numberOfVisibleModems++;
    
                    if ( numberOfIntersections(poly, ray) <= m.getK() )
                        numberOfVisibleModems++;

                }

                if (numberOfVisibleModems > 0 && numberOfVisibleModems <= numberOfAllowedCollisions + 1)
                    nPointsInVisibilityPolygon++ ;

        }
        
        return (nPointsInVisibilityPolygon / (double) nPointsInPolygon);
        
    }
    
    public static void monteCarloVP_SavePoints(
            int itterations , Polygon poly , Modem[] modem , int numberOfAllowedCollisions , String path , boolean showTimeDifference
    ){
       
        ArrayList<Coordinate> vPoly = new ArrayList<>();
        
        long t = System.currentTimeMillis();
        
        int nPointsInPolygon = 0 , nPointsInVisibilityPolygon = 0;
        
        for(int i = 0 ; i < itterations ; i++ ){

                Coordinate radnomCord = poly.randomPoint();
                Point radnomPoint = new GeometryFactory().createPoint(radnomCord);

                int numberOfVisibleModems = 0;
                
                nPointsInPolygon++ ;

                for(Modem m : modem){

                    LineSegment ray = new LineSegment(m.getCordinates() , radnomCord);
                    LineString rayString = ray.toGeometry(new GeometryFactory());
                    
//                    Geometry gm = poly.getPoly().intersection(rayString);
//
//                    if ( gm.getCoordinates().length - 2 <= m.getK())
//                        numberOfVisibleModems++;

                    if ( numberOfIntersections(poly, ray) <= m.getK()  )
                        numberOfVisibleModems++;

                }

                if (numberOfVisibleModems > 0 && numberOfVisibleModems <= numberOfAllowedCollisions + 1){ 
                    vPoly.add(new Coordinate( radnomCord.x ,radnomCord.y ) );
                    nPointsInVisibilityPolygon++ ;
                }
                
        }
        
        if (showTimeDifference)
            System.out.println((System.currentTimeMillis() - t) / 1000f);
        
        String str = "MULTIPOINT(";
        for(Coordinate cr : vPoly)
            str += "(" + cr.x + " " + cr.y + "), ";
        
        str = str.substring(0, str.length() -3 ) + "))";
//        System.out.println(str);
        
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(path + "\\montecarlo_out.wkt"));
            br.write(str);
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(VPCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    public static double monteCarloBenchmark(Polygon poly , int itterations){
        
        int nPointsInPolygon = 0;
        
        for(int i = 0 ; i < itterations ; i++ ){
            
            Envelope exteriorBound = poly.getPoly().getEnvelope().getEnvelopeInternal();
            RandomPointsInGridBuilder rpigb = new RandomPointsInGridBuilder();
            rpigb.setNumPoints(1);
            rpigb.setExtent( exteriorBound );   
            Coordinate radnomCord = rpigb.getGeometry().getCoordinate();
            Point radnomPoint = new GeometryFactory().createPoint(radnomCord);
            
            if(poly.getPoly().contains(radnomPoint))
                nPointsInPolygon++;
            

        }
        
        return (nPointsInPolygon / (double) itterations);
        
    }
    
    private static int numberOfIntersections(Polygon poly , LineSegment string){
        
        LineString shells[] = new LineString[ poly.getPoly().getNumInteriorRing() + 1 ];
        int count = 0;
        
        shells[0] = poly.getPoly().getExteriorRing();
        
        for (int i = 1 ; i < poly.getPoly().getNumInteriorRing() + 1 ; i++)
            shells[i] = poly.getPoly().getInteriorRingN(i-1);
//        System.out.println(Arrays.toString(shells));
        
        for (LineString ls : shells){
                        
            for (int i = 0 ; i < ls.getNumPoints() - 2 ; i++){
                Coordinate A , B , C , D , intersction_point;
               
                A = new Coordinate(ls.getCoordinateN(i));
                B = new Coordinate(ls.getCoordinateN(i+1));
                C = new Coordinate (string.p1.x , string.p1.y);
                D = new Coordinate (string.p0.x , string.p0.y);
                
//                boolean cond1 = (Math.min(C.x , D.x) - Math.max(A.x , B.x) <= 0.05  && Math.max(C.x, D.x) - Math.min(A.x , B.x) >= 0.05);
//                boolean cond2 = (Math.min(C.y , D.y) - Math.max(A.y , B.y) <= 0.05  && Math.max(C.y, D.y) - Math.min(A.y , B.y) >= 0.05);
                
//                if (cond1 && cond2){
//                    continue;
//                }
                
                double hC = implicitFunction(A, B, C);
                double hD = implicitFunction(A, B, D);
                
                double gA = implicitFunction(C, D, A);
                double gB = implicitFunction(C, D, B);
                
                if (hC * hD <= 0.05 && gA * gB <= 0.05 )
                    count++;
                
                
//                CoordinateArraySequence cas = new CoordinateArraySequence(new Coordinate[]{A,B});
//                CoordinateArraySequence cas2 = new CoordinateArraySequence(new Coordinate[]{C,D});
//                
//                LineString ls1 = new LineString(cas, new GeometryFactory());
//                LineString ls2 = new LineString(cas2, new GeometryFactory());
//                
//                if ( ls1.crosses(ls2) )
//                    count++;
                
//                double slopeCD = Math.atan((C.y - D.y) / (C.x - D.x));
//                double slopeCA = Math.atan((A.y - C.y) / (A.x - C.x));
//                double slopeCB = Math.atan((B.y - C.y) / (B.x - C.x));
//                
//                boolean cond1 = (slopeCD <= Math.max(slopeCA, slopeCB)) && (slopeCD >= Math.min(slopeCA, slopeCB)) ;
//                
//                   
//                double slopeAB = Math.atan((A.y - B.y) / (A.x - B.x));
//                double slopeAD = Math.atan((A.y - D.y) / (A.x - D.x));
//                double slopeAC = Math.atan(A.y - C.y) / (A.x - C.x);
//                               
//                boolean cond2 = (slopeAB <= Math.max(slopeAD, slopeAC)) && (slopeAB >= Math.min(slopeAD, slopeAC)) ;  
//                
//                if (slopeAB - slopeCD <= 0.01)
//                    continue;
//                
//                if (cond1 && cond2)
//                    count++;


                
//                double difX_1 = (A.x - B.x);
//                double difY_1 = (A.y - B.y);
//                double difX_2 = (C.x - D.x);
//                double difY_2 = (C.y - D.y);
//                double denominator = (difX_1 * difY_2 - difX_2 * difY_1);
//                if (denominator <= 0.01)
//                    denominator = 0;
//                if (denominator == 0)
//                    continue;
//                
//                intersction_point = new Coordinate();
//                intersction_point.x = (((A.x * B.y - A.y * B.x) * difX_2 - (C.x * D.y - C.y * D.x) * difX_1) / denominator);
//                intersction_point.y = (((A.x * B.y - A.y * B.x) * difY_2 - (C.x * D.y - C.y * D.x) * difY_1) / denominator);
//                boolean isItBetweenLine1 = intersction_point.x >= Math.min(A.x, B.x) && intersction_point.x <= Math.max(A.x, B.x);
//                boolean isItBetweenLine2 = intersction_point.x >= Math.min(C.x, D.x) && intersction_point.x <= Math.max(C.x, D.x);
//                if ( isItBetweenLine1 && isItBetweenLine2)
//                    count++;
                
//                double x = 0;
//                double y = 0;
//                double a1 = B.y - A.y;
//                double b1 = A.x - B.x; 
//                double c1 = a1*(A.x) + b1*(A.y); 
//                double a2 = D.y - C.y; 
//                double b2 = C.x - D.x; 
//                double c2 = a2*(C.x)+ b2*(C.y); 
//                double determinant = a1*b2 - a2*b1; 
//                
//                if (determinant == 0){
//                    count++;
//                    continue;
//                }
//                
//                else {
//                    x = (float) (b2*c1 - b1*c2)/determinant; 
//                    y = (float) (a1*c2 - a2*c1)/determinant;
//                }
//                
//                if (    x>=Math.min(A.x, B.x) &&
//                        x<=Math.max(A.x, B.x) &&
//                        y>=Math.min(A.y, B.y) &&
//                        y<=Math.max(A.y, B.y) &&
//                        x>=Math.min(C.x, D.x) &&
//                        x<=Math.max(C.x, D.x) &&
//                        y>=Math.min(C.y, D.y) &&
//                        y<=Math.max(C.y, D.y)){
//                    
//                    count++;
//                }
            }
        
        }
       
        return count;
    }

    
    private static double implicitFunction(Coordinate A, Coordinate B, Coordinate C){
        
        double hp = ((B.x - A.x) * (C.y - A.y)) - ((B.y - A.y) * (C.x - A.x));
        return hp;
        
    }
    
    
    public static double rayTracingVP(double minDegre , Polygon poly , Modem[] modem){
        
        
        
        
        return 0f;
    }
}
