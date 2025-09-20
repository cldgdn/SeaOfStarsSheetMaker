package com.github.cldgdn.seaofstars.controller.dialog;

import com.github.cldgdn.seaofstars.HelloApplication;
import com.github.cldgdn.seaofstars.character.Ability;
import com.github.cldgdn.seaofstars.character.Advantage;
import com.github.cldgdn.seaofstars.controller.CreateAdvantageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CreateAdvantageDialog extends Dialog<Advantage> {
    public CreateAdvantageDialog() {
        setTitle("Creazione Vantaggio");

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("createAdvantageDialog.fxml"));

        try {
            ScrollPane content = loader.load();
            CreateAdvantageController controller = loader.getController();

            getDialogPane().setContent(content);

            ButtonType okButton = new ButtonType("Crea", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
            getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

            setResultConverter(dialogButton -> {
                if (dialogButton == okButton) {
                    return new Advantage();
                }
                else return null;
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
