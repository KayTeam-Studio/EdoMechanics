package org.kayteam.edomechanics.inventories.editor;

import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.inventories.EdoMechanicsInventory;
import org.kayteam.edomechanics.inventories.settings.*;
import org.kayteam.edomechanics.mechanics.MechanicType;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.Arrays;
import java.util.List;

public class MechanicListInventory extends InventoryBuilder {

    public MechanicListInventory(EdoMechanics plugin) {
        super(plugin.getInventories().getString("settings.mechanics.inventoryTitle"), 6);
        Yaml inventories = plugin.getInventories();
        fillItem(() -> inventories.getItemStack("settings.mechanics.panel"));
        // Back
        addItem(0, () -> inventories.getItemStack("settings.mechanics.back"));
        addLeftAction(0, (player, i) -> plugin.getInventoryManager().openInventory(player, new EdoMechanicsInventory(plugin)));
        // Close
        addItem(8, () -> inventories.getItemStack("settings.mechanics.close"));
        addLeftAction(8, (player, i) -> player.closeInventory());
        // Arrow
        addItem(10, () -> inventories.getItemStack("settings.mechanics.arrow"));
        addLeftAction(10, (player, i) -> plugin.getInventoryManager().openInventory(player, new ArrowSettingsInventory(plugin)));
        // DragonFireball
        addItem(11, () -> inventories.getItemStack("settings.mechanics.dragonFireball"));
        addLeftAction(11, (player, i) -> plugin.getInventoryManager().openInventory(player, new DragonFireballSettingsInventory(plugin)));
        // Egg
        addItem(12, () -> inventories.getItemStack("settings.mechanics.egg"));
        addLeftAction(12, (player, i) -> plugin.getInventoryManager().openInventory(player, new EggSettingsInventory(plugin)));
        // Fireball
        addItem(13, () -> inventories.getItemStack("settings.mechanics.fireball"));
        addLeftAction(13, (player, i) -> plugin.getInventoryManager().openInventory(player, new FireballSettingsInventory(plugin)));
        // Lightning
        addItem(14, () -> inventories.getItemStack("settings.mechanics.lightning"));
        addLeftAction(14, (player, i) -> plugin.getInventoryManager().openInventory(player, new LightningSettingsInventory(plugin)));
        // PotionEffect
        addItem(15, () -> inventories.getItemStack("settings.mechanics.potionEffect"));
        addLeftAction(15, (player, i) -> plugin.getInventoryManager().openInventory(player, new PotionEffectSettingsInventory(plugin)));
        // ShulkerBullet
        addItem(16, () -> inventories.getItemStack("settings.mechanics.shulkerBullet"));
        addLeftAction(16, (player, i) -> plugin.getInventoryManager().openInventory(player, new ShulkerBulletSettingsInventory(plugin)));
        // SmallFireball
        addItem(17, () -> inventories.getItemStack("settings.mechanics.smallFireball"));
        addLeftAction(17, (player, i) -> plugin.getInventoryManager().openInventory(player, new SmallFireballSettingsInventory(plugin)));
        // Snowball
        addItem(18, () -> inventories.getItemStack("settings.mechanics.snowball"));
        addLeftAction(18, (player, i) -> plugin.getInventoryManager().openInventory(player, new SnowballSettingsInventory(plugin)));
        // Trident
        addItem(19, () -> inventories.getItemStack("settings.mechanics.trident"));
        addLeftAction(19, (player, i) -> plugin.getInventoryManager().openInventory(player, new TridentSettingsInventory(plugin)));
        // WitherSkull
        addItem(20, () -> inventories.getItemStack("settings.mechanics.witherSkull"));
        addLeftAction(20, (player, i) -> plugin.getInventoryManager().openInventory(player, new WitherSkullSettingsInventory(plugin)));
    }
}