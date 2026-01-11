package com.github.cldgdn.seaofstars.sosd.write;

import com.github.cldgdn.seaofstars.character.Background;
import com.github.cldgdn.seaofstars.character.Character;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CharacterWriter {
    public static void write(Character character, String characterPath) throws IOException {
        Path dirPath = Path.of(characterPath);

        Files.createDirectories(dirPath);

        writeOtherInfo(character, characterPath);
        InfoWriter.write(character.info, characterPath);
        OriginWriter.write(character.origin, characterPath);
        BackgroundWriter.write(character.background, characterPath);
        TraitsWriter.write(character.traits, characterPath);
        AbilitiesWriter.write(character.abilities, characterPath);
        AdvantagesWriter.write(character.advantages, characterPath);
        ImplantsWriter.write(character.implants, characterPath);
    }

    private static void writeOtherInfo(Character character, String characterPath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "/otherStats.sosd"))) {
            writer.write("hitPoints : " + character.hitPoints + "\n");
        }
    }
}
