package com.github.cldgdn.seaofstars.sosd.read;

import com.github.cldgdn.seaofstars.character.Bonus;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BonusReader {
    public static List<Bonus> readBonuses(BufferedReader reader, String sourceID) throws IOException, BadSOSDFormatException {
        List<Bonus> bonuses = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            Bonus b = new Bonus();

            if (line.startsWith("# BONUS")) {
                b.sourceID = sourceID;

                line = reader.readLine();
                if (line == null) throw new BadSOSDFormatException("Incomplete bonus");
                b.targetID = line.substring("targetID : ".length());

                line = reader.readLine();
                if (line == null) throw new BadSOSDFormatException("Incomplete bonus");
                b.value = Integer.parseInt(line.substring("value : ".length()));

                line = reader.readLine();
                if (line == null) throw new BadSOSDFormatException("Incomplete bonus");
                b.isConditional = Boolean.parseBoolean(line.substring("isConditional : ".length()));

                bonuses.add(b);
            }
        }

        return bonuses;
    }
}
