package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.LibCache;
import com.github.cldgdn.seaofstars.character.Ability;
import com.github.cldgdn.seaofstars.character.Advantage;
import com.github.cldgdn.seaofstars.character.Bonus;
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

public class AdvantageElementController {

    private Advantage advantage;
    private CharacterCreationController controller;
    private boolean isDisplayOnly = false;

    @FXML private Label nameLabel;
    @FXML private Label costLabel;
    @FXML private TextArea descriptionArea;
    @FXML private HBox bonusesHBox;
    @FXML private CheckBox selectionBox;

    public void setAdvantage(Advantage advantage) throws BadSOSDFormatException, IOException {
        this.advantage = advantage;

        nameLabel.setText(advantage.name);
        costLabel.setText(advantage.isPassive ? "Effetto Passivo" : advantage.heroPointCost + " Punti Storia");
        descriptionArea.setText(advantage.description);

        if (advantage.isCustom) {
            descriptionArea.setEditable(true);
        }

        Trait[] traits = LibCache.getTraits();
        Ability[] abilities = LibCache.getAbilities();

        for (Bonus b : advantage.bonuses) {
            Label currBonus = new Label();

            currBonus.setText(b.toString());

            bonusesHBox.getChildren().add(currBonus);
        }
    }

    public void setDisplayOnly() {
        selectionBox.setVisible(false);
        selectionBox.setManaged(false);
        isDisplayOnly = true;
    }

    public void setController(CharacterCreationController controller) {
        this.controller = controller;
    }

    @FXML
    public void toggleSelected(ActionEvent event) {
        if (isDisplayOnly) return;

        if (selectionBox.isSelected()) {
            if (controller.advantagesLeft > 0) {
                controller.advantagesLeft--;
                controller.selectedAdvantages.add(advantage);
            }
            else {
                selectionBox.setSelected(false);
            }
        }
        else {
            controller.advantagesLeft += (controller.advantagesLeft >= Constants.get.ADVANTAGES_AT_CREATION_AMT) ? 0 : 1;
            controller.selectedAdvantages.remove(advantage);
        }

        controller.updateText();
    }

    @FXML
    void updateDescription(KeyEvent event) {
        advantage.description = descriptionArea.getText();
    }
}
