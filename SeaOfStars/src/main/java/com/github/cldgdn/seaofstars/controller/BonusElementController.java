package com.github.cldgdn.seaofstars.controller;

import com.github.cldgdn.seaofstars.character.Bonus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class BonusElementController<T> {

    public VBox container;
    public Node element;
    public boolean removed;


    @FXML
    public ComboBox<T> attributeComboBox;
    @FXML
    public ComboBox<Integer> bonusValueComboBox;

    @FXML
    void removeSelf(ActionEvent event) {
        if (container != null) {
            container.getChildren().remove(element);
            removed = true;
        }
    }

    public void setAttributeList(T[] attributes) {
        attributeComboBox.getItems().addAll(attributes);
    }

    public void initialize() {
        bonusValueComboBox.getItems().addAll(new Integer[]{4, 3, 2, 1, -1, -2, -3, -4});
        removed = false;
    }
}