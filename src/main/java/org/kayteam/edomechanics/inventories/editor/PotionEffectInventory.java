package org.kayteam.edomechanics.inventories.editor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.ArrayList;
import java.util.List;

public class PotionEffectInventory extends InventoryBuilder {

    public PotionEffectInventory(EdoMechanics plugin, Player player, int itemSlot, int page) {
        super(plugin.getInventories().getString("potionEffects.title"), 6);
        Yaml inventories = plugin.getInventories();
        // Fill
        fillItem(() -> inventories.getItemStack("potionEffects.items.panel"), new int[] {1, 6});
        // Back
        addItem(0, () -> inventories.getItemStack("potionEffects.items.back"));
        addLeftAction(0, (player1, slot) -> plugin.getInventoryManager().openInventory(player, new ItemEditorInventory(plugin, player, itemSlot)));
        // Close
        addItem(8, () -> inventories.getItemStack("potionEffects.items.close"));
        addLeftAction(8, (player1, slot) -> player.closeInventory());
        // Preview
        addItem(4, () -> player.getInventory().getItem(itemSlot));
        //
        //
        List<PotionEffect> potionEffects = plugin.getMechanicManager().getItemPotionEffects(player.getInventory().getItem(itemSlot));
        for (int i = 9; i < 45; i++) {
            int index = ((page * (4 * 9)) - (4 * 9)) + (i - 9);
            if (index < potionEffects.size()) {
                addItem(i, () -> Yaml.replace(inventories.getItemStack("mechanics.items.mechanic"), new String[][] {
                        {"%effect_name%", potionEffects.get(index).getType().toString()},
                        {"%effect_duration%", String.valueOf(potionEffects.get(index).getDuration())},
                        {"%effect_level%", String.valueOf(potionEffects.get(index).getDuration())}
                }));
                addLeftAction(i, (player1, slot) -> {
                    List<PotionEffect> newPotionsEffects = new ArrayList<>(potionEffects);
                    potionEffects.remove(potionEffects.get(index));
                    ItemStack resultItemStack = plugin.getMechanicManager().setItemPotionEffects(player.getInventory().getContents()[itemSlot], newPotionsEffects);
                    player.getInventory().setItem(itemSlot, resultItemStack);
                    plugin.getInventoryManager().openInventory(player, new PotionEffectInventory(plugin, player, itemSlot, page));
                });
            }
        }
        // Add Potion Effect
        addItem(49, () -> inventories.getItemStack("potionEffects.items.addPotionEffect"));
        addLeftAction(49, (player1, slot) -> plugin.getInventoryManager().openInventory(player, new PotionEffectInventory(plugin, player, itemSlot, 1)));
        // Previous Page
        if (page > 1) {
            addItem(45, () -> inventories.getItemStack("potionEffects.items.previousPage"));
            addLeftAction(45, (player1, slot) -> plugin.getInventoryManager().openInventory(player, new PotionEffectInventory(plugin, player, itemSlot, page - 1)));
        }
        // Next Page
        if (potionEffects.size() > (page * (4 * 9))) {
            addItem(53, () -> inventories.getItemStack("potionEffects.items.nextPage"));
            addLeftAction(53, (player1, slot) -> plugin.getInventoryManager().openInventory(player, new PotionEffectInventory(plugin, player, itemSlot, page + 1)));
        }
    }
}
