package com.github.cldgdn.seaofstars.sosd.read;

import com.github.cldgdn.seaofstars.Constants;
import com.github.cldgdn.seaofstars.character.Implant;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImplantsReader {
    public static List<Implant> read(String characterPath) throws BadSOSDFormatException, IOException {
        File implantsFolder = new File(characterPath + "/implants");
        List<Implant> implants = new ArrayList<>();

        if (implantsFolder.exists() && implantsFolder.isDirectory()) {
            File[] files = implantsFolder.listFiles();
            if (files == null || files.length == 0) {
                throw new BadSOSDFormatException("No implant files in " + characterPath + "/implants folder. (at least empty lib.sosd required)");
            }

            for (File file : files) {
                if (file.getName().equals("lib.sosd")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;

                        while ((line = reader.readLine()) != null) {
                            Implant i = readSingle(new File(Constants.get.LIB_FOLDER + "/implants/" + line + ".sosd"));
                            i.isCustom = false;
                            implants.add(i);
                        }
                    }
                }
                else if (file.getName().endsWith(".sosd")) {
                    Implant i = readSingle(file);
                    i.isCustom = true;
                    implants.add(i);
                }
            }
        }

        return implants;
    }

    public static Implant readSingle(File file) throws BadSOSDFormatException, IOException {
        Implant i = new Implant();
        i.ID = file.getName().replace(".sosd", "");

        boolean nameFound = false,
                descFound = false,
                locationFound = false,
                humanCostFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("name : ") && !nameFound) {
                    i.name = line.substring("name : ".length());
                    nameFound = true;
                }
                else if (line.startsWith("description : ") && !descFound) {
                    i.description = line.substring("description : ".length()).replace("\\n", "\n");
                    descFound = true;
                }
                else if (line.startsWith("location : ") && !locationFound) {
                    i.location = line.substring("location : ".length());
                    locationFound = true;
                }
                else if (line.startsWith("humanCost : ") && !humanCostFound) {
                    i.humanCost = Integer.parseInt(line.substring("humanCost : ".length()));
                    humanCostFound = true;
                }
                else if (line.startsWith("# LIST")) {
                    if (!nameFound || !descFound || !locationFound || !humanCostFound) {
                        throw new BadSOSDFormatException("Implant file " + i.ID + ".sosd is either incomplete or the bonuses are not at the end.");
                    }
                    else {
                        i.bonuses = BonusReader.readBonuses(reader, i.ID);
                        break;
                    }
                }
            }
        }

        if (!nameFound || !descFound || !locationFound || !humanCostFound) {
            throw new BadSOSDFormatException("Incomplete implant file: " + i.ID + ".sosd");
        }

        return i;
    }
}
