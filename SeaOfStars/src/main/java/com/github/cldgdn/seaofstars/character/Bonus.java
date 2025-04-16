package com.github.cldgdn.seaofstars.character;

public class Bonus {
    public int value;
    public String sourceID; //the ID of the attribute which gave out this bonus
    public String targetID; //the ID of the attribute which benefits from this bonus

    public Bonus(int value, String sourceID, String targetID) {
        this.value = value;
        this.sourceID = sourceID;
        this.targetID = targetID;
    }

    public Bonus() {
        value = 0;
        sourceID = null;
        targetID = null;
    }
}
