package com.github.cldgdn.seaofstars;

import com.github.cldgdn.seaofstars.character.Ability;
import com.github.cldgdn.seaofstars.character.Trait;
import com.github.cldgdn.seaofstars.sosd.read.AbilitiesReader;
import com.github.cldgdn.seaofstars.sosd.read.TraitsReader;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;

import java.io.File;
import java.io.IOException;

public class LibCache {
    private static Trait[] traits = null;
    private static Ability[] abilities = null;

    public static Trait[] getTraits(boolean reset) throws BadSOSDFormatException, IOException {
        if (reset || traits == null) {
            File traitsFolder = new File("lib\\traits");

            if (traitsFolder.exists() && traitsFolder.isDirectory()) {
                File[] traitFiles = traitsFolder.listFiles();

                if (traitFiles != null && traitFiles.length != 0) {
                    traits = new Trait[traitFiles.length];

                    for (int i = 0; i < traitFiles.length; i++) {
                        traits[i] = TraitsReader.readSingle(traitFiles[i], false, Constants.ZERO_TRAIT_LEVEL);
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
            File abilitiesFolder = new File("lib\\abilities");

            if (abilitiesFolder.exists() && abilitiesFolder.isDirectory()) {
                File[] abilityFiles = abilitiesFolder.listFiles();

                if (abilityFiles != null && abilityFiles.length != 0) {
                    abilities = new Ability[abilityFiles.length];

                    for (int i = 0; i < abilityFiles.length; i++) {
                        abilities[i] = AbilitiesReader.readSingle(abilityFiles[i], false, Constants.MIN_ABILITY_LEVEL);
                    }
                }
            }
        }

        return abilities;
    }

    public static Ability[] getAbilities() throws BadSOSDFormatException, IOException {
        return getAbilities(false);
    }
}
