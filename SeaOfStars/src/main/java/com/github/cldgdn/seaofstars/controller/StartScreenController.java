package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.SeaOfStarsMain;
import com.github.cldgdn.seaofstars.LibCache;
import com.github.cldgdn.seaofstars.character.Advantage;
import com.github.cldgdn.seaofstars.character.CharacterInfo;
import com.github.cldgdn.seaofstars.character.Implant;
import com.github.cldgdn.seaofstars.controller.dialog.CreateAdvantageDialog;
import com.github.cldgdn.seaofstars.controller.dialog.CreateCharacterDialog;
import com.github.cldgdn.seaofstars.controller.dialog.CreateImplantDialog;
import com.github.cldgdn.seaofstars.sosd.read.InfoReader;
import com.github.cldgdn.seaofstars.sosd.write.AdvantagesWriter;
import com.github.cldgdn.seaofstars.sosd.write.CharacterWriter;
import com.github.cldgdn.seaofstars.sosd.write.ImplantsWriter;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
public class StartScreenController {

    @FXML private Tab advantagesTab;
    @FXML private VBox advantagesVBox;
    @FXML private Tab charactersTab;
    @FXML private VBox charactersVBox;
    @FXML private Tab implantsTab;
    @FXML private VBox implantsVBox;
    @FXML private MenuBar menuBar;

    @FXML
    void createNewAdvantage(ActionEvent event) {
        CreateAdvantageDialog dialog = new CreateAdvantageDialog();
        dialog.showAndWait().ifPresent(advantage -> {
            try {
                AdvantagesWriter.writeSingle(advantage, "lib");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void createNewCharacter(ActionEvent event) throws IOException {
        CreateCharacterDialog dialog = new CreateCharacterDialog();
        dialog.showAndWait().ifPresent(character -> {
            try {
                character.init();
                character.hitPoints = character.hitPointsMax;

                CharacterWriter.write(character, "characters/" + character.info.name);

                FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("characterElement.fxml"));
                Node element = loader.load();

                CharacterElementController cec = loader.getController();

                cec.setCharacter(character.info.name, character.info.playerName, new File("character/" + character.info.name));

                charactersVBox.getChildren().add(element);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    @FXML
    void createNewImplant(ActionEvent event) {
        CreateImplantDialog dialog = new CreateImplantDialog();
        dialog.showAndWait().ifPresent(implant -> {
            try {
                ImplantsWriter.writeSingle(implant, "lib");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void createNewItem(ActionEvent event) {

    }

    @FXML
    void openHowTo(ActionEvent event) {

    }

    @FXML
    void openRules(ActionEvent event) {

    }

    public void initialize() throws IOException, BadSOSDFormatException {
        File characterFolder = new File("characters");
        if (characterFolder.exists() && characterFolder.isDirectory()) {
            File[] files = characterFolder.listFiles();

            if (files == null)
                return;

            for (File character : files) {
                if (character.isDirectory()) {
                    FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("characterElement.fxml"));
                    Node element = loader.load();

                    CharacterElementController cec = loader.getController();

                    CharacterInfo info = InfoReader.read(character.getPath());

                    cec.setCharacter(info.name, info.playerName, character);

                    charactersVBox.getChildren().add(element);
                }
            }
        }

        for (Advantage a : LibCache.getAdvantages(true)) {
            FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("advantageElement.fxml"));
            Node element = loader.load();

            AdvantageElementController con = loader.getController();
            con.setDisplayOnly();
            con.setAdvantage(a);

            advantagesVBox.getChildren().add(element);
        }

        for (Implant i : LibCache.getImplants()) {
            FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("implantElement.fxml"));
            Node element = loader.load();

            ImplantElementController con = loader.getController();
            con.setDisplayOnly();
            con.setImplant(i);

            implantsVBox.getChildren().add(element);
        }
    }

}
