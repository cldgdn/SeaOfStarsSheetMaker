package com.github.cldgdn.seaofstars.controller.dialog;

import com.github.cldgdn.seaofstars.SeaOfStarsMain;
import com.github.cldgdn.seaofstars.character.Character;
import com.github.cldgdn.seaofstars.controller.RollWithIncrementsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RollWithIncrementsDialog extends Dialog<Integer> {
    private final Character character;

    public RollWithIncrementsDialog(Character c) {
        character = c;

        setTitle("Lancio dadi");

        FXMLLoader loader = new FXMLLoader(SeaOfStarsMain.class.getResource("rollWithIncrementsDialog.fxml"));

        try {
            VBox content = loader.load();
            RollWithIncrementsController controller = loader.getController();
            controller.setCharacter(c);
            controller.setup();

            getDialogPane().setContent(content);

            ButtonType doneButton = new ButtonType("Finito", ButtonBar.ButtonData.OK_DONE);
            getDialogPane().getButtonTypes().add(doneButton);

            setResultConverter(dialogButton -> controller.lastIncrementsRolled);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
