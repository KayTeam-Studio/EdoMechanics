package org.kayteam.edomechanics.inventories.editor;

import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.mechanics.MechanicType;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.Arrays;
import java.util.List;

public class MechanicsSelectorInventory extends InventoryBuilder {

    public MechanicsSelectorInventory(EdoMechanics plugin, ItemStack itemStack, int page){
        super(plugin.getInventories().getString("mechanicsSelector.inventoryTitle"), 6);
        Yaml inventories = plugin.getInventories();
        // Fill
        fillItem(() -> inventories.getItemStack("mechanicsSelector.panel"), new int[]{1, 6});
        // Close
        addItem(0, () -> inventories.getItemStack("mechanicsSelector.back"));
        addLeftAction(0, (player, slot) -> plugin.getInventoryManager().openInventory(player, new MechanicsInventory(plugin, itemStack, 1)));
        // Close
        addItem(0, () -> inventories.getItemStack("mechanicsSelector.close"));
        addLeftAction(0, (player, slot) -> player.closeInventory());
        // Mechanics
        MechanicType[] mechanicTypes = MechanicType.values();
        List<MechanicType> mechanics = Arrays.asList(mechanicTypes);
        mechanics.remove(MechanicType.POTION_EFFECT);
        for (int i = 9; i < 45; i++) {
            int index = ((page * (4 * 9)) - (4 * 9)) + (i - 9);
            if (index < mechanics.size()) {
                addItem(i, () -> Yaml.replace(inventories.getItemStack("mechanicsSelector.mechanic"), new String[][] {
                        {"%mechanic_name%", mechanics.get(index).toString()}
                }));
                addLeftAction(i, (player, slot) -> {
                    ItemStack resultItemStack = plugin.getMechanicManager().addItemMechanic(itemStack, mechanics.get(index));
                    plugin.getInventoryManager().openInventory(player, new MechanicsInventory(plugin, resultItemStack, 1));
                });
            }
        }
        // Previous Page
        if (page > 1) {
            addItem(45, () -> inventories.getItemStack("mechanicsSelector.previousPage"));
            addLeftAction(45, (player, slot) -> plugin.getInventoryManager().openInventory(player, new MechanicsSelectorInventory(plugin, itemStack, page - 1)));
        }
        // Next Page
        if (mechanics.size() > (page * (4 * 9))) {
            addItem(53, () -> inventories.getItemStack("mechanicsSelector.nextPage"));
            addLeftAction(53, (player, slot) -> plugin.getInventoryManager().openInventory(player, new MechanicsSelectorInventory(plugin, itemStack, page + 1)));
        }
    }
}
