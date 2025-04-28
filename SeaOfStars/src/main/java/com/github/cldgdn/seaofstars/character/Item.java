package com.github.cldgdn.seaofstars.character;

public class Item {
    public String ID;
    public String name;
    public String description;
    public int amount;

    public Item(String ID, String name, String description, int amount) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.amount = amount;
    }
}
