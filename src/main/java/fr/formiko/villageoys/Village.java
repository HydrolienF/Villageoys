package fr.formiko.villageoys;

import fr.formiko.villageoys.Building.BuilderBuilding;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.block.Rotation;

public class Village extends Localizable {
    private List<Villageoy> villageoys;
    private List<Building> buildings;
    private String name;
    private final VillagerType type;


    public Village(@Nullable String name, @NotNull Location spawnLocation) {
        super(spawnLocation);
        this.name = name;
        type = VillagerType.byBiome(getBiome());
        villageoys = new ArrayList<>();
        buildings = new ArrayList<>();
    }

    public Village(@NotNull Location spawnLocation) { this(null, spawnLocation); }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public VillagerType getType() { return type; }

    public void newVillageoy() { villageoys.add(new Villageoy(getLocation(), type)); }
    public void newBuilding(BuildingType type, int chunkX, int chunkZ, @Nullable Rotation rotation, boolean builded) {
        BuilderBuilding builder = Building.builder().setWorldUuid(getWorldUuid()).setChunkX(chunkX).setChunkZ(chunkZ).setType(type)
                .setRotation(rotation).setBuilded(builded);
        for (Building b : buildings) {
            if (b.isNextTo(chunkX, chunkZ)) {
                builder.addNeighborY(b.getY());
            }
        }
        buildings.add(builder.build());
    }

    @Override
    public String toString() {
        return "Village{" + name + ", type=" + type + ", " + super.toString() + ", villageoys=" + villageoys + ", buildings=" + buildings
                + '}';
    }

}
