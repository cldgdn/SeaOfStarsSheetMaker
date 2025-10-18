package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.SeaOfStarsMain;
import com.github.cldgdn.seaofstars.sosd.read.CharacterReader;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class CharacterElementController {

    @FXML
    private TextField characterNameField;

    @FXML
    private TextField playerNameField;

    private File file;


    @FXML
    void openCharacter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("characterScreen.fxml"));
            Parent root = loader.load();

            CharacterScreenController controller = loader.getController();
            controller.setCharacter(CharacterReader.read(file.getPath()));

            Stage stage = new Stage();
            stage.setTitle("Sea Of Stars - " + characterNameField.getText());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException | BadSOSDFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCharacter(String characterName, String playerName, File path) {
        characterNameField.setText(characterName);
        playerNameField.setText(playerName);
        this.file = path;
    }

}
