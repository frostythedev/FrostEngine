package me.frostythedev.frostengine.bukkit.metadata;

import me.frostythedev.frostengine.bukkit.FEPlugin;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;

/**
 * Programmed by Tevin on 7/22/2016.
 */
public class Metadata {

    public static void removeIfPresent(Metadatable metadatable, String path, IMetadata iMetadata) {
        if (metadatable.hasMetadata(path)) {
            metadatable.removeMetadata(path, FEPlugin.get());
            iMetadata.accept();
        }
    }

    public static void applyTrue(Metadatable metadata, String path) {
        removeIfPresent(metadata, path, () -> {
            metadata.setMetadata(path, new FixedMetadataValue(FEPlugin.get(), true));
        });
    }

    public static void applyFalse(Metadatable metadata, String path) {
        removeIfPresent(metadata, path, () -> {
            metadata.setMetadata(path, new FixedMetadataValue(FEPlugin.get(), false));
        });
    }

    public static void applyNull(Metadatable metadata, String path) {
        removeIfPresent(metadata, path, () -> {
            metadata.setMetadata(path, new FixedMetadataValue(FEPlugin.get(), null));
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
        return Boolean.valueOf(String.valueOf(getValue(metadatable, path)));
    }


    public interface IMetadata {
        void accept();
    }

}
