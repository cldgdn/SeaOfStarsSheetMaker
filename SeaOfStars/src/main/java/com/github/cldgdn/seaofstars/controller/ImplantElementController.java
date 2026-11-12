package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.character.Bonus;
import com.github.cldgdn.seaofstars.character.Character;
import com.github.cldgdn.seaofstars.character.Implant;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ImplantElementController {
    private Implant implant;
    private CharacterCreationController creationController;
    private CharacterScreenController screenController;
    private boolean isDisplayOnly = false;

    @FXML private HBox bonusesHBox;
    @FXML private Label costLabel;
    @FXML private TextArea descriptionArea;
    @FXML private Label locationLabel;
    @FXML private Label nameLabel;
    @FXML private CheckBox selectionBox;
    @FXML private CheckBox overclockCheckBox;

    public void setDisplayOnly() {
        selectionBox.setVisible(false);
        selectionBox.setManaged(false);
        overclockCheckBox.setVisible(true);
        isDisplayOnly = true;
    }

    public void setCreationController(CharacterCreationController creationController) {
        this.creationController = creationController;
    }

    public void setScreenController(CharacterScreenController screenController) {
        this.screenController = screenController;
    }

    public void setImplant(Implant i) throws BadSOSDFormatException, IOException {
        this.implant = i;

        nameLabel.setText(implant.name);
        descriptionArea.setText(implant.description);
        costLabel.setText("Costo umano: " + implant.getCost());
        locationLabel.setText("Posizione: " + implant.location);

        if (implant.isCustom) {
            descriptionArea.setEditable(true);
        }

        overclockCheckBox.setSelected(implant.isOverclocked);

        setBonuses();
    }

    @FXML
    void toggleSelected(ActionEvent event) {
        if (isDisplayOnly) return;

        Character character = creationController.character;

        if (selectionBox.isSelected()) {
            character.addImplant(implant);
            creationController.updateText();
        } else {
            if (character.implants.contains(implant)) {
                character.removeImplant(implant.ID);
                creationController.updateText();
            }
        }
    }

    @FXML
    void updateDescription(KeyEvent event) {
        implant.description = descriptionArea.getText();
    }


    @FXML
    void overclockToggle(ActionEvent event) {
        if (!isDisplayOnly) {
            return;
        }

        implant.isOverclocked = overclockCheckBox.isSelected();
        costLabel.setText("Costo umano: " + implant.getCost());


        setBonuses();
        screenController.character.init();
        screenController.refreshCharacter(null);
    }

    void setBonuses() {
        bonusesHBox.getChildren().clear();

        for (Bonus b : implant.bonuses) {
            Label currBonus = new Label();

            currBonus.setText(b.toString(implant.getMultiplier()));

            bonusesHBox.getChildren().add(currBonus);
        }
    }
}
