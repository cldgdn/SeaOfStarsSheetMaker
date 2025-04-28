package com.github.cldgdn.seaofstars.character;

public class Origin {
    public final String ID;
    public final String name;
    public final String description;
    public final Bonus[] traitBonuses;

    public Origin(String ID, String name, String description, Bonus[] traitBonuses) {
        if (traitBonuses.length != 2)
            throw new IllegalArgumentException("Origin" + name + " has bad amount of Trait Bonuses");

        this.ID = ID;
        this.name = name;
        this.description = description;
        this.traitBonuses = traitBonuses;
    }
}
