package me.frostythedev.frostengine.bukkit.utils.location;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import org.bukkit.Location;

import java.util.List;

public class LocationUtil {

    @Inject private static FEPlugin plugin;

    public static boolean isInRadius(Location center, Location loc, double radius) {
        /*
        If the world of the 2 locations isn't the same,
		then they're clearly not in the radius!
		 */
        if (!loc.getWorld().equals(center.getWorld())) {
            return false;
        }

        return center.distanceSquared(loc) <= (radius * radius);
    }


    public static String listOfLocationsToString(List<Location> locations){
        StringBuilder result = new StringBuilder();
        if(!locations.isEmpty()){
            for(Location loc : locations){
                if(result.toString().equals("")){
                    result.append(plugin.getGson().toJson(loc));
                }else{
                    result.append("#").append(plugin.getGson().toJson(loc));
                }
            }
        }
        return result.toString();
    }

    public static List<Location> listOfLocationsFromString(String data){
        List<Location> locations = Lists.newArrayList();
        if(!data.contains("#")){
            locations.add(plugin.getGson().fromJson(data, Location.class));
        }else{
            for(String part : data.split("#")){
                Location l = plugin.getGson().fromJson(part, Location.class);
                if(l != null){
                    locations.add(l);
                }
            }
        }
        return locations;
    }
}
