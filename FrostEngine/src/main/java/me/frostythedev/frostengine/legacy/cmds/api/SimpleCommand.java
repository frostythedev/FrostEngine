package me.frostythedev.frostengine.legacy.cmds.api;

import org.bukkit.plugin.java.JavaPlugin;

public class SimpleCommand extends Command {

    public SimpleCommand(JavaPlugin plugin, String name) {
      this(plugin, name, "");
    }

    public SimpleCommand(JavaPlugin plugin, String name, String permission) {
      this(plugin, name, permission, "");
    }

    public SimpleCommand(JavaPlugin plugin, String name, String permission, String... aliases) {
       this(plugin, name, permission, aliases, false);
    }

    public SimpleCommand(JavaPlugin plugin, String name, String permission, String[] aliases, boolean playerOnly) {
        super(name, permission, aliases, playerOnly);
    }

    public void register(JavaPlugin plugin){
        plugin.getCommand(getName()).setExecutor(plugin);
    }

}
