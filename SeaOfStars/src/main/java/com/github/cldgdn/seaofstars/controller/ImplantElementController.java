package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.LibCache;
import com.github.cldgdn.seaofstars.character.Ability;
import com.github.cldgdn.seaofstars.character.Bonus;
import com.github.cldgdn.seaofstars.character.Character;
import com.github.cldgdn.seaofstars.character.Implant;
import com.github.cldgdn.seaofstars.character.Trait;
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
    private CharacterCreationController controller;
    private boolean isDisplayOnly = false;

    @FXML private HBox bonusesHBox;
    @FXML private Label costLabel;
    @FXML private TextArea descriptionArea;
    @FXML private Label locationLabel;
    @FXML private Label nameLabel;
    @FXML private CheckBox selectionBox;

    public void setDisplayOnly() {
        selectionBox.setVisible(false);
        selectionBox.setManaged(false);
        isDisplayOnly = true;
    }

    public void setController(CharacterCreationController controller) {
        this.controller = controller;
    }

    public void setImplant(Implant i) throws BadSOSDFormatException, IOException {
        this.implant = i;

        nameLabel.setText(implant.name);
        descriptionArea.setText(implant.description);
        costLabel.setText("Costo umano: " + implant.humanCost);
        locationLabel.setText("Posizione: " + implant.location);

        if (implant.isCustom) {
            descriptionArea.setEditable(true);
        }

        for (Bonus b : implant.bonuses) {
            Label currBonus = new Label();

            currBonus.setText(b.toString());

            bonusesHBox.getChildren().add(currBonus);
        }
    }

    @FXML
    void toggleSelected(ActionEvent event) {
        if (isDisplayOnly) return;

        Character character = controller.character;

        if (selectionBox.isSelected()) {
            if (character.usedHumanLimit + implant.humanCost < character.humanLimit) {
                character.addImplant(implant);
                controller.updateText();
            }
            else {
                selectionBox.setSelected(false);
            }
        } else {
            if (character.implants.contains(implant)) {
                character.removeImplant(implant.ID);
                controller.updateText();
            }
        }
    }

    @FXML
    void updateDescription(KeyEvent event) {
        implant.description = descriptionArea.getText();
    }

}
