package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.RollManager;
import com.github.cldgdn.seaofstars.character.*;
import com.github.cldgdn.seaofstars.character.Character;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.List;

public class RollWithIncrementsController {

    @FXML private ComboBox<Ability> abilityCombo;
    @FXML private ComboBox<Trait> traitCombo;
    @FXML private VBox activeAbilityBonusesVBox;
    @FXML private VBox activeTraitBonusesVBox;
    @FXML private Label rollResultLabel;
    @FXML private ComboBox<Integer> bonusDiceBox;
    @FXML private VBox rollHistoryVBox;

    public Character character;
    public int lastIncrementsRolled = 0;
    public List<String> rollHistory;

    private int activeAbilityBonusTotal = 0;
    private int activeTraitBonusTotal = 0;

    @FXML
    void callRoll(ActionEvent event) {
        Trait trait = traitCombo.getSelectionModel().getSelectedItem();
        Ability ability = abilityCombo.getSelectionModel().getSelectedItem();
        int bonusDice = bonusDiceBox.getSelectionModel().getSelectedItem();

        int[] rolls = RollManager.rollDice(
                ability.computedLevel + activeAbilityBonusTotal + bonusDice,
                trait.modifier + activeTraitBonusTotal,
                Constants.get.RISK_DICE_FACES,
                ability.computedLevel >= Constants.get.DICE_EXPLODE_ABILITY_LEVEL
        );

        int increments = RollManager.countIncrements(rolls);

        lastIncrementsRolled = increments;

        String rollText =
                trait.name + (trait.modifier + activeTraitBonusTotal < 0 ? " " : " +") + (trait.modifier + activeTraitBonusTotal) + " + " +
                ability.name + " " + (ability.computedLevel + activeAbilityBonusTotal) +
                "(" + (bonusDice < 0 ? "" : "+") + bonusDice + ") -> " +
                Arrays.toString(rolls) + " -> " + increments + " incrementi";

        rollResultLabel.setText(rollText);
        rollHistory.add(rollText);
        rollHistoryVBox.getChildren().add(new Label(rollText));
        bonusDiceBox.getSelectionModel().select(Integer.valueOf(0));
    }

    @FXML
    void updateTraitBonuses(ActionEvent event) {
        activeTraitBonusesVBox.getChildren().clear();

        Trait t = traitCombo.getSelectionModel().getSelectedItem();

        for (Bonus b : character.traitBonuses) {
            if (b.targetID.equals(t.ID)) {
                Label l = new Label(b.toString() + " - " + findSource(b));
                if (b.isConditional) {
                    HBox hBox = new HBox();
                    CheckBox cb = new CheckBox();

                    cb.setOnAction(actionEvent -> {
                        if (cb.isSelected()) {
                            activeTraitBonusTotal += b.value;
                        } else {
                            activeTraitBonusTotal -= b.value;
                        }
                    });

                    hBox.setSpacing(5);
                    hBox.getChildren().addAll(cb, l);
                    activeTraitBonusesVBox.getChildren().add(hBox);
                } else {
                    activeTraitBonusesVBox.getChildren().add(l);
                }
            }
        }
    }

    @FXML
    void updateAbilityBonuses(ActionEvent event) {
        activeAbilityBonusesVBox.getChildren().clear();

        Ability a = abilityCombo.getSelectionModel().getSelectedItem();

        for (Bonus b : character.abilityBonuses) {
            if (b.targetID.equals(a.ID)) {
                Label l = new Label(b.toString() + " - " + findSource(b));
                if (b.isConditional) {
                    HBox hBox = new HBox();
                    CheckBox cb = new CheckBox();

                    cb.setOnAction(actionEvent -> {
                        if (cb.isSelected()) {
                            activeAbilityBonusTotal += b.value;
                        } else {
                            activeAbilityBonusTotal -= b.value;
                        }
                    });

                    hBox.setSpacing(5);
                    hBox.getChildren().addAll(cb, l);
                    activeAbilityBonusesVBox.getChildren().add(hBox);
                } else {
                    activeAbilityBonusesVBox.getChildren().add(l);
                }
            }
        }
    }

    private String findSource(Bonus b) {
        String sourceName = "none";
        if (b.sourceID.startsWith("adv")) {
            for (Advantage a : character.advantages) {
                if (a.ID.equals(b.sourceID)) {
                    sourceName = a.name;
                    break;
                }
            }
        } else if (b.sourceID.startsWith("imp")) {
            for (Implant i : character.implants) {
                if (i.ID.equals(b.sourceID)) {
                    sourceName = i.name;
                    break;
                }
            }
        } else if (b.sourceID.startsWith("bkg")) {
            sourceName = character.background.name;
        } else if (b.sourceID.startsWith("ori")){
            sourceName = character.origin.name;
        }

        return sourceName;
    }

    public void setCharacter(Character character) {this.character = character;}
    public void setRollHistory(List<String> rollHistory) {this.rollHistory = rollHistory;}

    public void setup() {
        abilityCombo.setConverter(new StringConverter<Ability>() {
            @Override
            public String toString(Ability ability) {
                return ability == null ? null : ability.name + " (" + ability.computedLevel + ")";
            }

            @Override
            public Ability fromString(String s) {
                return null;
            }
        });

        traitCombo.setConverter(new StringConverter<Trait>() {
            @Override
            public String toString(Trait trait) {
                return trait == null ? null : trait.name + " (" + (trait.modifier < 0 ? "" : "+") + trait.modifier + ")";
            }

            @Override
            public Trait fromString(String s) {
                return null;
            }
        });

        try {
            traitCombo.getItems().addAll(character.traits);
            abilityCombo.getItems().addAll(character.abilities);
            bonusDiceBox.getItems().addAll(new Integer[]{6, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -6});
            bonusDiceBox.getSelectionModel().select(Integer.valueOf(0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (String roll : rollHistory) {
            rollHistoryVBox.getChildren().add(new Label(roll));
        }
    }
}
