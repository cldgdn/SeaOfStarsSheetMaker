package com.github.cldgdn.seaofstars.character;

import com.github.cldgdn.seaofstars.Constants;

import java.util.ArrayList;
import java.util.List;

public class Implant {
    public String ID;
    public String name;
    public String description;
    public String location;
    public int humanCost;
    public List<Bonus> bonuses;

    public boolean isCustom;
    public boolean isOverclocked;

    public Implant(String ID, String name, String description, String location, int humanCost, List<Bonus> bonuses) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.location = location;
        this.humanCost = humanCost;
        this.bonuses = bonuses;
        this.isOverclocked = false;
    }

    public Implant() {
        this.ID = "";
        this.name = "";
        this.description = "";
        this.location = "";
        this.humanCost = 0;
        this.bonuses = new ArrayList<>();
    }

    public float getMultiplier() {
        return isOverclocked ? Constants.get.IMPLANT_OVERCLOCK_MULTIPLIER : 1;
    }

    public int getCost() {
        return Math.round(humanCost * getMultiplier());
    }
}
