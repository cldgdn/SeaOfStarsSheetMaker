package com.github.cldgdn.seaofstars.sosd.write;

import com.github.cldgdn.seaofstars.character.Background;
import com.github.cldgdn.seaofstars.character.Bonus;
import com.github.cldgdn.seaofstars.character.Origin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BackgroundWriter {
    public static void write(Background background, String characterPath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "\\background.sosd"))) {
            writer.write("id : " + background.ID + "\n");
            writer.write("name : " + background.name + "\n");
            writer.write("description : " + background.description + "\n");
            writer.write("# LIST\n");

            BonusWriter.writeBonuses(background.abilityBonuses, writer);
        }
    }
}
