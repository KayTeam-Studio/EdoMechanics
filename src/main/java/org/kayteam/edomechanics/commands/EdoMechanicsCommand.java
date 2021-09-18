package org.kayteam.edomechanics.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.inventories.EdoMechanicsInventory;
import org.kayteam.kayteamapi.command.SimpleCommand;
import org.kayteam.kayteamapi.yaml.Yaml;

public class EdoMechanicsCommand extends SimpleCommand {

    private final EdoMechanics plugin;

    public EdoMechanicsCommand(EdoMechanics plugin) {
        super("EdoMechanics");
        this.plugin = plugin;
    }

    @Override
    public void onPlayerExecute(Player player, String[] arguments) {
        Yaml messages = plugin.getMessages();
        if (player.hasPermission("edomechanics.admin")) {
            plugin.getInventoryManager().openInventory(player, new EdoMechanicsInventory(plugin));
        } else {
            messages.sendMessage(player, "edoMechanics.noPermission");
        }
    }

    @Override
    public void onConsoleExecute(ConsoleCommandSender console, String[] arguments) {
        Yaml messages = plugin.getMessages();
        if (arguments.length > 0) {
            switch (arguments[0].toLowerCase()) {
                case "reload":
                    plugin.onReload();
                    messages.sendMessage(console, "edoMechanics.reloaded");
                case "version":
                    messages.sendMessage(console, "edoMechanics.version", new String[][]{{"%version%", plugin.getDescription().getVersion()}});
                default:
                    messages.sendMessage(console, "edoMechanics.invalidSubcommand");
            }
        } else {
            messages.sendMessage(console, "edoMechanics.emptySubcommand");
        }
    }
}