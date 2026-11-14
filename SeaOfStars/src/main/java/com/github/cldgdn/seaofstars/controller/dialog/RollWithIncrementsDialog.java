package com.github.cldgdn.seaofstars.controller.dialog;

import com.github.cldgdn.seaofstars.SeaOfStarsMain;
import com.github.cldgdn.seaofstars.Utils;
import com.github.cldgdn.seaofstars.character.Character;
import com.github.cldgdn.seaofstars.controller.RollWithIncrementsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class RollWithIncrementsDialog extends Dialog<List<String>> {
    public RollWithIncrementsDialog(Character c, List<String> rollHistory) {
        setTitle("Lancio dadi");
        setResizable(true);

        FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("rollWithIncrementsDialog.fxml"));

        try {
            ScrollPane content = loader.load();
            RollWithIncrementsController controller = loader.getController();
            controller.setCharacter(c);
            controller.setRollHistory(rollHistory);
            controller.setup();

            Utils.setStyle(getDialogPane());

            getDialogPane().setContent(content);

            ButtonType doneButton = new ButtonType("Finito", ButtonBar.ButtonData.OK_DONE);
            getDialogPane().getButtonTypes().add(doneButton);

            setResultConverter(dialogButton -> controller.rollHistory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
