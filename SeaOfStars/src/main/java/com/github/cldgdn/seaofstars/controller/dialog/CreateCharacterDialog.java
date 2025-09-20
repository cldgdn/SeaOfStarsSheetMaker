package com.github.cldgdn.seaofstars.controller.dialog;

import com.github.cldgdn.seaofstars.HelloApplication;
import com.github.cldgdn.seaofstars.character.Character;
import com.github.cldgdn.seaofstars.controller.CharacterCreationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.PopupWindow;

import java.io.IOException;

public class CreateCharacterDialog extends Dialog<Character> {
    public CreateCharacterDialog() {
        setTitle("Sea of Stars - Creazione Personaggio");

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("characterCreation.fxml"));

        try {
            AnchorPane content = loader.load();
            CharacterCreationController controller = loader.getController();

            getDialogPane().setContent(content);

            ButtonType okButton = new ButtonType("Crea", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Annulla" , ButtonBar.ButtonData.CANCEL_CLOSE);
            getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

            setResultConverter(dialogButton -> {
                if (dialogButton == okButton) {
                    return controller.getCharacter();
                }
                else return null;
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
