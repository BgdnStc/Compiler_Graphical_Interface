package org.bgdnstc;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
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
    @FXML
    private TextArea textAreaCommand1;
    @FXML
    private TextArea textAreaCommand2;
    private static Path source1File = null;
    private static Path source2File = null;
    private static Path asmPath = null;
    private static Path byntPath = null;
    private static Path outputPath = null;

    @FXML
    public void initialize() {
        System.out.println("Initialized.");
        Image header = new Image(new File("src/main/resources/org/bgdnstc/images/ByteNetHeader.png").toURI().toString());
        imageViewHeader.setImage(header);
        try {
            byntPath = !Files.readString(Path.of("src/main/resources/org/bgdnstc/config/configBynt.txt")).isEmpty() ? Path.of(Files.readString(Path.of("src/main/resources/org/bgdnstc/config/configBynt.txt"))) : null;
            System.out.println(byntPath);
            asmPath = !Files.readString(Path.of("src/main/resources/org/bgdnstc/config/configASM.txt")).isEmpty() ? Path.of(Files.readString(Path.of("src/main/resources/org/bgdnstc/config/configASM.txt"))) : null;
            System.out.println(asmPath);
            outputPath = !Files.readString(Path.of("src/main/resources/org/bgdnstc/config/configOut.txt")).isEmpty() ? Path.of(Files.readString(Path.of("src/main/resources/org/bgdnstc/config/configOut.txt"))) : null;
            System.out.println(outputPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BackgroundFill backgroundFill = new BackgroundFill(Color.valueOf(String.valueOf(Color.BLACK)), new CornerRadii(0), new Insets(0));
        Background background = new Background(backgroundFill);
        textAreaCommand1.setBackground(background);
        textAreaCommand2.setBackground(background);
        textAreaSource1.setBackground(background);
        textAreaSource2.setBackground(background);
        textAreaCommand1.setStyle("-fx-text-fill: lightgreen ;");
        textAreaCommand2.setStyle("-fx-text-fill: lightgreen ;");

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
    public void compile1() {
        boolean error = false;
        try {
            if (byntPath == null || asmPath == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Internal Library Paths");
                alert.setHeaderText("Compiler configuration file paths are missing!");
                alert.showAndWait();
            } else {
                if (source1File == null) {
                    saveFile1();
                    String compileExec = "java -cp .;" + byntPath + ";" + asmPath + " org.bgdnstc.Main " + source1File;
                    System.out.println(source1File);
                    appendTextCommand1("Saving...\nParsing...\n");
                    if (source1File != null) {
                        System.out.println(compileExec);
                        Process process = Runtime.getRuntime().exec(compileExec);
                        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s;
                        System.out.println("Here is the standard error of the command (if any):\n");
                        while (true) {
                            try {
                                if ((s = stdError.readLine()) == null) break;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println(s);
                            appendTextCommand1(s);
                            error = true;
                        }
                        System.out.println("Here is the standard output of the command:\n");
                        while (true) {
                            try {
                                if ((s = stdInput.readLine()) == null) break;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println(s);
                            appendTextCommand1("Processing file: \"");
                            appendTextCommand1(s);
                            appendTextCommand1("\"\n");
                        }
                        System.out.println("Compiled!");
                    }
                } else {
                    saveFile1();
                    String compileExec = "java -cp .;" + byntPath + ";" + asmPath + " org.bgdnstc.Main " + source1File;
                    System.out.println(compileExec);
                    appendTextCommand1("Saving...\nParsing...\n");
                    Process process = Runtime.getRuntime().exec(compileExec);
                    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String s;
                    System.out.println("Here is the standard error of the command (if any):\n");
                    while (true) {
                        try {
                            if ((s = stdError.readLine()) == null) break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(s);
                        appendTextCommand1(s);
                        error = true;
                    }
                    System.out.println("Here is the standard output of the command:\n");
                    while (true) {
                        try {
                            if ((s = stdInput.readLine()) == null) break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(s);
                        appendTextCommand1("Processing file: \"");
                        appendTextCommand1(s);
                        appendTextCommand1("\"\n");
                    }
                    System.out.println("Compiled!");
                }
                if (!error) {
                    appendTextCommand1("Compiled!\n\n");
                } else {
                    appendTextCommand1(("Error!\n\n"));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void compile2() {
        boolean error = false;
        try {
            if (byntPath == null || asmPath == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No Internal Library Paths");
                alert.setHeaderText("Compiler configuration file paths are missing!");
                alert.showAndWait();
            } else {
                if (source2File == null) {
                    saveFile2();
                    String compileExec = "java -cp .;" + byntPath + ";" + asmPath + " org.bgdnstc.Main " + source2File;
                    System.out.println(source2File);
                    appendTextCommand2("Saving...\nParsing...\n");
                    if (source2File != null) {
                        System.out.println(compileExec);
                        Process process = Runtime.getRuntime().exec(compileExec);
                        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s;
                        System.out.println("Here is the standard error of the command (if any):\n");
                        while (true) {
                            try {
                                if ((s = stdError.readLine()) == null) break;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println(s);
                            appendTextCommand2(s);
                            error = true;
                        }
                        System.out.println("Here is the standard output of the command:\n");
                        while (true) {
                            try {
                                if ((s = stdInput.readLine()) == null) break;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println(s);
                            appendTextCommand2("Processing file: \"");
                            appendTextCommand2(s);
                            appendTextCommand2("\"\n");
                        }
                        System.out.println("Compiled!");
                    }
                } else {
                    saveFile2();
                    String compileExec = "java -cp .;" + byntPath + ";" + asmPath + " org.bgdnstc.Main " + source2File;
                    System.out.println(source2File);
                    System.out.println(compileExec);
                    appendTextCommand2("Saving...\nParsing...\n");
                    Process process = Runtime.getRuntime().exec(compileExec);
                    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String s;
                    System.out.println("Here is the standard error of the command (if any):\n");
                    while (true) {
                        try {
                            if ((s = stdError.readLine()) == null) break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(s);
                        appendTextCommand2(s);
                        error = true;
                    }
                    System.out.println("Here is the standard output of the command:\n");
                    while (true) {
                        try {
                            if ((s = stdInput.readLine()) == null) break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(s);
                        appendTextCommand2("Processing file: \"");
                        appendTextCommand2(s);
                        appendTextCommand2("\"\n");
                    }
                    System.out.println("Compiled!");
                }
                if (!error) {
                    appendTextCommand2("Compiled!\n\n");
                } else {
                    appendTextCommand2(("Error!\n\n"));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void run1() {
        try {
            if (source1File == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No source");
                alert.setHeaderText("The source file is missing!");
                alert.showAndWait();
            } else {
                String[] s = source1File.toString().split("\\\\");
                String runExec = "java -cp .;" + outputPath + " " + s[s.length - 1].split("\\.")[0];
                Runtime.getRuntime().exec(runExec);
                System.out.println(runExec);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void run2() {
        try {
            if (source2File == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No source");
                alert.setHeaderText("The source file is missing!");
                alert.showAndWait();
            } else {
                String[] s = source2File.toString().split("\\\\");
                String runExec = "java -cp .;" + outputPath + " " + s[s.length - 1].split("\\.")[0];
                Runtime.getRuntime().exec(runExec);
                System.out.println(runExec);
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

    private void appendTextCommand1(String string) {
        textAreaCommand1.appendText(string);
    }

    private void appendTextCommand2(String string) {
        textAreaCommand2.appendText(string);
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
