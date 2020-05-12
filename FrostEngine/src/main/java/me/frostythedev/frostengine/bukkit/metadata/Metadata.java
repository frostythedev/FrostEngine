package me.frostythedev.frostengine.bukkit.metadata;

import com.google.inject.Inject;
import me.frostythedev.frostengine.bukkit.FEPlugin;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;

/**
 * Programmed by Tevin on 7/22/2016.
 */
public class Metadata {
    
    @Inject private static FEPlugin plugin;

    public static void removeIfPresent(Metadatable metadatable, String path, IMetadata iMetadata) {
        if (metadatable.hasMetadata(path)) {
            metadatable.removeMetadata(path, plugin);
            iMetadata.accept();
        }
    }

    public static void applyTrue(Metadatable metadata, String path) {
        removeIfPresent(metadata, path, () -> {
            metadata.setMetadata(path, new FixedMetadataValue(plugin, true));
        });
    }

    public static void applyFalse(Metadatable metadata, String path) {
        removeIfPresent(metadata, path, () -> {
            metadata.setMetadata(path, new FixedMetadataValue(plugin, false));
        });
    }

    public static void applyNull(Metadatable metadata, String path) {
        removeIfPresent(metadata, path, () -> {
            metadata.setMetadata(path, new FixedMetadataValue(plugin, null));
        });
    }

    public static Object getValue(Metadatable metadatable, String path){
        if(metadatable.hasMetadata(path)){
            return metadatable.getMetadata(path).get(0);
        }
        return null;
    }

    public static boolean getValueAsBoolean(Metadatable metadatable, String path){
        if(getValue(metadatable, path) == null){
            return false;
        }
        return Boolean.parseBoolean(String.valueOf(getValue(metadatable, path)));
    }


    public interface IMetadata {
        void accept();
    }

}
