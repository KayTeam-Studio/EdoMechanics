package org.kayteam.edomechanics.inventories.editor;

import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.inventories.EdoMechanicsInventory;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class ItemEditorInventory extends InventoryBuilder {

    public ItemEditorInventory(EdoMechanics plugin, ItemStack itemStack){
        super(plugin.getInventories().getString("itemMechanicsEditor.inventoryTitle"), 6);
        Yaml inventories = plugin.getInventories();
        // Fill
        fillItem(() -> inventories.getItemStack("itemMechanicsEditor.panel"));
        // Back
        addItem(0, () -> inventories.getItemStack("itemMechanicsEditor.back"));
        addLeftAction(0, (player, slot) -> plugin.getInventoryManager().openInventory(player, new EdoMechanicsInventory(plugin)));
        // Close
        addItem(8, () -> inventories.getItemStack("itemMechanicsEditor.close"));
        addLeftAction(8, (player, slot) -> player.closeInventory());
        // Mechanics
        addItem(12, () -> inventories.getItemStack("itemMechanicsEditor.mechanics"));
        addLeftAction(12, (player, slot) -> plugin.getInventoryManager().openInventory(player, new MechanicsInventory(plugin, itemStack)));
        // Potion Effects
        addItem(14, () -> inventories.getItemStack("itemMechanicsEditor.potionEffects"));
    }
}
