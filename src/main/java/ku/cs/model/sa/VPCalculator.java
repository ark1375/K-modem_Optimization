
package ku.cs.model.sa;

import java.util.ArrayList;
import java.util.Arrays;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;

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
}
