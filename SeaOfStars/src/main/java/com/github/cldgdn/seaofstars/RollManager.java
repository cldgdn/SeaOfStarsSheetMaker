package com.github.cldgdn.seaofstars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RollManager {
    public static int rollAndCountIncrements(int abilityLevel, int traitModifier) {
        int[] rolls = rollDice(abilityLevel, traitModifier, Constants.RISK_DICE_FACES, abilityLevel >= Constants.DICE_EXPLODE_ABILITY_LEVEL);
        boolean[] used = new boolean[rolls.length];
        List<Integer> bestGroup, currGroup;
        int swap, curr, increments = 0, bestBeginning, total, bestTotal, currBeginning;
        boolean betterFound;

        //sort the rolls in reverse order
        Arrays.sort(rolls);
        for (int i = 0; i < rolls.length / 2; i++) {
            swap = rolls[i];
            rolls[i] = rolls[rolls.length - 1- i];
            rolls[rolls.length - 1 - i] = swap;
        }

        System.out.println("rolls : " + Arrays.toString(rolls));

        while (true) {
            //pick the highest available roll to add to the current group
            curr = -1;
            for (int i = 0; i < used.length; i++) {
                if (!used[i]) {
                    curr = i;
                    used[i] = true;
                    break;
                }
            }
            if (curr == -1)
                break;

            //if the high roll picked is already an increment by itself (10 or more), then it is counted as such and the algorythm moves on to the next highest roll
            if (rolls[curr] >= Constants.INCREMENT_THRESHOLD) {
                increments++;
                continue;
            }

            bestGroup = null;
            bestBeginning = rolls.length;
            bestTotal = Integer.MAX_VALUE;

            do {
                currGroup = new ArrayList<>();
                currGroup.add(curr);
                total = rolls[curr];
                currBeginning = -1;
                betterFound = false;

                //check the lowest rolls by grouping them up until the total rolled reaches the increment threshold
                for (int i = bestBeginning - 1; i >= 0; i--) {
                    if (used[i])
                        continue;

                    if (currBeginning == -1) {
                        currBeginning = i;
                    }


                    total += rolls[i];
                    currGroup.add(i);
                    if (total >= Constants.INCREMENT_THRESHOLD) {
                        //if threshold is reached for the first time, mark the current group as best
                        if (bestGroup == null) {
                            betterFound = true;
                            bestGroup = currGroup;
                            bestTotal = total;
                            bestBeginning = currBeginning;
                            break;
                        }
                        //if threshold is reached and the total is lower (closer to threshold) than best, current is new best
                        if (total < bestTotal) {
                            betterFound = true;
                            bestGroup = currGroup;
                            bestTotal = total;
                            bestBeginning = currBeginning;
                            break;
                        }
                        //if threshold is reached exactly same as best total, if current group uses fewer dice then it is new best
                        if (total == bestTotal && currGroup.size() < bestGroup.size()) {
                            betterFound = true;
                            bestGroup = currGroup;
                            bestTotal = total;
                            bestBeginning = currBeginning;
                            break;
                        }

                        betterFound = false;
                        break;
                    }
                }

                if (!betterFound) {
                    if (bestGroup == null) {
                        break;
                    }
                    increments++;
                    for (Integer i : bestGroup) {
                        used[i] = true;
                    }
                }
            } while (betterFound);
        }

        return increments;
    }

    public static int[] rollDice(int diceAmt, int mod, int faces, boolean explode) {
        if (diceAmt <= 0) {
            throw new IllegalArgumentException("Cannot roll " + diceAmt + " dice!");
        }

        Random r = new Random();
        List<Integer> rolls = new ArrayList<>();
        int roll;

        for (int i = 0; i < diceAmt; i++) {
            roll = r.nextInt(faces) + 1;
            if (explode && roll == faces) {
                diceAmt++;
            }
            rolls.add(roll + mod);
        }

        return rolls.stream().mapToInt(Integer::intValue).toArray();
    }

    public static int rollDie(int mod, int faces) {
        return new Random().nextInt(faces) + 1 + mod;
    }
}
