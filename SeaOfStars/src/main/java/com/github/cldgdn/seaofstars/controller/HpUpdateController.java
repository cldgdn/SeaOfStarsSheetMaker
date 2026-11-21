package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class HpUpdateController {

    @FXML private ComboBox<String> signCombo;
    @FXML private TextField valueField;

    @FXML
    void initialize() {
        signCombo.getItems().addAll(new String[]{"Cura", "Danno"});
        Utils.configureIntegerField(valueField, value -> {});
    }

    public int getValue() {
        int value;

        try {
            value = Math.abs(Integer.parseInt(valueField.getText()));
        } catch (NumberFormatException e) {
            value = 0;
        }

        if (signCombo.getSelectionModel().getSelectedItem().equals("Danno")) value = -value;

        return value;
    }
}
