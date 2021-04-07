/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.cs.model.sa;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateXY;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

/**
 *
 * @author James Ark
 */
public class Modem {
    
    
    private int k = 0;
    private CoordinateXY cordinates = new CoordinateXY(0f , 0f);

    public Modem() {
    }
    
    public Modem(double x , double y) {
        setX(x);
        setY(y);
    }
    
    public Modem(double x , double y , int k) {
        setX(x);
        setY(y);
        setK(k);
    }


    public void setK(int k) {
        if (k >= 0 )
            this.k = k;
    }
    
    public void setX(double x){
        this.cordinates.setX(x);
    }

    public void setY(double y){
        this.cordinates.setY(y);
    }
    
    public void setCordinates(CoordinateXY cordinates) {
        this.cordinates = cordinates;
    }

    public int getK() {
        return k;
    }

    public CoordinateXY getCordinates() {
        return cordinates;
    }

    @Override
    public String toString(){
        return String.format("(%f %f)\n", cordinates.x , cordinates.y);
    }
}
