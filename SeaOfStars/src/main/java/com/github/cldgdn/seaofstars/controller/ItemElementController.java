package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.character.Item;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class ItemElementController {
    private Item item;

    @FXML private Button addButton;
    @FXML private Label amtLabel;
    @FXML private TextArea descriptionArea;
    @FXML private Label nameLabel;
    @FXML private Button remButton;

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

        amtLabel.setText("Quantità: " + item.amount);
    }

    @FXML
    void updateDescription(KeyEvent event) {
        item.description = descriptionArea.getText();
    }


    public void setItem(Item i) {
        this.item = i;

        nameLabel.setText(item.name);
        descriptionArea.setText(item.description);
        amtLabel.setText("Quantità: " + item.amount);
    }
}
