/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package line;

import geometry.Vertex3D;
import line.AnyOctantLineRenderer;
import windowing.drawable.Drawable;

/**
 *
 * @author Administrator
 */
public class DDALineRenderer implements LineRenderer{
    private DDALineRenderer(){}
    
    //TODO
    @Override
    public void drawLine(Vertex3D p1, Vertex3D p2, Drawable drawable) {
        double deltaX = p2.getIntX() - p1.getIntX();
        double deltaY = p2.getIntY() - p1.getIntY();
        int step;
        if(Math.abs(deltaX) > Math.abs(deltaY)){
            step = (int)Math.abs(Math.round(deltaX));
        }else{
            step = (int)Math.abs(Math.round(deltaY));
        }
        
        double dY = deltaY/step;
        double dX = deltaX/step;
        int argbColor = p1.getColor().asARGB();
        double y = p1.getIntY();
        double x = p1.getIntX();
        int i = 1;
        while(i <= step) {
                int iX = (int)Math.round(x);
                int iY = (int)Math.round(y);
                drawable.setPixel(iX, iY, 0.0, argbColor);
                y = y+ dY;
                x = x + dX;
                i++;
        }
    }
    
    public static LineRenderer make() {
                DDALineRenderer renderer = new DDALineRenderer();
		return new AnyOctantLineRenderer(renderer);
	}
}
