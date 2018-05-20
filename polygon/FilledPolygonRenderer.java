/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polygon;

import geometry.Point3DH;
import geometry.Vertex3D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import line.DDALineRenderer;
import line.LineRenderer;
import windowing.drawable.Drawable;

/**
 *
 * @author Administrator
 */
public class FilledPolygonRenderer implements PolygonRenderer {
   
    private FilledPolygonRenderer(){}
        // assumes polygon is ccw.
    @Override
    public void drawPolygon(Polygon polygon, Drawable drawable, Shader vertexShader){
        
        List<Vertex3D> vertices = polygon.vertices;
        
        Collections.sort(vertices, Comparator.comparingDouble(v -> v.getY())); 
        for(Vertex3D v : vertices){
            System.out.println(v.toIntString());
        }
        
        if(vertices.get(0).getIntY() == vertices.get(1).getIntY()){
            fillBottomTriangle(vertices, drawable);
            return;
        }
        
        if(vertices.get(1).getIntY() == vertices.get(2).getIntY()){
            fillTopTriangle(vertices, drawable);
            return;
        }
        
        Vertex3D topV = vertices.get(2);
        Vertex3D higherV = vertices.get(0);
        Vertex3D lowerV = vertices.get(1);
        if(higherV.getY() < lowerV.getY()){
            higherV = vertices.get(1);
            lowerV  = vertices.get(0);
        }
        
        double offset =(higherV.getY() - topV.getY())/(lowerV.getY() - topV.getY()) *
                               (lowerV.getX() - topV.getX()); 
//        System.out.printf("offset: %.2f\n",offset);
        double x4 = topV.getX() + offset;
        double y4 = higherV.getY();
        
        Vertex3D intermediateV = new Vertex3D(x4, y4, 0, topV.getColor());
        List<Vertex3D> list1 = new ArrayList<Vertex3D>();
        list1.add(higherV);
        list1.add(intermediateV);
        list1.add(topV);
        fillBottomTriangle(list1, drawable);
        
        List<Vertex3D> list2 = new ArrayList<Vertex3D>();
        list2.add(lowerV);
        list2.add(higherV);
        list2.add(intermediateV);
        fillTopTriangle(list2, drawable);
    }
    
     public static PolygonRenderer make() {
		return new FilledPolygonRenderer();
    }
    
    private void fillTopTriangle(List<Vertex3D> vertices, Drawable drawable) {
        LineRenderer renderer = DDALineRenderer.make();
        Vertex3D vl = vertices.get(2);
        Vertex3D vr = vertices.get(1);
        Vertex3D vt = vertices.get(0);
        assert(vt.getIntY() == vr.getIntY());
        assert(vt.getIntY() < vl.getIntY());
        
        if (vl.getX() > vr.getX()) {
            Vertex3D tmp = vl;
            vl = vr;
            vr = tmp;
        }
        
        double deltaY  = vt.getY() - vl.getY();
        double deltaXl = vt.getX() - vl.getX();
        double deltaXr = vt.getX() - vr.getX();
        double mleft = deltaXl/deltaY;
        double mright = deltaXr/deltaY;
        double leftBound = vt.getIntX();
        double rightBound = vt.getIntX();
        
        int start = vt.getIntY();
        int stop = vl.getIntY();
        Vertex3D leftV = vt;
        Vertex3D rightV = vt;
        for(int i = start; i <= stop; i++){
           renderer.drawLine(leftV, rightV, drawable);
           leftV = new Vertex3D(leftBound, i, 0, vl.getColor());
           rightV = new Vertex3D(rightBound, i, 0, vl.getColor());
           leftBound += mleft;
           rightBound += mright; 
        }
    }
    
    private void fillBottomTriangle(List<Vertex3D> vertices, Drawable drawable) {
        LineRenderer renderer = DDALineRenderer.make();
        Vertex3D vl = vertices.get(0);
        Vertex3D vr = vertices.get(1);
        Vertex3D vt = vertices.get(2);
        assert(vl.getIntY() == vr.getIntY());
        
        if (vl.getX() > vr.getX()) {
            vl = vertices.get(1);
            vr = vertices.get(0);
        }
//        System.out.printf("vl: %s vr: %s\n", vl.toIntString(), vr.toIntString());
        double deltaY  = vt.getY() - vl.getY();
        double deltaXl = vt.getX() - vl.getX();
        double deltaXr = vt.getX() - vr.getX();
        double mleft  = deltaXl/deltaY;
        double mright = deltaXr/deltaY;
        double leftBound = vl.getIntX();
        double rightBound = vr.getIntX();
        Vertex3D leftV = vt;
        Vertex3D rightV = vt;
        int top = vt.getIntY();
        int bottom = vl.getIntY();
        for(int i = bottom; i <= top; i++){
           renderer.drawLine(leftV, rightV, drawable);
//           System.out.printf("leftbound: %.2f rightbound: %.2f mleft %.2f i: %d\n", leftBound, rightBound, mleft, i);
           leftV = new Vertex3D(leftBound, i, 0, vl.getColor());
           rightV = new Vertex3D(rightBound, i, 0, vl.getColor());
           leftBound += mleft;
           rightBound += mright; 
        }
    }

}
