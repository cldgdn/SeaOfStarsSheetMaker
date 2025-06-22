package com.github.cldgdn.seaofstars.sosd.read;

import com.github.cldgdn.seaofstars.character.CharacterInfo;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;

import java.io.*;

public class InfoReader {
    public static CharacterInfo read(String characterPath) throws BadSOSDFormatException, IOException {
        CharacterInfo info = new CharacterInfo();
        boolean nameFound = false,
                playerNameFound = false,
                ageFound = false,
                fameFound = false,
                moneyFound = false;


        try (BufferedReader reader = new BufferedReader(new FileReader(characterPath + "\\info.sosd"))) {
            String line;

            while((line = reader.readLine()) != null) {
                if (line.startsWith("name : ") && !nameFound) {
                    info.name = line.substring("name : ".length());
                    nameFound = true;
                }
                else if (line.startsWith("playerName : ") && !playerNameFound) {
                    info.playerName = line.substring("playerName : ".length());
                    playerNameFound = true;
                }
                else if (line.startsWith("age : ") && !ageFound) {
                    info.age = Integer.parseInt(line.substring("age : ".length()));
                    ageFound = true;
                }
                else if (line.startsWith("fame : ") && !fameFound) {
                    info.fame = line.substring("fame : ".length());
                    fameFound = true;
                }
                else if (line.startsWith("money : ") && !moneyFound) {
                    info.money = Integer.parseInt(line.substring("money : ".length()));
                    moneyFound = true;
                }
            }

            if (!nameFound || !playerNameFound || !ageFound || !fameFound || !moneyFound) {
                throw new BadSOSDFormatException("Incomplete info.sosd file.");
            }
        }

        return info;
    }
}
