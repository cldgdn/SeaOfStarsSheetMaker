package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.SeaOfStarsMain;
import com.github.cldgdn.seaofstars.character.*;
import com.github.cldgdn.seaofstars.character.Character;
import com.github.cldgdn.seaofstars.controller.dialog.CreateItemDialog;
import com.github.cldgdn.seaofstars.controller.dialog.HpUpdateDialog;
import com.github.cldgdn.seaofstars.controller.dialog.RollWithIncrementsDialog;
import com.github.cldgdn.seaofstars.sosd.read.CharacterReader;
import com.github.cldgdn.seaofstars.sosd.write.CharacterWriter;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CharacterScreenController {

    public Character character = null;
    private final String[] moneyNames = new String[]{"SUNC", "Crediti", "Sunnies"};

    private List<ItemElementController> itemControllers = new ArrayList<>();

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

    private String prevMoneyText = "";
    private List<String> rollHistory = new ArrayList<>();

    @FXML
    void refreshCharacter(ActionEvent event) {
        try {
            advantagesVBox.getChildren().clear();
            implantsVBox.getChildren().clear();
            itemsVBox.getChildren().clear();
            originVBox.getChildren().clear();
            backgroundVBox.getChildren().clear();

            setCharacter(character);
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
        RollWithIncrementsDialog dialog = new RollWithIncrementsDialog(character, rollHistory);
        Window window = dialog.getDialogPane().getScene().getWindow();
        if (window instanceof Stage stage) {
            stage.initModality(Modality.NONE);
        }
        dialog.showAndWait().ifPresent(history -> rollHistory = history);
    }

    @FXML
    void addItem(ActionEvent event) {
        CreateItemDialog dialog = new CreateItemDialog();
        dialog.showAndWait().ifPresent(item -> {
            character.items.add(item);
            refreshCharacter(null);
        });
    }

    @FXML
    void saveCharacterEdits(ActionEvent event) {
        character.background.description = backgroundDescriptionArea.getText();
        character.origin.description = originDescriptionArea.getText();
        character.info.fame = fameField.getText();

        try {
            CharacterWriter.write(character, "characters/" + character.info.name.replace(" ", "_"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void updateFameField(KeyEvent event) {
        character.info.fame = fameField.getText();
    }

    @FXML
    void updateMoneyField(KeyEvent event) {
        String currentMoneyText = moneyField.getText().trim();
        if (!prevMoneyText.equals(currentMoneyText)) {
            try {
                character.info.money = Integer.parseInt(currentMoneyText);
                prevMoneyText = currentMoneyText;
            } catch (NumberFormatException e) {
                moneyField.setText(prevMoneyText);
            }
        }
    }

    public void setCharacter(Character c) throws IOException, BadSOSDFormatException {
        long time = System.nanoTime();
        long timeTotal = time;
        this.character = c;

        System.out.println("[INFO] Preparing " + character.info.name + " ...");

        character.init();

        //BASIC CHARACTER INFORMATION DISPLAY
        characterNameField.setText(character.info.name);
        playerNameField.setText(character.info.playerName);
        fameField.setText(character.info.fame);
        moneyLabel.setText(moneyNames[ThreadLocalRandom.current().nextInt(moneyNames.length)]);
        moneyField.setText("" + character.info.money);
        hpField.setText(character.hitPoints + "/" + character.hitPointsMax);
        hlField.setText(character.usedHumanLimit + "/" + character.humanLimit);

        prevMoneyText = moneyField.getText();

        System.out.println("[INFO] Character information prepared. (" + ((System.nanoTime() - time) / 1000000.0f) + "ms)");
        time = System.nanoTime();

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

        System.out.println("[INFO] Character origin prepared. (" + ((System.nanoTime() - time) / 1000000.0f) + "ms)");
        time = System.nanoTime();

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

        System.out.println("[INFO] Character background prepared. (" + ((System.nanoTime() - time) / 1000000.0f) + "ms)");
        time = System.nanoTime();

        //TRAITS INFORMATION DISPLAY
        for (Trait t : character.traits) {
            FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("traitElement.fxml"));
            Node element = loader.load();

            TraitElementController controller = loader.getController();
            controller.setTrait(t);
            controller.setDisplayOnly();

            traitsVBox.getChildren().add(element);
        }

        System.out.println("[INFO] Character traits prepared. (" + ((System.nanoTime() - time) / 1000000.0f) + "ms)");
        time = System.nanoTime();

        //ABILITIES INFORMATION DISPLAY
        for (Ability a : character.abilities) {
            FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("abilityElement.fxml"));
            Node element = loader.load();

            AbilityElementController controller = loader.getController();
            controller.setAbility(a);
            controller.setDisplayOnly();

            abilitiesVBox.getChildren().add(element);
        }

        System.out.println("[INFO] Character abilities prepared. (" + ((System.nanoTime() - time) / 1000000.0f) + "ms)");
        time = System.nanoTime();

        //ADVANTAGES INFORMATION DISPLAY
        for (Advantage a : character.advantages) {
            FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("advantageElement.fxml"));
            Node element = loader.load();

            AdvantageElementController controller = loader.getController();
            controller.setAdvantage(a);
            controller.setDisplayOnly();

            advantagesVBox.getChildren().add(element);
        }

        System.out.println("[INFO] Character advantages prepared. (" + ((System.nanoTime() - time) / 1000000.0f) + "ms)");
        time = System.nanoTime();

        //IMPLANTS INFORMATION DISPLAY
        for (Implant i : character.implants) {
            FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("implantElement.fxml"));
            Node element = loader.load();

            ImplantElementController controller = loader.getController();
            controller.setImplant(i);
            controller.setDisplayOnly();

            implantsVBox.getChildren().add(element);
        }

        System.out.println("[INFO] Character implants prepared. (" + ((System.nanoTime() - time) / 1000000.0f) + "ms)");
        time = System.nanoTime();

        //ITEMS INFORMATION DISPLAY
        itemControllers.clear();
        for (Item i : character.items) {
            FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("itemElement.fxml"));
            Node element = loader.load();

            ItemElementController controller = loader.getController();
            controller.setItem(i);

            itemControllers.add(controller);
            itemsVBox.getChildren().add(element);
        }

        System.out.println("[INFO] Character inventory prepared. (" + ((System.nanoTime() - time) / 1000000.0f) + "ms)");
        System.out.println("[INFO] Done. (" + ((System.nanoTime() - timeTotal) / 1000000.0f) + "ms)\n");
    }
}
