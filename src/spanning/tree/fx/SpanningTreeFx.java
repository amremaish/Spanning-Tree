/*
    Updated 31/8/2017 
 */
package spanning.tree.fx;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Amr
 */
public class SpanningTreeFx extends Application {

    static boolean rotate, move;
    public static ArrayList<node> nodes;
    public static ArrayList<edge> edges;
    public static BorderPane MainRoot;
    private Stage stage;
    private Label wait;
    private Button createNode, solve;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        nodes = new ArrayList();
        edges = new ArrayList();
        Scene scene = new Scene(Paint(), 800, 600);
        primaryStage.setTitle("Spanning Algorithm ");
        scene.getStylesheets().add(SpanningTreeFx.class.getResource("StyleSheet.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    Pane Paint() {
        MainRoot = new BorderPane();
        MainRoot.setStyle("-fx-background: #FFFFFF;");
        AnchorPane paint = new AnchorPane();
        HBox box = new HBox();
        box.setSpacing(20);
        createNode = new Button("Create node");
        createNode.setOnAction(e -> {
            node node = new node();
            paint.getChildren().addAll(node.PaintNode());
            nodes.add(node);
        });
        wait = new Label("Wait for constructing solution");
        wait.setVisible(false);
        HBox.setMargin(wait, new Insets(5, 0, 0, 0));
        box.setPadding(new Insets(10, 0, 10, 10));
      
        //---------------------------------------------------------------  
        solve = new Button(" Solve ");
        solve.setOnAction(e -> {
            Platform.runLater(() -> {
                solve.setDisable(true);
                createNode.setDisable(true);
                wait.setVisible(true);

                new drawSolution().drawSolution();
                stage.close();
            });

        });
     box.getChildren().addAll(createNode,solve ,  wait);


        //----------------------------------------------------------
        MainRoot.setCenter(paint);
        MainRoot.setBottom(box);

        return MainRoot;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
