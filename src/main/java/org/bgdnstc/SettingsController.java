package org.bgdnstc;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class SettingsController extends Application {
    @FXML
    private TextField textFieldByteNet;
    @FXML
    private TextField textFieldASM;
    @FXML
    private TextField textFieldOutput;

    @FXML
    private void initialize() {
    }

    @FXML
    private void apply() {
        if (!textFieldByteNet.getCharacters().isEmpty()) {
            try {
                Files.write(Path.of("src/main/resources/org/bgdnstc/configBynt.txt"), textFieldByteNet.getText().getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!textFieldASM.getCharacters().isEmpty()) {
            try {
                Files.write(Path.of("src/main/resources/org/bgdnstc/configASM.txt"), (textFieldASM.getText()).getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!textFieldOutput.getCharacters().isEmpty()) {
            try {
                Files.write(Path.of("src/main/resources/org/bgdnstc/configOut.txt"), (textFieldOutput.getText().getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void close(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScene.fxml")));
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
