/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.testPages;

import geometry.Vertex3D;
import java.util.Random;
import javafx.util.Pair;
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
public class MeshPolygonTest {
    private static final int NUM_RAYS = 90;
    private static final double FRACTION_OF_PANEL_FOR_DRAWING = 0.9;
    
    public static final int NO_PERTURBATION = 0;
    public static final int USE_PERTURBATION = 1;
    
    private final PolygonRenderer renderer;
    private final Drawable panel;
    private  final int perturbation;
    private final int gridSize = 10;
    private final int margin = 15;

    public MeshPolygonTest(Drawable panel, PolygonRenderer renderer, int perturbation) {
            this.panel = panel;
            this.renderer = renderer;
            this.perturbation = perturbation;
            
            render();
    }

    private void render() {		
           int width = panel.getWidth();
           int height = panel.getHeight();
           double offsetX = width/gridSize;
           double offsetY = height/gridSize;
           double iX = 0.0;
           double iY = 0.0;
           Random generator = new Random(15);
           Vertex3D[][] grid = new Vertex3D[10][10];
           for(int i = 0; i < 10; i++){
               for(int j = 0; j < 10; j++){
                   double pertX = generator.nextDouble()*24 - 12;
                   double pertY = generator.nextDouble()*24 - 12;
                   if(perturbation == NO_PERTURBATION){
                       pertX = 0.0;
                       pertY = 0.0;
                   }
                   
                   iX = margin + offsetX * j + pertX;
                   iY = margin + offsetY * i + pertY;
                   Color color = Color.random(generator);
                   if(iX >= width - margin){
                       iX = width - margin;
                   }
                   
                   if(iX <= 0.0){
                       iX = margin;
                   }
                   
                   if(iY <= 0.0){
                       iY = margin;
                   }
                   
                   grid[i][j] = new Vertex3D(iX, iY, 0, color);
               }
           }
           
           for(int i = 0; i < 9; i++){
               for(int j = 0; j < 9; j++) {
                   Color c1 = Color.random(generator);
                   Vertex3D v1 = grid[i][j];
                   Vertex3D v2 = grid[i + 1][j];
                   Vertex3D v3 = grid[i][j + 1];
                   Polygon p1 = Polygon.make(v1, v2, v3);
                   renderer.drawPolygon(p1, panel);
                   
                   Vertex3D v4 = new Vertex3D(v2.getX(), v2.getY(), 0.0, c1);
                   Vertex3D v5 = new Vertex3D(v3.getX(), v3.getY(), 0.0, c1);
                   Vertex3D v6 = grid[i + 1][j + 1];
                   Polygon p2 = Polygon.make(v4, v5, v6);
                   renderer.drawPolygon(p2, panel);
               }
           }
    }
}
