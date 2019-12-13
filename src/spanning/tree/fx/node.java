/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spanning.tree.fx;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import static spanning.tree.fx.SpanningTreeFx.edges;

/*
 *
 * @author Amr
 */
public class node extends Circle {

    double preX, preY;
    static int NodeCounter = 0;
    //---------------------------------------------
    private final Delta dragDelta = new Delta();
    private StackPane root;
    private int myNumber;

    public node() {
       
        // set number
        myNumber = NodeCounter;
        NodeCounter++;
        root = new StackPane();
        //------------------------------
        root.setOnMousePressed((MouseEvent mouseEvent) -> {
            root.toFront();
            dragDelta.x = root.getLayoutX() - mouseEvent.getSceneX();
            dragDelta.y = root.getLayoutY() - mouseEvent.getSceneY();
            preX = mouseEvent.getSceneX();
            preY = mouseEvent.getSceneY();
            this.relocate(0, 0);
            root.setCursor(Cursor.MOVE);
            ConnectEdges(mouseEvent);
        });
        root.setOnMouseReleased((MouseEvent mouseEvent) -> {

            root.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
            root.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
            System.out.println("Cneter : " + root.getLayoutX() + " , "  + root.getLayoutY()  );
            node toNode = ConnectWithExistNodeHere(mouseEvent.getSceneX() + dragDelta.x, mouseEvent.getSceneY() + dragDelta.y);
            if (toNode != null && isConnectedBefore(this, toNode) == false) {
                // if exist node here 
                edge edge = new edge(this, toNode);
                edge.PaintEdge(preX + dragDelta.x, preY + dragDelta.y, toNode.root.getLayoutX(), toNode.root.getLayoutY());
                edges.add(edge);
                this.root.toFront();
                root.setLayoutX(preX + dragDelta.x);
                root.setLayoutY(preY + dragDelta.y);
                  reConnectEdges();
            }
            
            root.setCursor(Cursor.HAND);
        });
        root.setOnMouseDragged((MouseEvent mouseEvent) -> {
            ConnectEdges(mouseEvent);
            root.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
            root.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
        });
        root.setOnMouseEntered((MouseEvent mouseEvent) -> {
            root.setCursor(Cursor.HAND);
        });

    }

    private void ConnectEdges(MouseEvent mouseEvent) {
        for (int i = 0; i < edges.size(); i++) {
            edge cur = edges.get(i);
            if (this.myNumber == cur.getTo().getMyNumber()) {
                cur.setEndX(mouseEvent.getSceneX()+ dragDelta.x + 40);
                cur.setEndY(mouseEvent.getSceneY() + dragDelta.y + 40);
            } else if (this.myNumber == cur.getFrom().getMyNumber()) {
                cur.setStartX(mouseEvent.getSceneX() + dragDelta.x + 40);
                cur.setStartY(mouseEvent.getSceneY() + dragDelta.y + 40);
            }
        }
    }
    
     private void reConnectEdges() {
        for (int i = 0; i < edges.size(); i++) {
            edge cur = edges.get(i);
            if (this.myNumber == cur.getTo().getMyNumber()) {
                cur.setEndX(root.getLayoutX() + 40 );
                cur.setEndY(root.getLayoutY() + 40);
            } else if (this.myNumber == cur.getFrom().getMyNumber()) {
                cur.setStartX(root.getLayoutX()   + 40);
                cur.setStartY(root.getLayoutY()   + 40);
            }
        }
    }

    private node ConnectWithExistNodeHere(double x, double y) {
        System.out.println(" mouse :  X: " + x + " Y: " + y);
        for (int i = 0; i < SpanningTreeFx.nodes.size(); i++) {
            node cur = SpanningTreeFx.nodes.get(i);
            if ((cur.root.getLayoutX() - 20 <= x && (cur.root.getLayoutX() + 20) >= x)
                    && (cur.root.getLayoutY() - 20 <= y && (cur.root.getLayoutY() + 20) >= y)
                    && this.getMyNumber() != cur.getMyNumber()) {
                return cur;
            }
        }
        return null;
    }

    public Pane PaintNode() {
        Text text = createText(this.myNumber + "");
        encircle(text);
        this.setEffect(new Bloom());
        this.setStroke(Color.web("#0B3861"));
        this.setStrokeWidth(5);
        root.getChildren().addAll(this, text);
        root.setPadding(new Insets(20));
        return root;
    }

    private Text createText(String string) {
        Text text = new Text(string);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setFill(Color.web("#084B8A"));
        text.setStyle(
                "-fx-font-family: calibri;" + "-fx-font-size: 20px;");
        return text;
    }

    private void encircle(Text text) {
        this.setFill(Color.WHITE);
        final double PADDING = 15;
        this.setRadius(getWidth(text) / 2 + PADDING);
    }

    private double getWidth(Text text) {
        new Scene(new Group(text));
        text.applyCss();
        return text.getLayoutBounds().getWidth();
    }

    private boolean isConnectedBefore(node from, node to) {

        for (int i = 0; i < edges.size(); i++) {
            if ((from == edges.get(i).getFrom() && to == edges.get(i).getTo()) || (to == edges.get(i).getFrom() && from == edges.get(i).getTo())) {
                return true;
            }
        }
        return false;
    }

    public int getMyNumber() {
        return myNumber;
    }

    public void setMyNumber(int myNumber) {
        this.myNumber = myNumber;
    }

    public StackPane getRoot() {
        return root;
    }

    public void setRoot(StackPane root) {
        this.root = root;
    }

    class Delta {
        double x, y;
    }
}
