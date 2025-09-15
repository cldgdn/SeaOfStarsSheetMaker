package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.character.Ability;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;

public class AbilityElementController {

    private Ability ability;
    private RadioButton[] radios;
    private CharacterCreationController creator;

    @FXML
    private Label levelLabel;

    @FXML
    private RadioButton lv10Button;

    @FXML
    private RadioButton lv1Button;

    @FXML
    private RadioButton lv2Button;

    @FXML
    private RadioButton lv3Button;

    @FXML
    private RadioButton lv4Button;

    @FXML
    private RadioButton lv5Button;

    @FXML
    private RadioButton lv6Button;

    @FXML
    private RadioButton lv7Button;

    @FXML
    private RadioButton lv8Button;

    @FXML
    private RadioButton lv9Button;

    @FXML
    private Label nameLabel;

    @FXML
    private Tooltip nameTooltip;

    @FXML
    void updateLevel(ActionEvent event) {
        Object source = event.getSource();

        if (source instanceof RadioButton) {
            RadioButton radio = (RadioButton) source;

            int newLevel;

            for (int i = 0; i < radios.length; i++) {
                if (radios[i].getId().equals(radio.getId())) {
                    newLevel = i + 1;

                    if (newLevel < ability.baseLevel) {
                        creator.abilityPointsLeft += ability.baseLevel - newLevel;
                        ability.baseLevel = newLevel;
                        creator.updateText();
                    }
                    else if (newLevel > ability.baseLevel) {
                        if (creator.abilityPointsLeft >= (newLevel - ability.baseLevel)) {
                            creator.abilityPointsLeft -= newLevel - ability.baseLevel;
                            ability.baseLevel = newLevel;
                            creator.updateText();
                        }
                    }

                    levelLabel.setText("Livello: " + ability.baseLevel);

                    break;
                }
            }

            for (int i = 0; i < radios.length; i++) {
                radios[i].setSelected(i + 1 <= ability.baseLevel);
            }
        }
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
        nameLabel.setText(ability.name);
        levelLabel.setText("Livello: " + ability.computedLevel);

        for (int i = 0; i < ability.computedLevel; i++) {
            radios[i].setSelected(true);
        }
    }

    void setCreator(CharacterCreationController creator) {
        this.creator = creator;
    }

    void setDisplayOnly() {
        for (RadioButton radio : radios) {
            radio.setDisable(true);
        }
    }

    public void initialize() {
        radios = new RadioButton[]{lv1Button, lv2Button, lv3Button, lv4Button, lv5Button,
            lv6Button, lv7Button, lv8Button, lv9Button, lv10Button};
    }

}
