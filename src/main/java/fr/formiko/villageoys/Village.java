package fr.formiko.villageoys;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.block.Rotation;

public class Village extends Localizable {
    private final UUID uuid;
    private List<Villageoy> villageoys;
    private List<Building> buildings;
    private String name;
    private final VillagerType type;


    public Village(@Nullable String name, @NotNull Location spawnLocation) {
        super(spawnLocation);
        this.uuid = UUID.randomUUID();
        this.name = name;
        type = VillagerType.byBiome(getBiome());
        villageoys = new ArrayList<>();
        buildings = new ArrayList<>();

        newBuilding(BuildingType.TOWNHALL, spawnLocation, Rotation.NONE, true);
    }

    public Village(@NotNull Location spawnLocation) { this(null, spawnLocation); }

    public UUID getUuid() { return uuid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void newVillageoy() { villageoys.add(new Villageoy(getLocation(), type)); }
    public void newBuilding(BuildingType type, Location location, @Nullable Rotation rotation, boolean builded) {
        buildings.add(new Building(location, type, rotation));
    }

    @Override
    public String toString() {
        return "Village{" + name + " " + uuid + ", type=" + type + ", " + super.toString() + ", villageoys=" + villageoys + ", buildings="
                + buildings + '}';
    }

}
