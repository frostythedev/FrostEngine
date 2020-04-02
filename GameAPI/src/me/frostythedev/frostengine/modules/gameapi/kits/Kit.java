package gameapi.kits;

import org.bukkit.entity.Player;

public interface Kit {

    default void save(){}

    default void giveKit(Player player){

    }
}
