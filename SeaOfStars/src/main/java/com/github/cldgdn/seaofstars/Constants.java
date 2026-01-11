package com.github.cldgdn.seaofstars;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Constants {

    //TRAIT CONSTANTS-----------------------------------------------------
    public int MIN_TRAIT_BASE_LEVEL = 1;
    public int MAX_TRAIT_BASE_LEVEL = 7;
    public int MIN_TRAIT_LEVEL = 1;
    public int MAX_TRAIT_LEVEL = 2 * MAX_TRAIT_BASE_LEVEL;
    public int ZERO_TRAIT_LEVEL = 4;

    public int CRITICAL_CONDITION_BUFF = 4;
    public int CRITICAL_CONDITION_DEBUFF = -2;

    public static final String VIGOR_ID = "trt-vig";
    public static final String GRACE_ID = "trt-grc";
    public static final String ACUMEN_ID = "trt-acm";
    public static final String PANACHE_ID = "trt-pan";
    public static final String RESOLVE_ID = "trt-rsv";
    public static final String RESISTANCE_ID = "trt-rst";

    public int STARTING_TRAIT_POINTS = 6;

    //ABILITY CONSTANTS---------------------------------------------------
    public int MIN_ABILITY_BASE_LEVEL = 1;
    public int MAX_ABILITY_BASE_LEVEL = 5;
    public int MIN_ABILITY_LEVEL = 1;
    public int MAX_ABILITY_LEVEL = 10;
    public int DICE_EXPLODE_ABILITY_LEVEL = 7;
    public int STARTING_ABILITY_POINTS = 16;

    //CHARACTER CONSTANTS-------------------------------------------------
    public int HUMAN_LIMIT_BASE = 3;
    public int HIT_POINT_BASE = 16;
    public int CRITICAL_HP = 3;

    public static final String HUMAN_LIMIT_ID = "chr-lmt";
    public static final String HIT_POINTS_MAX_ID = "chr-hpm";

    //RULES CONSTANTS-----------------------------------------------------
    public int ORIGIN_BONUSES_AMT = 2;
    public int BACKGROUND_BONUSES_AMT = 3;
    public int RISK_DICE_FACES = 10; //you roll d10s for risks
    public int INCREMENT_THRESHOLD = 10;
    public int ADVANTAGES_AT_CREATION_AMT = 3;

    public static Constants get = null;

    public Constants() {
        get = this;

        try (BufferedReader reader = new BufferedReader(new FileReader("lib/config"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank() || line.startsWith("#")) continue;

                if (line.startsWith("MIN TRAIT BASE LEVEL")) {
                    MIN_TRAIT_BASE_LEVEL = getValue(line);
                } else if (line.startsWith("MAX TRAIT BASE LEVEL")) {
                    MAX_TRAIT_BASE_LEVEL = getValue(line);
                } else if (line.startsWith("MIN TRAIT LEVEL")) {
                    MIN_TRAIT_LEVEL = getValue(line);
                } else if (line.startsWith("MAX TRAIT LEVEL")) {
                    MAX_TRAIT_LEVEL = getValue(line);
                } else if (line.startsWith("ZERO TRAIT LEVEL")) {
                    ZERO_TRAIT_LEVEL = getValue(line);
                } else if (line.startsWith("CRITICAL CONDITION BUFF")) {
                    CRITICAL_CONDITION_BUFF = getValue(line);
                } else if (line.startsWith("CRITICAL CONDITION DEBUFF")) {
                    CRITICAL_CONDITION_DEBUFF = getValue(line);
                } else if (line.startsWith("STARTING TRAIT POINTS")) {
                    STARTING_TRAIT_POINTS = getValue(line);
                } else if (line.startsWith("MIN ABILITY BASE LEVEL")) {
                    MIN_ABILITY_BASE_LEVEL = getValue(line);
                } else if (line.startsWith("MAX ABILITY BASE LEVEL")) {
                    MAX_ABILITY_BASE_LEVEL = getValue(line);
                } else if (line.startsWith("MIN ABILITY LEVEL")) {
                    MIN_ABILITY_LEVEL = getValue(line);
                } else if (line.startsWith("MAX ABILITY LEVEL")) {
                    MAX_ABILITY_LEVEL = getValue(line);
                } else if (line.startsWith("DICE EXPLODE ABILITY LEVEL")) {
                    DICE_EXPLODE_ABILITY_LEVEL = getValue(line);
                } else if (line.startsWith("STARTING ABILITY POINTS")) {
                    STARTING_ABILITY_POINTS = getValue(line);
                } else if (line.startsWith("HUMAN LIMIT BASE")) {
                    HUMAN_LIMIT_BASE = getValue(line);
                } else if (line.startsWith("HIT POINT BASE")) {
                    HIT_POINT_BASE = getValue(line);
                } else if (line.startsWith("CRITICAL HP")) {
                    CRITICAL_HP = getValue(line);
                } else if (line.startsWith("ORIGIN BONUSES AMT")) {
                    ORIGIN_BONUSES_AMT = getValue(line);
                } else if (line.startsWith("BACKGROUND BONUSES AMT")) {
                    BACKGROUND_BONUSES_AMT = getValue(line);
                } else if (line.startsWith("RISK DICE FACES")) {
                    RISK_DICE_FACES = getValue(line);
                } else if (line.startsWith("INCREMENT THRESHOLD")) {
                    INCREMENT_THRESHOLD = getValue(line);
                } else if (line.startsWith("ADVANTAGES AT CREATION AMT")) {
                    ADVANTAGES_AT_CREATION_AMT = getValue(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getValue(String s) {
        return Integer.parseInt(
                s.substring(
                        s.indexOf(':') + 1
                ).replace(" ", "")
        );
    }
}
