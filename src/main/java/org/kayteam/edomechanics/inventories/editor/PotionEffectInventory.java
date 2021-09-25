package org.kayteam.edomechanics.inventories.editor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.List;

public class PotionEffectInventory extends InventoryBuilder {

    public PotionEffectInventory(EdoMechanics plugin, Player player, int itemSlot, int page) {
        super(plugin.getInventories().getString("potionEffects.title"), 6);
        Yaml inventories = plugin.getInventories();
        ItemStack itemStack = player.getInventory().getItem(itemSlot);
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
        // Item Potion Effects
        List<PotionEffect> potionEffects = (List<PotionEffect>) plugin.getMechanicManager().getItemPotionEffects(itemStack);
        for (int i = 9; i < 45; i++) {
            int index = ((page * (4 * 9)) - (4 * 9)) + (i - 9);
            if (index < potionEffects.size()) {
                addItem(i, () -> Yaml.replace(inventories.getItemStack("potionEffects.items.potionEffect"), new String[][] {
                        {"%effect_name%", potionEffects.get(index).getType().getName()},
                        {"%effect_duration%", String.valueOf(potionEffects.get(index).getDuration()/20)},
                        {"%effect_level%", String.valueOf(potionEffects.get(index).getAmplifier()+2)}
                }));
                addLeftAction(i, (player1, slot) -> {
                    player.getInventory().setItem(itemSlot, plugin.getMechanicManager().removePotionEffect(itemStack, potionEffects.get(index).getType()));
                    plugin.getInventoryManager().openInventory(player, new PotionEffectInventory(plugin, player, itemSlot, page));
                });
            }
        }
        // Add Potion Effect
        addItem(49, () -> inventories.getItemStack("potionEffects.items.addPotionEffect"));
        addLeftAction(49, (player1, slot) -> plugin.getInventoryManager().openInventory(player, new PotionEffectSelectorInventory(plugin, player, itemSlot, 1)));
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
