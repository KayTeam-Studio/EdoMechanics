package org.kayteam.edomechanics.inventories.editor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.inventories.EdoMechanicsInventory;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class ItemEditorInventory extends InventoryBuilder {

    public ItemEditorInventory(EdoMechanics plugin, Player player, int itemSlot){
        super(plugin.getInventories().getString("itemMechanicsEditor.title"), 6);
        Yaml inventories = plugin.getInventories();
        // Fill
        fillItem(() -> inventories.getItemStack("itemMechanicsEditor.items.panel"));
        // Back
        addItem(0, () -> inventories.getItemStack("itemMechanicsEditor.items.back"));
        addLeftAction(0, (player1, slot) -> plugin.getInventoryManager().openInventory(player, new EdoMechanicsInventory(plugin)));
        // Close
        addItem(8, () -> inventories.getItemStack("itemMechanicsEditor.items.close"));
        addLeftAction(8, (player1, slot) -> player.closeInventory());
        // Mechanics
        addItem(12, () -> inventories.getItemStack("itemMechanicsEditor.items.mechanics"));
        addLeftAction(12, (player1, slot) -> plugin.getInventoryManager().openInventory(player, new MechanicsInventory(plugin, player, itemSlot, 1)));
        // Potion Effects
        addItem(14, () -> inventories.getItemStack("itemMechanicsEditor.items.potionEffects"));
        addLeftAction(14, ((player1, i) -> plugin.getInventoryManager().openInventory(player, new PotionEffectInventory(plugin, player, itemSlot, 1))));
        // Clear Mechanics
        addItem(21, () -> inventories.getItemStack("itemMechanicsEditor.items.clearMechanics"));
        addLeftAction(21, ((player1, i) -> {
            ItemStack newItem = plugin.getMechanicManager().clearItemMechanics(player.getInventory().getItem(itemSlot));
            player.getInventory().setItem(itemSlot, newItem);
            plugin.getInventoryManager().openInventory(player, new ItemEditorInventory(plugin, player, itemSlot));
        }));
        // Clear Potion Effects
        addItem(23, () -> inventories.getItemStack("itemMechanicsEditor.items.clearPotionEffects"));
        addLeftAction(23, ((player1, i) -> {
            ItemStack newItem = plugin.getMechanicManager().clearItemPotionEffects(player.getInventory().getItem(itemSlot));
            player.getInventory().setItem(itemSlot, newItem);
            plugin.getInventoryManager().openInventory(player, new ItemEditorInventory(plugin, player, itemSlot));
        }));
    }

}