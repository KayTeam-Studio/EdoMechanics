package org.kayteam.edomechanics.inventories.editor;

import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.inventories.EdoMechanicsInventory;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class MechanicsInventory extends InventoryBuilder {

    public MechanicsInventory(EdoMechanics plugin, ItemStack itemStack) {
        super(plugin.getInventories().getString("mechanics.inventoryTitle"), 6);
        Yaml inventories = plugin.getInventories();
        // Fill
        fillItem(() -> inventories.getItemStack("mechanics.panel"), new int[] {1, 6});
        // Back
        addItem(0, () -> inventories.getItemStack("mechanics.back"));
        addLeftAction(0, (player, slot) -> plugin.getInventoryManager().openInventory(player, new ItemEditorInventory(plugin, itemStack)));
        // Close
        addItem(8, () -> inventories.getItemStack("mechanics.close"));
        addLeftAction(8, (player, slot) -> player.closeInventory());
        
    }

}