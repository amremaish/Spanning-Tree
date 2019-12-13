/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spanning.tree.fx;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static spanning.tree.fx.SpanningTreeFx.MainRoot;
import static spanning.tree.fx.SpanningTreeFx.edges;

/**
 *
 * @author Amr
 */
public class drawSolution {

    private ArrayList< ArrayList<ArrayList< Integer>>> resultSet; // the solution of Algorithms
    private Stage stage;
    private int visEdge[];           // record choosed Edges
    private int index = 0;          // indicator for next and prev 
    private Pane prePane;
    private BorderPane root;
    private String numberOfTree;
    private Label CurIndexlbl;

    public drawSolution() {
        visEdge = new int[300];

        resultSet = new ArrayList<>();
    }

    public void drawSolution() {
        // Solve it 
        resultSet = new SpanningAlgorithm().Solve(SpanningTreeFx.edges);
        numberOfTree = String.valueOf(resultSet.size() - 1);
        stage = new Stage();
        Scene scene = new Scene(DrawContent(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("Solution ");
        scene.getStylesheets().add(SpanningTreeFx.class.getResource("StyleSheet.css").toExternalForm());
        stage.show();

    }

    private Pane DrawContent() {
        root = new BorderPane();
        root.setStyle("-fx-background: #FFFFFF;");
        //------------------------------------
        HBox indicatorBox = new HBox();
        Button next = new Button("next");
        next.setOnAction(e -> nextAction());
        Button previous = new Button("previous");
        previous.setOnAction(e -> PrevAction());
        CurIndexlbl = new Label();
        CurIndexlbl.setText(index + " / " + numberOfTree);
        indicatorBox.getChildren().addAll(previous, next, CurIndexlbl);
        indicatorBox.setSpacing(15);
        indicatorBox.setPadding(new Insets(10, 10, 10, 10));
        //-------------------------------------
        root.setBottom(indicatorBox);
        root.setCenter(drawTree(index++));
        MainRoot.getChildren().clear();
        return root;
    }

    void nextAction() {
        try {
            prePane.getChildren().clear();
        } catch (Exception ee) {
        }
        index++;
        if (index == resultSet.size()) {
            index--;
        }

        if (index == -1) {
            index++;
        }
        CurIndexlbl.setText(index + " / " + numberOfTree);
        prePane = drawTree(index);
        root.setCenter(prePane);
    }

    void PrevAction() {
        try {
            prePane.getChildren().clear();
        } catch (Exception ee) {
        }
        index--;
        if (index == -1) {
            index++;
        }

        if (index == resultSet.size()) {
            index--;
        }

        CurIndexlbl.setText(index + " / " + numberOfTree);
        prePane = drawTree(index);

        root.setCenter(prePane);
    }

    private Pane drawTree(int i) {

        Pane pane = new Pane();
        for (int j = 0; j < resultSet.get(i).size(); j++) {   // nodes  number

            for (int k = 0; k < resultSet.get(i).get(j).size(); k++) {        // node connected 
                int toResult = resultSet.get(i).get(j).get(k);
                if (toResult == -1) {
                    continue;
                }
                for (int m = 0; m < edges.size(); m++) {   // edges designed    

                    int from = edges.get(m).getFrom().getMyNumber();
                    int to = edges.get(m).getTo().getMyNumber();
                    if (((from == j && to == toResult) || (from == toResult && to == j)) && visEdge[m] == 0) {
                        try {
                            pane.getChildren().add(edges.get(m).getFrom().getRoot());
                        } catch (Exception e) {
                        }
                        try {
                            pane.getChildren().add(edges.get(m).getTo().getRoot());
                        } catch (Exception e) {
                        }
                        try {
                            pane.getChildren().add(edges.get(m));
                        } catch (Exception e) {
                        }
                        visEdge[m] = 1;
                    }
                }
            }
        }

        for (int j = 0; j < visEdge.length; j++) {
            visEdge[j] = 0;
        }

        return pane;
    }

    class pair {

        boolean vis;
        int first, second;
    }
}
