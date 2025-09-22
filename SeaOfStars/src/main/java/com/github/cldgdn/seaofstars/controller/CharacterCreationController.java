package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.HelloApplication;
import com.github.cldgdn.seaofstars.LibCache;
import com.github.cldgdn.seaofstars.character.*;
import com.github.cldgdn.seaofstars.character.Character;
import com.github.cldgdn.seaofstars.controller.dialog.CreateAdvantageDialog;
import com.github.cldgdn.seaofstars.controller.dialog.CreateImplantDialog;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CharacterCreationController {

    public int traitPointsLeft = Constants.STARTING_TRAIT_POINTS;
    public int abilityPointsLeft = Constants.STARTING_ABILITY_POINTS;
    public int advantagesLeft = Constants.ADVANTAGES_AT_CREATION_AMT;
    private Character character = new Character();
    private CharacterInfo info = new CharacterInfo();
    private Trait[] traits;
    private Ability[] abilities;
    private Advantage[] advantages;
    private Implant[] implants;
    private List<Advantage> customAdvantages = new ArrayList<>();
    private List<Implant> customImplants = new ArrayList<>();

    private final String hpLabelPref = "Punti Ferita: ";
    private final String hlLabelPref = "Limite Umano: ";
    private final String tpLabelPref = "Punti da assegnare: ";

    @FXML private Label hlLabel;
    @FXML private Label hpLabel;

    @FXML private TextField characterAgeField;
    @FXML private TextField characterFameField;
    @FXML private TextField characterNameField;
    @FXML private TextField playerNameField;

    @FXML private VBox traitsVBox;
    @FXML private CheckBox libraryTraitsCheckBox;
    @FXML private Label traitPointsLabel;

    @FXML private VBox abilitiesVBox;
    @FXML private CheckBox libraryAbilitiesCheckBox;
    @FXML private Label abilityPointsLabel;

    @FXML private VBox advantagesVBox;
    @FXML private CheckBox libraryAdvantagesCheckBox;
    @FXML private Label advantagePointsLabel;

    @FXML private VBox implantsVBox;
    @FXML private CheckBox libraryImplantsCheckBox;
    @FXML private Label implantsPointsLabel;

    @FXML private TextField bgNameField;
    @FXML private TextArea bgDescriptionArea;
    @FXML private ComboBox<Ability> p1BackgroundMenu;
    @FXML private ComboBox<Ability> p2BackgroundMenu;
    @FXML private ComboBox<Ability> m1BackgroundMenu;

    @FXML private TextField ogNameField;
    @FXML private TextArea ogDescriptionArea;
    @FXML private ComboBox<Trait> originBonusAMenu;
    @FXML private ComboBox<Trait> originBonusBMenu;

    public CharacterCreationController() {
        character.implants = new ArrayList<>();
        character.advantages = new ArrayList<>();
        character.items = new ArrayList<>();
    }

    @FXML
    void toggleLibraryTraits(ActionEvent event) {
        //TODO
    }
    @FXML
    void toggleLibraryAbilities(ActionEvent event) {
        //TODO
    }
    @FXML
    void toggleLibraryAdvantages(ActionEvent event) {
        //TODO
    }

    @FXML
    void createNewTrait(ActionEvent event) {
        //TODO
    }

    @FXML
    void createNewAbility(ActionEvent event) {
        //TODO
    }

    @FXML
    void createNewAdvantage(ActionEvent event) {
        CreateAdvantageDialog dialog = new CreateAdvantageDialog();
        dialog.showAndWait().ifPresent(advantage -> {
            customAdvantages.add(advantage);

            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("advantageElement.fxml"));
                Node element = loader.load();

                AdvantageElementController aec = loader.getController();

                aec.setAdvantage(advantage);
                aec.setController(this);

                advantagesVBox.getChildren().add(element);
            } catch (BadSOSDFormatException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    @FXML
    void createNewImplant(ActionEvent event) {
        CreateImplantDialog dialog = new CreateImplantDialog();
        dialog.showAndWait().ifPresent(implant -> {
            //TODO
        });
    }

    @FXML
    void updateCharacter(ActionEvent event) {

    }

    public void initialize() {
        try {
            if (libraryTraitsCheckBox.isSelected()) {
                traits = LibCache.getTraits();
            }
            if (libraryAbilitiesCheckBox.isSelected()) {
                abilities = LibCache.getAbilities();
            }
            if (libraryAdvantagesCheckBox.isSelected()) {
                advantages = LibCache.getAdvantages();
            }

            StringConverter<Ability> abilityConverter = new StringConverter<Ability>() {
                @Override
                public String toString(Ability ability) {
                    return ability == null ? null : ability.name;
                }

                @Override
                public Ability fromString(String s) {
                    return null;
                }
            };

            StringConverter<Trait> traitConverter = new StringConverter<Trait>() {
                @Override
                public String toString(Trait trait) {
                    return trait == null ? null : trait.name;
                }

                @Override
                public Trait fromString(String s) {
                    return null;
                }
            };

            p1BackgroundMenu.setConverter(abilityConverter);
            p2BackgroundMenu.setConverter(abilityConverter);
            m1BackgroundMenu.setConverter(abilityConverter);

            originBonusAMenu.setConverter(traitConverter);
            originBonusBMenu.setConverter(traitConverter);

            p1BackgroundMenu.getItems().addAll(abilities);
            p2BackgroundMenu.getItems().addAll(abilities);
            m1BackgroundMenu.getItems().addAll(abilities);

            originBonusAMenu.getItems().addAll(traits);
            originBonusBMenu.getItems().addAll(traits);

            for (Trait t : traits) {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("traitElement.fxml"));
                Node element = loader.load();

                TraitElementController tec = loader.getController();
                tec.setTrait(t);
                tec.setCreator(this);

                traitsVBox.getChildren().add(element);
            }

            for (Ability a : abilities) {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("abilityElement.fxml"));
                Node element = loader.load();

                AbilityElementController aec = loader.getController();
                aec.setAbility(a);
                aec.setCreator(this);

                abilitiesVBox.getChildren().add(element);
            }

            for (Advantage a : advantages) {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("advantageElement.fxml"));
                Node element = loader.load();

                AdvantageElementController aec = loader.getController();
                aec.setAdvantage(a);
                aec.setController(this);

                advantagesVBox.getChildren().add(element);
            }

            updateText();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateText() {
        traitPointsLabel.setText(tpLabelPref + traitPointsLeft);
        abilityPointsLabel.setText(tpLabelPref + abilityPointsLeft);
        advantagePointsLabel.setText(tpLabelPref + advantagesLeft);
    }

    public Character getCharacter() {
        return character;
    }
}
