package me.frostythedev.frostengine.legacy.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import me.frostythedev.frostengine.legacy.ui.SendConsole;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Configuration
{
    private static Plugin plugin = null;
    private static YamlConfiguration pluginConfig;
    private static File configFile;
    private static int configVersion = 1;
    private boolean debug;
    private boolean log;
    private boolean firstStart;

    public Configuration(Plugin plugin)
    {
        plugin = plugin;
    }

    public Configuration(Plugin plugin, int config)
    {
        plugin = plugin;
        configVersion = config;
        loadConfig();
        if ((configVersion != -1) && (configVersion != getConfig().getInt("config"))) {
            updateConfig();
        }
        this.debug = getConfig().getBoolean("debug");
        this.log = getConfig().getBoolean("log");
        this.firstStart = getConfig().getBoolean("firstStart");
        if (this.firstStart) {
            getConfig().set("firstStart", Boolean.valueOf(false));
        }
    }

    public Configuration(Plugin plugin, int config, boolean remote)
    {
        plugin = plugin;
        configVersion = config;
        loadConfig();
        if ((configVersion != -1) &&
                (configVersion != getConfig().getInt("config"))) {
            updateConfig();
        }
        this.debug = getConfig().getBoolean("debug");
        this.log = getConfig().getBoolean("log");
        this.firstStart = getConfig().getBoolean("firstStart");
        if (this.firstStart) {
            getConfig().set("firstStart", Boolean.valueOf(false));
        }
        if (remote) {}
    }

    private static boolean loaded = false;

    public static YamlConfiguration getConfig()
    {
        if (!loaded) {
            loadConfig();
        }
        return pluginConfig;
    }

    public static File getConfigFile()
    {
        return configFile;
    }

    public static String getContents()
    {
        try
        {
            FileInputStream fis = new FileInputStream(configFile);
            byte[] data = new byte[(int)configFile.length()];
            fis.read(data);
            fis.close();

            return new String(data, "UTF-8");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }

    public static int getConfigVersion()
    {
        return configVersion;
    }

    public static void setConfigVersion(int configVersion)
    {
        configVersion = configVersion;
    }

    public static void loadConfig()
    {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (configFile.exists())
        {
            pluginConfig = new YamlConfiguration();
            try
            {
                pluginConfig.load(configFile);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            loaded = true;
        }
        else
        {
            try
            {
                plugin.getDataFolder().mkdir();
                InputStream jarURL = plugin.getClass().getResourceAsStream("/config.yml");
                if (jarURL != null)
                {
                    SendConsole.info("Copying '" + configFile + "' from the resources!");

                    copyFile(jarURL, configFile);
                    pluginConfig = new YamlConfiguration();
                    pluginConfig.load(configFile);
                    loaded = true;
                }
                else
                {
                    SendConsole.severe("Configuration file not found inside the plugin!");

                    SendConsole.severe("This error occurs when you forced a reload!");
                }
                if (jarURL != null) {
                    jarURL.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void updateConfig()
    {
        SendConsole.info("Updating configuration file!");
        File backupConfig = new File(plugin.getDataFolder(), "config_" + new Date().getTime() + ".yml");

        configFile.renameTo(backupConfig);
        SendConsole.info("Backup config saved to: " + backupConfig.getName());

        configFile = null;
        loadConfig();
    }

    private static void copyFile(InputStream in, File out)
            throws Exception
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(out);Throwable localThrowable3 = null;
            try
            {
                byte[] buf = new byte['?'];
                int i;
                while ((i = in.read(buf)) != -1) {
                    fos.write(buf, 0, i);
                }
            }
            catch (Throwable localThrowable1)
            {
                localThrowable3 = localThrowable1;throw localThrowable1;
            }
            finally
            {
                if (fos != null) {
                    if (localThrowable3 != null) {
                        try
                        {
                            fos.close();
                        }
                        catch (Throwable localThrowable2)
                        {
                            localThrowable3.addSuppressed(localThrowable2);
                        }
                    } else {
                        fos.close();
                    }
                }
            }
        }
        finally
        {
            if (in != null) {
                in.close();
            }
        }
    }
}

