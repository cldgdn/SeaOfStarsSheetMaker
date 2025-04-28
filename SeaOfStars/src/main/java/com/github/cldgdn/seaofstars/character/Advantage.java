package com.github.cldgdn.seaofstars.character;

import java.util.List;

public class Advantage {
    public String ID;
    public String name;
    public String description;
    public boolean isPassive;
    public int heroPointCost;
    public List<Bonus> bonuses;

    public Advantage(String ID, String name, String description, boolean isPassive, int heroPointCost, List<Bonus> bonuses) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.isPassive = isPassive;
        this.heroPointCost = heroPointCost;
        this.bonuses = bonuses;
    }
}
