package org.kayteam.edomechanics.inventories.settings;

import org.bukkit.Material;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.inventories.EdoMechanicsInventory;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class MechanicListInventory extends InventoryBuilder {

    public MechanicListInventory(EdoMechanics plugin) {
        super(plugin.getInventories().getString("settings.mechanics.inventoryTitle"), 6);
        Yaml inventories = plugin.getInventories();
        fillItem(() -> inventories.getItemStack("settings.mechanics.panel"));
        // Back
        addItem(0, () -> inventories.getItemStack("settings.mechanics.back"));
        addLeftAction(0, (player, i) -> plugin.getInventoryManager().openInventory(player, new EdoMechanicsInventory(plugin)));
        // Close
        addItem(8, () -> inventories.getItemStack("settings.mechanics.close"));
        addLeftAction(8, (player, i) -> player.closeInventory());
        // Wither Skull
        addItem(10, () -> inventories.getItemStack("settings.mechanics.witherSkull"));
        addLeftAction(10, (player, i) -> plugin.getInventoryManager().openInventory(player, new WitherSkullSettingsInventory(plugin)));

    }

}