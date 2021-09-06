package org.kayteam.edomechanics.inventories.editor;

import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.mechanics.MechanicType;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class MechanicsSelectorInventory extends InventoryBuilder {

    public MechanicsSelectorInventory(EdoMechanics plugin, ItemStack itemStack, int page){
        super(plugin.getInventories().getString("itemMechanicsSelector.title"), 6);
        Yaml inventories = plugin.getInventories();
        // FILL ITEM
        fillItem(() -> inventories.getItemStack("itemMechanicsSelector.items.panel"));
        // CLOSE ITEM
        addItem(0, () -> inventories.getItemStack("itemMechanicsSelector.items.close"));
        addLeftAction(0, (player, slot) -> player.closeInventory());
        // MECHANICS
        MechanicType[] mechanicTypes = MechanicType.values();
        for (int i = 9; i < 45; i++) {
            int index = ((page * (4 * 9)) - (4 * 9)) + (i - 9);
            if (index < mechanicTypes.length) {
                addItem(i, () -> Yaml.replace(inventories.getItemStack("itemMechanicsSelector.items.mechanic"), new String[][] {{"%mechanic_name%", mechanicTypes[index].toString()}}));
                addLeftAction(i, (player, slot) -> {
                    plugin.getInventoryManager().openInventory(player, new ItemEditorInventory(plugin, plugin.getMechanicManager().addItemMechanic(itemStack, mechanicTypes[index])));
                });
            }
        }
        // PreviousPage
        if (page > 1) {
            addItem(45, () -> inventories.getItemStack("itemMechanicsSelector.items.previousPage"));
            addLeftAction(45, (player, slot) -> plugin.getInventoryManager().openInventory(player, new MechanicsSelectorInventory(plugin, itemStack, page - 1)));
        }
        // NextPage
        if (mechanicTypes.length > (page * (4 * 9))) {
            addItem(53, () -> inventories.getItemStack("itemMechanicsSelector.items.nextPage"));
            addLeftAction(53, (player, slot) -> plugin.getInventoryManager().openInventory(player, new MechanicsSelectorInventory(plugin, itemStack, page + 1)));
        }
    }
}
