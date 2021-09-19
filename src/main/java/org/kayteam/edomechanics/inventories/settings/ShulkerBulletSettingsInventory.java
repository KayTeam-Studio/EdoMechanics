package org.kayteam.edomechanics.inventories.settings;

import org.bukkit.entity.Player;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.inventories.editor.MechanicListInventory;
import org.kayteam.kayteamapi.input.inputs.ChatInput;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class ShulkerBulletSettingsInventory extends InventoryBuilder {

    public ShulkerBulletSettingsInventory(EdoMechanics plugin) {
        super(plugin.getInventories().getString("settings.shulkerBullet.inventoryTitle"), 5);
        Yaml settings = plugin.getSettings();
        Yaml inventories = plugin.getInventories();
        fillItem(() -> inventories.getItemStack("settings.shulkerBullet.panel"));
        // Back
        addItem(0, () -> inventories.getItemStack("settings.shulkerBullet.back"));
        addLeftAction(0, (player, i) -> plugin.getInventoryManager().openInventory(player, new MechanicListInventory(plugin)));
        // Close
        addItem(8, () -> inventories.getItemStack("settings.shulkerBullet.close"));
        addLeftAction(8, (player, i) -> player.closeInventory());
        // Cooldown
        addItem(20, () -> Yaml.replace(inventories.getItemStack("settings.shulkerBullet.cooldown"), new String[][] {{"%seconds%", settings.getInt("mechanics.shulkerBullet.cooldown") + ""}}));
        setUpdatable(20, true);
        setUpdateInterval(20, 4);
        addLeftAction(20, (player, i) -> {
            int cooldown = settings.getInt("mechanics.shulkerBullet.cooldown");
            if (cooldown > 0) {
                settings.set("mechanics.shulkerBullet.cooldown", cooldown - 1);
                settings.saveFileConfiguration();
            }
        });
        addRightAction(20, (player, i) -> {
            int cooldown = settings.getInt("mechanics.shulkerBullet.cooldown");
            settings.set("mechanics.shulkerBullet.cooldown", cooldown + 1);
            settings.saveFileConfiguration();
        });

        // Min Damage
        addItem(21, () -> Yaml.replace(inventories.getItemStack("settings.shulkerBullet.minDamage"), new String[][]{{"%damage%", settings.getInt("mechanics.shulkerBullet.minDamage") + ""}}));
        setUpdatable(21, true);
        setUpdateInterval(21, 4);
        addLeftAction(21, (player, i) -> {
            int damage = settings.getInt("mechanics.shulkerBullet.minDamage");
            if (damage > 0) {
                settings.set("mechanics.shulkerBullet.minDamage", damage - 1);
                settings.saveFileConfiguration();
            }
        });
        addRightAction(21, (player, i) -> {
            int damage = settings.getInt("mechanics.shulkerBullet.minDamage");
            int maxDamage = settings.getInt("mechanics.shulkerBullet.maxDamage");
            if (damage + 1 < maxDamage) {
                settings.set("mechanics.shulkerBullet.minDamage", damage + 1);
                settings.saveFileConfiguration();
            }
        });

        // Max Damage
        addItem(22, () -> Yaml.replace(inventories.getItemStack("settings.shulkerBullet.maxDamage"), new String[][]{{"%damage%", settings.getInt("mechanics.shulkerBullet.maxDamage") + ""}}));
        setUpdatable(22, true);
        setUpdateInterval(22, 4);
        addLeftAction(22, (player, i) -> {
            int damage = settings.getInt("mechanics.shulkerBullet.maxDamage");
            int minDamage = settings.getInt("mechanics.shulkerBullet.minDamage");
            if (damage - 1 > minDamage) {
                settings.set("mechanics.shulkerBullet.maxDamage", damage - 1);
                settings.saveFileConfiguration();
            }
        });
        addRightAction(22, (player, i) -> {
            int damage = settings.getInt("mechanics.shulkerBullet.maxDamage");
            settings.set("mechanics.shulkerBullet.maxDamage", damage + 1);
            settings.saveFileConfiguration();
        });

        // Permission to use
        addItem(23, () -> Yaml.replace(inventories.getItemStack("settings.shulkerBullet.permissionToUse"), new String[][]{{"%permission%", settings.getString("mechanics.shulkerBullet.permissionToUse")}}));
        addLeftAction(23, (player, i) -> {
            player.closeInventory();
            Yaml messages = plugin.getMessages();
            messages.sendMessage(player, "mechanics.shulkerBullet.inputNewPermissionToUse");
            plugin.getInputManager().addInput(player, new ChatInput() {
                @Override
                public boolean onChatInput(Player player, String input) {
                    settings.set("mechanics.shulkerBullet.permissionToUse", input);
                    settings.saveFileConfiguration();
                    plugin.getInventoryManager().openInventory(player, new ShulkerBulletSettingsInventory(plugin));
                    return true;
                }

                @Override
                public void onPlayerSneak(Player player) {
                    plugin.getInventoryManager().openInventory(player, new ShulkerBulletSettingsInventory(plugin));
                }
            });
        });
        // Permission to bypass cooldown
        addItem(24, () -> Yaml.replace(inventories.getItemStack("settings.shulkerBullet.permissionToBypassCooldown"), new String[][]{{"%permission%", settings.getString("mechanics.shulkerBullet.permissionToBypassCooldown")}}));
        addLeftAction(24, (player, i) -> {
            player.closeInventory();
            Yaml messages = plugin.getMessages();
            messages.sendMessage(player, "mechanics.shulkerBullet.inputNewPermissionToBypassCooldown");
            plugin.getInputManager().addInput(player, new ChatInput() {
                @Override
                public boolean onChatInput(Player player, String input) {
                    settings.set("mechanics.shulkerBullet.permissionToBypassCooldown", input);
                    settings.saveFileConfiguration();
                    plugin.getInventoryManager().openInventory(player, new ShulkerBulletSettingsInventory(plugin));
                    return true;
                }

                @Override
                public void onPlayerSneak(Player player) {
                    plugin.getInventoryManager().openInventory(player, new ShulkerBulletSettingsInventory(plugin));
                }
            });
        });
    }
}
