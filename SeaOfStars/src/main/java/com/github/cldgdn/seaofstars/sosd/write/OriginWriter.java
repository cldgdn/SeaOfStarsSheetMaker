package com.github.cldgdn.seaofstars.sosd.write;

import com.github.cldgdn.seaofstars.character.Bonus;
import com.github.cldgdn.seaofstars.character.Origin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OriginWriter {
    public static void write(Origin origin, String characterPath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "/origin.sosd"))) {
            writer.write("id : " + origin.ID + "\n");
            writer.write("name : " + origin.name + "\n");
            writer.write("description : " + origin.description + "\n");
            writer.write("# LIST\n");

            BonusWriter.writeBonuses(origin.traitBonuses, writer);
        }
    }
}
