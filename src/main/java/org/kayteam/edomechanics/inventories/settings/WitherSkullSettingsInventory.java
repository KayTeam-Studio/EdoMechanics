package org.kayteam.edomechanics.inventories.settings;

import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.inventories.EdoMechanicsInventory;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class WitherSkullSettingsInventory extends InventoryBuilder {

    public WitherSkullSettingsInventory(EdoMechanics plugin) {
        super(plugin.getInventories().getString("settings.witherSkull.inventoryTitle"), 5);
        Yaml settings = plugin.getSettings();
        Yaml inventories = plugin.getInventories();
        fillItem(() -> inventories.getItemStack("settings.witherSkull.panel"));
        // Back
        addItem(0, () -> inventories.getItemStack("settings.witherSkull.back"));
        addLeftAction(0, (player, i) -> plugin.getInventoryManager().openInventory(player, new EdoMechanicsInventory(plugin)));
        // Close
        addItem(8, () -> inventories.getItemStack("settings.witherSkull.close"));
        addLeftAction(8, (player, i) -> player.closeInventory());
        // Cooldown
        addItem(20, () -> inventories.getItemStack("settings.witherSkull.cooldown"));
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
            plugin.getInventoryManager().openInventory(player, new WitherSkullSettingsInventory(plugin));
        });
        setUpdatable(20, true);
        // Min Damage
        // Max Damage
        // Permission to use
        // Permission to bypass cooldown
    }
}
