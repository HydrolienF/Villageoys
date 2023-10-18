package fr.formiko.villageoys;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class VillageoysPlugin extends JavaPlugin {
    @Override
    public void onEnable() { new Metrics(this, 20076); }

}