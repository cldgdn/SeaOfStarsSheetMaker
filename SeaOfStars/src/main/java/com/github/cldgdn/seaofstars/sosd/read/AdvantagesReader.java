package com.github.cldgdn.seaofstars.sosd.read;

import com.github.cldgdn.seaofstars.character.Advantage;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdvantagesReader {
    public static List<Advantage> read(String characterPath) throws BadSOSDFormatException, IOException {
        File advantagesFolder = new File(characterPath + "/advantages");
        List<Advantage> advantages = new ArrayList<>();

        if (advantagesFolder.exists() && advantagesFolder.isDirectory()) {
            File[] files = advantagesFolder.listFiles();
            if (files == null || files.length == 0) {
                throw new BadSOSDFormatException("No advantage files in " + characterPath + "/advantages folder");
            }

            for (File file : files) {
                if (file.getName().equals("lib.sosd")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;

                        while ((line = reader.readLine()) != null) {
                            Advantage a = readSingle(new File("lib/advantages/" + line + ".sosd"));
                            a.isCustom = false;
                            advantages.add(a);
                        }
                    }
                }
                else if (file.getName().endsWith(".sosd")) {
                    Advantage a = readSingle(file);
                    a.isCustom = true;
                    advantages.add(a);
                }
            }
        }

        return advantages;
    }

    public static Advantage readSingle(File file) throws IOException, BadSOSDFormatException {
        Advantage a = new Advantage();
        a.ID = file.getName().replace(".sosd", "");

        boolean nameFound = false,
                descFound = false,
                passiveFound = false,
                pointCostFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("name : ") && !nameFound) {
                    a.name = line.substring("name : ".length());
                    nameFound = true;
                }
                else if (line.startsWith("description : ") && !descFound) {
                    a.description = line.substring("description : ".length()).replace("\\n", "\n");
                    descFound = true;
                }
                else if (line.startsWith("isPassive : ") && !passiveFound) {
                    a.isPassive = Boolean.parseBoolean(line.substring("isPassive : ".length()));
                    passiveFound = true;
                }
                else if (line.startsWith("heroPointCost : ") && !pointCostFound) {
                    a.heroPointCost = Integer.parseInt(line.substring("heroPointCost : ".length()));
                    pointCostFound = true;
                }
                else if (line.startsWith("# LIST")) {
                    if (!nameFound || !descFound || !passiveFound || !pointCostFound) {
                        throw new BadSOSDFormatException("Advantage file " + a.ID + ".sosd is either incomplete or the bonuses are not at the end.");
                    }
                    else {
                        a.bonuses = BonusReader.readBonuses(reader, a.ID);
                        break;
                    }
                }
            }
        }

        if (!nameFound || !descFound || !passiveFound || !pointCostFound) {
            throw new BadSOSDFormatException("Incomplete advantage file: " + a.ID + ".sosd");
        }

        return a;
    }
}
