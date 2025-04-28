package com.github.cldgdn.seaofstars.character;

import java.util.List;

public class Implant {
    public String ID;
    public String name;
    public String description;
    public String location;
    public int humanCost;
    public List<Bonus> bonuses;

    public Implant(String ID, String name, String description, String location, int humanCost, List<Bonus> bonuses) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.location = location;
        this.humanCost = humanCost;
        this.bonuses = bonuses;
    }
}
