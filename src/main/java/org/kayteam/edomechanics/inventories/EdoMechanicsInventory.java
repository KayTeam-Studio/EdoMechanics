package org.kayteam.edomechanics.inventories;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.kayteamapi.input.inputs.DropInput;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class EdoMechanicsInventory extends InventoryBuilder {

    public EdoMechanicsInventory(EdoMechanics plugin){
        super(plugin.getInventories().getString("edoMechanics.title"), 6);
        Yaml inventories = plugin.getInventories();
        // FILL ITEM
        fillItem(() -> inventories.getItemStack("edoMechanics.items.panel"));
        // CLOSE ITEM
        addItem(0, () -> inventories.getItemStack("edoMechanics.items.close"));
        addLeftAction(0, (player, slot) -> player.closeInventory());
        // ITEM MECHANICS EDITOR INVENTORY OPENER
        addItem(12, () -> inventories.getItemStack("edoMechanics.items.itemMechanicsEditor"));
        addLeftAction(12, (player, slot) -> {
            player.closeInventory();
            Yaml messages = plugin.getMessages();
            messages.sendMessage(player, "edoMechanics.dropInput.itemMechanicsEditor");
            plugin.getInputManager().addInput(player, new DropInput() {
                @Override
                public void onPLayerDrop(Player player, ItemStack itemStack) {
                    plugin.getInventoryManager().openInventory(player, new ItemMechanicsEditorInventory(plugin, itemStack));
                }

                @Override
                public void onPlayerSneak(Player player) {
                    plugin.getInventoryManager().openInventory(player, new EdoMechanicsInventory(plugin));
                }
            });
        });
    }
}
