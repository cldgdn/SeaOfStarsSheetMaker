package com.github.cldgdn.seaofstars;

import com.github.cldgdn.seaofstars.character.Ability;
import com.github.cldgdn.seaofstars.character.Advantage;
import com.github.cldgdn.seaofstars.character.Implant;
import com.github.cldgdn.seaofstars.character.Trait;
import com.github.cldgdn.seaofstars.sosd.read.AbilitiesReader;
import com.github.cldgdn.seaofstars.sosd.read.AdvantagesReader;
import com.github.cldgdn.seaofstars.sosd.read.ImplantsReader;
import com.github.cldgdn.seaofstars.sosd.read.TraitsReader;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;

import java.io.File;
import java.io.IOException;

public class LibCache {
    private static Trait[] traits = null;
    private static Ability[] abilities = null;
    private static Advantage[] advantages = null;
    private static Implant[] implants = null;

    public static Trait[] getTraits(boolean reset) throws BadSOSDFormatException, IOException {
        if (reset || traits == null) {
            File traitsFolder = new File("lib/traits");

            if (traitsFolder.exists() && traitsFolder.isDirectory()) {
                File[] traitFiles = traitsFolder.listFiles();

                if (traitFiles != null && traitFiles.length != 0) {
                    int len = traitFiles.length;

                    for (File f : traitFiles) {
                        if (f.getName().startsWith("."))
                            len--;
                    }

                    traits = new Trait[len];

                    int j = 0;
                    for (int i = 0; i < traitFiles.length; i++) {
                        if (traitFiles[i].getName().startsWith(".")) {
                            System.out.println("[INFO] skipped file in lib/traits: " + traitFiles[i].getName());
                            continue;
                        }

                        traits[j] = TraitsReader.readSingle(traitFiles[i], false, Constants.get.ZERO_TRAIT_LEVEL);
                        j++;
                    }
                }
            }
        }

        return traits;
    }

    public static Trait[] getTraits() throws BadSOSDFormatException, IOException {
        return getTraits(false);
    }

    public static Ability[] getAbilities(boolean reset) throws BadSOSDFormatException, IOException {
        if (reset || abilities == null) {
            File abilitiesFolder = new File("lib/abilities");

            if (abilitiesFolder.exists() && abilitiesFolder.isDirectory()) {
                File[] abilityFiles = abilitiesFolder.listFiles();

                if (abilityFiles != null && abilityFiles.length != 0) {
                    int len = abilityFiles.length;

                    for (File f : abilityFiles) {
                        if (f.getName().startsWith("."))
                            len--;
                    }

                    abilities = new Ability[len];

                    int j = 0;
                    for (int i = 0; i < abilityFiles.length; i++) {
                        if (abilityFiles[i].getName().startsWith(".")) {
                            System.out.println("INFO: skipped file in lib/abilities: " + abilityFiles[i].getName());
                            continue;
                        }

                        abilities[j] = AbilitiesReader.readSingle(abilityFiles[i], false, Constants.get.MIN_ABILITY_LEVEL);
                        j++;
                    }
                }
            }
        }

        return abilities;
    }

    public static Ability[] getAbilities() throws BadSOSDFormatException, IOException {
        return getAbilities(false);
    }

    public static Advantage[] getAdvantages(boolean reset) throws BadSOSDFormatException, IOException {
        if (reset || advantages == null) {
            File advantagesFolder = new File("lib/advantages");

            if (advantagesFolder.exists() && advantagesFolder.isDirectory()) {
                File[] advantageFiles = advantagesFolder.listFiles();

                if (advantageFiles != null && advantageFiles.length != 0) {
                    int len = advantageFiles.length;

                    for (File f : advantageFiles) {
                        if (f.getName().startsWith("."))
                            len--;
                    }

                    advantages = new Advantage[len];

                    int j = 0;
                    for (int i = 0; i < advantageFiles.length; i++) {
                        if (advantageFiles[i].getName().startsWith(".")) {
                            System.out.println("INFO: skipped file in lib/advantages: " + advantageFiles[i].getName());
                            continue;
                        }

                        advantages[j] = AdvantagesReader.readSingle(advantageFiles[i]);
                        j++;
                    }
                }
            }
        }

        return advantages;
    }

    public static Advantage[] getAdvantages() throws BadSOSDFormatException, IOException {
        return getAdvantages(false);
    }

    public static Implant[] getImplants(boolean reset) throws BadSOSDFormatException, IOException {
        if (reset || implants == null) {
            File implantsFolder = new File("lib/implants");

            if (implantsFolder.exists() && implantsFolder.isDirectory()) {
                File[] implantFiles = implantsFolder.listFiles();

                if (implantFiles != null && implantFiles.length != 0) {
                    int len = implantFiles.length;

                    for (File f : implantFiles) {
                        if (f.getName().startsWith("."))
                            len--;
                    }

                    implants = new Implant[len];

                    int j = 0;
                    for (int i = 0; i < implantFiles.length; i++) {
                        if (implantFiles[i].getName().startsWith(".")) {
                            System.out.println("INFO: skipped file in lib/implants: " + implantFiles[i].getName());
                            continue;
                        }

                        implants[j] = ImplantsReader.readSingle(implantFiles[i]);
                        j++;
                    }
                }
            }
        }

        return implants;
    }

    public static Implant[] getImplants() throws BadSOSDFormatException, IOException {
        return getImplants(false);
    }
}
