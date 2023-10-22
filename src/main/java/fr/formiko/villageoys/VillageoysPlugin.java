package fr.formiko.villageoys;

import fr.formiko.villageoys.commands.VillageoysCommand;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nullable;
import org.bstats.bukkit.Metrics;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
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
        manager.getCommandCompletions().registerAsyncCompletion("villageNameOrUuid", c -> getInstance().getVillagesNames());
        manager.getCommandCompletions().registerAsyncCompletion("buildingType",
                c -> Arrays.stream(BuildingType.values()).map(BuildingType::name).collect(Collectors.toList()));

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
        File f = new File(FILE_PATH).getParentFile();
        if (!f.exists() && !f.mkdirs()) {
            getLogger().warning("Failed to create directory " + f.getAbsolutePath());
            return false;
        }
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

    public void addVillage(@Nullable String name, @NotNull Location spawnLocation) { villages.add(new Village(name, spawnLocation)); }
    public boolean removeVillage(@NotNull String nameOrUuid) {
        for (Village village : villages) {
            if (village.getName().equals(nameOrUuid) || village.getUuid().toString().equals(nameOrUuid)) {
                villages.remove(village);
                return true;
            }
        }
        return false;
    }
    public List<Village> getVillages() { return villages; }
    public @Nullable Village getVillage(String nameOrUuid) {
        for (Village village : villages) {
            if (village.getName().equals(nameOrUuid) || village.getUuid().toString().equals(nameOrUuid)) {
                return village;
            }
        }
        return null;
    }
    public List<String> getVillagesNames() {
        List<String> names = new ArrayList<>();
        for (Village village : villages) {
            names.add(village.getName());
        }
        return names;
    }


}