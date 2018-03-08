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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

/**
 * MapReduceFX v0.2:
 * Simple program for performing the map reduce algorithm
 * on a body of text.
 * 
 * @author Stanley Fuller
 */
public class MapReduceFX extends Application{

    public static void main(String[] args) {
        launch(args);
    }
    
    public enum SortMode {
        ALPHABETICALLY,
        HIGHEST_TO_LOWEST_OCCURRENCE,
        LOWEST_TO_HIGHEST_OCCURRENCE
    }

    /**
     * Sorting algorithm for output lines:
     * Currently only sorts from highest to lowest word occurrence
     */
    public class OutputLineComparator implements Comparator<String> {
        SortMode sortMode;

        public OutputLineComparator(SortMode _sortMode) {
            sortMode = _sortMode;
        }

        @Override
        public int compare(String a, String b) {
            switch(sortMode) {
                case HIGHEST_TO_LOWEST_OCCURRENCE:
                    {
                    //Get occurrence from counts
                    int aCount = Integer.parseInt(a.replaceAll("[\\D]",""));
                    int bCount = Integer.parseInt(b.replaceAll("[\\D]",""));

                    //Return the occurrence difference between the two words
                    return bCount-aCount;
                    }
                case LOWEST_TO_HIGHEST_OCCURRENCE:
                    {
                    //Get occurrence from counts
                    int aCount = Integer.parseInt(a.replaceAll("[\\D]",""));
                    int bCount = Integer.parseInt(b.replaceAll("[\\D]",""));

                    //Return the negative occurrence difference between the two words
                    return aCount-bCount;
                    }
                default:
                    return 0;
            }
            
        }
    }

    /**
     * Takes text input and outputs a map reduced version
     * counting the frequency of each word.
     */
    public String mapReduceText(String input, SortMode sortMode) {
        //Remove all non-word characters excluding spaces
        input = input.replaceAll("[^a-zA-Z_ ]", "");

        //Convert to lowercase
        input = input.toLowerCase();

        //Split words into an array
        String[] words = input.split(" ");

        //Sort words into alphabetic order
        Arrays.sort(words);

        ArrayList<String> outputLines = new ArrayList<String>(); //Output lines for each word
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
                outputLines.add(cWord + ", " + cCounter);
                cWord = i;
                cCounter = 1;
            }
        }
        //Add final word to output
        outputLines.add(cWord + ", " + cCounter);

        //Sort from heighest to lowest occurrence
        Collections.sort(outputLines, new OutputLineComparator(sortMode));

        //Convert into human-readable string
        String output = String.join("\n", outputLines);

        return output;
    }

    @Override
    public void start(Stage stage) {
        //Initialize root layout
        VBox root = new VBox();
        root.setPadding(new Insets(8,8,8,8));

        //Initialize title
        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("MapReduceFX v0.2 by Stanley Fuller");
        titleLabel.setStyle("-fx-font-size:18;");

        titleBar.getChildren().add(titleLabel);

        //Initialize labels
        Label inputLabel = new Label("Text Input:");
        Label outputLabel = new Label("Output:");
        Label sortModeLabel = new Label("Sort Mode: ");

        //Initialize area for text input
        TextArea inputArea = new TextArea();

        //Initialize area for map reduce output
        TextArea outputArea = new TextArea();

        //Initialize button bar
        HBox buttonBar = new HBox();
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPadding(new Insets(5, 0, 5, 0));

        //Initialize combo box for selecting sort mode
        ObservableList<String> options = 
            FXCollections.observableArrayList(
                "Alphabetically",
                "Highest to lowest occurrence",
                "Lowest to highest occurrence"
        );
        ComboBox sortModeBox = new ComboBox<>(options);
        sortModeBox.getSelectionModel().select(0);  //Select 'alphabetically' as default option

        //Seperator to create a space between the combo box and perform button
        Separator barSeperator = new Separator();
        barSeperator.setOrientation(Orientation.VERTICAL);
        barSeperator.setVisible(false);

        //Initialize button for performing map reduce
        Button performButton = new Button("Perform Map Reduce");
        performButton.setOnAction(e -> {
            //Get sort mode from combo box
            SortMode sortMode = SortMode.ALPHABETICALLY;;
            String sortModeValue = (String)sortModeBox.getValue();
            if(sortModeValue.equals("Highest to lowest occurrence")) {
                sortMode = SortMode.HIGHEST_TO_LOWEST_OCCURRENCE;
            }
            else if (sortModeValue.equals("Lowest to highest occurrence")) {
                sortMode = SortMode.LOWEST_TO_HIGHEST_OCCURRENCE;
            }

            //Perform map reduce on input with the selected sort mode
            String mapReduced = mapReduceText(inputArea.getText(), sortMode);

            //Display result in output text area
            outputArea.setText(mapReduced);
        });

        //Add all elements to the button bar
        buttonBar.getChildren().addAll(sortModeLabel, sortModeBox, barSeperator, performButton);

        //Add all UI elements to the root layout
        root.getChildren().addAll(titleBar, inputLabel, inputArea, buttonBar, outputLabel, outputArea);

        //Set scene and display UI
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}