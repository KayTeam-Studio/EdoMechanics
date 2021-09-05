package org.kayteam.edomechanics.inventories;

import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class ItemMechanicsEditorInventory extends InventoryBuilder {

    public ItemMechanicsEditorInventory(EdoMechanics plugin, ItemStack itemStack){
        super(plugin.getInventories().getString("itemMechanicsEditor.title"), 6);
        Yaml inventories = plugin.getInventories();
        // FILL ITEM
        fillItem(() -> inventories.getItemStack("itemMechanicsEditor.items.panel"));
        // CLOSE ITEM
        addItem(0, () -> inventories.getItemStack("itemMechanicsEditor.items.close"));
        addLeftAction(0, (player, slot) -> player.closeInventory());

    }
}
