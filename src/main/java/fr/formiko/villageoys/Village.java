package fr.formiko.villageoys;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.block.Rotation;

public class Village {
    private final UUID uuid;
    private List<Villageoy> villageoys;
    private List<Building> buildings;
    private String name;
    private final VillagerType type;
    private Location spawnLocation;

    public Village(@Nullable String name, @NotNull VillagerType type, @NotNull Location spawnLocation) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.type = type;
        this.spawnLocation = spawnLocation;
        villageoys = new ArrayList<>();
        buildings = new ArrayList<>();
    }
    public Village(@NotNull VillagerType type, @NotNull Location spawnLocation) { this(null, type, spawnLocation); }

    public UUID getUuid() { return uuid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void newVillageoy() { villageoys.add(new Villageoy(spawnLocation, type)); }
    public void newBuilding(BuildingType type, Location location, Rotation rotation) {
        buildings.add(new Building(location, type, rotation));
    }

}
