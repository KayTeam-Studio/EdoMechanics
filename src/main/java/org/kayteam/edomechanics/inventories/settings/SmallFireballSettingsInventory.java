package org.kayteam.edomechanics.inventories.settings;

import org.bukkit.entity.Player;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.inventories.editor.MechanicListInventory;
import org.kayteam.kayteamapi.input.inputs.ChatInput;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class SmallFireballSettingsInventory extends InventoryBuilder {

    public SmallFireballSettingsInventory(EdoMechanics plugin) {
        super(plugin.getInventories().getString("settings.smallFirebal.inventoryTitle"), 5);
        Yaml settings = plugin.getSettings();
        Yaml inventories = plugin.getInventories();
        fillItem(() -> inventories.getItemStack("settings.smallFirebal.panel"));
        // Back
        addItem(0, () -> inventories.getItemStack("settings.smallFirebal.back"));
        addLeftAction(0, (player, i) -> plugin.getInventoryManager().openInventory(player, new MechanicListInventory(plugin)));
        // Close
        addItem(8, () -> inventories.getItemStack("settings.smallFirebal.close"));
        addLeftAction(8, (player, i) -> player.closeInventory());
        // Cooldown
        addItem(20, () -> Yaml.replace(inventories.getItemStack("settings.smallFirebal.cooldown"), new String[][] {{"%seconds%", settings.getInt("mechanics.smallFirebal.cooldown") + ""}}));
        setUpdatable(20, true);
        setUpdateInterval(20, 4);
        addLeftAction(20, (player, i) -> {
            int cooldown = settings.getInt("mechanics.smallFirebal.cooldown");
            if (cooldown > 0) {
                settings.set("mechanics.smallFirebal.cooldown", cooldown - 1);
                settings.saveFileConfiguration();
            }
        });
        addRightAction(20, (player, i) -> {
            int cooldown = settings.getInt("mechanics.smallFirebal.cooldown");
            settings.set("mechanics.smallFirebal.cooldown", cooldown + 1);
            settings.saveFileConfiguration();
        });

        // Min Damage
        addItem(21, () -> Yaml.replace(inventories.getItemStack("settings.smallFirebal.minDamage"), new String[][]{{"%damage%", settings.getInt("mechanics.smallFirebal.minDamage") + ""}}));
        setUpdatable(21, true);
        setUpdateInterval(21, 4);
        addLeftAction(21, (player, i) -> {
            int damage = settings.getInt("mechanics.smallFirebal.minDamage");
            if (damage > 0) {
                settings.set("mechanics.smallFirebal.minDamage", damage - 1);
                settings.saveFileConfiguration();
            }
        });
        addRightAction(21, (player, i) -> {
            int damage = settings.getInt("mechanics.smallFirebal.minDamage");
            int maxDamage = settings.getInt("mechanics.smallFirebal.maxDamage");
            if (damage + 1 < maxDamage) {
                settings.set("mechanics.smallFirebal.minDamage", damage + 1);
                settings.saveFileConfiguration();
            }
        });

        // Max Damage
        addItem(22, () -> Yaml.replace(inventories.getItemStack("settings.smallFirebal.maxDamage"), new String[][]{{"%damage%", settings.getInt("mechanics.smallFirebal.maxDamage") + ""}}));
        setUpdatable(22, true);
        setUpdateInterval(22, 4);
        addLeftAction(22, (player, i) -> {
            int damage = settings.getInt("mechanics.smallFirebal.maxDamage");
            int minDamage = settings.getInt("mechanics.smallFirebal.minDamage");
            if (damage - 1 > minDamage) {
                settings.set("mechanics.smallFirebal.maxDamage", damage - 1);
                settings.saveFileConfiguration();
            }
        });
        addRightAction(22, (player, i) -> {
            int damage = settings.getInt("mechanics.smallFirebal.maxDamage");
            settings.set("mechanics.smallFirebal.maxDamage", damage + 1);
            settings.saveFileConfiguration();
        });

        // Permission to use
        addItem(23, () -> Yaml.replace(inventories.getItemStack("settings.smallFirebal.permissionToUse"), new String[][]{{"%permission%", settings.getString("mechanics.smallFirebal.permissionToUse")}}));
        addLeftAction(23, (player, i) -> {
            player.closeInventory();
            Yaml messages = plugin.getMessages();
            messages.sendMessage(player, "mechanics.smallFirebal.inputNewPermissionToUse");
            plugin.getInputManager().addInput(player, new ChatInput() {
                @Override
                public boolean onChatInput(Player player, String input) {
                    settings.set("mechanics.smallFirebal.permissionToUse", input);
                    settings.saveFileConfiguration();
                    plugin.getInventoryManager().openInventory(player, new SmallFireballSettingsInventory(plugin));
                    return true;
                }

                @Override
                public void onPlayerSneak(Player player) {
                    plugin.getInventoryManager().openInventory(player, new SmallFireballSettingsInventory(plugin));
                }
            });
        });
        // Permission to bypass cooldown
        addItem(24, () -> Yaml.replace(inventories.getItemStack("settings.smallFirebal.permissionToBypassCooldown"), new String[][]{{"%permission%", settings.getString("mechanics.smallFirebal.permissionToBypassCooldown")}}));
        addLeftAction(24, (player, i) -> {
            player.closeInventory();
            Yaml messages = plugin.getMessages();
            messages.sendMessage(player, "mechanics.smallFirebal.inputNewPermissionToBypassCooldown");
            plugin.getInputManager().addInput(player, new ChatInput() {
                @Override
                public boolean onChatInput(Player player, String input) {
                    settings.set("mechanics.smallFirebal.permissionToBypassCooldown", input);
                    settings.saveFileConfiguration();
                    plugin.getInventoryManager().openInventory(player, new SmallFireballSettingsInventory(plugin));
                    return true;
                }

                @Override
                public void onPlayerSneak(Player player) {
                    plugin.getInventoryManager().openInventory(player, new SmallFireballSettingsInventory(plugin));
                }
            });
        });
    }
}
