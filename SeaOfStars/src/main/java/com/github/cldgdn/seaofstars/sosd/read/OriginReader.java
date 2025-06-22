package com.github.cldgdn.seaofstars.sosd.read;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.character.Bonus;
import com.github.cldgdn.seaofstars.character.Origin;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class OriginReader {
    public static Origin read(String characterPath) throws IOException, BadSOSDFormatException {
        String ID = "", name = "", description = "";
        Bonus[] bonuses = new Bonus[2];
        boolean idFound = false,
                nameFound = false,
                descFound = false,
                bonusesFound = false,
                readingBonuses = false;
        int bonusesFoundAmt = 0;


        try (BufferedReader reader = new BufferedReader(new FileReader(characterPath + "\\origin.sosd"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (!readingBonuses) {
                    if (line.startsWith("id : ") && !idFound) {
                        ID = line.substring("id : ".length());
                        idFound = true;
                    } else if (line.startsWith("name : ") && !nameFound) {
                        name = line.substring("name : ".length());
                        nameFound = true;
                    } else if (line.startsWith("description : ") && !descFound) {
                        description = line.substring("description : ".length());
                        descFound = true;
                    } else if (line.startsWith("# LIST")) {
                        if (!idFound || !nameFound || !descFound) {
                            throw new BadSOSDFormatException("Bonuses not at end of file.");
                        }
                        else {
                            List<Bonus> b = BonusReader.readBonuses(reader, ID);
                            bonuses = b.toArray(Bonus[]::new);
                            bonusesFound = true;
                            break;
                        }
                    }
                }
            }
        }

        if (!bonusesFound || bonuses.length != Constants.ORIGIN_BONUSES_AMT) {
            throw new BadSOSDFormatException("Incomplete origin.sosd");
        }

        return new Origin(ID, name, description, bonuses);
    }
}
