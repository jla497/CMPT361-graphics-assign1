/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package line;

import geometry.Vertex3D;
import windowing.drawable.Drawable;
import windowing.graphics.Color;

/**
 *
 * @author Administrator
 */
public class AntialiasingLineRenderer implements LineRenderer{
     private AntialiasingLineRenderer(){}
    
    @Override
    public void drawLine(Vertex3D p1, Vertex3D p2, Drawable drawable) {
        double deltaX = p2.getIntX() - p1.getIntX();
        double deltaY = p2.getIntY() - p1.getIntY();
        double slope = deltaY/deltaX;
        double intercept = p1.getIntY() - slope * p1.getIntX();
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
                
//                double d0        = getLengthD(iX, iY - 2, slope, intercept);
//                double fraction0 = getShadedFraction(d0);
//                Color tmp0        = p1.getColor();
//                int color0       = tmp0.scale(fraction0).asARGB();
//                drawable.setPixel(iX, iY - 2, 0.0, color0);
                
                double d1        = getLengthD(iX, iY - 1, slope, intercept, iX, iY);
                double fraction1 = getShadedFraction(d1);
                Color tmp        = p1.getColor();
                int color1       = tmp.scale(fraction1).asARGB();
                drawable.setPixel(iX, iY - 1, 0.0, color1);
             
                double d2        = getLengthD(iX, iY, slope, intercept, iX, iY);
                double fraction2 = getShadedFraction(d2);
                Color tmp2       = p1.getColor();
                int color2       = tmp2.scale(fraction2).asARGB();
                drawable.setPixel(iX, iY, 0.0, color2);
               
                double d3        = getLengthD(iX, iY + 1, slope, intercept, iX, iY);
                double fraction3 = getShadedFraction(d3);
                Color tmp3       = p1.getColor();
                int color3       = tmp3.scale(fraction3).asARGB();
                drawable.setPixel(iX, iY + 1, 0.0, color3);
//                System.out.printf(" iX: %d iY: %d slope: %.2f intercept: %.2f d1: %.2f f1: %.2f d2: %.2f f2: %.2f  d3: %.2f f3: %.2f\n",
//                                 iX, iY, slope, intercept, d1, fraction1, d2,fraction2, d3,fraction3);
                y = y+ dY;
                x = x + dX;
                i++;
        }
    }
    
    public static LineRenderer make() {
        AntialiasingLineRenderer tmp = new AntialiasingLineRenderer();
        
        return new AnyOctantLineRenderer(tmp); 
    }
    
    private double getLengthD(int X, int Y, double slope, double b1, int xo, int yo){
 
        if( slope <= 0){
            
            return Math.abs(yo - Y);
        }
//        double slope2  = -1 * 1/slope;
//        double b2      = Y - (1/slope)*X;
        
        double slope2  = -1 * 1/slope;
        double b2      = Y + (1/slope)*X;
        double Xint    = (b2 - b1)/(slope - slope2);
        double Yint    = slope * Xint + b1;
        double Ya      = slope * X + b1;
        double tmp  = (Yint - Y)*(Yint - Y) + (Xint - X)*(Xint - X);
        double adj  = Math.sqrt(tmp);
        double hyp  = Math.abs(Ya - Y);
        double hyp2 = 0.5 / Math.cos(adj/hyp);
        
        double d    = (hyp - hyp2) * Math.cos(adj/hyp);
        if(Double.isNaN(d)){
            return 0.0;
        }
//        
//        if(x== -250 && y == 105){
//            
//        System.out.printf("b2: %.2f\n", b2);
//        System.out.printf("Xint: %.2f Yint: %.2f\n", Xint, Yint);
//            System.out.printf("adj: %.2f hyp: %.2f", adj, hyp);
//            System.out.printf("hyp2: %.2f Ya: %.2f, d: %.2f ", hyp2, Ya,d);
//        }
//       
        return d;
    }
    
    private double getShadedFraction(double d){
        double r = 0.5;
        if (d == 0) {
            return 1.0;
        }
        if (Math.abs(d) >= r){
            return 0.0;
        }
        double triangleArea = d * Math.sqrt((r * r) - (d * d));
        double angle = Math.acos(d/r);
        double piRsquared = Math.PI * r * r;
        double wedgeArea = piRsquared * (1 - (angle/Math.PI));
        double fraction = 1 - (triangleArea + wedgeArea)/piRsquared;
        return fraction;
    }
    
}