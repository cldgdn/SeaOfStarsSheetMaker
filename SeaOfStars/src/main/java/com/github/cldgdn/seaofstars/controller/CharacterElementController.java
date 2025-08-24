package com.github.cldgdn.seaofstars.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;

public class CharacterElementController {

    @FXML
    private TextField characterNameField;

    @FXML
    private TextField playerNameField;

    private File file;


    @FXML
    void openCharacter(ActionEvent event) {
        System.out.println(file);
    }

    public void setCharacter(String characterName, String playerName, File path) {
        characterNameField.setText(characterName);
        playerNameField.setText(playerName);
        this.file = path;
    }

}
