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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PotionEffectSelectorInventory extends InventoryBuilder {

    private int potionAmplifier;
    private int potionDuration;

    public PotionEffectSelectorInventory(EdoMechanics plugin, Player player, int itemSlot, int page){
        super(plugin.getInventories().getString("potionEffectSelector.title"), 6);
        Yaml inventories = plugin.getInventories();
        ItemStack itemStack = player.getInventory().getItem(itemSlot);
        fillItem(() -> inventories.getItemStack("potionEffectSelector.items.panel"), new int[] {1, 6});
        // Back
        addItem(0, () -> inventories.getItemStack("potionEffectSelector.items.back"));
        addLeftAction(0, (player1, i) -> plugin.getInventoryManager().openInventory(player, new EdoMechanicsInventory(plugin)));
        // Close
        addItem(8, () -> inventories.getItemStack("potionEffectSelector.items.close"));
        addLeftAction(8, (player1, i) -> player.closeInventory());
        // POTION EFFECTS
        PotionEffectType[] potionEffectTypes = PotionEffectType.values();
        List<PotionEffectType> potionEffects = new ArrayList<>(Arrays.asList(potionEffectTypes));
        potionEffects.removeAll(plugin.getMechanicManager().getItemPotionEffectTypes(itemStack));
        for (int i = 9; i < 45; i++) {
            int index = ((page * (4 * 9)) - (4 * 9)) + (i - 9);
            if (index < potionEffects.size()) {
                addItem(i, () -> Yaml.replace(inventories.getItemStack("potionEffectSelector.items.potion"), new String[][] {{"%effect_name%", potionEffects.get(index).getName()}}));
                addLeftAction(i, (player1, slot) -> {
                    player.closeInventory();
                    Yaml messages = plugin.getMessages();
                    messages.sendMessage(player, "potionEffectSelector.chatInput.durationInput");
                    plugin.getInputManager().addInput(player, new ChatInput() {
                        @Override
                        public boolean onChatInput(Player player, String s) {
                            try{
                                potionDuration = Integer.parseInt(s);
                                messages.sendMessage(player, "potionEffectSelector.chatInput.amplifierInput");
                                plugin.getServer().getScheduler().runTaskLater(plugin, () -> plugin.getInputManager().addInput(player, new ChatInput() {
                                    @Override
                                    public boolean onChatInput(Player player2, String ss) {
                                        try{
                                            potionAmplifier = Integer.parseInt(ss);
                                            PotionEffect potionEffect = new PotionEffect(potionEffects.get(index), potionDuration, potionAmplifier-1);
                                            ItemStack newItem = plugin.getMechanicManager()
                                                    .addItemPotionEffect(itemStack, potionEffect);
                                            player2.getInventory().setItem(itemSlot, newItem);
                                            plugin.getInventoryManager().openInventory(player2, new PotionEffectInventory(plugin, player2, itemSlot, 1));
                                            return true;
                                        }catch (Exception e){
                                            messages.sendMessage(player2, "potionEffectSelector.chatInput.amplifierInput");
                                            return false;
                                        }
                                    }

                                    @Override
                                    public void onPlayerSneak(Player player2) {
                                        plugin.getInventoryManager().openInventory(player2, new PotionEffectSelectorInventory(plugin, player2, itemSlot, page));
                                    }
                                }), 5);
                                return true;
                            }catch (Exception e){
                                messages.sendMessage(player, "potionEffectSelector.chatInput.durationInput");
                                return false;
                            }
                        }

                        @Override
                        public void onPlayerSneak(Player player) {
                            plugin.getInventoryManager().openInventory(player, new PotionEffectSelectorInventory(plugin, player, itemSlot, page));
                        }
                    });
                });
            }
        }
        // PreviousPage
        if (page > 1) {
            addItem(45, () -> inventories.getItemStack("potionEffectSelector.items.previousPage"));
            addLeftAction(45, (player1, slot) -> plugin.getInventoryManager().openInventory(player, new PotionEffectSelectorInventory(plugin, player, itemSlot, page - 1)));
        }
        // NextPage
        if (potionEffects.size() > (page * (4 * 9))) {
            addItem(53, () -> inventories.getItemStack("potionEffectSelector.items.nextPage"));
            addLeftAction(53, (player1, slot) -> plugin.getInventoryManager().openInventory(player, new PotionEffectSelectorInventory(plugin, player, itemSlot, page + 1)));
        }
    }
}
