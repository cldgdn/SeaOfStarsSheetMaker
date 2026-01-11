package com.github.cldgdn.seaofstars.sosd.write;

import com.github.cldgdn.seaofstars.character.Item;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ItemsWriter {
    public static void write(List<Item> items, String characterPath) throws IOException {
        Path dirPath = Path.of(characterPath + "/items");

        Files.createDirectories(dirPath);

        for (Item item : items) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "/items/" + item.ID + ".sosd"))) {
                writer.write("name : " + item.name + "\n");
                writer.write("description : " + item.description + "\n");
                writer.write("amount : " + item.amount);
            }
        }
    }
}
