package com.github.cldgdn.seaofstars.character;

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

    public Implant(String ID, String name, String description, String location, int humanCost, List<Bonus> bonuses) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.location = location;
        this.humanCost = humanCost;
        this.bonuses = bonuses;
    }

    public Implant() {
        this.ID = "";
        this.name = "";
        this.description = "";
        this.location = "";
        this.humanCost = 0;
        this.bonuses = new ArrayList<>();
    }
}
