package fr.formiko.villageoys.util;

import fr.formiko.villageoys.BuildingType;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Rotation;

public class Util {
    private Util() {}

    public static Holder<Biome> getBiome(Location location) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        return level.getBiome(level.getSharedSpawnPos());
    }

    public static Rotation getRotationFromString(String rot) {
        return switch (rot) {
            case "NORTH" -> Rotation.NONE;
            case "EAST" -> Rotation.CLOCKWISE_90;
            case "SOUTH" -> Rotation.CLOCKWISE_180;
            case "WEST" -> Rotation.COUNTERCLOCKWISE_90;
            default -> Rotation.NONE;
        };
    }
    public static String getStringFromRotation(Rotation rot) {
        return switch (rot) {
            case NONE -> "NORTH";
            case CLOCKWISE_90 -> "EAST";
            case CLOCKWISE_180 -> "SOUTH";
            case COUNTERCLOCKWISE_90 -> "WEST";
        };
    }
    public static BuildingType getBuildingTypeFromString(String bt) {
        for (BuildingType b : BuildingType.values()) {
            if (b.name().equalsIgnoreCase(bt)) {
                return b;
            }
        }
        return null;
    }

    /**
     * Return the best ground level of a chunk to have the more flat building
     */
    public static int getBestGround(Chunk chunk) {
        Map<Integer, Integer> ground = new HashMap<>();
        int bestGround = 0;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (ground.containsKey(getGround(chunk, x, z))) {
                    ground.put(getGround(chunk, x, z), ground.get(getGround(chunk, x, z)) + 1);
                } else {
                    ground.put(getGround(chunk, x, z), 1);
                }
            }
        }
        int max = 16 * 16;
        int cpt = 0;
        for (int i : ground.keySet().stream().sorted().toArray(Integer[]::new)) {
            cpt += ground.get(i);
            if (cpt > max / 2) {
                bestGround = i;
                break;
            }
        }
        return bestGround;
    }
    public static int getGround(Chunk chunk, int x, int z) {
        int y = 255;
        while (chunk.getBlock(x, y, z).isEmpty() || isLeafBlock(chunk, x, y, z)) {
            y--;
        }
        return y;
    }

    public static boolean isLeafBlock(Chunk chunk, int x, int y, int z) {
        return chunk.getBlock(x, y, z).getType().name().endsWith("_LEAVES");
    }
}
