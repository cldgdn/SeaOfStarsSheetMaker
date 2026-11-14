package com.github.cldgdn.seaofstars.controller.dialog;

import com.github.cldgdn.seaofstars.SeaOfStarsMain;
import com.github.cldgdn.seaofstars.Utils;
import com.github.cldgdn.seaofstars.character.Item;
import com.github.cldgdn.seaofstars.controller.CreateItemController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;

public class CreateItemDialog extends Dialog<Item> {
    public CreateItemDialog() {
        setTitle("Sea Of Stars - Creazione Oggetto");
        setResizable(true);

        FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("createItemDialog.fxml"));

        try {
            ScrollPane content = loader.load();
            CreateItemController controller = loader.getController();

            Utils.setStyle(getDialogPane());

            getDialogPane().setContent(content);

            ButtonType okButton = new ButtonType("Crea", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
            getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

            setResultConverter(dialogButton -> {
                if (dialogButton == okButton) {
                    return controller.getItem();
                }
                else return null;
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
