package com.github.cldgdn.seaofstars.sosd.read;

import com.github.cldgdn.seaofstars.character.Character;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;

import java.io.*;

public class CharacterReader {
    public static Character read(String characterPath) throws BadSOSDFormatException, IOException {
        File characterFolder = new File(characterPath);

        Character character;

        if (characterFolder.exists() && characterFolder.isDirectory()) {
            character = readOtherInfo(characterPath);
            character.info = InfoReader.read(characterPath);
            character.origin = OriginReader.read(characterPath);
            character.background = BackgroundReader.read(characterPath);
            character.traits = TraitsReader.read(characterPath);
            character.abilities = AbilitiesReader.read(characterPath);
            character.advantages = AdvantagesReader.read(characterPath);
            character.implants = ImplantsReader.read(characterPath);
            character.items = ItemsReader.read(characterPath);

            return character;
        }

        return null;
    }

    public static Character readOtherInfo(String characterPath) throws IOException, BadSOSDFormatException {
        Character character = new Character();
        boolean hpFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(characterPath + "/otherStats.sosd"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("hitPoints : ") && !hpFound) {
                    character.hitPoints = Integer.parseInt(line.substring("hitPoints : ".length()));
                    hpFound = true;
                }
            }

            if (!hpFound) {
                throw new BadSOSDFormatException("otherStats.sosd has missing fields.");
            }
        }

        return character;
    }
}
