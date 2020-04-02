package me.frostythedev.frostengine.modules;

import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PunishmentBan extends TimedPunishment {

    public PunishmentBan(PunishmentType punishmentType, String enforcer, String offender, String reason, long duration) {
        super(punishmentType, enforcer, offender, reason, duration);
    }

    public PunishmentBan(PunishmentType punishmentType, String enforcer, String offender, String reason, long timestamp, long duration) {
        super(punishmentType, enforcer, offender, reason, timestamp, duration);
    }

    @Override
    public void broadcast(boolean silent) {
        if(!silent){
            Locale.broadcast("&c" + getOffender() + " has been banned by " + getEnforcer() + " for " + getReason());
        }else{
            Locale.broadcast("fe_engine.admin", "&c" + getOffender() + " has been banned by " + getEnforcer() + " for " + getReason());
        }
    }

    @Override
    public void execute() {
        if(Bukkit.getPlayer(getOffender()) != null && Bukkit.getPlayer(getOffender()).isOnline()){
            Player offender = Bukkit.getPlayer(getOffender());
            offender.kickPlayer("&4&l&nBANNED \n\n&cYou have been banned by " + getEnforcer() + " for " + getReason() + "\n\n" +
                    "&6&lExpires: "+ (getExpiry() == -1 ? "&4&lPERMANENT" : "&b" + getEndTime()));
        }
    }
}
