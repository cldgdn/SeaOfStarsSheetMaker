package com.github.cldgdn.seaofstars;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static HelloApplication instance = null;
    public static Stage stage;

    public HelloApplication() {
        if (instance == null) {
            instance = this;
        }
    }

    public void setStage(String title, Scene scene) {
        stage.setTitle(title);
        stage.setScene(scene);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("startScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sea Of Stars - Home");
        stage.setScene(scene);
        stage.show();
        HelloApplication.stage = stage;
    }

    public static void main(String[] args) {
        launch();
    }
}