package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.HelloApplication;
import com.github.cldgdn.seaofstars.LibCache;
import com.github.cldgdn.seaofstars.Utils;
import com.github.cldgdn.seaofstars.character.Ability;
import com.github.cldgdn.seaofstars.character.Advantage;
import com.github.cldgdn.seaofstars.character.Bonus;
import com.github.cldgdn.seaofstars.character.Trait;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateAdvantageController {

    @FXML private HBox costHBox;
    @FXML private ComboBox<Integer> costValueCombo;
    @FXML private TextArea descriptionArea;
    @FXML private CheckBox isPassiveBox;
    @FXML private TextField nameField;
    @FXML private VBox bonusesVbox;
    @FXML private Button traitBonusButton;
    @FXML private Button abilityBonusButton;
    @FXML private Button otherBonusButton;

    private List<BonusElementController<Trait>> traitBonusControllers = new ArrayList<>();
    private List<BonusElementController<Ability>> abilityBonusControllers = new ArrayList<>();
    private List<BonusElementController<String>> otherBonusControllers = new ArrayList<>();

    @FXML
    void toggleCostHBox(ActionEvent event) {
        boolean display = !isPassiveBox.isSelected();

        costHBox.setVisible(display);
        costHBox.setManaged(display);
    }

    @FXML
    void addBonus(ActionEvent event) throws IOException, BadSOSDFormatException {
        Object source = event.getSource();

        if (source instanceof Button) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("bonusElement.fxml"));
            Node element = loader.load();

            Button b = (Button) source;

            if (b == traitBonusButton) {
                BonusElementController<Trait> bec = loader.getController();
                bec.setAttributeList(LibCache.getTraits());
                bec.container = bonusesVbox;
                bec.element = element;
                bec.attributeComboBox.setConverter(new StringConverter<Trait>() {
                    @Override
                    public String toString(Trait trait) {
                        return trait == null ? null : trait.name;
                    }

                    @Override public Trait fromString(String string) {return null;}
                });

                traitBonusControllers.add(bec);
            }
            else if (b == abilityBonusButton) {
                BonusElementController<Ability> bec = loader.getController();
                bec.setAttributeList(LibCache.getAbilities());
                bec.container = bonusesVbox;
                bec.element = element;
                bec.attributeComboBox.setConverter(new StringConverter<Ability>() {
                    @Override
                    public String toString(Ability ability) {
                        return ability == null ? null : ability.name;
                    }
                    @Override public Ability fromString(String string) {return null;}
                });

                abilityBonusControllers.add(bec);
            }
            else if (b == otherBonusButton) {
                BonusElementController<String> bec = loader.getController();
                bec.setAttributeList(new String[]{"Punti Ferita", "Limite Umano"});
                bec.container = bonusesVbox;
                bec.element = element;

                otherBonusControllers.add(bec);
            }

            bonusesVbox.getChildren().add(element);
        }
    }

    public void initialize() {
        costValueCombo.getItems().addAll(new Integer[]{1, 2, 3, 4});
    }

    public Advantage getAdvantage() {
        Advantage a = new Advantage();

        a.isCustom = true; //assumed custom, gets managed later if needed.

        a.name = nameField.getText();
        a.ID = Utils.genID("adv", a.name);
        a.description = descriptionArea.getText().replace("\n", " ");

        if (isPassiveBox.isSelected()) {
            a.isPassive = true;
        } else {
            a.isPassive = false;
            a.heroPointCost = costValueCombo.getSelectionModel().getSelectedItem();
        }

        for (BonusElementController<Trait> controller : traitBonusControllers) {
            if (controller.removed) break;

            Bonus b = new Bonus();
            b.sourceID = a.ID;
            b.targetID = controller.attributeComboBox.getSelectionModel().getSelectedItem().ID;
            b.value = controller.bonusValueComboBox.getSelectionModel().getSelectedItem();
            b.isConditional = !a.isPassive;
            a.bonuses.add(b);
        }
        for (BonusElementController<Ability> controller : abilityBonusControllers) {
            if (controller.removed) break;

            Bonus b = new Bonus();
            b.sourceID = a.ID;
            b.targetID = controller.attributeComboBox.getSelectionModel().getSelectedItem().ID;
            b.value = controller.bonusValueComboBox.getSelectionModel().getSelectedItem();
            b.isConditional = !a.isPassive;
            a.bonuses.add(b);
        }
        for (BonusElementController<String> controller : otherBonusControllers) {
            if (controller.removed) break;

            Bonus b = new Bonus();
            b.sourceID = a.ID;

            switch (controller.attributeComboBox.getSelectionModel().getSelectedItem()) {
                case "Punti Ferita": {
                    b.targetID = Constants.HIT_POINTS_MAX_ID;
                    break;
                }
                case "Limite Umano": {
                    b.targetID = Constants.HUMAN_LIMIT_ID;
                    break;
                }
                default: {
                    throw new RuntimeException("No Bonus trait selected");
                }
            }

            b.value = controller.bonusValueComboBox.getSelectionModel().getSelectedItem();
            b.isConditional = !a.isPassive;
            a.bonuses.add(b);
        }

        return a;
    }

}