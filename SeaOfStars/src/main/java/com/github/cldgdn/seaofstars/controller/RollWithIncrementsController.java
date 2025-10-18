package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.LibCache;
import com.github.cldgdn.seaofstars.RollManager;
import com.github.cldgdn.seaofstars.character.Ability;
import com.github.cldgdn.seaofstars.character.Bonus;
import com.github.cldgdn.seaofstars.character.Character;
import com.github.cldgdn.seaofstars.character.Trait;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Arrays;

public class RollWithIncrementsController {

    @FXML private ComboBox<Ability> abilityCombo;
    @FXML private ComboBox<Trait> traitCombo;
    @FXML private VBox activeAbilityBonusesVBox;
    @FXML private VBox activeTraitBonusesVBox;
    @FXML private Label rollResultLabel;

    public Character character;
    public int lastIncrementsRolled = 0;

    @FXML
    void callRoll(ActionEvent event) {
        Trait trait = traitCombo.getSelectionModel().getSelectedItem();
        Ability ability = abilityCombo.getSelectionModel().getSelectedItem();

        int[] rolls = RollManager.rollDice(
                ability.computedLevel,
                trait.modifier,
                Constants.RISK_DICE_FACES,
                ability.computedLevel >= Constants.DICE_EXPLODE_ABILITY_LEVEL
        );

        int increments = RollManager.countIncrements(rolls);

        lastIncrementsRolled = increments;

        rollResultLabel.setText(Arrays.toString(rolls) + " -> " + increments + " incrementi");
    }

    @FXML
    void updateTraitBonuses(ActionEvent event) {
        activeTraitBonusesVBox.getChildren().clear();

        Trait t = traitCombo.getSelectionModel().getSelectedItem();

        for (Bonus b : character.traitBonuses) {
            if (b.targetID.equals(t.ID)) {
                activeTraitBonusesVBox.getChildren().add(new Label(b.toString()));
            }
        }
    }

    @FXML
    void updateAbilityBonuses(ActionEvent event) {
        activeAbilityBonusesVBox.getChildren().clear();

        Ability a = abilityCombo.getSelectionModel().getSelectedItem();

        for (Bonus b : character.abilityBonuses) {
            if (b.targetID.equals(a.ID)) {
                activeAbilityBonusesVBox.getChildren().add(new Label(b.toString()));
            }
        }
    }

    public void setCharacter(Character character) {this.character = character;}

    public void setup() {
        abilityCombo.setConverter(new StringConverter<Ability>() {
            @Override
            public String toString(Ability ability) {
                return ability == null ? null : ability.name;
            }

            @Override
            public Ability fromString(String s) {
                return null;
            }
        });

        traitCombo.setConverter(new StringConverter<Trait>() {
            @Override
            public String toString(Trait trait) {
                return trait == null ? null : trait.name;
            }

            @Override
            public Trait fromString(String s) {
                return null;
            }
        });

        try {
            traitCombo.getItems().addAll(character.traits);
            abilityCombo.getItems().addAll(character.abilities);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
