package com.github.cldgdn.seaofstars.sosd.write;

import com.github.cldgdn.seaofstars.character.Implant;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ImplantsWriter {
    public static void write(List<Implant> implants, String characterPath) throws IOException {
        Path dirPath = Path.of(characterPath + "\\implants");

        Files.createDirectories(dirPath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "\\implants\\lib.sosd"))) {
            writer.write("");
        }

        for (Implant i : implants) {
            writeSingle(i, characterPath);
        }
    }

    public static void writeSingle(Implant i, String characterPath) throws IOException{
        if (i.isCustom) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "\\implants\\" + i.ID + ".sosd"))) {
                writer.write("name : " + i.name + "\n");
                writer.write("description : " + i.description + "\n");
                writer.write("location : " + i.location + "\n");
                writer.write("humanCost : " + i.humanCost + "\n");

                BonusWriter.writeBonuses(i.bonuses, writer);
            }
        }
        else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "\\implants\\lib.sosd", true))) {
                writer.write(i.ID + "\n");
            }
        }
    }
}
