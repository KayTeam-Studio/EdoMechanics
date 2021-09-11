package org.kayteam.edomechanics.inventories.editor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.mechanics.MechanicType;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MechanicsSelectorInventory extends InventoryBuilder {

    public MechanicsSelectorInventory(EdoMechanics plugin, Player player, int itemSlot, int page){
        super(plugin.getInventories().getString("mechanicsSelector.inventoryTitle"), 6);
        Yaml inventories = plugin.getInventories();
        // Fill
        fillItem(() -> inventories.getItemStack("mechanicsSelector.panel"), new int[]{1, 6});
        // Back
        addItem(0, () -> inventories.getItemStack("mechanicsSelector.back"));
        addLeftAction(0, (player1, slot) -> plugin.getInventoryManager().openInventory(player, new MechanicsInventory(plugin, player, itemSlot, 1)));
        // Close
        addItem(8, () -> inventories.getItemStack("mechanicsSelector.close"));
        addLeftAction(8, (player1, slot) -> player.closeInventory());
        // Mechanics
        MechanicType[] mechanicTypes = MechanicType.values();
        List<MechanicType> mechanics = Arrays.asList(mechanicTypes);
        // todo fix it -> mechanics.remove(MechanicType.POTION_EFFECT);
        for (int i = 9; i < 45; i++) {
            int index = ((page * (4 * 9)) - (4 * 9)) + (i - 9);
            if (index < mechanics.size()) {
                addItem(i, () -> Yaml.replace(inventories.getItemStack("mechanicsSelector.mechanic"), new String[][] {
                        {"%mechanic_name%", mechanics.get(index).toString()}
                }));
                addLeftAction(i, (player1, slot) -> {
                    ItemStack resultItemStack = plugin.getMechanicManager().addItemMechanic(player.getInventory().getContents()[itemSlot], mechanics.get(index));
                    player.getInventory().setItem(itemSlot, resultItemStack);
                    plugin.getInventoryManager().openInventory(player, new MechanicsInventory(plugin, player, itemSlot, 1));
                });
            }
        }
        // Previous Page
        if (page > 1) {
            addItem(45, () -> inventories.getItemStack("mechanicsSelector.previousPage"));
            addLeftAction(45, (player1, slot) -> plugin.getInventoryManager().openInventory(player, new MechanicsSelectorInventory(plugin, player, itemSlot, page - 1)));
        }
        // Next Page
        if (mechanics.size() > (page * (4 * 9))) {
            addItem(53, () -> inventories.getItemStack("mechanicsSelector.nextPage"));
            addLeftAction(53, (player1, slot) -> plugin.getInventoryManager().openInventory(player, new MechanicsSelectorInventory(plugin, player, itemSlot, page + 1)));
        }
    }
}
