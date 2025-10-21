package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.character.Trait;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;

public class TraitElementController {
    private Trait trait;
    private RadioButton[] radios;

    private CharacterCreationController creator;

    @FXML
    private Label levelLabel;

    @FXML
    private Label modLabel;

    @FXML
    private RadioButton radioButtonLv1;

    @FXML
    private RadioButton radioButtonLv10;

    @FXML
    private RadioButton radioButtonLv11;

    @FXML
    private RadioButton radioButtonLv12;

    @FXML
    private RadioButton radioButtonLv13;

    @FXML
    private RadioButton radioButtonLv14;

    @FXML
    private RadioButton radioButtonLv2;

    @FXML
    private RadioButton radioButtonLv3;

    @FXML
    private RadioButton radioButtonLv4;

    @FXML
    private RadioButton radioButtonLv5;

    @FXML
    private RadioButton radioButtonLv6;

    @FXML
    private RadioButton radioButtonLv7;

    @FXML
    private RadioButton radioButtonLv8;

    @FXML
    private RadioButton radioButtonLv9;

    @FXML
    private Tooltip descTooltip;


    @FXML
    private Label traitNameLabel;

    @FXML
    void updateLevel(ActionEvent event) {
        Object source = event.getSource();

        if (source instanceof RadioButton) {
            RadioButton radio = (RadioButton) source;

            int newLevel;

            for (int i = 0; i < radios.length; i++) {
                if (radios[i].getId().equals(radio.getId())) {
                    newLevel = i + 1;

                    if (newLevel < trait.baseLevel) {
                        creator.traitPointsLeft += trait.baseLevel - newLevel;
                        trait.baseLevel = newLevel;
                        creator.updateText();
                    }
                    else if (newLevel > trait.baseLevel) {
                        if (creator.traitPointsLeft >= (newLevel - trait.baseLevel)) {
                            creator.traitPointsLeft -= newLevel - trait.baseLevel;
                            trait.baseLevel = newLevel;
                            creator.updateText();
                        }
                    }

                    levelLabel.setText("Livello: " + trait.baseLevel);

                    int mod = (trait.baseLevel - Constants.get.ZERO_TRAIT_LEVEL);
                    modLabel.setText("Mod: " + ((mod >= 0) ? "+" : "") + mod);

                    break;
                }
            }

            for (int i = 0; i < radios.length; i++) {
                radios[i].setSelected(i + 1 <= trait.baseLevel);
            }
        }
    }

    public void initialize() {
        radios = new RadioButton[]{radioButtonLv1, radioButtonLv2, radioButtonLv3, radioButtonLv4, radioButtonLv5, radioButtonLv6, radioButtonLv7,
                radioButtonLv8, radioButtonLv9, radioButtonLv10, radioButtonLv11, radioButtonLv12, radioButtonLv13, radioButtonLv14};
    }

    void setTrait(Trait t) {
        trait = t;
        traitNameLabel.setText(t.name);
        levelLabel.setText("Livello: " + t.baseLevel);
        descTooltip.setText(trait.description);

        int mod = (t.baseLevel - Constants.get.ZERO_TRAIT_LEVEL);
        modLabel.setText("Mod: " + ((mod >= 0) ? "+" : "") + mod);

        for (int i = 0; i < t.baseLevel; i++) {
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
}
