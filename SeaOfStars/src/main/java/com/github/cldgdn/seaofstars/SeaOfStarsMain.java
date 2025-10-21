package com.github.cldgdn.seaofstars;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SeaOfStarsMain extends Application {

    public static SeaOfStarsMain instance = null;
    public static Stage stage;

    public SeaOfStarsMain() {
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
        new Constants();
        FXMLLoader fxmlLoader = new FXMLLoader(SeaOfStarsMain.class.getResource("startScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sea Of Stars - Home");
        stage.setScene(scene);
        stage.show();
        SeaOfStarsMain.stage = stage;
    }

    public static void main(String[] args) {
        launch();
    }
}