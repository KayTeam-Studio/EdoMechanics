package org.kayteam.edomechanics.inventories.editor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.kayteamapi.input.inputs.ChatInput;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class PotionEffectSelectorInventory extends InventoryBuilder {

    public PotionEffectSelectorInventory(EdoMechanics plugin, int itemSlot, int page){
        super(plugin.getInventories().getString("potionEffectSelector.title"), 6);
        Yaml inventories = plugin.getInventories();
        // FILL ITEM
        fillItem(() -> inventories.getItemStack("potionEffectSelector.items.panel"));
        // CLOSE ITEM
        addItem(0, () -> inventories.getItemStack("potionEffectSelector.items.close"));
        addLeftAction(0, (player, slot) -> player.closeInventory());
        // POTION EFFECTS
        PotionEffectType[] potionEffects = PotionEffectType.values();
        for (int i = 9; i < 45; i++) {
            int index = ((page * (4 * 9)) - (4 * 9)) + (i - 9);
            if (index < potionEffects.length) {
                addItem(i, () -> Yaml.replace(inventories.getItemStack("potionEffectSelector.items.potion"), new String[][] {{"%effect_name%", potionEffects[index].toString()}}));
                addLeftAction(i, (player, slot) -> {
                    player.closeInventory();
                    Yaml messages = plugin.getMessages();
                    messages.sendMessage(player, "");
                    plugin.getInputManager().addInput(player, new ChatInput() {
                        @Override
                        public boolean onChatInput(Player player, String s) {
                            plugin.getInputManager().addInput(player, new ChatInput() {
                                @Override
                                public boolean onChatInput(Player player, String s) {
                                    return false;
                                }

                                @Override
                                public void onPlayerSneak(Player player) {
                                    plugin.getInventoryManager().openInventory(player, new PotionEffectSelectorInventory(plugin, itemSlot, page - 1));
                                }
                            });
                            return false;
                        }

                        @Override
                        public void onPlayerSneak(Player player) {
                            plugin.getInventoryManager().openInventory(player, new PotionEffectSelectorInventory(plugin, itemSlot, page - 1));
                        }
                    });
                });
            }
        }
        // PreviousPage
        if (page > 1) {
            addItem(45, () -> inventories.getItemStack("potionEffectSelector.items.previousPage"));
            addLeftAction(45, (player, slot) -> plugin.getInventoryManager().openInventory(player, new PotionEffectSelectorInventory(plugin, itemSlot, page - 1)));
        }
        // NextPage
        if (potionEffects.length > (page * (4 * 9))) {
            addItem(53, () -> inventories.getItemStack("potionEffectSelector.items.nextPage"));
            addLeftAction(53, (player, slot) -> plugin.getInventoryManager().openInventory(player, new PotionEffectSelectorInventory(plugin, itemSlot, page + 1)));
        }
    }
}
