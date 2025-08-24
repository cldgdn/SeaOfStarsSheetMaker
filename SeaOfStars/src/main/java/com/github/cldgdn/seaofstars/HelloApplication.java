package com.github.cldgdn.seaofstars;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static HelloApplication instance = null;
    public static Stage stage;
    public String balls = "cringe";

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
        balls = "based";
        HelloApplication.stage = stage;
    }

    public static void main(String[] args) {
        launch();

        long startTime = System.currentTimeMillis();
        System.out.println("number of increments: " + RollManager.rollAndCountIncrements(10, 0));
        long endTime = System.currentTimeMillis();

        System.out.println("time taken: " + (endTime - startTime) + "ms");
    }
}