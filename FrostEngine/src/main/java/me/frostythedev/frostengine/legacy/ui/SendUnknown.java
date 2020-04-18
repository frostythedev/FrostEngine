package me.frostythedev.frostengine.legacy.ui;

import org.bukkit.entity.Player;

public class SendUnknown
{
    public static void toSender(String message, Object sender)
    {
        if ((sender instanceof Player)) {
            SendGame.toPlayer(message, (Player)sender);
        } else {
            SendConsole.message(message);
        }
    }
}
