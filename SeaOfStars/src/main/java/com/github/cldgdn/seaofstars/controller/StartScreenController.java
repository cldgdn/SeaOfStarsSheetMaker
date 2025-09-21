package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.HelloApplication;
import com.github.cldgdn.seaofstars.character.Advantage;
import com.github.cldgdn.seaofstars.character.CharacterInfo;
import com.github.cldgdn.seaofstars.controller.dialog.CreateAdvantageDialog;
import com.github.cldgdn.seaofstars.controller.dialog.CreateCharacterDialog;
import com.github.cldgdn.seaofstars.sosd.read.InfoReader;
import com.github.cldgdn.seaofstars.sosd.write.AdvantagesWriter;
import com.github.cldgdn.seaofstars.sosd.write.CharacterWriter;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
public class StartScreenController {

    @FXML
    private VBox charactersVBox;

    @FXML
    private MenuBar menuBar;

    @FXML
    void createNewAdvantage(ActionEvent event) {
        CreateAdvantageDialog dialog = new CreateAdvantageDialog();
        dialog.showAndWait().ifPresent(advantage -> {
            try {
                AdvantagesWriter.writeSingle(advantage, "lib");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void createNewCharacter(ActionEvent event) throws IOException {
        /*FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("characterCreation.fxml"));

        Scene scene = new Scene(loader.load());

        HelloApplication application = HelloApplication.instance;

        application.setStage("Sea Of Stars - Creazione Personaggio", scene);*/

        CreateCharacterDialog dialog = new CreateCharacterDialog();
        dialog.showAndWait().ifPresent(character -> {
            try {
                CharacterWriter.write(character, "characters");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    @FXML
    void createNewImplant(ActionEvent event) {

    }

    @FXML
    void createNewItem(ActionEvent event) {

    }

    @FXML
    void openHowTo(ActionEvent event) {

    }

    @FXML
    void openRules(ActionEvent event) {

    }

    public void initialize() throws IOException, BadSOSDFormatException {
        File characterFolder = new File("characters");
        if (characterFolder.exists() && characterFolder.isDirectory()) {
            File[] files = characterFolder.listFiles();

            if (files == null)
                return;

            for (File character : files) {
                if (character.isDirectory()) {
                    FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("characterElement.fxml"));
                    Node element = loader.load();

                    CharacterElementController cec = loader.getController();

                    CharacterInfo info = InfoReader.read(character.getPath());

                    cec.setCharacter(info.name, info.playerName, character);

                    charactersVBox.getChildren().add(element);
                }
            }
        }
    }

}
