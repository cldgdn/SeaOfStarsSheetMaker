package com.github.cldgdn.seaofstars.character;

public class CharacterInfo {
    public String name;
    public String playerName;
    public int age;
    public String fame;
    public int money;

    public CharacterInfo(String name, String playerName, int age, String fame, int money) {
        this.name = name;
        this.playerName = playerName;
        this.age = age;
        this.fame = fame;
        this.money = money;
    }

    public CharacterInfo() {
        this.money = 0;
    }
}
