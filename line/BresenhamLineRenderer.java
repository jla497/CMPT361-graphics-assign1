/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package line;

import geometry.Vertex3D;
import windowing.drawable.Drawable;
import line.AnyOctantLineRenderer;
/**
 *
 * @author Administrator
 */
public class BresenhamLineRenderer implements LineRenderer {
    private BresenhamLineRenderer(){}
    
    @Override
    public void drawLine(Vertex3D p1, Vertex3D p2, Drawable drawable) {
        double deltaX = p2.getIntX() - p1.getIntX();
        double deltaY = p2.getIntY() - p1.getIntY();
        
        double err = 2 * deltaY - deltaX;
        double k   = 2 * deltaY - 2 * deltaX;
        double m_num = 2 * deltaY;
        int y = p1.getIntY();
        int argbColor = p1.getColor().asARGB();
		
        for(int x = p1.getIntX(); x < p2.getIntX(); x++){
           drawable.setPixel(x, y, 0.0, argbColor);
           if(err >= 0){
               y++;
               err += k;
           }else {
               err += m_num;
           }
        } 
    }
    
    public static LineRenderer make() {
        BresenhamLineRenderer tmp = new BresenhamLineRenderer();    
        return new AnyOctantLineRenderer(tmp);
    }

}
