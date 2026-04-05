package com.github.cldgdn.seaofstars;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

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
        stage.setTitle("Sea Of Stars - Home (ver. " + Constants.get.VERSION + ")");
        stage.setScene(scene);
        stage.show();
        SeaOfStarsMain.stage = stage;
    }

    public static void main(String[] args) {
        //wrap the stderr stream to filter out the javaFX version mismatch warnings
        PrintStream originalErr = System.err;

        System.setErr(new PrintStream(new OutputStream() {
            private StringBuilder buffer = new StringBuilder();

            @Override
            public void write(int b) {
                if (b == '\n') {
                    String line = buffer.toString();
                    if (!line.contains("AVVERTENZA") && !line.contains("WARNING") && !line.contains("processValue")) {
                        originalErr.println(line);
                    }
                    buffer.setLength(0);
                } else {
                    buffer.append((char) b);
                }
            }
        }));

        launch();
    }
}