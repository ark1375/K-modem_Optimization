
package ku.cs.model.sa.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import org.locationtech.jts.awt.PolygonShape;

public class PlotPanel extends JPanel{
    
    PolygonShape ps;
    
    public PlotPanel(PolygonShape ps){
        
        this.ps = ps;
        
    }
    
    @Override
    public void paintComponent(Graphics g){
    
        super.paintComponent(g);
        this.setBackground(Color.white);
        
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(new Color(250 , 222 , 36));
        
        g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        float dash1[] = {30f , 10f};
        BasicStroke bstr = new BasicStroke(2.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        12f, dash1, 10f);
        
        
        g2D.fill(ps);
        g2D.draw(ps);
        g2D.setStroke(bstr);
        g2D.setPaint(new Color(40 , 40 , 40));
        g2D.draw(ps);
//        g2D.setPaint(Color.black);
//        g2D.setBackground(Color.BLACK);
//        g2D.transform(new AffineTransform(new double[]{1 , 0 , 0 , -1 , 0 , 0}));
        


        
    
   
    }
    
    
}
