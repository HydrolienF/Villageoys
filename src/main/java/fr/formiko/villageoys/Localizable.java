package fr.formiko.villageoys;

import fr.formiko.villageoys.util.Util;
import java.io.Serializable;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

public abstract class Localizable implements Serializable {
    private final int chunkX;
    private final int chunkZ;
    private final int y;
    private final UUID worldUuid;

    protected Localizable(int chunkX, int chunkZ, int y, UUID worldUuid) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.y = y;
        this.worldUuid = worldUuid;
    }
    protected Localizable(Location loc) { this(loc.getChunk().getX(), loc.getChunk().getZ(), loc.getBlockY(), loc.getWorld().getUID()); }

    public int getChunkX() { return chunkX; }
    public int getChunkZ() { return chunkZ; }
    public int getY() { return y; }
    public UUID getWorldUuid() { return worldUuid; }
    public World getWorld() { return Bukkit.getWorld(worldUuid); }
    public Location getLocation() { return new Location(getWorld(), chunkX * 16, y, chunkZ * 16); }
    public Chunk getChunk() { return getWorld().getChunkAt(chunkX, chunkZ); }
    public Holder<Biome> getBiome() { return Util.getBiome(getLocation()); }

    @Override
    public String toString() { return "chunkX=" + chunkX + ", chunkZ=" + chunkZ + ", y=" + y + ", worldUuid=" + worldUuid; }
}
