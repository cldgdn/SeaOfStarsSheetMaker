package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.HelloApplication;
import com.github.cldgdn.seaofstars.LibCache;
import com.github.cldgdn.seaofstars.character.Ability;
import com.github.cldgdn.seaofstars.character.Trait;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;

public class CreateAdvantageController {

    @FXML
    private VBox bonusesVbox;

    @FXML private Button traitBonusButton;
    @FXML private Button abilityBonusButton;
    @FXML private Button otherBonusButton;

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
            }
            else if (b == otherBonusButton) {
                BonusElementController<String> bec = loader.getController();
                bec.setAttributeList(new String[]{"Punti Ferita", "Limite Umano"});
                bec.container = bonusesVbox;
                bec.element = element;
            }

            bonusesVbox.getChildren().add(element);
        }
    }

}