package fr.formiko.villageoys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Chunk;
import org.bukkit.Location;
import net.minecraft.world.level.block.Rotation;

public class Building implements Serializable {
    private final Location spawnLocation;
    private final BuildingType type;
    private final Rotation rotation;
    private final List<Chunk> chunks;

    public Building(Location spawnLocation, BuildingType type, Rotation rotation) {
        this.spawnLocation = spawnLocation;
        this.type = type;
        this.rotation = rotation;
        this.chunks = new ArrayList<>();
        this.chunks.add(spawnLocation.getChunk());
    }


    public Location getSpawnLocation() { return spawnLocation; }
    public BuildingType getType() { return type; }
    public Rotation getRotation() { return rotation; }
    public List<Chunk> getChunks() { return chunks; }

    @Override
    public String toString() {
        return "Building{" + "spawnLocation=" + spawnLocation + ", type=" + type + ", rotation=" + rotation + ", chunks=" + chunks + '}';
    }


}
