package com.github.cldgdn.seaofstars.sosd.read;

import com.github.cldgdn.seaofstars.character.Trait;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TraitsReader {
    public static Trait[] read(String characterPath) throws BadSOSDFormatException, IOException {
        File traitsFolder = new File(characterPath + "/traits");
        List<Trait> traits = new ArrayList<>();

        if (traitsFolder.exists() && traitsFolder.isDirectory()) {
            File[] files = traitsFolder.listFiles();
            if (files == null || files.length == 0) {
                throw new BadSOSDFormatException("No trait files in " + characterPath + "/traits folder");
            }

            for (File f : files) {
                if (f.getName().equals("lib.sosd")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                        String line;

                        while ((line = reader.readLine()) != null) {
                            String[] words = line.split(" : ");

                            traits.add(readSingle(new File("lib/traits/" + words[0] + ".sosd"), false, Integer.parseInt(words[1])));
                        }
                    }
                }
                else if (f.getName().endsWith(".sosd")) {
                    traits.add(readSingle(f));
                }
            }
        } else {
            throw new IllegalArgumentException("No traits folder in " + characterPath + " character folder.");
        }

        return traits.toArray(Trait[]::new);
    }

    public static Trait readSingle(File traitFile) throws IOException, BadSOSDFormatException {
        return readSingle(traitFile, true, 0);
    }

    public static Trait readSingle(File traitFile, boolean isCustom, int baseLevel) throws IOException, BadSOSDFormatException {
        String ID = traitFile.getName().replace(".sosd", "");
        String name = "", description = "";

        boolean nameFound = false,
                descFound = false,
                baseValueFound = false;

        if (!isCustom) {
            baseValueFound = true;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(traitFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("name : ") && !nameFound) {
                    name = line.substring("name : ".length());
                    nameFound = true;
                }
                else if (line.startsWith("description : ") && !descFound) {
                    description = line.substring("description : ".length()).replace("\\n", "\n");
                    descFound = true;
                }
                else if (line.startsWith("baseLevel : ") && !baseValueFound) {
                    baseLevel = Integer.parseInt(line.substring("baseLevel : ".length()));
                    baseValueFound = true;
                }
            }
        }

        if (!nameFound || !descFound || !baseValueFound) {
            throw new BadSOSDFormatException("Trait file " + traitFile + " is incomplete");
        }

        return new Trait(ID, name, description, baseLevel, isCustom);
    }
}
