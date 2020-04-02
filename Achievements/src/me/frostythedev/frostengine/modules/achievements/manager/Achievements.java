package me.frostythedev.frostengine.modules.achievements.manager;

import me.frostythedev.frostengine.modules.achievements.core.Achievement;
import me.frostythedev.frostengine.modules.achievements.core.AchievementType;

import java.util.Arrays;
import java.util.List;

public enum  Achievements {
    FIRST_JOIN(0,"First Join", true, "GENERAL", "Join the server for the very first time"),
    ;


    private int id;
    private String name;
    private boolean enabled;
    private String achievementType;
    private List<String> description;

    Achievements(int id, String name, boolean enabled, String achievementType, List<String> description) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.achievementType = achievementType;
        this.description = description;
    }

    Achievements(int id, String name, boolean enabled, String achievementType, String... description) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.achievementType = achievementType;
        this.description = Arrays.asList(description);
    }

    ///////////////////////////////////////////////
    // MADE METHODS
    ///////////////////////////////////////////////

    public Achievement get(){
        return new Achievement(getId(), getName(), isEnabled(), getAchievementType(), getDescription());
    }


    ///////////////////////////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////////////////////////



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAchievementType() {
        return achievementType;
    }

    public void setAchievementType(String achievementType) {
        this.achievementType = achievementType;
    }
}
