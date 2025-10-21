package com.github.cldgdn.seaofstars.controller.dialog;

import com.github.cldgdn.seaofstars.SeaOfStarsMain;
import com.github.cldgdn.seaofstars.controller.HpUpdateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class HpUpdateDialog extends Dialog<Integer> {

    public HpUpdateDialog() {
        setTitle("Subisci o Cura ferite");
        setResizable(true);

        FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("hpUpdate.fxml"));

        try {
            HBox content = loader.load();
            HpUpdateController controller = loader.getController();

            getDialogPane().setContent(content);

            ButtonType okButton = new ButtonType("Conferma", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
            getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

            setResultConverter(dialogButton -> {
                if (dialogButton == okButton) {
                    return controller.getValue();
                }
                else return 0;
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
