package com.github.cldgdn.seaofstars.sosd.write;

import com.github.cldgdn.seaofstars.character.CharacterInfo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class InfoWriter {

    public static void write(CharacterInfo info, String characterPath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(characterPath + "/info.sosd"))) {
            writer.write("name : " + info.name + "\n");
            writer.write("playerName : " + info.playerName + "\n");
            writer.write("age : " + info.age + "\n");
            writer.write("fame : " + info.fame + "\n");
            writer.write("money : " + info.money + "\n");
        }
    }
}
