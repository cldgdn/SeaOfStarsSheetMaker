package com.github.cldgdn.seaofstars.sosd.write;

import com.github.cldgdn.seaofstars.character.Ability;
import com.github.cldgdn.seaofstars.character.Trait;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AbilitiesWriter {
    public static void write(Ability[] abilities, String characterPath) throws IOException {
        Path dirPath = Path.of(characterPath + "/abilities");

        Files.createDirectories(dirPath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "/abilities/lib.sosd"))) {
            writer.write("");
        }

        for (Ability a : abilities) {
            writeSingle(a, characterPath);
        }
    }

    private static void writeSingle(Ability a, String characterPath) throws IOException {
        if (a.isCustom) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "/abilities/" + a.ID + ".sosd"))) {
                writer.write("name : " + a.name + "\n");
                writer.write("description : " + a.description + "\n");
                writer.write("baseLevel : " + a.baseLevel);
            }
        }
        else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "/abilities/lib.sosd", true))) {
                writer.write(a.ID + " : " + a.baseLevel + "\n");
            }
        }
    }
}
