package com.github.cldgdn.seaofstars;

import com.github.cldgdn.seaofstars.character.Character;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.IntConsumer;
import java.util.function.UnaryOperator;

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

    public static void configureIntegerField(
            TextField textField,
            int min,
            int max,
            IntConsumer onCommit
    ) {
        if (min > max) {
            throw new IllegalArgumentException("Integer field minimum value cannot be greater than the maximum." +
                    "\nField: " + textField.getId() + "\nMin, max: (" + min + ", "+ max + ")");
        }

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (newText.matches("-?\\d*")) {
                return change;
            }

            return null;
        };

        textField.setTextFormatter(new TextFormatter<>(filter));

        String[] lastValidText = {textField.getText()};

        Runnable commit = () -> {
            String text = textField.getText().trim();

            try {
                int value = Integer.parseInt(text);

                if (value < min || value > max) {
                    throw new NumberFormatException("Value " + value + " is outside the allowed range: [" + min + ", " + max + "]");
                }

                onCommit.accept(value);
                lastValidText[0] = text;
            } catch (NumberFormatException e) {
                textField.setText(lastValidText[0]);
            }
        };

        textField.addEventHandler(ActionEvent.ACTION, event -> commit.run());

        textField.focusedProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue) {
                commit.run();
            }
        });
    }

    public static void configureIntegerField(
            TextField field,
            IntConsumer onCommit
    ) {
        configureIntegerField(
                field,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE,
                onCommit
        );
    }
}
