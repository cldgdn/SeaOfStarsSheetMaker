package com.github.cldgdn.seaofstars.sosd.write;

import com.github.cldgdn.seaofstars.character.Advantage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AdvantagesWriter {
    public static void write(List<Advantage> advantages, String characterPath) throws IOException {
        Path dirPath = Path.of(characterPath + "/advantages");

        Files.createDirectories(dirPath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "/advantages/lib.sosd"))) {
            writer.write("");
        }

        for (Advantage a : advantages) {
            writeSingle(a, characterPath);
        }
    }

    public static void writeSingle(Advantage a, String characterPath) throws IOException {
        if (a.isCustom) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "/advantages/" + a.ID + ".sosd"))) {
                writer.write("name : " + a.name + "\n");
                writer.write("description : " + a.description + "\n");
                writer.write("isPassive : " + a.isPassive + "\n");
                writer.write("heroPointCost : " + a.heroPointCost + "\n");
                writer.write("# LIST\n");

                BonusWriter.writeBonuses(a.bonuses, writer);
            }
        }
        else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "/advantages/lib.sosd", true))) {
                writer.write(a.ID + "\n");
            }
        }
    }
}
