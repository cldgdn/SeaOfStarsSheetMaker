package com.github.cldgdn.seaofstars.sosd.write;

import com.github.cldgdn.seaofstars.character.Bonus;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BonusWriter {
    public static void writeBonuses(List<Bonus> bonuses, BufferedWriter writer) throws IOException {
        for (Bonus b : bonuses) {
            writer.write("# BONUS\n");
            writer.write("targetID : " + b.targetID + "\n");
            writer.write("value : " + b.value + "\n");
            writer.write("isConditional : " + b.isConditional + "\n");
        }
    }

    public static void writeBonuses(Bonus[] bonuses, BufferedWriter writer) throws IOException {
        writeBonuses(Arrays.stream(bonuses).toList(), writer);
    }
}
