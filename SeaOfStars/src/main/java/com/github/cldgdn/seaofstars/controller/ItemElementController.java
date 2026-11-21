package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.Utils;
import com.github.cldgdn.seaofstars.character.Item;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ItemElementController {
    private Item item;
    private CharacterScreenController controller;

    @FXML private Button addButton;
    @FXML private TextField amtField;
    @FXML private TextArea descriptionArea;
    @FXML private Label nameLabel;
    @FXML private Button remButton;

    @FXML
    void initialize() {
        Utils.configureIntegerField(amtField, value -> {
            item.amount = value;
        });
    }

    @FXML
    void changeItemAmount(ActionEvent event) {
        Object source = event.getSource();

        if (source instanceof Button) {
            Button b = (Button) source;

            if (b == addButton) {
                item.amount++;
            }
            else if (b == remButton) {
                item.amount--;
            }
        }

        amtField.setText("" + item.amount);
    }

    @FXML
    void updateDescription(KeyEvent event) {
        item.description = descriptionArea.getText();
    }


    @FXML
    void removeItem(ActionEvent event) {
        Path path = Path.of(
                Constants.get.CHARACTERS_FOLDER + "/" + controller.character.info.name.replace(" ", "_") + "/items/" + item.ID + ".sosd"
        );

        try {
            Files.delete(path);
        } catch (IOException e) {
            System.err.println("[ERROR] Could not delete item file:\n" + path);
        }

        controller.character.removeItem(item.ID);

        controller.refreshCharacter(false);
    }

    public void setItem(Item i) {
        this.item = i;

        nameLabel.setText(item.name);
        descriptionArea.setText(item.description);
        amtField.setText("" + item.amount);
    }

    public void setController(CharacterScreenController controller) {
        this.controller = controller;
    }
}
