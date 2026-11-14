package com.github.cldgdn.seaofstars;

import com.github.cldgdn.seaofstars.character.Character;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;

public class Utils {
    public static String genID(String pref, String name) {
        String id = pref.substring(0, 3) + "-" + name.replace(" ", "").toLowerCase();

        /*char[] chars = name.toCharArray();
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
        }*/

        return id;
    }

    public static void setStyle(Scene scene) {
        if (Constants.get.DARK_THEME) {
            scene.getStylesheets().add(
                    SeaOfStarsMain.class.getResource("dark-theme.css").toExternalForm()
            );
        }
    }

    public static void setStyle(DialogPane pane) {
        if (Constants.get.DARK_THEME) {
            pane.getStylesheets().add(
                    SeaOfStarsMain.class.getResource("dark-theme.css").toExternalForm()
            );
        }
    }
}
