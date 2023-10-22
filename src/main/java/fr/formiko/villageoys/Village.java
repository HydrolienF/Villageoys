package fr.formiko.villageoys;

import fr.formiko.villageoys.util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.block.Rotation;

public class Village implements Serializable {
    private final UUID uuid;
    private List<Villageoy> villageoys;
    private List<Building> buildings;
    private String name;
    private final VillagerType type;
    private Location spawnLocation;

    public Village(@Nullable String name, @NotNull Location spawnLocation) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        spawnLocation.setX((int) (spawnLocation.getX()));
        spawnLocation.setY((int) (spawnLocation.getY()));
        spawnLocation.setZ((int) (spawnLocation.getZ()));

        this.spawnLocation = spawnLocation;
        type = VillagerType.byBiome(Util.getBiome(spawnLocation));
        villageoys = new ArrayList<>();
        buildings = new ArrayList<>();

        newBuilding(BuildingType.TOWNHALL, spawnLocation, Rotation.NONE);
    }

    public Village(@NotNull Location spawnLocation) { this(null, spawnLocation); }

    public UUID getUuid() { return uuid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void newVillageoy() { villageoys.add(new Villageoy(spawnLocation, type)); }
    public void newBuilding(BuildingType type, Location location, Rotation rotation) {
        buildings.add(new Building(location, type, rotation));
    }

    @Override
    public String toString() {
        return "Village{" + name + " " + uuid + ", type=" + type + ", spawnLocation=" + spawnLocation + ", villageoys=" + villageoys
                + ", buildings=" + buildings + '}';
    }

}
