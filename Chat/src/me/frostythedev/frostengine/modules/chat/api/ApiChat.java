package me.frostythedev.frostengine.modules.chat.api;

import org.bukkit.entity.Player;

public interface ApiChat {

    ApiChat DEFAULT = new ApiChat() {};

    default void chat(Player player, String message){

    }

    default String getPrefix(Player player){
        return "";
    }

    default String getSuffix(Player player){
        return "";
    }

    default String getFormat(){
        return "";
    }

}
