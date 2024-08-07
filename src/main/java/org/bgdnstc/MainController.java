package org.bgdnstc;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainController extends Application {
    private static Path source1File = null;
    private static Path source2File = null;
    private static Path asmPath = null;
    private static Path byntPath = null;
    private static Path outputPath = null;
    @FXML
    private ImageView imageViewHeader;
    @FXML
    private TextArea textAreaSource1;
    @FXML
    private TextArea textAreaSource2;
    @FXML
    private TextArea textAreaCommand1;
    @FXML
    private TextArea textAreaCommand2;

    @FXML
    public void initialize() {
        Image header = new Image(new File("src/main/resources/org/bgdnstc/images/ByteNetHeader.png").toURI().toString());
        imageViewHeader.setImage(header);
        try {
            byntPath = !Files.readString(Path.of("src/main/resources/org/bgdnstc/config/configBynt.txt")).isEmpty() ? Path.of(Files.readString(Path.of("src/main/resources/org/bgdnstc/config/configBynt.txt"))) : null;
            asmPath = !Files.readString(Path.of("src/main/resources/org/bgdnstc/config/configASM.txt")).isEmpty() ? Path.of(Files.readString(Path.of("src/main/resources/org/bgdnstc/config/configASM.txt"))) : null;
            outputPath = !Files.readString(Path.of("src/main/resources/org/bgdnstc/config/configOut.txt")).isEmpty() ? Path.of(Files.readString(Path.of("src/main/resources/org/bgdnstc/config/configOut.txt"))) : null;
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
        try {
            if (!textAreaSource1.getParagraphs().toArray()[0].toString().isEmpty()) {
                if (source1File != null) {
                    Files.write(Path.of(source1File.toString()), textAreaSource1.getText().getBytes());
                } else {
                    source1File = Path.of("source1.bynt").toAbsolutePath();
                    Files.write(Path.of("source1.bynt"), textAreaSource1.getParagraphs());
                }
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
                    appendTextCommand1("\nSaving...\nParsing...\n");
                    if (source1File != null) {
                        Process process = Runtime.getRuntime().exec(compileExec);
                        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s;
                        while (true) {
                            try {
                                if ((s = stdError.readLine()) == null) break;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            appendTextCommand1(s);
                            error = true;
                        }
                        while (true) {
                            try {
                                if ((s = stdInput.readLine()) == null) break;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            appendTextCommand1("Processing file: \"");
                            appendTextCommand1(s);
                            appendTextCommand1("\"\n");
                        }
                    }
                } else {
                    saveFile1();
                    String compileExec = "java -cp .;" + byntPath + ";" + asmPath + " org.bgdnstc.Main " + source1File;
                    appendTextCommand1("\nSaving...\nParsing...\n");
                    Process process = Runtime.getRuntime().exec(compileExec);
                    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String s;
                    while (true) {
                        try {
                            if ((s = stdError.readLine()) == null) break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        appendTextCommand1(s);
                        error = true;
                    }
                    while (true) {
                        try {
                            if ((s = stdInput.readLine()) == null) break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        appendTextCommand1("Processing file: \"");
                        appendTextCommand1(s);
                        appendTextCommand1("\"\n");
                    }
                }
                if (!error) {
                    appendTextCommand1("Compiled!\n");
                } else {
                    appendTextCommand1(("^^^Error!^^^\n"));
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
                    appendTextCommand2("\nSaving...\nParsing...\n");
                    if (source2File != null) {
                        Process process = Runtime.getRuntime().exec(compileExec);
                        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String s;
                        while (true) {
                            try {
                                if ((s = stdError.readLine()) == null) break;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            appendTextCommand2(s);
                            error = true;
                        }
                        while (true) {
                            try {
                                if ((s = stdInput.readLine()) == null) break;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            appendTextCommand2("Processing file: \"");
                            appendTextCommand2(s);
                            appendTextCommand2("\"\n");
                        }
                    }
                } else {
                    saveFile2();
                    String compileExec = "java -cp .;" + byntPath + ";" + asmPath + " org.bgdnstc.Main " + source2File;
                    appendTextCommand2("\nSaving...\nParsing...\n");
                    Process process = Runtime.getRuntime().exec(compileExec);
                    BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String s;
                    while (true) {
                        try {
                            if ((s = stdError.readLine()) == null) break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        appendTextCommand2(s);
                        error = true;
                    }
                    while (true) {
                        try {
                            if ((s = stdInput.readLine()) == null) break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        appendTextCommand2("Processing file: \"");
                        appendTextCommand2(s);
                        appendTextCommand2("\"\n");
                    }
                }
                if (!error) {
                    appendTextCommand2("Compiled!\n");
                } else {
                    appendTextCommand2(("^^^Error!^^^\n"));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void run1() {
        AtomicBoolean error = new AtomicBoolean(false);
        if (source1File == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No source");
            alert.setHeaderText("There is no source file to run!");
            alert.showAndWait();
        } else {
            String[] source1 = source1File.toString().split("\\\\");
            String runExec = "java -cp .;" + outputPath + " " + source1[source1.length - 1].split("\\.")[0];
            Platform.runLater(() -> appendTextCommand1("\nExecuting...\n"));
            new Thread(() -> {
                Process process = null;
                try {
                    process = Runtime.getRuntime().exec(runExec);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String s;
                while (true) {
                    try {
                        if ((s = stdError.readLine()) == null) break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String errorString = s;
                    Platform.runLater(() -> appendTextCommand1(errorString));
                    error.set(true);
                }
                while (true) {
                    try {
                        if ((s = stdInput.readLine()) == null) break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String stdOutputString = s;
                    Platform.runLater(() -> appendTextCommand1(stdOutputString));
                    Platform.runLater(() -> appendTextCommand1("\n"));
                }
                if (error.get()) {
                    Platform.runLater(() -> appendTextCommand1(("^^^Error!^^^\n")));
                } else {
                    Platform.runLater(() -> appendTextCommand1("\nExecution finished with success!\n"));
                }
            }).start();
        }
    }

    @FXML
    private void run2() {
        AtomicBoolean error = new AtomicBoolean(false);
        if (source2File == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No source");
            alert.setHeaderText("There is no source file to run!");
            alert.showAndWait();
        } else {
            String[] source2 = source2File.toString().split("\\\\");
            String runExec = "java -cp .;" + outputPath + " " + source2[source2.length - 1].split("\\.")[0];
            Platform.runLater(() -> appendTextCommand2("\nExecuting...\n"));
            new Thread(() -> {
                Process process = null;
                try {
                    process = Runtime.getRuntime().exec(runExec);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String s;
                while (true) {
                    try {
                        if ((s = stdError.readLine()) == null) break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    final String errorString = s;
                    Platform.runLater(() -> appendTextCommand2(errorString));
                    error.set(true);
                }
                while (true) {
                    try {
                        if ((s = stdInput.readLine()) == null) break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    final String stdOutputString = s;
                    Platform.runLater(() -> appendTextCommand2(stdOutputString));
                    Platform.runLater(() -> appendTextCommand2("\n"));
                }
                if (error.get()) {
                    Platform.runLater(() -> appendTextCommand2(("^^^Error!^^^\n")));
                } else {
                    Platform.runLater(() -> appendTextCommand2("\nExecution finished with success!\n"));
                }
            }).start();
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
