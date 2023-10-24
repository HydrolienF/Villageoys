package fr.formiko.villageoys;

import fr.formiko.villageoys.util.BuildingPlan;
import fr.formiko.villageoys.util.Resources;
import fr.formiko.villageoys.util.Util;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;

public class Building extends Localizable {
    private final UUID villageUuid;
    private final BuildingType type;
    private final Rotation rotation;

    private Building(int chunkX, int chunkZ, int y, UUID worlUuid, BuildingType type, Rotation rotation, UUID villageUuid) {
        super(chunkX, chunkZ, y, worlUuid);
        this.villageUuid = villageUuid;
        this.type = type;
        this.rotation = rotation;
        // createPlatform();
        Bukkit.getLogger().info("Building created: " + this.toString());
    }


    public BuildingType getType() { return type; }
    public Rotation getRotation() { return rotation; }
    public Village getVillage() {
        Bukkit.getLogger().info("Village UUID: " + villageUuid);
        Bukkit.getLogger().info("Village list " + VillageoysPlugin.getInstance().getVillages());
        return VillageoysPlugin.getInstance().getVillage(villageUuid);
    }

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
    public void instantBuild() {
        BuildingPlan plan = getPlan();
        Bukkit.getLogger().info("Building plan size: " + plan.getBlocksQuantity());
        int size = plan.getBlocksQuantity();
        Level level = getServerLevel();
        for (int i = 0; i < size; i++) {
            BlockPos blockPos = new BlockPos(getChunkX() * 16 + plan.getBlockPos(i).getX(), getY() + plan.getBlockPos(i).getY(),
                    getChunkZ() * 16 + plan.getBlockPos(i).getZ());
            level.setBlockAndUpdate(blockPos, plan.getBlockState(i));
        }
        Bukkit.getLogger().info("Building plan done");
    }

    public BuildingPlan getPlan() {
        // return BuildingPlan.createFromResource(Resources.resource(type, getVillage().getType()),
        // getServerLevel().getServer().getResourceManager());
        // new File(VillageoysPlugin.STRUCTURE_PATH + "plains/house_1.nbt")
        // TODO fix getVillage() returning null
        return BuildingPlan.createFromResourceFile(Resources.getStructureFile(type, getVillage() != null ? getVillage().getType() : null));
    }

    public Map<Material, Integer> getChestContent() {
        // TODO to fix. It don't find real content of container
        Chunk chunk = getChunk();
        Map<Material, Integer> content = new HashMap<>();
        for (int y = -64; y < 255; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    if (chunk.getBlock(x, y, z) instanceof org.bukkit.block.Container container) {
                        Bukkit.getLogger().info("Container found in " + x + " " + y + " " + z + ": " + container + " "
                                + Arrays.toString(container.getInventory().getContents()));
                        container.getInventory().forEach(item -> {
                            if (item != null) {
                                if (content.containsKey(item.getType())) {
                                    content.put(item.getType(), content.get(item.getType()) + item.getAmount());
                                } else {
                                    content.put(item.getType(), item.getAmount());
                                }
                            }
                        });
                    }
                }
            }
        }
        return content;
    }

    public Map<Material, Integer> getMaterialContent() {
        Chunk chunk = getChunk();
        Map<Material, Integer> content = new HashMap<>();
        for (int y = -64; y < 255; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunk.getBlock(x, y, z).getDrops().forEach(item -> {
                        if (content.containsKey(item.getType())) {
                            content.put(item.getType(), content.get(item.getType()) + item.getAmount());
                        } else {
                            content.put(item.getType(), item.getAmount());
                        }
                    });
                }
            }
        }
        return content;
    }


    // builder ----------------------------------------------------------------
    public static BuilderBuilding builder() { return new BuilderBuilding(); }

    static class BuilderBuilding {
        private int chunkX;
        private int chunkZ;
        private List<Integer> neighborY;
        private UUID worldUuid;
        private BuildingType type;
        private Rotation rotation;
        private boolean builded;
        private UUID villageUuid;

        private BuilderBuilding() { neighborY = new LinkedList<>(); }

        // @formatter:off
        public BuilderBuilding setChunkX(int chunkX) { this.chunkX = chunkX; return this; }
        public BuilderBuilding setChunkZ(int chunkZ) { this.chunkZ = chunkZ; return this; }
        public BuilderBuilding addNeighborY(int y){ neighborY.add(y); return this; }
        public BuilderBuilding setWorldUuid(UUID worldUuid) { this.worldUuid = worldUuid; return this; }
        public BuilderBuilding setType(BuildingType type) { this.type = type; return this; }
        public BuilderBuilding setRotation(Rotation rotation) { this.rotation = rotation; return this; }
        public BuilderBuilding setLocation(Location loc){
            setChunkX(loc.getChunk().getX());
            setChunkZ(loc.getChunk().getZ());
            setWorldUuid(loc.getWorld().getUID());
            return this;
        }
        public BuilderBuilding setBuilded(boolean builded){ this.builded = builded; return this; }
        public BuilderBuilding setVillageUuid(UUID villageUuid) { this.villageUuid = villageUuid; return this; }
        // @formatter:on

        public Building build() {
            Building building = new Building(chunkX, chunkZ, getBestPossibleY(), worldUuid, type, rotation, villageUuid);
            if (builded) {
                building.instantBuild();
            }
            return building;
        }
        /**
         * @return the best possible Y for the building.
         *         If possible the one that need the less terraforming. Else one that is compatible with all neighbors
         */
        private int getBestPossibleY() {
            int bestY = Util.getBestGround(Bukkit.getWorld(worldUuid).getChunkAt(chunkX, chunkZ));
            Bukkit.getLogger().info("Choose best y depending of neighbors: " + neighborY + " and bestY: " + bestY);
            List<Integer> possibleY;
            if (neighborY.isEmpty()) {
                return bestY;
            } else {
                possibleY = new LinkedList<>();
                for (int y = 0; y < 255; y++) {
                    possibleY.add(y);
                }
                for (int y : neighborY) {
                    possibleY = possibleY.stream().filter(i -> i >= y - 1 && i <= y + 1).toList();
                }
            }
            if (possibleY.isEmpty()) {
                Bukkit.getLogger().warning("No possible Y found for building in " + chunkX + " " + chunkZ);
                // return Integer.MIN_VALUE;
                return bestY;
            } else if (possibleY.size() == 1) {
                return possibleY.get(0);
            } else {
                int closestY = possibleY.get(0);
                for (int y : possibleY) {
                    if (y == bestY) {
                        return bestY;
                    }
                    if (Math.abs(y - bestY) < Math.abs(closestY - bestY)) {
                        closestY = y;
                    }
                }
                return closestY;
            }
        }
    }


}
