package fr.formiko.villageoys.util;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;

public class Util {
    private Util() {}

    public static Holder<Biome> getBiome(Location location) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        return level.getBiome(level.getSharedSpawnPos());
    }
}
