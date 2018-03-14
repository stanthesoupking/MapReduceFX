import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.property.SimpleStringProperty;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

/**
 * MapReduceFX v0.3:
 * Simple program for performing the map reduce algorithm
 * on a body of text.
 * 
 * @author Stanley Fuller
 */
public class MapReduceFX extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * 
     */
    public class OutputRow {
        private String word;
        private int frequency;

        public OutputRow(String _word, int _frequency) {
            this.word = _word;
            this.frequency = _frequency;
        }

        public String getWord() {
            return word;
        }

        public int getFrequency() {
            return frequency;
        }
    }

    /**
     * Takes text input and outputs a map reduced version
     * counting the frequency of each word.
     */
    public ObservableList<OutputRow> mapReduceText(String input) {
        //Remove all non-word characters excluding spaces
        input = input.replaceAll("[^a-zA-Z_ ]", "");

        //Convert to lowercase
        input = input.toLowerCase();

        //Split words into an array
        String[] words = input.split(" ");

        //Sort words into alphabetic order
        Arrays.sort(words);

        ObservableList<OutputRow> outputRows = FXCollections.observableArrayList(); //Output rows for each word, to be added to table
        String cWord = ""; //Current word that is being counted

        int cCounter = 1;   //Counter for how many times the current word
                            //has been found
        for(String i : words) {
            if(cWord.equals("")) {
                //Initial word has not been set yet so set to 'i'
                cWord = i;
            }
            else if (cWord.equals(i)) {
                //Same word has been detected, add one to counter
                cCounter++;
            }
            else {
                //Different word has been detected, add to output, reset counter
                //and set the current word to the new word
                outputRows.add(new OutputRow(cWord, cCounter));
                cWord = i;
                cCounter = 1;
            }
        }
        //Add final word to output
        outputRows.add(new OutputRow(cWord, cCounter));

        return outputRows;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("MapReduceFX v0.3");

        //Initialize root layout
        VBox root = new VBox();
        root.setPadding(new Insets(8,8,8,8));

        //Initialize title
        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("MapReduceFX v0.3 by Stanley Fuller");
        titleLabel.setStyle("-fx-font-size:18;");

        titleBar.getChildren().add(titleLabel);

        //Initialize labels
        Label inputLabel = new Label("Text Input:");
        Label outputLabel = new Label("Output:");

        //Initialize area for text input
        TextArea inputArea = new TextArea();

        //Initialize table for map reduce output
        TableView<OutputRow> outputArea = new TableView<OutputRow>();
        TableColumn<OutputRow,String> wordColumn = new TableColumn<OutputRow,String>("Word");
        wordColumn.setCellValueFactory(
            new PropertyValueFactory<OutputRow,String>("word")
        );
        TableColumn<OutputRow,Integer> frequencyColumn = new TableColumn<OutputRow,Integer>("Frequency");
        frequencyColumn.setCellValueFactory(
            new PropertyValueFactory<OutputRow,Integer>("frequency")
        );
        
        //Add columns to table
        outputArea.getColumns().setAll(wordColumn, frequencyColumn);

        //Initialize button bar
        HBox buttonBar = new HBox();
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPadding(new Insets(5, 0, 5, 0));

        //Initialize button for performing map reduce
        Button performButton = new Button("Perform Map Reduce");
        performButton.setOnAction(e -> {
            //Perform map reduce on input with the selected sort mode
            ObservableList<OutputRow> mapReduced = mapReduceText(inputArea.getText());

            //Display result in output table
            outputArea.setItems(mapReduced);
        });

        //Add all elements to the button bar
        buttonBar.getChildren().addAll(performButton);

        //Add all UI elements to the root layout
        root.getChildren().addAll(titleBar, inputLabel, inputArea, buttonBar, outputLabel, outputArea);

        //Set scene and display UI
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}