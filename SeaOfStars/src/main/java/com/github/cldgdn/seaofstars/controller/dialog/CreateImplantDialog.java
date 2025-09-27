package com.github.cldgdn.seaofstars.controller.dialog;

import com.github.cldgdn.seaofstars.HelloApplication;
import com.github.cldgdn.seaofstars.character.Implant;
import com.github.cldgdn.seaofstars.controller.CreateAdvantageController;
import com.github.cldgdn.seaofstars.controller.CreateImplantController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;

import java.io.IOException;

public class CreateImplantDialog extends Dialog<Implant> {
    public CreateImplantDialog() {
        setTitle("Sea Of Stars - Creazione Impianto");

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("createImplantDialog.fxml"));

        try {
            ScrollPane content = loader.load();
            CreateImplantController controller = loader.getController();

            getDialogPane().setContent(content);

            ButtonType okButton = new ButtonType("Crea", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
            getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

            setResultConverter(dialogButton -> {
                if (dialogButton == okButton) {
                    return controller.getImplant();
                }
                else return null;
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
