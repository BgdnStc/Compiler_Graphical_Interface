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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
    private static Path source1File = null;
    private static Path source2File = null;

    @FXML
    public void initialize() {
        System.out.println("Initialized.");
        Image header = new Image(new File("src/main/resources/org/bgdnstc/ByteNetHeader.png").toURI().toString());
        imageViewHeader.setImage(header);
    }

    @FXML
    public void newFile1() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                textAreaSource1.setText(Files.readString(selectedFile.toPath()));
                source1File = selectedFile.toPath();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void newFile2() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                textAreaSource1.setText(Files.readString(selectedFile.toPath()));
                source2File = selectedFile.toPath();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void openFile1() {
        System.out.println("Open file.");
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                textAreaSource1.setText(Files.readString(selectedFile.toPath()));
                source1File = selectedFile.toPath();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void openFile2() {
        System.out.println("Open file.");
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                textAreaSource2.setText(Files.readString(selectedFile.toPath()));
                source2File = selectedFile.toPath();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void saveFile1() {
        try {
            if (!textAreaSource1.getParagraphs().toArray()[0].toString().isEmpty()) {
                if (source1File != null) {
                    Files.write(Path.of(source1File.toString()), textAreaSource1.getParagraphs());
                } else {
                    Files.write(Path.of("source1.bynt"), textAreaSource1.getParagraphs());
                }
            } else {
                System.out.println("Nothing to be saved.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void saveFile2() {
        try {
            if (!textAreaSource2.getParagraphs().toArray()[0].toString().isEmpty()) {
                if (source2File != null) {
                    Files.write(Path.of(source2File.toString()), textAreaSource2.getParagraphs());
                } else {
                    Files.write(Path.of("source2.bynt"), textAreaSource2.getParagraphs());
                }
            } else {
                System.out.println("Nothing to be saved.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void compile1() {
        try {
            String compilerExec = "java -cp .;C:\\Users\\Bogdan\\IdeaProjects\\Compiler_Solution_for_JVM_Based_Language\\out\\production\\Compiler_Solution_for_JVM_Based_Language;C:\\Users\\Bogdan\\.m2\\repository\\org\\ow2\\asm\\asm\\9.6\\asm-9.6.jar org.bgdnstc.Main";
            Runtime.getRuntime().exec(compilerExec);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void compile2() {

    }

    @FXML
    private void run1() {

    }

    @FXML
    private void run2() {

    }

    @FXML
    private void closeFile1() {
        textAreaSource1.setText("");
        source1File = null;
    }

    @FXML
    private void closeFile2() {
        textAreaSource2.setText("");
        source2File = null;
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
