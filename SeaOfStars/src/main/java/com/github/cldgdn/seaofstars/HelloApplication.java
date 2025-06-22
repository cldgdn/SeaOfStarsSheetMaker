package com.github.cldgdn.seaofstars;

import com.github.cldgdn.seaofstars.character.Character;
import com.github.cldgdn.seaofstars.sosd.read.*;
import com.github.cldgdn.seaofstars.sosd.write.*;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

        long startTime = System.currentTimeMillis();
        System.out.println("number of increments: " + RollManager.rollAndCountIncrements(10, -2));
        long endTime = System.currentTimeMillis();

        System.out.println("time taken: " + (endTime - startTime) + "ms");
    }
}