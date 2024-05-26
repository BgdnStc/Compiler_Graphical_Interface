package org.bgdnstc;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class MainController extends Application {
    @FXML
    private GridPane gridPane;
    @FXML
    private ImageView imageViewHeader;
    @FXML
    private TextArea textAreaSource1;
    @FXML
    private TextArea textAreaSource2;
    @FXML
    private Button buttonCompile1;
    @FXML
    private Button buttonCompile2;
    @FXML
    private Button buttonRun1;
    @FXML
    private Button buttonRun2;

    @FXML
    public void initialize() {
        System.out.println("Initialized.");
        Image header = new Image(new File("src/main/resources/org/bgdnstc/ByteNetHeader.png").toURI().toString());
        imageViewHeader.setImage(header);
        openFile();
    }

    @FXML
    public void openFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());
    }

    @FXML
    private void saveFile() {
        System.out.println(textAreaSource1.getParagraphs().toArray()[0].toString().length());
        try {
            if (textAreaSource1.getParagraphs().toArray()[0].toString().isEmpty()) {
                Files.write(Path.of("source1.txt"), textAreaSource1.getParagraphs());
            } else {
                System.out.println("empty");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
