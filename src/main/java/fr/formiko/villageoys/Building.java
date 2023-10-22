package fr.formiko.villageoys;

import fr.formiko.villageoys.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import net.minecraft.world.level.block.Rotation;

public class Building extends Localizable {
    private final BuildingType type;
    private final Rotation rotation;

    public Building(Location loc, BuildingType type, Rotation rotation) {
        super(loc.getChunk().getX(), loc.getChunk().getZ(), Util.getBestGround(loc.getChunk()), loc.getWorld().getUID());
        this.type = type;
        this.rotation = rotation;
        // createPlatform();
        Bukkit.getLogger().info("Building created: " + this.toString());
    }


    public BuildingType getType() { return type; }
    public Rotation getRotation() { return rotation; }

    @Override
    public String toString() { return "Building{" + super.toString() + ", type=" + type + ", rotation=" + rotation + '}'; }

    public void createPlatform() {
        Chunk chunk = getChunk();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.getBlock(x, getY(), z).setType(Material.GRASS_BLOCK);
                // replace upper block by air
                for (int y = getY() + 1; y < 256; y++) {
                    chunk.getBlock(x, y, z).setType(Material.AIR);
                }
            }
        }
    }
}
