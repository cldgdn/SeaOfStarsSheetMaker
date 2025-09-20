package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.HelloApplication;
import com.github.cldgdn.seaofstars.LibCache;
import com.github.cldgdn.seaofstars.character.Ability;
import com.github.cldgdn.seaofstars.character.Character;
import com.github.cldgdn.seaofstars.character.CharacterInfo;
import com.github.cldgdn.seaofstars.character.Trait;
import com.github.cldgdn.seaofstars.controller.dialog.CreateAdvantageDialog;
import com.github.cldgdn.seaofstars.sosd.read.AbilitiesReader;
import com.github.cldgdn.seaofstars.sosd.read.TraitsReader;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.StringConcatException;
import java.util.ArrayList;

public class CharacterCreationController {

    public int traitPointsLeft = Constants.STARTING_TRAIT_POINTS;
    public int abilityPointsLeft = Constants.STARTING_ABILITY_POINTS;
    private Character character = new Character();
    private CharacterInfo info = new CharacterInfo();
    private Trait[] traits;
    private Ability[] abilities;

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
    private Label abilityPointsLabel;

    @FXML
    private CheckBox libraryTraitsCheckBox;

    @FXML
    private TextField playerNameField;

    @FXML
    private VBox traitsVBox;

    @FXML
    private VBox abilitiesVBox;

    @FXML
    private CheckBox libraryAbilitiesCheckBox;

    @FXML
    private TextField bgNameField;

    @FXML
    private TextArea bdDescriptionArea;

    @FXML
    private ComboBox<Ability> p1BackgroundMenu;
    @FXML
    private ComboBox<Ability> p2BackgroundMenu;
    @FXML
    private ComboBox<Ability> m1BackgroundMenu;

    @FXML
    void toggleLibraryTraits(ActionEvent event) {
        //TODO
    }

    @FXML
    void toggleLibraryAbilities(ActionEvent event) {
        //TODO
    }

    @FXML
    void createNewTrait(ActionEvent event) {
        //TODO
    }

    @FXML
    void createNewAbility(ActionEvent event) {
        //TODO
    }

    @FXML
    void createNewAdvantage(ActionEvent event) {
        CreateAdvantageDialog dialog = new CreateAdvantageDialog();
        dialog.showAndWait().ifPresent(advantage -> {
            System.out.println("Advantage created.");
        });
    }

    public void initialize() {
        try {
            if (libraryTraitsCheckBox.isSelected()) {
                traits = LibCache.getTraits();
            }
            if (libraryAbilitiesCheckBox.isSelected()) {
                abilities = LibCache.getAbilities();
            }

            StringConverter<Ability> converter = new StringConverter<Ability>() {
                @Override
                public String toString(Ability ability) {
                    return ability == null ? null : ability.name;
                }

                @Override
                public Ability fromString(String s) {
                    return null;
                }
            };

            p1BackgroundMenu.setConverter(converter);
            p2BackgroundMenu.setConverter(converter);
            m1BackgroundMenu.setConverter(converter);

            for (Trait t : traits) {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("traitElement.fxml"));
                Node element = loader.load();

                TraitElementController tec = loader.getController();
                tec.setTrait(t);
                tec.setCreator(this);

                traitsVBox.getChildren().add(element);
            }

            for (Ability a : abilities) {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("abilityElement.fxml"));
                Node element = loader.load();

                AbilityElementController aec = loader.getController();
                aec.setAbility(a);
                aec.setCreator(this);

                abilitiesVBox.getChildren().add(element);

                p1BackgroundMenu.getItems().add(a);
                p2BackgroundMenu.getItems().add(a);
                m1BackgroundMenu.getItems().add(a);
            }

            updateText();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateText() {
        traitPointsLabel.setText(tpLabelPref + traitPointsLeft);
        abilityPointsLabel.setText(tpLabelPref + abilityPointsLeft);
    }

    public Character getCharacter() {
        return character;
    }

}
