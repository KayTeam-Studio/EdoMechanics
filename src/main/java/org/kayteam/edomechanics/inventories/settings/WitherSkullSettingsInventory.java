package org.kayteam.edomechanics.inventories.settings;

import org.bukkit.entity.Player;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.inventories.editor.MechanicListInventory;
import org.kayteam.edomechanics.mechanics.MechanicType;
import org.kayteam.kayteamapi.input.inputs.ChatInput;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class WitherSkullSettingsInventory extends InventoryBuilder {

    public WitherSkullSettingsInventory(EdoMechanics plugin) {
        super(plugin.getInventories().getString("settings.mechanic.title",
                new String[][]{{"%mechanic_name%", MechanicType.WITHER_SKULL.toString()}}), 5);
        Yaml settings = plugin.getSettings();
        Yaml inventories = plugin.getInventories();
        fillItem(() -> inventories.getItemStack("settings.mechanic.items.panel"));
        // Back
        addItem(0, () -> inventories.getItemStack("settings.mechanic.items.back"));
        addLeftAction(0, (player, i) -> plugin.getInventoryManager().openInventory(player, new MechanicListInventory(plugin)));
        // Close
        addItem(8, () -> inventories.getItemStack("settings.mechanic.items.close"));
        addLeftAction(8, (player, i) -> player.closeInventory());
        // Cooldown
        addItem(20, () -> Yaml.replace(inventories.getItemStack("settings.mechanic.items.cooldown"),
                new String[][] {{"%seconds%", settings.getInt("mechanics.witherSkull.cooldown") + ""}}));
        setUpdatable(20, true);
        setUpdateInterval(20, 4);
        addLeftAction(20, (player, i) -> {
            int cooldown = settings.getInt("mechanics.witherSkull.cooldown");
            if (cooldown > 0) {
                settings.set("mechanics.witherSkull.cooldown", cooldown - 1);
                settings.saveFileConfiguration();
            }
        });
        addRightAction(20, (player, i) -> {
            int cooldown = settings.getInt("mechanics.witherSkull.cooldown");
            settings.set("mechanics.witherSkull.cooldown", cooldown + 1);
            settings.saveFileConfiguration();
        });

        // Min Damage
        addItem(21, () -> Yaml.replace(inventories.getItemStack("settings.mechanic.items.minDamage"),
                new String[][]{{"%damage%", settings.getInt("mechanics.witherSkull.minDamage") + ""}}));
        setUpdatable(21, true);
        setUpdateInterval(21, 4);
        addLeftAction(21, (player, i) -> {
            int damage = settings.getInt("mechanics.witherSkull.minDamage");
            if (damage > 0) {
                settings.set("mechanics.witherSkull.minDamage", damage - 1);
                settings.saveFileConfiguration();
            }
        });
        addRightAction(21, (player, i) -> {
            int damage = settings.getInt("mechanics.witherSkull.minDamage");
            int maxDamage = settings.getInt("mechanics.witherSkull.maxDamage");
            if (damage + 1 < maxDamage) {
                settings.set("mechanics.witherSkull.minDamage", damage + 1);
                settings.saveFileConfiguration();
            }
        });

        // Max Damage
        addItem(22, () -> Yaml.replace(inventories.getItemStack("settings.mechanic.items.maxDamage"),
                new String[][]{{"%damage%", settings.getInt("mechanics.witherSkull.maxDamage") + ""}}));
        setUpdatable(22, true);
        setUpdateInterval(22, 4);
        addLeftAction(22, (player, i) -> {
            int damage = settings.getInt("mechanics.witherSkull.maxDamage");
            int minDamage = settings.getInt("mechanics.witherSkull.minDamage");
            if (damage - 1 > minDamage) {
                settings.set("mechanics.witherSkull.maxDamage", damage - 1);
                settings.saveFileConfiguration();
            }
        });
        addRightAction(22, (player, i) -> {
            int damage = settings.getInt("mechanics.witherSkull.maxDamage");
            settings.set("mechanics.witherSkull.maxDamage", damage + 1);
            settings.saveFileConfiguration();
        });

        // Permission to use
        addItem(23, () -> Yaml.replace(inventories.getItemStack("settings.mechanic.items.permissionToUse"),
                new String[][]{{"%permission%", settings.getString("mechanics.witherSkull.permissionToUse")}}));
        addLeftAction(23, (player, i) -> {
            player.closeInventory();
            Yaml messages = plugin.getMessages();
            messages.sendMessage(player, "mechanics.inputNewPermissionToUse");
            plugin.getInputManager().addInput(player, new ChatInput() {
                @Override
                public boolean onChatInput(Player player, String input) {
                    settings.set("mechanics.witherSkull.permissionToUse", input);
                    settings.saveFileConfiguration();
                    plugin.getInventoryManager().openInventory(player, new WitherSkullSettingsInventory(plugin));
                    return true;
                }

                @Override
                public void onPlayerSneak(Player player) {
                    plugin.getInventoryManager().openInventory(player, new WitherSkullSettingsInventory(plugin));
                }
            });
        });
        // Permission to bypass cooldown
        addItem(24, () -> Yaml.replace(inventories.getItemStack("settings.mechanic.items.permissionToBypassCooldown"),
                new String[][]{{"%permission%", settings.getString("mechanics.witherSkull.permissionToBypassCooldown")}}));
        addLeftAction(24, (player, i) -> {
            player.closeInventory();
            Yaml messages = plugin.getMessages();
            messages.sendMessage(player, "mechanics.inputNewPermissionToBypassCooldown");
            plugin.getInputManager().addInput(player, new ChatInput() {
                @Override
                public boolean onChatInput(Player player, String input) {
                    settings.set("mechanics.witherSkull.permissionToBypassCooldown", input);
                    settings.saveFileConfiguration();
                    plugin.getInventoryManager().openInventory(player, new WitherSkullSettingsInventory(plugin));
                    return true;
                }

                @Override
                public void onPlayerSneak(Player player) {
                    plugin.getInventoryManager().openInventory(player, new WitherSkullSettingsInventory(plugin));
                }
            });
        });
    }
}
