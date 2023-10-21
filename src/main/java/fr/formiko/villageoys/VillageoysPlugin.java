package fr.formiko.villageoys;

import fr.formiko.villageoys.commands.VillageoysCommand;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import co.aikar.commands.PaperCommandManager;

public class VillageoysPlugin extends JavaPlugin {

    public static final String ADMIN_PERMISSION = "villageoys.admin";
    private static VillageoysPlugin instance;
    private List<Village> villages;
    private static final String FILE_PATH = "plugins/Villageoys/villages.data";

    @Override
    public void onEnable() {
        new Metrics(this, 20076);
        instance = this;

        PaperCommandManager manager = new PaperCommandManager(instance);
        manager.registerCommand(new VillageoysCommand());

        if (!loadVillages()) {
            getLogger().warning("Failed to load villages, creating new list");
            villages = new ArrayList<>();
        }
    }

    @Override
    public void onDisable() {
        if (!saveVillages()) {
            getLogger().warning("Failed to save villages");
        }
    }

    public static VillageoysPlugin getInstance() { return instance; }


    public boolean saveVillages() {
        try (BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(FILE_PATH)))) {
            out.writeObject(villages);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @SuppressWarnings("unchecked")
    public boolean loadVillages() {
        try (BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(FILE_PATH)))) {
            villages = (List) in.readObject();
            return true;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}