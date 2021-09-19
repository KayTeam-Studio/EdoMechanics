package org.kayteam.edomechanics.inventories.settings;

import org.bukkit.entity.Player;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.inventories.editor.MechanicListInventory;
import org.kayteam.kayteamapi.input.inputs.ChatInput;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class LightningSettingsInventory extends InventoryBuilder {

    public LightningSettingsInventory(EdoMechanics plugin) {
        super(plugin.getInventories().getString("settings.lightning.inventoryTitle"), 5);
        Yaml settings = plugin.getSettings();
        Yaml inventories = plugin.getInventories();
        fillItem(() -> inventories.getItemStack("settings.lightning.panel"));
        // Back
        addItem(0, () -> inventories.getItemStack("settings.lightning.back"));
        addLeftAction(0, (player, i) -> plugin.getInventoryManager().openInventory(player, new MechanicListInventory(plugin)));
        // Close
        addItem(8, () -> inventories.getItemStack("settings.lightning.close"));
        addLeftAction(8, (player, i) -> player.closeInventory());
        // Cooldown
        addItem(20, () -> Yaml.replace(inventories.getItemStack("settings.lightning.cooldown"), new String[][] {{"%seconds%", settings.getInt("mechanics.lightning.cooldown") + ""}}));
        setUpdatable(20, true);
        setUpdateInterval(20, 4);
        addLeftAction(20, (player, i) -> {
            int cooldown = settings.getInt("mechanics.lightning.cooldown");
            if (cooldown > 0) {
                settings.set("mechanics.lightning.cooldown", cooldown - 1);
                settings.saveFileConfiguration();
            }
        });
        addRightAction(20, (player, i) -> {
            int cooldown = settings.getInt("mechanics.lightning.cooldown");
            settings.set("mechanics.lightning.cooldown", cooldown + 1);
            settings.saveFileConfiguration();
        });

        // Min Damage
        addItem(21, () -> Yaml.replace(inventories.getItemStack("settings.lightning.minDamage"), new String[][]{{"%damage%", settings.getInt("mechanics.lightning.minDamage") + ""}}));
        setUpdatable(21, true);
        setUpdateInterval(21, 4);
        addLeftAction(21, (player, i) -> {
            int damage = settings.getInt("mechanics.lightning.minDamage");
            if (damage > 0) {
                settings.set("mechanics.lightning.minDamage", damage - 1);
                settings.saveFileConfiguration();
            }
        });
        addRightAction(21, (player, i) -> {
            int damage = settings.getInt("mechanics.lightning.minDamage");
            int maxDamage = settings.getInt("mechanics.lightning.maxDamage");
            if (damage + 1 < maxDamage) {
                settings.set("mechanics.lightning.minDamage", damage + 1);
                settings.saveFileConfiguration();
            }
        });

        // Max Damage
        addItem(22, () -> Yaml.replace(inventories.getItemStack("settings.lightning.maxDamage"), new String[][]{{"%damage%", settings.getInt("mechanics.lightning.maxDamage") + ""}}));
        setUpdatable(22, true);
        setUpdateInterval(22, 4);
        addLeftAction(22, (player, i) -> {
            int damage = settings.getInt("mechanics.lightning.maxDamage");
            int minDamage = settings.getInt("mechanics.lightning.minDamage");
            if (damage - 1 > minDamage) {
                settings.set("mechanics.lightning.maxDamage", damage - 1);
                settings.saveFileConfiguration();
            }
        });
        addRightAction(22, (player, i) -> {
            int damage = settings.getInt("mechanics.lightning.maxDamage");
            settings.set("mechanics.lightning.maxDamage", damage + 1);
            settings.saveFileConfiguration();
        });

        // Permission to use
        addItem(23, () -> Yaml.replace(inventories.getItemStack("settings.lightning.permissionToUse"), new String[][]{{"%permission%", settings.getString("mechanics.lightning.permissionToUse")}}));
        addLeftAction(23, (player, i) -> {
            player.closeInventory();
            Yaml messages = plugin.getMessages();
            messages.sendMessage(player, "mechanics.lightning.inputNewPermissionToUse");
            plugin.getInputManager().addInput(player, new ChatInput() {
                @Override
                public boolean onChatInput(Player player, String input) {
                    settings.set("mechanics.lightning.permissionToUse", input);
                    settings.saveFileConfiguration();
                    plugin.getInventoryManager().openInventory(player, new LightningSettingsInventory(plugin));
                    return true;
                }

                @Override
                public void onPlayerSneak(Player player) {
                    plugin.getInventoryManager().openInventory(player, new LightningSettingsInventory(plugin));
                }
            });
        });
        // Permission to bypass cooldown
        addItem(24, () -> Yaml.replace(inventories.getItemStack("settings.lightning.permissionToBypassCooldown"), new String[][]{{"%permission%", settings.getString("mechanics.lightning.permissionToBypassCooldown")}}));
        addLeftAction(24, (player, i) -> {
            player.closeInventory();
            Yaml messages = plugin.getMessages();
            messages.sendMessage(player, "mechanics.lightning.inputNewPermissionToBypassCooldown");
            plugin.getInputManager().addInput(player, new ChatInput() {
                @Override
                public boolean onChatInput(Player player, String input) {
                    settings.set("mechanics.lightning.permissionToBypassCooldown", input);
                    settings.saveFileConfiguration();
                    plugin.getInventoryManager().openInventory(player, new LightningSettingsInventory(plugin));
                    return true;
                }

                @Override
                public void onPlayerSneak(Player player) {
                    plugin.getInventoryManager().openInventory(player, new LightningSettingsInventory(plugin));
                }
            });
        });
    }
}
