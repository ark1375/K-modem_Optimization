
package ku.cs.model.sa.graphics;

import javax.swing.*;
import java.awt.*;
import org.locationtech.jts.awt.PolygonShape;

public class PlotFrame extends JFrame{
    
    PlotPanel plp;
    
    public PlotFrame(PolygonShape ps){
        
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        plp = new PlotPanel(ps);
        this.add(plp);        
        
    }
    
}
