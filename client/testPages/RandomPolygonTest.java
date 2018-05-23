/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.testPages;

import geometry.Vertex3D;
import java.util.Random;
import polygon.Polygon;
import polygon.PolygonRenderer;
import windowing.drawable.Drawable;
import windowing.graphics.Color;
/**
 *
 * @author Administrator
 */
public class RandomPolygonTest {
    
    private final PolygonRenderer renderer;
    private final Drawable panel;
    public RandomPolygonTest(Drawable panel, PolygonRenderer renderer){
        this.renderer = renderer;
        this.panel = panel;
        render();
    }
    
    private void render(){
        int[] arr = new Random().ints(60, 0, 299).toArray();
        Random gen = new Random(15);
        for(int i = 0; i < 20; i++){
            Color color = Color.random(gen);
            Vertex3D v1 = new Vertex3D(arr[i], arr[i+1], 0.0, color);
            Vertex3D v2 = new Vertex3D(arr[i + 2], arr[i+3], 0.0, color);
            Vertex3D v3 = new Vertex3D(arr[i + 4], arr[i+5], 0.0, color);
            Polygon p = Polygon.make(v1, v2, v3);
            renderer.drawPolygon(p, panel);
        }
    }
    
}
