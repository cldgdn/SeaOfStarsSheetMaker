package com.github.cldgdn.seaofstars.character;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.LibCache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bonus {
    public int value;
    public String sourceID; //the ID of the attribute which gave out this bonus
    public String targetID; //the ID of the attribute which benefits from this bonus

    public boolean isConditional;

    public Bonus(int value, String sourceID, String targetID, boolean isConditional) {
        this.value = value;
        this.sourceID = sourceID;
        this.targetID = targetID;
        this.isConditional = isConditional;
    }

    public Bonus() {
        value = 0;
        sourceID = null;
        targetID = null;
        isConditional = false;
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean overclocked) {
        String targetName = "nothing";
        Trait[] traits = null;
        Ability[] abilities = null;

        try {
            traits = LibCache.getTraits();
            abilities = LibCache.getAbilities();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (targetID.startsWith("trt")) {
            for (Trait t : traits) {
                if (t.ID.equals(targetID)) {
                    targetName = "(T) " + t.name;
                    break;
                }
            }
        } else if (targetID.startsWith("abl")) {
            for (Ability a : abilities) {
                if (a.ID.equals(targetID)) {
                    targetName = "(A) " + a.name;
                    break;
                }
            }
        } else if (targetID.equals(Constants.HIT_POINTS_MAX_ID)) {
            targetName = "Punti Ferita";
        } else if (targetID.equals(Constants.HUMAN_LIMIT_ID)) {
            targetName = "Limite Umano";
        }
        int value = this.getValue(overclocked);
        return targetName + " " + (value >= 0 ? "+" + value : value);
    }

    public static List<Bonus> collapseBonuses(List<Bonus> bonuses) {
        List<Bonus> collapsed = new ArrayList<>();
        boolean found;

        for (Bonus b : bonuses) {
            found = false;
            for (Bonus c : collapsed) {
                if (b.targetID.equals(c.targetID) && (b.isConditional == c.isConditional)) {
                    c.value += b.value;
                    found = true;
                    break;
                }
            }

            if (!found) {
                collapsed.add(b);
            }
        }

        return collapsed;
    }

    public Bonus copy() {
        return new Bonus(value, sourceID, targetID, isConditional);
    }

    public int getValue(boolean overclocked) {
        if (targetID.equals(Constants.HUMAN_LIMIT_ID) || targetID.equals(Constants.HIT_POINTS_MAX_ID)) {
            return value;
        }

        return Math.round(value * (overclocked ? Constants.get.IMPLANT_OVERCLOCK_MULTIPLIER : 1));
    }
}
