package me.frostythedev.frostengine.modules;

import com.google.common.collect.Lists;
import me.frostythedev.frostengine.bukkit.impl.Constants;
import me.frostythedev.frostengine.bukkit.utils.items.ItemBuilder;
import me.frostythedev.frostengine.config.BukkitDocument;
import me.frostythedev.frostengine.util.Trackable;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class ColleactableGadget implements Trackable {

    private final String[] DEFAULT_PARTS = new String[]{getName() + "a", getName() + "b", getName() + "c", getName() + "d"};
    public final String DEFAULT_SAVE_DIRECTORY = Constants.MODULE_STORAGE_DIRECTORY + "/collectables/players/";

    private final int id;
    private final String name;
    private final boolean enabled;
    private String[] parts;
    private String saveDirectory;

    private ItemBuilder icon;

    public ColleactableGadget(int id, String name, boolean enabled) {
        this.enabled = enabled;
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public String[] getParts() {
        return parts;
    }

    public void setParts(String... parts) {
        this.parts = parts;
    }

    @Override
    public String getSaveDirectory() {
        return (this.saveDirectory == null || this.saveDirectory.equals("") ? this.DEFAULT_SAVE_DIRECTORY : this.saveDirectory);
    }

    public void setSaveDirectory(String saveDirectory) {
        this.saveDirectory = saveDirectory;
    }

    public int getTotal(){
        return this.getParts().length;
    }

    public ItemBuilder getIcon() {
        return icon;
    }

    public void setIcon(ItemBuilder icon) {
        this.icon = icon;
    }

    public boolean give(Player player){

        BukkitDocument document = BukkitDocument.of(this.getSaveDirectory() + player.getUniqueId().toString() + ".yml");
        document.load();
        document.createStringListIfNotExists("Unlocked");
        List<String> stringList = document.getStringList("Unlocked");

        String[] savedParts = CollectableUtils.searchStringListForCollectable(stringList, this);

        if(hasCompleted(savedParts)){
            return false;
        }

        String[] unOwned = this.getUncompletedParts(savedParts);
        String random = unOwned[ThreadLocalRandom.current().nextInt(unOwned.length)];

        String newData = this.getName() + ":";

        if (savedParts.length == 0) {
            newData += random;
        } else {
            newData += random;
            for (String str : savedParts) {
                newData += ";" + str;
            }
        }
        stringList.add(newData);

        document.set("Unlocked", stringList);
        document.save();
        return true;
    }

    public int getAmountCompleted(String[] array){
        int count = 0;
        List<String> listParts = Lists.newArrayList(this.getParts());
        for(String str : array){
            if(listParts.contains(str)){
                count++;
            }
        }
        return count;
    }

    public int getPercentageCompleted(String[] array){
        return (getAmountCompleted(array) / this.getParts().length * 100);
    }

    public boolean hasCompleted(String[] array){
        return this.getAmountCompleted(array) == this.parts.length;
    }



    public String[] getUncompletedParts(String[] array){
        if(hasCompleted(array)){
            return null;
        }

        List<String> unowned = Lists.newArrayList();
        List<String> listParts = Lists.newArrayList(array);

        for(String str : this.getParts()){
            if(!listParts.contains(str)){
                unowned.add(str);
            }
        }

        return unowned.toArray(new String[unowned.size()]);
    }


    /////// PLAYER API ////////

    public boolean hasCompleted(Player player){
        return CollectableAPI.hasCompleted(player, this);
    }

    public int getPercentageCompleted(Player player){
        return CollectableAPI.getPercentageCompleted(player, this);
    }

    public int getAmountCompleted(Player player){
        return CollectableAPI.getAmountCompleted(player, this);
    }

    public abstract void activate(Player player);
}
