package me.frostythedev.frostengine.modules.gameapi.core.settings;

import me.frostythedev.frostengine.bukkit.utils.LogUtils;
import me.frostythedev.frostengine.modules.gameapi.Minigame;
import me.frostythedev.frostengine.modules.gameapi.ModuleGameAPI;
import me.frostythedev.frostengine.modules.gameapi.core.Setting;
import me.frostythedev.frostengine.modules.gameapi.core.gui.GUIGameSettings2;
import me.frostythedev.frostengine.modules.gameapi.core.settings.types.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class SettingManager {

    private Map<String, Setting<?>> settings;

    public int id;
    private Minigame minigame;

    public SettingManager(Minigame minigame) {

        if(minigame != null){

            this.minigame = minigame;

            minigame.setSettingManager(this);
            this.settings = new HashMap<>();
            id = ThreadLocalRandom.current().nextInt();
            System.out.println("Minigame passthrough setting manager: " + minigame.getName());
            System.out.println("SM ID: " + id);

            load(new BlockBreak(minigame, "Break", false));
            load(new BlockPlace(minigame, "Place", false));
            load(new Buckets(minigame, "Buckets", false));
            load(new Chat(minigame, "Chat", false));
            load(new Drop(minigame, "Drop", false));
            load(new Explode(minigame, "Explode", false));
            load(new InteractEntity(minigame, "InteractEntity", false));
            load(new Movement(minigame, "Movement", false));
            load(new Notify(minigame, "Notify", false));
            load(new Pickup(minigame, "Pickup", false));
            load(new PVE(minigame, "PVE", false));
            load(new PvP(minigame, "PVP", false));
            load(new Target(minigame, "Target", false));
            load(new Teleport(minigame, "Teleport", false));

            if(minigame.getSettingManager() != null){
                minigame.setSettings(new GUIGameSettings2(minigame));
            }else{
                LogUtils.severe("Could not find settings manager for minigame");
            }
        }else{
            LogUtils.severe("Could not find supplied minigame!");
        }
    }

    public void load(Setting<?> setting){
        if(get(setting.getName()).isPresent()) return;

        this.settings.put(setting.getName().toLowerCase(), setting);
       minigame.registerListeners(setting);
    }

    public boolean toggle(String name){
        if(get(name.toLowerCase()).isPresent()){
            boolean b = !get(name.toLowerCase()).get().isEnabled();
            get(name.toLowerCase()).get().setEnabled(b);
            return true;
        }

        return false;
    }

    public void toggleOn(String name){
        get(name.toLowerCase()).ifPresent(s -> s.setEnabled(true));
    }

    public void toggleOff(String name){
        get(name.toLowerCase()).ifPresent(s -> s.setEnabled(false));
    }

    public Optional<Setting<?>> get(String name){
        if(settings.containsKey(name)) return Optional.of(settings.get(name));
        return Optional.empty();
    }

    public Collection<Setting<?>> getAll() {
        return this.settings.values();
    }
}
