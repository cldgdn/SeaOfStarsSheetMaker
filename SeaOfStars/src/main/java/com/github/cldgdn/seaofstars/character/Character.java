package com.github.cldgdn.seaofstars.character;

import com.github.cldgdn.seaofstars.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Character {
    public CharacterInfo info;
    public Trait[] traits;
    public List<Bonus> traitBonuses;
    public Ability[] abilities;
    public List<Bonus> abilityBonuses;
    public Origin origin;
    public Background background;
    public List<Advantage> advantages;
    public List<Implant> implants;
    public List<Item> items;
    public List<Bonus> otherBonuses;

    public int hitPointsBase = Constants.get.HIT_POINT_BASE;
    public int hitPointsMax;
    public int hitPoints;

    public int humanLimitBase = Constants.get.HUMAN_LIMIT_BASE;
    public int humanLimit;
    public int usedHumanLimit;

    public int storyPoints;

    public Character() {}

    public void init() {
        traitBonuses = new ArrayList<>();
        abilityBonuses = new ArrayList<>();
        otherBonuses = new ArrayList<>();

        sortBonuses();
        update();
    }

    private void sortBonuses() {
        traitBonuses.addAll(Arrays.asList(origin.traitBonuses));
        abilityBonuses.addAll(Arrays.asList(background.abilityBonuses));

        for (Advantage a : advantages) {
            dumpBonuses(a.bonuses);
        }

        for (Implant i : implants) {
            dumpBonuses(i.bonuses);
        }
    }

    private void update() {
        hitPointsMax = hitPointsBase;
        humanLimit = humanLimitBase;
        usedHumanLimit = 0;

        //update Traits
        for (Trait t : traits) {
            t.computedLevel = t.baseLevel;

            //for each trait bonus, check if it is for this specific trait and in that case add it to the computed trait level
            for (Bonus b : traitBonuses) {
                if (t.ID.equals(b.targetID) && !b.isConditional) {
                    t.computedLevel += b.value;
                }
            }

            //cap trait to max allowed level (def is 14)
            if (t.computedLevel > Constants.get.MAX_TRAIT_LEVEL) {
                t.computedLevel = Constants.get.MAX_TRAIT_LEVEL;
            }
        }

        //update Abilities
        for (Ability a : abilities) {
            a.computedLevel = a.baseLevel;

            //for each ability bonus, check if it is for this specific ability and in that case, if it is non coditional add it to the computed ability level
            for (Bonus b : abilityBonuses) {
                if (a.ID.equals(b.targetID) && !b.isConditional) {
                    a.computedLevel += b.value;
                }
            }

            //clamp ability level between min and max values
            a.computedLevel = Math.max(Constants.get.MIN_ABILITY_LEVEL, Math.min(a.computedLevel, Constants.get.MAX_ABILITY_LEVEL));
        }

        //go through the bonuses that affect HP and HL
        for (Bonus b : otherBonuses) {
            switch (b.targetID) {
                case Constants.HIT_POINTS_MAX_ID -> hitPointsMax += b.value;
                case Constants.HUMAN_LIMIT_ID -> humanLimit += b.value;
            }
        }

        //modifiers are calculated last as they are dependent on critical conditions, which can be triggered by multiple sources
        for (Trait t : traits) {
            //calculate modifier taking into account that
            if (t.ID.equals(Constants.RESISTANCE_ID) || t.ID.equals(Constants.RESOLVE_ID)) {
                t.modifier = (t.computedLevel - Constants.get.ZERO_TRAIT_LEVEL);
                //calculates bonus to human limit given by trait if resistance or resolve
                humanLimit += (t.modifier <= 0) ? (t.modifier) : (t.modifier * Constants.get.HUMAN_LIMIT_BASE);
            }
            else {
                t.modifier = (t.computedLevel - Constants.get.ZERO_TRAIT_LEVEL);
            }

            if (t.ID.equals(Constants.RESISTANCE_ID)) {
                //character gets 1 extra hp per level of resistance
                hitPointsMax += t.computedLevel;
            }
        }

        //compute total human cost
        for (Implant i : implants) {
            usedHumanLimit += i.humanCost;
        }
    }

    public boolean addAdvantage(Advantage advantage) {
        for (Advantage a : advantages) {
            if (a.ID.equals(advantage.ID)) {
                return false;
            }
        }

        advantages.add(advantage);
        dumpBonuses(advantage.bonuses);

        update();

        return true;
    }

    public Advantage removeAdvantage(String ID) {
        Advantage a = null;
        for (int i = 0; i < advantages.size(); i++) {
            if (advantages.get(i).ID.equals(ID)) {
                a = advantages.remove(i);
                break;
            }
        }

        if (a != null) {
            removeBonuses(ID);
        }

        update();

        return a;
    }

    public boolean addImplant(Implant implant) {
        for (Implant i : implants) {
            if (i.ID.equals(implant.ID)) {
                return false;
            }
        }

        implants.add(implant);
        dumpBonuses(implant.bonuses);

        update();

        return true;
    }

    public Implant removeImplant(String ID) {
        Implant implant = null;
        for (int i = 0; i < implants.size(); i++) {
            if (implants.get(i).ID.equals(ID)) {
                implant = implants.remove(i);
                break;
            }
        }

        if (implant != null) {
            removeBonuses(ID);
        }

        update();

        return implant;
    }

    private void removeBonuses(String sourceID) {
        traitBonuses.removeIf(b -> b.sourceID.equals(sourceID));
        abilityBonuses.removeIf(b -> b.sourceID.equals(sourceID));
        otherBonuses.removeIf(b -> b.sourceID.equals(sourceID));
    }

    private void dumpBonuses(List<Bonus> bonuses) {
        String pref;
        for (Bonus b : bonuses) {
            pref = b.targetID.substring(0, 3);

            if (pref.equals("trt")) {
                traitBonuses.add(b);
            } else if (pref.equals("abl")) {
                abilityBonuses.add(b);
            } else {
                otherBonuses.add(b);
            }
        }
    }
}
