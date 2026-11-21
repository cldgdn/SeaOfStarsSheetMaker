package com.github.cldgdn.seaofstars.character;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadBonusException;

import java.util.List;

public class Ability {
    public String ID;
    public String name;
    public String description;
    public int baseLevel;
    public int computedLevel;
    public final boolean isCustom;

    public Ability(String ID, String name, String description, int baseLevel, boolean isCustom) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.baseLevel = baseLevel;
        this.isCustom = isCustom;
    }

    public int level() {
        return Math.min(
                Constants.get.MAX_ABILITY_LEVEL,
                Math.max(
                        computedLevel,
                        Constants.get.MIN_ABILITY_LEVEL
                )
        );
    }
}
