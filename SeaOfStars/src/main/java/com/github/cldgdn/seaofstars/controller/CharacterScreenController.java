package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.SeaOfStarsMain;
import com.github.cldgdn.seaofstars.character.*;
import com.github.cldgdn.seaofstars.character.Character;
import com.github.cldgdn.seaofstars.controller.dialog.HpUpdateDialog;
import com.github.cldgdn.seaofstars.controller.dialog.RollWithIncrementsDialog;
import com.github.cldgdn.seaofstars.sosd.read.CharacterReader;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class CharacterScreenController {

    private Character character = null;
    private final String[] moneyNames = new String[]{"SUNC", "Crediti", "Sunnies"};

    @FXML private VBox abilitiesVBox;
    @FXML private VBox advantagesVBox;
    @FXML private TextArea backgroundDescriptionArea;
    @FXML private Label backgroundNameLabel;
    @FXML private VBox backgroundVBox;
    @FXML private TextField characterNameField;
    @FXML private TextField fameField;
    @FXML private TextField hlField;
    @FXML private TextField hpField;
    @FXML private VBox implantsVBox;
    @FXML private VBox itemsVBox;
    @FXML private TextField moneyField;
    @FXML private Label moneyLabel;
    @FXML private TextArea originDescriptionArea;
    @FXML private Label originNameLabel;
    @FXML private VBox originVBox;
    @FXML private TextField playerNameField;
    @FXML private VBox traitsVBox;


    @FXML
    void refreshCharacter(ActionEvent event) {
        try {
            Character c = CharacterReader.read(character.info.name);

            //TODO: make it so each vbox drops the elements from before so that setCharacter can put in the new ones
            advantagesVBox.getChildren().clear();
            implantsVBox.getChildren().clear();
            itemsVBox.getChildren().clear();

            setCharacter(c);
        } catch (BadSOSDFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void hpChange(ActionEvent event) {
        HpUpdateDialog dialog = new HpUpdateDialog();
        dialog.showAndWait().ifPresent(value -> {
            character.hitPoints += value;
            hpField.setText(character.hitPoints + "/" + character.hitPointsMax);
        });
    }

    @FXML
    void rollWithIncrements(ActionEvent event) {
        RollWithIncrementsDialog dialog = new RollWithIncrementsDialog(character);
        dialog.showAndWait();
    }


    public void setCharacter(Character c) throws IOException, BadSOSDFormatException {
        this.character = c;

        character.init();

        //BASIC CHARACTER INFORMATION DISPLAY
        characterNameField.setText(character.info.name);
        playerNameField.setText(character.info.playerName);
        fameField.setText(character.info.fame);
        moneyLabel.setText(moneyNames[ThreadLocalRandom.current().nextInt(moneyNames.length)]);
        moneyField.setText("" + character.info.money);
        hpField.setText(character.hitPoints + "/" + character.hitPointsMax);
        hlField.setText(character.usedHumanLimit + "/" + character.humanLimit);

        //ORIGIN INFORMATION DISPLAY
        originNameLabel.setText(character.origin.name);
        originDescriptionArea.setText(character.origin.description);

        for (Bonus b : character.origin.traitBonuses) {
            Label bLabel = new Label();
            String targetName = "nothing";

            for (Trait t : character.traits) {
                if (t.ID.equals(b.targetID)) {
                    targetName = "(T) " + t.name;
                    break;
                }
            }

            bLabel.setText(targetName + " " + (b.value >= 0 ? "+" + b.value : b.value));
            originVBox.getChildren().add(bLabel);
        }

        //BACKGROUND INFORMATION DISPLAY
        backgroundNameLabel.setText(character.background.name);
        backgroundDescriptionArea.setText(character.background.description);

        for (Bonus b : character.background.abilityBonuses) {
            Label bLabel = new Label();
            String targetName = "nothing";

            for (Ability a : character.abilities) {
                if (a.ID.equals(b.targetID)) {
                    targetName = "(A) " + a.name;
                    break;
                }
            }

            bLabel.setText(targetName + " " + (b.value >= 0 ? "+" + b.value : b.value));
            backgroundVBox.getChildren().add(bLabel);
        }

        //TRAITS INFORMATION DISPLAY
        for (Trait t : character.traits) {
            FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("traitElement.fxml"));
            Node element = loader.load();

            TraitElementController controller = loader.getController();
            controller.setTrait(t);
            controller.setDisplayOnly();

            traitsVBox.getChildren().add(element);
        }

        //ABILITIES INFORMATION DISPLAY
        for (Ability a : character.abilities) {
            FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("abilityElement.fxml"));
            Node element = loader.load();

            AbilityElementController controller = loader.getController();
            controller.setAbility(a);
            controller.setDisplayOnly();

            abilitiesVBox.getChildren().add(element);
        }

        //ADVANTAGES INFORMATION DISPLAY
        for (Advantage a : character.advantages) {
            FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("advantageElement.fxml"));
            Node element = loader.load();

            AdvantageElementController controller = loader.getController();
            controller.setAdvantage(a);
            controller.setDisplayOnly();

            advantagesVBox.getChildren().add(element);
        }

        //IMPLANTS INFORMATION DISPLAY
        for (Implant i : character.implants) {
            FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("implantElement.fxml"));
            Node element = loader.load();

            ImplantElementController controller = loader.getController();
            controller.setImplant(i);
            controller.setDisplayOnly();

            implantsVBox.getChildren().add(element);
        }
    }
}
