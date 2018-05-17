/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polygon;

import geometry.Point3DH;
import geometry.Vertex3D;
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
        
        Collections.sort(vertices, Comparator.comparingInt(v -> v.getIntY())); 
        for(Vertex3D v : vertices){
            System.out.printf(v.toString());
        }
        
        fillBottomTriangle(vertices, drawable);
    }
    
     public static PolygonRenderer make() {
		return new FilledPolygonRenderer();
    }
     
    private void fillBottomTriangle(List<Vertex3D> vertices, Drawable drawable) {
        LineRenderer renderer = DDALineRenderer.make();
        Vertex3D vt = vertices.get(0);
        Vertex3D vl = vertices.get(1);
        Vertex3D vr = vertices.get(2);
        assert(vt.getIntY() == vr.getIntY());
        
        if (vl.getIntX() > vr.getIntX()) {
            Vertex3D tmp = vl;
            vl = vr;
            vr = vl;
        }
        
        double deltaY  = vl.getIntY() - vt.getIntY();
        double deltaXl = vl.getIntX() - vt.getIntX();
        double deltaXr = vr.getIntX() - vt.getIntX();
        double mleft = deltaXl/deltaY;
        double mright = deltaXr/deltaY;
        double leftBound = vt.getIntX();
        double rightBound = vt.getIntX();
        Vertex3D leftV = vt;
        Vertex3D rightV = vt;
        for(int i = vt.getIntY(); i > vl.getIntY(); i++){
            leftV.replacePoint(new Point3DH(leftBound, i,0,0 ));
            rightV.replacePoint(new Point3DH(rightBound, i, 0 ,0));
            renderer.drawLine(leftV, rightV, drawable);
            leftBound += mleft;
            rightBound += mright; 
        }
    }

}
