package org.bgdnstc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(""));
        Scene scene = new Scene(fxmlLoader.load());
    }

    public static void main(String[] args) {
        launch();
    }
}
