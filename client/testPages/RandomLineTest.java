/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.testPages;

import geometry.Vertex3D;
import line.LineRenderer;
import windowing.drawable.Drawable;
import java.util.Random;
import windowing.graphics.Color;

/**
 *
 * @author Administrator
 */
public class RandomLineTest {
    private static final int NUM_LINES = 30;
	private final LineRenderer renderer;
	private final Drawable panel;
	Vertex3D center;

	public RandomLineTest(Drawable panel, LineRenderer renderer) {
		this.panel = panel;
		this.renderer = renderer;
		
		render();
	}
	
	private void render() {		
		Random random = new Random(1);
                int maxX = panel.getWidth();
		int maxY = panel.getHeight();
                
                for(int ray = 0; ray < NUM_LINES; ray++) {
                        double X1 = random.nextInt(maxX);
                        double X2 = random.nextInt(maxX);
                        double Y1 = random.nextInt(maxY);
                        double Y2 = random.nextInt(maxY);
                        Color color = Color.random(random);
                        
                        Vertex3D V1 = new Vertex3D(X1, Y1, 0.0, color);
                        Vertex3D V2 = new Vertex3D(X2, Y2, 0.0, color);
			renderer.drawLine(V1, V2, panel);
		}
	}

}
