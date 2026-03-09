package com.github.cldgdn.seaofstars.sosd.write;

import com.github.cldgdn.seaofstars.character.Trait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TraitsWriter {
    public static void write(Trait[] traits, String characterPath) throws IOException {
        Path dirPath = Path.of(characterPath + "/traits");

        Files.createDirectories(dirPath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "/traits/lib.sosd"))) {
            writer.write("");
        }

        for (Trait t : traits) {
            writeSingle(t, characterPath);
        }
    }

    private static void writeSingle(Trait t, String characterPath) throws IOException {
        if (t.isCustom) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "/traits/" + t.ID + ".sosd"))) {
                writer.write("name : " + t.name + "\n");
                writer.write("description : " + t.description.replace("\n", "\\n") + "\n");
                writer.write("baseLevel : " + t.baseLevel);
            }
        }
        else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "/traits/lib.sosd", true))) {
                writer.write(t.ID + " : " + t.baseLevel + "\n");
            }
        }
    }
}
