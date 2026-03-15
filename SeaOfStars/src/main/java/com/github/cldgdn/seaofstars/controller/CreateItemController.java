package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.Utils;
import com.github.cldgdn.seaofstars.character.Item;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateItemController {

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextField nameField;

    @FXML
    private TextField quantityField;

    public Item getItem() {
        Item i = new Item();

        i.name = nameField.getText();
        i.ID = Utils.genID("itm", i.name);
        i.description = descriptionArea.getText();

        try {
            i.amount = Integer.parseInt(quantityField.getText().trim());
        } catch (NumberFormatException e) {
            return null;
        }

        return i;
    }

}