package com.github.cldgdn.seaofstars.character;

import com.github.cldgdn.seaofstars.Constants;

public class Background {
    public final String ID;
    public final String name;
    public final String description;
    public final Bonus[] abilityBonuses;

    public Background(String ID, String name, String description, Bonus[] abilityBonuses) {
        if (abilityBonuses.length != Constants.get.BACKGROUND_BONUSES_AMT)
            throw new IllegalArgumentException("Background " + name + " has bad amount of Trait Bonuses");

        this.ID = ID;
        this.name = name;
        this.description = description;
        this.abilityBonuses = abilityBonuses;
    }
}
