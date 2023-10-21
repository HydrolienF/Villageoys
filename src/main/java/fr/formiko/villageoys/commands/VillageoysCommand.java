package fr.formiko.villageoys.commands;

import fr.formiko.villageoys.VillageoysPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
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

    @Subcommand("new")
    @Description("Create a new Village or a new Villageoy")
    public static void onNew(CommandSender commandSender) {
        if (commandSender instanceof Player player) {
            // Villageoy villageoy = new Villageoy(player.getLocation(), null);
            // TODO create a new village or add the villageoy to an existing village
            // villageoy.setPosition(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
            player.sendMessage(Component.text("Villageoy spawned"));
        }
    }
}
