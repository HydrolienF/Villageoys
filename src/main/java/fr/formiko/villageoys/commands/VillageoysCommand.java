package fr.formiko.villageoys.commands;

import fr.formiko.villageoys.Village;
import fr.formiko.villageoys.VillageoysPlugin;
import fr.formiko.villageoys.util.Util;
import javax.annotation.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import net.kyori.adventure.text.Component;

@CommandAlias("villageoys|voy")
@CommandPermission(VillageoysPlugin.ADMIN_PERMISSION)
public class VillageoysCommand extends BaseCommand {
    @Default
    @Description("Lists the version of the plugin")
    @Subcommand("version")
    public static void onTownyWaypoints(CommandSender commandSender) {
        commandSender.sendMessage(Component.text(VillageoysPlugin.getInstance().toString()));
    }

    @Subcommand("reload")
    @Description("Reloads the plugin")
    public static void onReload(CommandSender commandSender) {
        VillageoysPlugin.getInstance().reloadConfig();
        commandSender.sendMessage(Component.text("Villageoys reloaded"));
    }

    @Subcommand("newvillage")
    @Syntax("<village name>")
    @Description("Create a new Village")
    public static void onNewVillage(CommandSender commandSender, @Nullable String villageName) {
        if (commandSender instanceof Player player) {
            VillageoysPlugin.getInstance().addVillage(villageName, player.getLocation());
            player.sendMessage(Component.text("Village created"));
        }
    }
    @Subcommand("newbuilding")
    @Syntax("<village name|uuid> <building name> <builded> <rotation>")
    @CommandCompletion("@villageNameOrUuid @buildingType @builded")
    @Description("Create a new Village or a new Villageoy")
    public static void onNewBuilding(CommandSender commandSender, @NotNull String villageNameOrUuid, @NotNull String buildingType,
            @Nullable String builded, @Nullable String rotation) {
        if (commandSender instanceof Player player) {
            Village v = VillageoysPlugin.getInstance().getVillage(villageNameOrUuid);
            if (v == null) {
                player.sendMessage(Component.text("Village " + villageNameOrUuid + " not found"));
                return;
            }
            v.newBuilding(Util.getBuildingTypeFromString(buildingType), player.getLocation(), Util.getRotationFromString(rotation),
                    Boolean.parseBoolean(builded));
            player.sendMessage(Component.text("New building initialized"));
        }
    }
    @Subcommand("newvillageoy")
    @Syntax("<village name|uuid>")
    @CommandCompletion("@villageNameOrUuid")
    @Description("Create a new Villageoy")
    public static void onNewVillageoy(CommandSender commandSender, @Nullable String villageNameOrUuid) {
        if (commandSender instanceof Player player) {
            Village v = VillageoysPlugin.getInstance().getVillage(villageNameOrUuid);
            if (v == null) {
                player.sendMessage(Component.text("Village " + villageNameOrUuid + " not found"));
                return;
            }
            v.newVillageoy();
            player.sendMessage(Component.text("Villageoy spawned"));
        }
    }

    @Subcommand("removevillage")
    @Syntax("<village name|uuid>")
    @CommandCompletion("@villageNameOrUuid")
    @Description("Remove an existing village")
    public static void onRemoveVillage(CommandSender commandSender, @NotNull String nameOrUuid) {
        if (commandSender instanceof Player player) {
            if (VillageoysPlugin.getInstance().removeVillage(nameOrUuid)) {
                player.sendMessage(Component.text("Village " + nameOrUuid + " removed"));
            } else {
                player.sendMessage(Component.text("Village " + nameOrUuid + " not found"));

            }
        }
    }

    @Subcommand("villageinfo")
    @Syntax("<village name|uuid>")
    @CommandCompletion("@villageNameOrUuid")
    @Description("Print info of an existing village")
    public static void onVillageInfo(CommandSender commandSender, @NotNull String nameOrUuid) {
        if (commandSender instanceof Player player) {
            player.sendMessage(Component.text("Village " + nameOrUuid + ": " + VillageoysPlugin.getInstance().getVillage(nameOrUuid)));
        }
    }
    @Subcommand("villagelist")
    @Description("Print name or uuid of every existing village")
    public static void onVillageList(CommandSender commandSender) {
        if (commandSender instanceof Player player) {
            player.sendMessage(Component.text("Villages: " + VillageoysPlugin.getInstance().getVillagesNames()));
        }
    }
}
