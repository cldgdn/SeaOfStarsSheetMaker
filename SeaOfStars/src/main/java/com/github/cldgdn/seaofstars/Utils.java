package com.github.cldgdn.seaofstars;

import com.github.cldgdn.seaofstars.character.Character;

public class Utils {
    public static String genID(String pref, String name) {
        String id = pref.substring(0, 3) + "-";

        char[] chars = name.toCharArray();
        int i = 0, consonants = 0;

        for (; i < chars.length; i++) {
            if ("AEIOUaeiou ".indexOf(chars[i]) == -1) {
                consonants ++;
            }
        }

        for (i = 0; i < chars.length; i++) {
            if (consonants < 3 && chars[i] != ' ') {
                id += chars[i];
                consonants++;
            } else if ("AEIOUaeiou ".indexOf(chars[i]) == -1) {
                id += chars[i];
            }

            if (id.length() == 7) break;
        }

        while (id.length() < 7) {
            id += '_';
        }

        return id.toLowerCase();
    }
}
