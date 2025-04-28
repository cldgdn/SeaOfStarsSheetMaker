package com.github.cldgdn.seaofstars.character;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadBonusException;
import com.github.cldgdn.seaofstars.throwable.CriticalConditions;

import java.util.List;

public class Trait {
    public String ID;
    public String name;
    public String description;
    public int baseLevel;
    public int computedLevel;
    public int modifier;

    public Trait(String ID, String name, String description, int baseLevel) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.baseLevel = baseLevel;
    }
}
