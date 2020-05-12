package me.frostythedev.frostengine.bukkit.utils.hologram;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import me.frostythedev.frostengine.bukkit.exception.HologramDuplicateException;
import me.frostythedev.frostengine.bukkit.messaging.Locale;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class Hologram {

    @Inject
    private FEPlugin plugin;
    
    @Inject
    private HologramManager hologramManager;

    private List<Integer> ids;

    private String name;
    private List<String> lines;
    private Location location;

    private final double spacer = 0.27;

    public Hologram(String name, List<String> lines, Location location) {
        this.name = name;
        this.lines = lines;
        this.location = location;
        this.ids = Lists.newArrayList();
    }


    ///////////////////////////////////////////////
    // OVERRIDES
    ///////////////////////////////////////////////

    @Override
    public String toString() {
        return plugin.getGson().toJson(this);
    }


    ///////////////////////////////////////////////
    // MADE METHODS
    ///////////////////////////////////////////////

    public boolean show(Player player){
        if(!lines.isEmpty()){

           if(hologramManager.getHologram(player, name) == null){
               Location loc = getLocation().clone();
               int index = 1;
               for(String str : lines){
                   Location holoLoc = new Location(loc.getWorld(), loc.getX(), loc.getY()-(spacer*index), loc.getZ(),0,0);
                   int id = Holograms.getHandler().spawn(player, Locale.toColors(str), holoLoc);
                   this.ids.add(id);
                   index++;
               }
               if(hologramManager.getHolograms(player) == null){
                   hologramManager.getPlayerHolograms().put(player.getUniqueId(), Lists.newArrayList());
               }
               hologramManager.getHolograms(player).add(this);
               return true;
           }else{
               try {
                   throw new HologramDuplicateException();
               } catch (HologramDuplicateException e) {
                   e.printStackTrace();
                   return false;
               }
           }
        }else{
            return false;
        }
    }

    public void destroy(Player player){
        for(int id : ids){
            Holograms.getHandler().destroy(player, id);
        }
        hologramManager.getHolograms(player).remove(this);
    }


    ///////////////////////////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////////////////////////



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
