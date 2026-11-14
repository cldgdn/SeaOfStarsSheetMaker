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

    public static String DEFAULT_CONFIG_PATH = "lib/config";
    public static String actualConfigPath = DEFAULT_CONFIG_PATH;

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
        new Constants(SeaOfStarsMain.actualConfigPath);

        System.out.println("[INFO] Using config file at: " + SeaOfStarsMain.actualConfigPath);
        System.out.println("[INFO] Character folder at: " + Constants.get.CHARACTERS_FOLDER);
        System.out.println("[INFO] Lib folder at: " + Constants.get.LIB_FOLDER + "\n\n");

        FXMLLoader fxmlLoader = new FXMLLoader(SeaOfStarsMain.class.getResource("startScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Utils.setStyle(scene);
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

        if (args.length != 0) {
            SeaOfStarsMain.actualConfigPath = args[0];
        }

        launch();
    }
}