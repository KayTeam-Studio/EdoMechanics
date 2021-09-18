package org.kayteam.edomechanics.inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.inventories.editor.ItemEditorInventory;
import org.kayteam.edomechanics.inventories.settings.MechanicListInventory;
import org.kayteam.kayteamapi.input.inputs.DropInput;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.Arrays;

public class EdoMechanicsInventory extends InventoryBuilder {

    public EdoMechanicsInventory(EdoMechanics plugin){
        super(plugin.getInventories().getString("edoMechanics.inventoryTitle"), 6);
        Yaml inventories = plugin.getInventories();
        // Fill
        fillItem(() -> inventories.getItemStack("edoMechanics.panel"));
        // Close
        addItem(8, () -> inventories.getItemStack("edoMechanics.close"));
        addLeftAction(8, (player, slot) -> player.closeInventory());
        // Item Editor
        addItem(10, () -> inventories.getItemStack("edoMechanics.itemEditor"));
        addLeftAction(10, (player, slot) -> {
            player.closeInventory();
            Yaml messages = plugin.getMessages();
            messages.sendMessage(player, "itemEditor.dropItemInput");
            plugin.getInputManager().addInput(player, new DropInput() {
                @Override
                public void onPLayerDrop(Player player, ItemStack itemStack) {
                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            int itemSlot = Arrays.asList(player.getInventory().getContents()).indexOf(itemStack);
                            plugin.getInventoryManager().openInventory(player, new ItemEditorInventory(plugin, player, itemSlot));
                        }
                    }, 1);
                }

                @Override
                public void onPlayerSneak(Player player) {
                    plugin.getInventoryManager().openInventory(player, new EdoMechanicsInventory(plugin));
                }
            });
        });
        // Mechanics Settings
        addItem(11, () -> inventories.getItemStack("edoMechanics.mechanicsSettings"));
        addLeftAction(11, (player, i) -> plugin.getInventoryManager().openInventory(player, new MechanicListInventory(plugin)));
    }
}
