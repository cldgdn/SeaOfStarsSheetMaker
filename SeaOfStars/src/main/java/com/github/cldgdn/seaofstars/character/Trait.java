package com.github.cldgdn.seaofstars.character;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.throwable.exceptions.BonusNotFoundException;
import com.github.cldgdn.seaofstars.throwable.CriticalConditions;

import java.util.List;

public class Trait {
    public String ID;
    public String name;
    public String description;
    public int baseLevel;
    public int computedLevel;
    public int modifier;
    public List<Bonus> appliedBonuses;
    public Bonus humanLimitBonus;

    public Trait(String ID, String name, String description, int baseLevel, List<Bonus> appliedBonuses) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.baseLevel = baseLevel;
        this.appliedBonuses = appliedBonuses;
        humanLimitBonus = null;
    }

    public void update() throws CriticalConditions {
        //Calculates the actual level of the Trait by adding each bonus to the base level, clamped to be between min and max * 2 values
        computedLevel = baseLevel;

        for (Bonus b : appliedBonuses) {
            computedLevel += b.value;
        }
        if (computedLevel < Constants.MIN_TRAIT_LEVEL) {
            throw new CriticalConditions();
        }
        if (computedLevel > Constants.MAX_TRAIT_LEVEL) {
            computedLevel = Constants.MAX_TRAIT_LEVEL;
        }

        //Calculates the Trait modifier by finding its difference with the zero modifier point in the level spread
        modifier = computedLevel - Constants.ZERO_TRAIT_LEVEL;

        //Calculates the bonus to human limit if this is either the Resolve or Resistance trait
        if (ID.equals(Constants.RESOLVE_ID) || ID.equals(Constants.RESISTANCE_ID)) {
            int bonusValue = (modifier <= 0) ? (modifier) : (modifier * Constants.HUMAN_LIMIT_BASE);

            //initializes the bonus if it hasn't been done already
            if (humanLimitBonus == null) {
                humanLimitBonus = new Bonus();
                humanLimitBonus.value = bonusValue;
                humanLimitBonus.targetID = Constants.HUMAN_LIMIT_ID;
            }
            humanLimitBonus.sourceID = ID;
        }
    }

    public void addBonus(Bonus add) throws CriticalConditions {
        if (add == null) return;

        //to avoid adding the same bonus multiple times
        boolean found = false;
        for (Bonus b : appliedBonuses) {
            if (b.sourceID.equals(add.sourceID)) {
                b.value = add.value;
                found = true;
                break;
            }
        }

        if (!found) {
            appliedBonuses.add(add);
        }

        update();
    }

    public Bonus removeBonus(String sourceID) throws BonusNotFoundException, CriticalConditions {
        for (Bonus b : appliedBonuses) {
            if (b.sourceID.equals(sourceID)) {
                appliedBonuses.remove(b);
                update();
                return b;
            }
        }
        throw new BonusNotFoundException("Trait " + name + " (ID: \"" + ID + "\") does not have a bonus from id \"" + sourceID + "\"!");
    }
}
