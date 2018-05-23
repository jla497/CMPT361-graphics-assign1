/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.testPages;

import geometry.Vertex3D;
import java.util.Random;
import line.DDALineRenderer;
import line.LineRenderer;
import polygon.Polygon;
import polygon.PolygonRenderer;
import windowing.drawable.Drawable;
import windowing.graphics.Color;
/**
 *
 * @author Administrator
 */
public class StarburstPolygonTest {
        private static final int NUM_RAYS = 90;
	private static final double FRACTION_OF_PANEL_FOR_DRAWING = 0.9;
	
	private final PolygonRenderer renderer;
	private final Drawable panel;
	Vertex3D center;

	public StarburstPolygonTest(Drawable panel, PolygonRenderer renderer) {
		this.panel = panel;
		this.renderer = renderer;
		
		makeCenter();
		render();
	}
	
	private void render() {		
		double radius = computeRadius();
		double angleDifference = (2.0 * Math.PI) / NUM_RAYS;
		double angle = 0.0;
                double sideLength = radius/Math.cos(angleDifference/2);
                Random generator = new Random(15);
                for(int ray = 0; ray < NUM_RAYS; ray++) {
                Color color = Color.random(generator);
                    Vertex3D V1 = radialPoint(sideLength, angle - (angleDifference/2), color);
                    Vertex3D V2 = radialPoint(sideLength, angle + (angleDifference/2), color);
                    Vertex3D centerV = new Vertex3D(center.getX(), center.getY(), 0.0, color);
                    Polygon p = Polygon.make(centerV, V1, V2);
//                    if(ray == 75){
//                    renderer.drawPolygon(p, panel, null);
//                    }
                    renderer.drawPolygon(p, panel, null);
                    angle = angle + angleDifference;
		}
	}

	private void makeCenter() {
		int centerX = panel.getWidth() / 2;
		int centerY = panel.getHeight() / 2;
		center = new Vertex3D(centerX, centerY, 0, Color.WHITE);
	}

	private Vertex3D radialPoint(double radius, double angle, Color color) {
		double x = center.getX() + radius * Math.cos(angle);
		double y = center.getY() + radius * Math.sin(angle);
		return new Vertex3D(x, y, 0, color);
	}
	
	private double computeRadius() {
		int width = panel.getWidth();
		int height = panel.getHeight();
		
		int minDimension = width < height ? width : height;
		
		return (minDimension / 2.0) * FRACTION_OF_PANEL_FOR_DRAWING;
	}
}
