package com.github.cldgdn.seaofstars.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class BonusElementController<T> {

    public VBox container;
    public Node element;


    @FXML
    public ComboBox<T> attributeComboBox;
    @FXML
    private ComboBox<Integer> bonusValueComboBox;

    @FXML
    void removeSelf(ActionEvent event) {
        if (container != null) {
            container.getChildren().remove(element);
        }
    }

    public void setAttributeList(T[] attributes) {
        attributeComboBox.getItems().addAll(attributes);
    }

    public void initialize() {
        bonusValueComboBox.getItems().addAll(new Integer[]{4, 3, 2, 1, -1, -2, -3, -4});
    }
}