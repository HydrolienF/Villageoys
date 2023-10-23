package fr.formiko.villageoys;

import fr.formiko.villageoys.util.Util;
import java.io.Serializable;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;

public abstract class Localizable implements Serializable {
    private final UUID uuid;
    private final int chunkX;
    private final int chunkZ;
    private final int y;
    private final UUID worldUuid;

    protected Localizable(int chunkX, int chunkZ, int y, UUID worldUuid) {
        this.uuid = UUID.randomUUID();
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.y = y;
        this.worldUuid = worldUuid;
    }
    protected Localizable(Location loc) { this(loc.getChunk().getX(), loc.getChunk().getZ(), loc.getBlockY(), loc.getWorld().getUID()); }

    public UUID getUuid() { return uuid; }
    public int getChunkX() { return chunkX; }
    public int getChunkZ() { return chunkZ; }
    public int getY() { return y; }
    public UUID getWorldUuid() { return worldUuid; }
    public World getWorld() { return Bukkit.getWorld(worldUuid); }
    public ServerLevel getServerLevel() { return ((CraftWorld) getWorld()).getHandle(); }
    public Location getLocation() { return new Location(getWorld(), chunkX * 16, y, chunkZ * 16); }
    public Chunk getChunk() { return getWorld().getChunkAt(chunkX, chunkZ); }
    public Holder<Biome> getBiome() { return Util.getBiome(getLocation()); }

    public boolean isNextTo(int chunkX, int chunkY, int distance) {
        return Math.abs(chunkX - getChunkX()) <= distance && Math.abs(chunkY - getChunkZ()) <= distance;
    }
    public boolean isNextTo(int chunkX, int chunkY) { return isNextTo(chunkX, chunkY, 1); }

    @Override
    public String toString() { return getUuid() + " chunkX=" + chunkX + ", chunkZ=" + chunkZ + ", y=" + y + ", worldUuid=" + worldUuid; }
}
