package org.bgdnstc;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import java.util.Objects;

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
    private static Path asmPath = null;
    private static Path byntPath = null;

    @FXML
    public void initialize() {
        System.out.println("Initialized.");
        Image header = new Image(new File("src/main/resources/org/bgdnstc/ByteNetHeader.png").toURI().toString());
        imageViewHeader.setImage(header);
        try {
            byntPath = !Files.readString(Path.of("src/main/resources/org/bgdnstc/configBynt.txt")).isEmpty() ? Path.of(Files.readString(Path.of("src/main/resources/org/bgdnstc/configBynt.txt"))) : null;
            if (Files.readString(Path.of("src/main/resources/org/bgdnstc/configBynt.txt")).isEmpty()) {
                System.out.println(byntPath);
            }
            asmPath = !Files.readString(Path.of("src/main/resources/org/bgdnstc/configASM.txt")).isEmpty() ? Path.of(Files.readString(Path.of("src/main/resources/org/bgdnstc/configASM.txt"))) : null;
            System.out.println(asmPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void newFile1() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                textAreaSource1.setText(Files.readString(selectedFile.toPath()));
                source1File = selectedFile.toPath().toAbsolutePath();
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
                source2File = selectedFile.toPath().toAbsolutePath();
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
                source1File = selectedFile.toPath().toAbsolutePath();
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
                source2File = selectedFile.toPath().toAbsolutePath();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void compile1() {
        try {
            if (byntPath == null || asmPath == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Internal Library Paths");
                alert.setHeaderText("Compiler configuration file paths are missing!");
                alert.showAndWait();
            } else {
                if (source1File == null) {
                    saveFile1();
                    System.out.println(source1File);
                    if (source1File != null) {
                        String compilerExec = "java -cp .;" + byntPath + ";" + asmPath + " org.bgdnstc.Main " + source1File;
                        System.out.println(compilerExec);
                        Runtime.getRuntime().exec(compilerExec);
                        System.out.println("Compiled!");
                    }
                } else {
                    saveFile1();
                    String compilerExec = "java -cp .;" + byntPath + ";" + asmPath + " org.bgdnstc.Main " + source1File;
                    System.out.println(compilerExec);
                    Runtime.getRuntime().exec(compilerExec);
                    System.out.println("Compiled!");
                }
            }
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

    @FXML
    private void saveFile1() {
        System.out.println("Saving");
        try {
            if (!textAreaSource1.getParagraphs().toArray()[0].toString().isEmpty()) {
                if (source1File != null) {
                    Files.write(Path.of(source1File.toString()), textAreaSource1.getText().getBytes());
                } else {
                    source1File = Path.of("source1.bynt").toAbsolutePath();
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
                    Files.write(Path.of(source2File.toString()), textAreaSource2.getText().getBytes());
                } else {
                    source2File = Path.of("source2.bynt").toAbsolutePath();
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
    public void openSettings(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SettingsScene.fxml")));
            Stage stage = MainApplication.mainStage;
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
