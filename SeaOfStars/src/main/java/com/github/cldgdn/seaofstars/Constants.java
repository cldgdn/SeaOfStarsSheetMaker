package com.github.cldgdn.seaofstars;

public class Constants {

    //TRAIT CONSTANTS-----------------------------------------------------
    public static final int MIN_TRAIT_BASE_LEVEL = 1;
    public static final int MAX_TRAIT_BASE_LEVEL = 7;
    public static final int MIN_TRAIT_LEVEL = 1;
    public static final int MAX_TRAIT_LEVEL = 2 * MAX_TRAIT_BASE_LEVEL;
    public static final int ZERO_TRAIT_LEVEL = 4;

    public static final int CRITICAL_CONDITION_BUFF = 4;
    public static final int CRITICAL_CONDITION_DEBUFF = -2;

    public static final String VIGOR_ID = "trt-vig";
    public static final String GRACE_ID = "trt-grc";
    public static final String ACUMEN_ID = "trt-acm";
    public static final String PANACHE_ID = "trt-pan";
    public static final String RESOLVE_ID = "trt-rsl";
    public static final String RESISTANCE_ID = "trt-rss";

    public static final int TRAITS_AMT = 6;
    public static final int STARTING_TRAIT_POINTS = 6;

    //ABILITY CONSTANTS---------------------------------------------------
    public static final int MIN_ABILITY_BASE_LEVEL = 1;
    public static final int MAX_ABILITY_BASE_LEVEL = 5;
    public static final int MIN_ABILITY_LEVEL = 1;
    public static final int MAX_ABILITY_LEVEL = 10;
    public static final int DICE_EXPLODE_ABILITY_LEVEL = 7;
    public static final int ABILITIES_AMT = 15;
    public static final int STARTING_ABILITY_POINTS = 16;

    //CHARACTER CONSTANTS-------------------------------------------------
    public static final int HUMAN_LIMIT_BASE = 3;
    public static final int HIT_POINT_BASE = 16;
    public static final int CRITICAL_HP = 3;

    public static final String HUMAN_LIMIT_ID = "chr-lmt";
    public static final String HIT_POINTS_MAX_ID = "chr-hpm";

    public static final int HERO_POINTS_MAX = 3;

    //RULES CONSTANTS-----------------------------------------------------
    public static final int ORIGIN_BONUSES_AMT = 2;
    public static final int BACKGROUND_BONUSES_AMT = 4;
    public static final int RISK_DICE_FACES = 10; //you roll d10s for risks
    public static final int INCREMENT_THRESHOLD = 10;
}
