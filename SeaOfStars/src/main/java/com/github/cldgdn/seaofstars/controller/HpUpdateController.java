package com.github.cldgdn.seaofstars.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class HpUpdateController {

    @FXML private ComboBox<String> signCombo;
    @FXML private TextField valueField;

    public void initialize() {
        signCombo.getItems().addAll(new String[]{"Cura", "Danno"});
    }

    public int getValue() {
        int value = Math.abs(Integer.parseInt(valueField.getText()));

        if (signCombo.getSelectionModel().getSelectedItem().equals("Danno")) value = -value;

        return value;
    }
}
