package lv.cmnts.BoatMagic;

import lv.cmnts.BoatMagic.events.BoatEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    public static Main instance;

    Logger log = this.getLogger();

    Path source = Paths.get(getDataFolder() + "\\config.yml");
    Path target = Paths.get(getDataFolder() + "\\config_old.yml");

    int configversion;

    @Override
    public void onEnable() {
        instance = this;
        configversion = 1;

        if (Files.exists(source)) {
            if (getConfig().getInt("config-version") != configversion) {

                try {
                    Files.copy(source, target);
                    Files.delete(source);
                    saveDefaultConfig();
                } catch (IOException e1) {
                    log.severe(e1.toString());
                }

                log.severe("Wrong config version. Backing up config and creating a new one.");
            } else {
                getConfig().options().copyDefaults(true);
                saveConfig();
            }
        } else {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }

        log.info("Plugin made by CMNTS#8876");

        Bukkit.getPluginManager().registerEvents(new BoatEvent(this), this);
    }

    @NotNull
    public Material getBoatType(TreeSpecies tree) {
        switch (tree) {
            case DARK_OAK: return Material.DARK_OAK_BOAT;
            case REDWOOD: return Material.SPRUCE_BOAT;
            case JUNGLE: return Material.JUNGLE_BOAT;
            case ACACIA: return Material.ACACIA_BOAT;
            case BIRCH: return Material.BIRCH_BOAT;
            default: return Material.OAK_BOAT;
        }
    }

}
