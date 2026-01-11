package com.github.cldgdn.seaofstars.sosd.read;

import com.github.cldgdn.seaofstars.character.Item;
import com.github.cldgdn.seaofstars.throwable.exceptions.BadSOSDFormatException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemsReader {
    public static List<Item> read(String characterPath) throws BadSOSDFormatException, IOException {
        File itemsFolder = new File(characterPath + "/items");
        List<Item> items = new ArrayList<>();

        if (itemsFolder.exists() && itemsFolder.isDirectory()) {
            File[] files = itemsFolder.listFiles();
            if (files == null || files.length == 0) {
                return items;
            }

            for (File f : files) {
                items.add(readSingle(f));
            }
        } else {
            throw new IllegalArgumentException("No items folder in " + characterPath + " character folder.");
        }

        return items;
    }

    private static Item readSingle(File file) throws IOException, BadSOSDFormatException {
        Item item = new Item();
        item.ID = file.getName().replace(".sosd", "");
        boolean nameFound = false,
                descFound = false,
                amountFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("name : ") && !nameFound) {
                    item.name = line.substring("name : ".length());
                    nameFound = true;
                }
                else if (line.startsWith("description : ") && !descFound) {
                    item.description = line.substring("description : ".length());
                    descFound = true;
                }
                else if (line.startsWith("amount : ") && !amountFound) {
                    item.amount = Integer.parseInt(line.substring("amount : ".length()));
                    amountFound = true;
                }
            }
        }

        if (!nameFound || !descFound || !amountFound) {
            throw new BadSOSDFormatException(item.ID + ".sosd is incomplete.");
        }

        return item;
    }
}
