package client.testPages;

import geometry.Point3DH;
import geometry.Vertex3D;
import line.LineRenderer;
import windowing.drawable.Drawable;
import windowing.drawable.InvertedYDrawable;
import windowing.graphics.Color;

public class ParallelogramTest {
	private final LineRenderer renderer;
	private final Drawable panel;
	Vertex3D center;

	public ParallelogramTest(Drawable panel, LineRenderer renderer) {
		this.panel = new InvertedYDrawable(panel);
		this.renderer = renderer;
		
		render();
	}
	
	private void render() {		
                Color color = new Color(1.0, 1.0, 1.0);
                
		for(int i = 0; i < 50; i++){
                    Point3DH p1 = new Point3DH(20, 80+i, 0, 0);
                    Point3DH p2 = new Point3DH(150, 150+i, 0, 0);
                    Vertex3D v1 = new Vertex3D(p1, color);
                    Vertex3D v2 = new Vertex3D(p2, color); 
                    renderer.drawLine(v1, v2, panel);
                }
                
                for(int i = 0; i < 50; i++){
                    Point3DH p1 = new Point3DH(160+i, 270, 0, 0);
                    Point3DH p2 = new Point3DH(240+i, 40, 0, 0);
                    Vertex3D v1 = new Vertex3D(p1, color);
                    Vertex3D v2 = new Vertex3D(p2, color); 
                    renderer.drawLine(v1, v2, panel);
                }
	}
}
