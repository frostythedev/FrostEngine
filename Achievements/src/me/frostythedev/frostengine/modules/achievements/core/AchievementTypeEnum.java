package me.frostythedev.frostengine.modules.achievements.core;

public enum AchievementTypeEnum {

    GENERAL("GENERAL"),
    ;


    private String name;

    AchievementTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
