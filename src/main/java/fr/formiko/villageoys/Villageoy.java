package fr.formiko.villageoys;

import java.io.Serializable;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerType;

public class Villageoy extends Villager implements Serializable {

    // public Villageoy(EntityType<? extends Villager> entityType, Level world) { super(entityType, world); }

    // public Villageoy(Level world) { this(EntityType.VILLAGER, world); }

    public Villageoy(@NotNull Location location, @Nullable VillagerType type) {
        super(EntityType.VILLAGER, ((CraftWorld) location.getWorld()).getHandle(), type == null ? VillagerType.PLAINS : type);
        setPosRaw(location.getX(), location.getY(), location.getZ(), false);

        // // give a sword in main hand
        // this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));

        ((CraftWorld) location.getWorld()).getHandle().addFreshEntity(this, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.COMMAND);
    }

    public void workerSwingArm() {
        if (!this.swinging) {
            this.swing(InteractionHand.MAIN_HAND);
        }
    }

    // @Override
    // protected Brain<?> makeBrain(Dynamic<?> dynamic) {
    // Brain<Villager> behaviorcontroller = this.brainProvider().makeBrain(dynamic);

    // this.registerBrainGoals(behaviorcontroller);
    // return behaviorcontroller;
    // }

    // @Override
    // public void refreshBrain(ServerLevel world) {
    // Brain<Villager> behaviorcontroller = this.getBrain();

    // behaviorcontroller.stopAll(world, this);
    // this.brain = behaviorcontroller.copyWithoutBehaviors();
    // this.registerBrainGoals(this.getBrain());
    // }

    // private void registerBrainGoals(Brain<Villager> brain) {
    // VillagerProfession villagerprofession = this.getVillagerData().getProfession();

    // if (this.isBaby()) {
    // brain.setSchedule(Schedule.VILLAGER_BABY);
    // brain.addActivity(Activity.PLAY, VillagerGoalPackages.getPlayPackage(0.5F));
    // } else {
    // brain.setSchedule(Schedule.VILLAGER_DEFAULT);
    // brain.addActivityWithConditions(Activity.WORK, VillagerGoalPackages.getWorkPackage(villagerprofession, 0.5F),
    // ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT)));
    // }

    // brain.addActivity(Activity.CORE, VillagerGoalPackages.getCorePackage(villagerprofession, 0.5F));
    // brain.addActivityWithConditions(Activity.MEET, VillagerGoalPackages.getMeetPackage(villagerprofession, 0.5F),
    // ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryStatus.VALUE_PRESENT)));
    // brain.addActivity(Activity.REST, VillagerGoalPackages.getRestPackage(villagerprofession, 0.5F));
    // brain.addActivity(Activity.IDLE, VillagerGoalPackages.getIdlePackage(villagerprofession, 0.5F));
    // brain.addActivity(Activity.PANIC, VillagerGoalPackages.getPanicPackage(villagerprofession, 0.5F));
    // brain.addActivity(Activity.PRE_RAID, VillagerGoalPackages.getPreRaidPackage(villagerprofession, 0.5F));
    // brain.addActivity(Activity.RAID, VillagerGoalPackages.getRaidPackage(villagerprofession, 0.5F));
    // brain.addActivity(Activity.HIDE, VillagerGoalPackages.getHidePackage(villagerprofession, 0.5F));
    // brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
    // brain.setDefaultActivity(Activity.IDLE);
    // brain.setActiveActivityIfPossible(Activity.IDLE);
    // // Folia - region threading
    // brain.updateActivityFromSchedule(this.level().getLevelData().getDayTime(), this.level().getLevelData().getGameTime());
    // }


}
