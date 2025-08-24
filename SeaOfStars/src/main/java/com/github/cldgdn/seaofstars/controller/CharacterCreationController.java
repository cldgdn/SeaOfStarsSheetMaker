package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.HelloApplication;
import com.github.cldgdn.seaofstars.character.Character;
import com.github.cldgdn.seaofstars.character.CharacterInfo;
import com.github.cldgdn.seaofstars.character.Trait;
import com.github.cldgdn.seaofstars.sosd.read.TraitsReader;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CharacterCreationController {

    public int traitPointsLeft = Constants.STARTING_TRAIT_POINTS;
    private Character character = new Character();
    private CharacterInfo info = new CharacterInfo();
    private Trait[] traits;

    private final String hpLabelPref = "Punti Ferita: ";
    private final String hlLabelPref = "Limite Umano: ";
    private final String tpLabelPref = "Punti da assegnare: ";


    public CharacterCreationController() {
        character.implants = new ArrayList<>();
        character.advantages = new ArrayList<>();
        character.items = new ArrayList<>();
    }


    @FXML
    private TextField characterAgeField;

    @FXML
    private TextField characterFameField;

    @FXML
    private TextField characterNameField;

    @FXML
    private Label hlLabel;

    @FXML
    private Label hpLabel;

    @FXML
    private Label traitPointsLabel;

    @FXML
    private CheckBox libraryTraitsCheckBox;

    @FXML
    private TextField playerNameField;

    @FXML
    private VBox traitsVBox;

    @FXML
    void cancelCreation(ActionEvent event) {

    }

    @FXML
    void createNewTrait(ActionEvent event) {

    }

    @FXML
    void saveCreation(ActionEvent event) {

    }

    @FXML
    void toggleLibraryTraits(ActionEvent event) {

    }

    public void initialize() {
        try {
            if (libraryTraitsCheckBox.isSelected()) {
                fetchLibraryTraits();
            }

            for (Trait t : traits) {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("traitElement.fxml"));
                Node element = loader.load();

                TraitElementController tec = loader.getController();
                tec.setTrait(t);
                tec.setCreator(this);

                traitsVBox.getChildren().add(element);
            }

            updateText();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchLibraryTraits() throws BadSOSDFormatException, IOException {
        File traitsFolder = new File("lib\\traits");

        if (traitsFolder.exists() && traitsFolder.isDirectory()) {
            File[] traitFiles = traitsFolder.listFiles();

            if (traitFiles != null && traitFiles.length != 0) {
                traits = new Trait[traitFiles.length];

                for (int i = 0; i < traitFiles.length; i++) {
                    traits[i] = TraitsReader.readSingle(traitFiles[i], false, Constants.ZERO_TRAIT_LEVEL);
                }
            }
        }
    }

    public void updateText() {
        traitPointsLabel.setText(tpLabelPref + traitPointsLeft);
    }

}
