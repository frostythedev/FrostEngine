package me.frostythedev.frostengine.modules;

import me.frostythedev.frostengine.util.Trackable;
import net.md_5.bungee.api.ChatColor;

import java.util.List;

public class CollectableUtils {

    public static String[] searchStringListForCollectable(List<String> list, Trackable collectable){
        for(String str : list){
            if(str.contains(collectable.getName())){
                list.remove(str);
               String data = str.split(":")[1];
                return data.split(";");
            }
        }
        return new String[0];
    }

    public static String getBarStatus(int lines, int amount, int total, ChatColor color1, ChatColor color2){
        String bar = "";

        int amountColored = (int) (((double)amount/total) *lines);
        int unColoredAmount = lines - amountColored;

        for(int i = 0; i < amountColored; i++){
            bar+=color1 + ":";
        }
        for(int i = 0; i < unColoredAmount; i++){
            bar+=color2 + ":";
        }

        return bar;
    }
}
