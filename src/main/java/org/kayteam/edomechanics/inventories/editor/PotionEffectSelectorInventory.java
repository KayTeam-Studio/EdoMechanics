package org.kayteam.edomechanics.inventories.editor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.inventories.EdoMechanicsInventory;
import org.kayteam.kayteamapi.input.inputs.ChatInput;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

public class PotionEffectSelectorInventory extends InventoryBuilder {

    private int potionAmplifier;
    private int potionDuration;

    public PotionEffectSelectorInventory(EdoMechanics plugin, int itemSlot, int page){
        super(plugin.getInventories().getString("potionEffectSelector.title"), 6);
        Yaml inventories = plugin.getInventories();
        fillItem(() -> inventories.getItemStack("potionEffectSelector.items.panel"), new int[] {1, 6});
        // Back
        addItem(0, () -> inventories.getItemStack("potionEffectSelector.items.back"));
        addLeftAction(0, (player, i) -> plugin.getInventoryManager().openInventory(player, new EdoMechanicsInventory(plugin)));
        // Close
        addItem(8, () -> inventories.getItemStack("potionEffectSelector.items.close"));
        addLeftAction(8, (player, i) -> player.closeInventory());
        // POTION EFFECTS
        PotionEffectType[] potionEffects = PotionEffectType.values();
        for (int i = 9; i < 45; i++) {
            int index = ((page * (4 * 9)) - (4 * 9)) + (i - 9);
            if (index < potionEffects.length) {
                addItem(i, () -> Yaml.replace(inventories.getItemStack("potionEffectSelector.items.potion"), new String[][] {{"%effect_name%", potionEffects[index].getName()}}));
                addLeftAction(i, (player, slot) -> {
                    player.closeInventory();
                    Yaml messages = plugin.getMessages();
                    messages.sendMessage(player, "potionEffectSelector.chatInput.durationInput");
                    plugin.getInputManager().addInput(player, new ChatInput() {
                        @Override
                        public boolean onChatInput(Player player, String s) {
                            try{
                                potionDuration = Integer.parseInt(s);
                                messages.sendMessage(player, "potionEffectSelector.chatInput.amplifierInput");
                                plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                                    @Override
                                    public void run() {
                                        plugin.getInputManager().addInput(player, new ChatInput() {
                                            @Override
                                            public boolean onChatInput(Player player, String ss) {
                                                try{
                                                    potionAmplifier = Integer.parseInt(ss);
                                                    PotionEffect potionEffect = new PotionEffect(potionEffects[index], potionDuration, potionAmplifier-1);
                                                    ItemStack newItem = plugin.getMechanicManager()
                                                            .addItemPotionEffect(player.getInventory().getItem(itemSlot), potionEffect);
                                                    player.getInventory().setItem(itemSlot, newItem);
                                                    plugin.getInventoryManager().openInventory(player, new PotionEffectInventory(plugin, player, itemSlot, 1));
                                                    return true;
                                                }catch (Exception e){
                                                    messages.sendMessage(player, "potionEffectSelector.chatInput.amplifierInput");
                                                    return false;
                                                }
                                            }

                                            @Override
                                            public void onPlayerSneak(Player player) {
                                                plugin.getInventoryManager().openInventory(player, new PotionEffectSelectorInventory(plugin, itemSlot, page));
                                            }
                                        });
                                    }
                                }, 5);
                                return true;
                            }catch (Exception e){
                                messages.sendMessage(player, "potionEffectSelector.chatInput.durationInput");
                                return false;
                            }
                        }

                        @Override
                        public void onPlayerSneak(Player player) {
                            plugin.getInventoryManager().openInventory(player, new PotionEffectSelectorInventory(plugin, itemSlot, page));
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
