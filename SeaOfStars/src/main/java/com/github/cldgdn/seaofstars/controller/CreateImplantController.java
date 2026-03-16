package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.SeaOfStarsMain;
import com.github.cldgdn.seaofstars.LibCache;
import com.github.cldgdn.seaofstars.Utils;
import com.github.cldgdn.seaofstars.character.Ability;
import com.github.cldgdn.seaofstars.character.Bonus;
import com.github.cldgdn.seaofstars.character.Implant;
import com.github.cldgdn.seaofstars.character.Trait;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateImplantController {

    @FXML private Button abilityBonusButton;
    @FXML private VBox bonusesVbox;
    @FXML private HBox costHBox;
    @FXML private ComboBox<Integer> costValueCombo;
    @FXML private TextArea descriptionArea;
    @FXML private TextField nameField;
    @FXML private TextField locationField;
    @FXML private Button otherBonusButton;
    @FXML private Button traitBonusButton;

    private List<BonusElementController<Trait>> traitBonusControllers = new ArrayList<>();
    private List<BonusElementController<Ability>> abilityBonusControllers = new ArrayList<>();
    private List<BonusElementController<String>> otherBonusControllers = new ArrayList<>();

    @FXML
    void addBonus(ActionEvent event) throws BadSOSDFormatException, IOException {
        Object source = event.getSource();

        if (source instanceof Button) {
            FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("bonusElement.fxml"));
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
                bec.passiveCheckBox.setManaged(false);
                bec.passiveCheckBox.setDisable(true);
                bec.passiveCheckBox.setVisible(false);

                otherBonusControllers.add(bec);
            }

            bonusesVbox.getChildren().add(element);
        }
    }

    public void initialize() {
        costValueCombo.getItems().addAll(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
    }

    public Implant getImplant() {
        Implant i = new Implant();

        i.isCustom = true; //assumed custom, gets managed later if needed

        i.name = nameField.getText();
        i.ID = Utils.genID("imp", i.name);
        i.description = descriptionArea.getText();
        i.location = locationField.getText();
        i.humanCost = costValueCombo.getValue();

        for (BonusElementController<Trait> controller : traitBonusControllers) {
            if (controller.removed) continue;

            Bonus b = new Bonus();
            b.sourceID = i.ID;
            b.targetID = controller.attributeComboBox.getSelectionModel().getSelectedItem().ID;
            b.value = controller.bonusValueComboBox.getSelectionModel().getSelectedItem();
            b.isConditional = !controller.passiveCheckBox.isSelected();

            i.bonuses.add(b);
        }
        for (BonusElementController<Ability> controller : abilityBonusControllers) {
            if (controller.removed) continue;

            Bonus b = new Bonus();
            b.sourceID = i.ID;
            b.targetID = controller.attributeComboBox.getSelectionModel().getSelectedItem().ID;
            b.value = controller.bonusValueComboBox.getSelectionModel().getSelectedItem();
            b.isConditional = !controller.passiveCheckBox.isSelected();

            i.bonuses.add(b);
        }
        for (BonusElementController<String> controller : otherBonusControllers) {
            if (controller.removed) continue;

            Bonus b = new Bonus();
            b.sourceID = i.ID;

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
            b.isConditional = false;

            i.bonuses.add(b);
        }

        return i;
    }

}
