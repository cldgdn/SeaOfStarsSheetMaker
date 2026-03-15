package com.github.cldgdn.seaofstars.character;

import com.github.cldgdn.seaofstars.Constants;

public class Origin {
    public final String ID;
    public final String name;
    public String description;
    public final Bonus[] traitBonuses;

    public Origin(String ID, String name, String description, Bonus[] traitBonuses) {
        if (traitBonuses.length != Constants.get.ORIGIN_BONUSES_AMT)
            throw new IllegalArgumentException("Origin" + name + " has bad amount of Trait Bonuses");

        this.ID = ID;
        this.name = name;
        this.description = description;
        this.traitBonuses = traitBonuses;
    }
}
