/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spanning.tree.fx;

import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import static spanning.tree.fx.SpanningTreeFx.edges;

public class edge extends Line {

    static int EdgeCounter = 0;
    //---------------------------------------------   
    private node from, to; 
    private int counter ;
    edge(node from, node to) {
        counter= EdgeCounter;
        EdgeCounter++;
        this.from = from;
        this.to = to;
    }

    public void PaintEdge(double startX, double startY, double endX, double endY) {
        this.setStartX(startX + 40);
        this.setStartY(startY + 40);
        this.setEndX(endX + 40);
        this.setEndY(endY + 40);
        this.setStrokeWidth(2);
        getStrokeDashArray().setAll(10.0, 5.0);
        setMouseTransparent(true);
        this.setStrokeLineCap(StrokeLineCap.BUTT);
        this.setOpacity(0.3);
        SpanningTreeFx.MainRoot.getChildren().add(this);
    }
    


    public node getFrom() {
        return from;
    }

    public void setFrom(node from) {
        this.from = from;

    }

    public node getTo() {
        return to;
    }

    public void setTo(node to) {
        this.to = to;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    
  
}
