package fr.formiko.villageoys.util;

import fr.formiko.villageoys.BuildingType;
import fr.formiko.villageoys.VillageoysPlugin;
import java.io.File;
import net.minecraft.world.entity.npc.VillagerType;

public class Resources {
    private Resources() {}

    public static File getStructureFile(BuildingType buildingType, VillagerType villagerType) {
        return new File(VillageoysPlugin.STRUCTURE_PATH + (villagerType == null ? "plains" : villagerType.toString().toLowerCase()) + "/"
                + buildingType.name().toLowerCase() + "_1.nbt");
    }
}
