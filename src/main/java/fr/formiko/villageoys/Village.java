package fr.formiko.villageoys;

import fr.formiko.villageoys.Building.BuilderBuilding;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.block.Rotation;

public class Village extends Localizable {
    private List<Villageoy> villageoys; // TODO make saviable by using id
    private List<Building> buildings;
    private String name;
    private String type;
    private transient VillagerType villagerType;


    public Village(@Nullable String name, @NotNull Location spawnLocation) {
        super(spawnLocation);
        this.name = name;
        type = VillagerType.byBiome(getBiome()).toString();
        villageoys = new ArrayList<>();
        buildings = new ArrayList<>();
    }

    public Village(@NotNull Location spawnLocation) { this(null, spawnLocation); }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Building> getBuildings() { return buildings; }
    public List<Villageoy> getVillageoys() { return villageoys; }
    public VillagerType getType() {
        if (villagerType == null) {
            villagerType = getVillagerTypeFromString(type);
        }
        return villagerType;
    }
    private VillagerType getVillagerTypeFromString(String type) {
        return switch (type) {
            case "DESERT" -> VillagerType.DESERT;
            case "JUNGLE" -> VillagerType.JUNGLE;
            case "PLAINS" -> VillagerType.PLAINS;
            case "SAVANNA" -> VillagerType.SAVANNA;
            case "SNOW" -> VillagerType.SNOW;
            case "SWAMP" -> VillagerType.SWAMP;
            case "TAIGA" -> VillagerType.TAIGA;
            default -> VillagerType.PLAINS;
        };
    }

    public void newVillageoy() { villageoys.add(new Villageoy(getLocation(), getType())); }
    public void newBuilding(BuildingType type, int chunkX, int chunkZ, @Nullable Rotation rotation, boolean builded) {
        BuilderBuilding builder = Building.builder().setWorldUuid(getWorldUuid()).setChunkX(chunkX).setChunkZ(chunkZ).setType(type)
                .setRotation(rotation).setBuilded(builded);
        for (Building b : getBuildings()) {
            if (b.isNextTo(chunkX, chunkZ)) {
                builder.addNeighborY(b.getY());
            }
        }
        buildings.add(builder.build());
    }

    @Override
    public String toString() {
        return "Village{" + name + ", type=" + type + ", " + super.toString() + ", villageoys=" + villageoys + ", buildings="
                + listBuilding() + " \nstorage: " + getChestContent() + '}';
    }

    public Map<BuildingType, Integer> listBuilding() {
        Map<BuildingType, Integer> buildingsMap = new EnumMap<>(BuildingType.class);
        for (Building b : getBuildings()) {
            if (buildingsMap.containsKey(b.getType())) {
                buildingsMap.put(b.getType(), buildingsMap.get(b.getType()) + 1);
            } else {
                buildingsMap.put(b.getType(), 1);
            }
        }
        return buildingsMap;
    }

    public Map<Material, Integer> getChestContent() {
        Map<Material, Integer> content = new EnumMap<>(Material.class);
        for (Building b : getBuildings()) {
            for (Map.Entry<Material, Integer> m : b.getChestContent().entrySet()) {
                if (content.containsKey(m.getKey())) {
                    content.put(m.getKey(), content.get(m.getKey()) + m.getValue());
                } else {
                    content.put(m.getKey(), m.getValue());
                }
            }
        }
        return content;
    }

}
