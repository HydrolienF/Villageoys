package fr.formiko.villageoys;

import org.bukkit.Location;
import net.minecraft.world.level.block.Rotation;

public class Building {
    private final Location spawnLocation;
    private final BuildingType type;
    private final Rotation rotation;

    public Building(Location spawnLocation, BuildingType type, Rotation rotation) {
        this.spawnLocation = spawnLocation;
        this.type = type;
        this.rotation = rotation;
    }


    public Location getSpawnLocation() { return spawnLocation; }
    public BuildingType getType() { return type; }
    public Rotation getRotation() { return rotation; }


}
