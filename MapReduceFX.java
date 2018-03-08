import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;

/**
 * MapReduceFX:
 * Simple program for performing the map reducealgorithm
 * on a body of text.
 * 
 * @author Stanley Fuller
 */
public class MapReduceFX extends Application{
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        //Initialize root layout
        VBox root = new VBox();
        root.setPadding(new Insets(8,8,8,8));

        //Initialize labels
        Label inputLabel = new Label("Text Input:");
        Label outputLabel = new Label("Output:");

        //Initialize area for text input
        TextArea inputArea = new TextArea();

        //Initialize button bar
        HBox buttonBar = new HBox();
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPadding(new Insets(5, 0, 5, 0));

        //Initialize button for performing map reduce
        Button performButton = new Button("Perform Map Reduce");

        //Add all buttons to the button bar
        buttonBar.getChildren().addAll(performButton);

        //Initialize area for map reduce output
        TextArea outputArea = new TextArea();

        //Add all UI elements to the root layout
        root.getChildren().addAll(inputLabel, inputArea, buttonBar, outputLabel, outputArea);

        //Set scene and display UI
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}