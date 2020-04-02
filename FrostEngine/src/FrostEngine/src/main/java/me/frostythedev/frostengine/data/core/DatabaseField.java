package me.frostythedev.frostengine.data.core;

public interface DatabaseField {
    String ID_PRIMARY_KEY = "id INT PRIMARY KEY AUTO_INCREMENT";
    String VARCHAR_9999 = "data VARCHAR(9999)";
    String KEY_DATA_VALUES = ID_PRIMARY_KEY + ", " + VARCHAR_9999;

    String ACHIEVEMENT_TABLE = "fe_achievements";
    String ACHIEVEMENT_PLAYERS_TABLE = "fe_ach_players";
    String ACHIEVEMENT_PLAYERS_VALUES = "id INT PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(36), data VARCHAR(9999)";

    String PUNISHMENT_TABLE = "fe_punish";
    String PUNISHMENT_PLAYER_TABLE = "fe_punish_players";
    String PUNISHMENT_VALUES = "id INT PRIMARY KEY AUTO_INCREMENT, user_id VARCHAR(9999), data VARCHAR(9999)";
    String PUNISHMENT_PLAYER_VALUES = "id INT PRIMARY KEY AUTO_INCREMENT, data VARCHAR(9999)";

    String MAPS_TABLE = "fe_maps";
    String MAPS_VALUES = KEY_DATA_VALUES;

    String ARENA_TABLE = "fe_arenas";
    String ARENA_VALUES = KEY_DATA_VALUES;

    String KIT_TABLE = "fe_kits";
    String KIT_VALUES = ID_PRIMARY_KEY + ", name VARCHAR(16), " + VARCHAR_9999;

    String PLAYERS_TABLE = "fe_players";
    String PLAYER_VALUES = KEY_DATA_VALUES;

    String HOLO_TABLE = "fe_holo";
    String HOLO_VALUES = KEY_DATA_VALUES;

    String NPC_TALBE = "fe_npcs";
    String NPC_VALUES = KEY_DATA_VALUES;
}
