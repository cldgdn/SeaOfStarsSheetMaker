package com.github.cldgdn.seaofstars.sosd.read;

import com.github.cldgdn.seaofstars.character.Ability;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AbilitiesReader {
    public static Ability[] read(String characterPath) throws BadSOSDFormatException, IOException {
        File abilitiesFolder = new File(characterPath + "\\abilities");
        List<Ability> abilities = new ArrayList<>();

        if (abilitiesFolder.exists() && abilitiesFolder.isDirectory()) {
            File[] files = abilitiesFolder.listFiles();
            if (files == null || files.length == 0) {
                throw new BadSOSDFormatException("No trait files in " + characterPath + "\\abilities folder");
            }

            for (File f : files) {
                if (f.getName().equals("lib.sosd")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                        String line;

                        while ((line = reader.readLine()) != null) {
                            String[] words = line.split(" : ");

                            abilities.add(readSingle(new File("lib\\abilities\\" + words[0] + ".sosd"), false, Integer.parseInt(words[1])));
                        }
                    }
                }
                else {
                    abilities.add(readSingle(f));
                }
            }
        } else {
            throw new IllegalArgumentException("No abilities folder in " + characterPath + " character folder.");
        }

        return abilities.toArray(Ability[]::new);
    }

    private static Ability readSingle(File abilityFile) throws IOException, BadSOSDFormatException {
        return readSingle(abilityFile, true, 0);
    }

    private static Ability readSingle(File abilityFile, boolean isCustom, int baseLevel) throws IOException, BadSOSDFormatException {
        String ID = abilityFile.getName().replace(".sosd", "");
        String name = "", description = "";

        boolean nameFound = false,
                descFound = false,
                baseValueFound = false;

        if (!isCustom) {
            baseValueFound = true;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(abilityFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("name : ") && !nameFound) {
                    name = line.substring("name : ".length());
                    nameFound = true;
                }
                else if (line.startsWith("description : ") && !descFound) {
                    description = line.substring("description : ".length());
                    descFound = true;
                }
                else if (line.startsWith("baseLevel : ") && !baseValueFound) {
                    baseLevel = Integer.parseInt(line.substring("baseLevel : ".length()));
                    baseValueFound = true;
                }
            }
        }

        if (!nameFound || !descFound || !baseValueFound) {
            throw new BadSOSDFormatException("Trait file " + abilityFile + " is incomplete");
        }

        return new Ability(ID, name, description, baseLevel, isCustom);
    }
}
