package org.bgdnstc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("GridScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image(new File("src/main/resources/org/bgdnstc/ByteNetIcon.png").toURI().toString());
        System.out.println(icon.getHeight());
        System.out.println(icon.getWidth());
        stage.getIcons().add(icon);
        stage.setTitle("ByteNet");
        stage.setOnCloseRequest(event -> {
            event.consume();
            logout(stage);
        });
        stage.setScene(scene);
        stage.show();
    }

    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit application");
        alert.setHeaderText("Do you want to close the application?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
