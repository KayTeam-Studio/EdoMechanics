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
        super(plugin.getInventories().getString("settings.mechanics.title"), 6);
        Yaml inventories = plugin.getInventories();
        fillItem(() -> inventories.getItemStack("settings.mechanics.items.panel"));
        // Back
        addItem(0, () -> inventories.getItemStack("settings.mechanics.items.back"));
        addLeftAction(0, (player, i) -> plugin.getInventoryManager().openInventory(player, new EdoMechanicsInventory(plugin)));
        // Close
        addItem(8, () -> inventories.getItemStack("settings.mechanics.items.close"));
        addLeftAction(8, (player, i) -> player.closeInventory());
        // Arrow
        addItem(9, () -> Yaml.replace(inventories.getItemStack("settings.mechanics.items.mechanic"), new String[][]{{"%mechanic_name%", MechanicType.ARROW.toString()}}));
        addLeftAction(9, (player, i) -> plugin.getInventoryManager().openInventory(player, new ArrowSettingsInventory(plugin)));
        // DragonFireball
        addItem(10, () -> Yaml.replace(inventories.getItemStack("settings.mechanics.items.mechanic"), new String[][]{{"%mechanic_name%", MechanicType.DRAGON_FIREBALL.toString()}}));
        addLeftAction(10, (player, i) -> plugin.getInventoryManager().openInventory(player, new DragonFireballSettingsInventory(plugin)));
        // Egg
        addItem(11, () -> Yaml.replace(inventories.getItemStack("settings.mechanics.items.mechanic"), new String[][]{{"%mechanic_name%", MechanicType.EGG.toString()}}));
        addLeftAction(11, (player, i) -> plugin.getInventoryManager().openInventory(player, new EggSettingsInventory(plugin)));
        // Fireball
        addItem(12, () -> Yaml.replace(inventories.getItemStack("settings.mechanics.items.mechanic"), new String[][]{{"%mechanic_name%", MechanicType.FIREBALL.toString()}}));
        addLeftAction(12, (player, i) -> plugin.getInventoryManager().openInventory(player, new FireballSettingsInventory(plugin)));
        // Lightning
        addItem(13, () -> Yaml.replace(inventories.getItemStack("settings.mechanics.items.mechanic"), new String[][]{{"%mechanic_name%", MechanicType.LIGHTNING.toString()}}));
        addLeftAction(13, (player, i) -> plugin.getInventoryManager().openInventory(player, new LightningSettingsInventory(plugin)));
        // PotionEffect
        addItem(14, () -> Yaml.replace(inventories.getItemStack("settings.mechanics.items.mechanic"), new String[][]{{"%mechanic_name%", MechanicType.POTION_EFFECT.toString()}}));
        addLeftAction(14, (player, i) -> plugin.getInventoryManager().openInventory(player, new PotionEffectSettingsInventory(plugin)));
        // ShulkerBullet
        addItem(15, () -> Yaml.replace(inventories.getItemStack("settings.mechanics.items.mechanic"), new String[][]{{"%mechanic_name%", MechanicType.SHULKER_BULLET.toString()}}));
        addLeftAction(15, (player, i) -> plugin.getInventoryManager().openInventory(player, new ShulkerBulletSettingsInventory(plugin)));
        // SmallFireball
        addItem(16, () -> Yaml.replace(inventories.getItemStack("settings.mechanics.items.mechanic"), new String[][]{{"%mechanic_name%", MechanicType.SMALL_FIREBALL.toString()}}));
        addLeftAction(16, (player, i) -> plugin.getInventoryManager().openInventory(player, new SmallFireballSettingsInventory(plugin)));
        // Snowball
        addItem(17, () -> Yaml.replace(inventories.getItemStack("settings.mechanics.items.mechanic"), new String[][]{{"%mechanic_name%", MechanicType.SNOWBALL.toString()}}));
        addLeftAction(17, (player, i) -> plugin.getInventoryManager().openInventory(player, new SnowballSettingsInventory(plugin)));
        // Trident
        addItem(18, () -> Yaml.replace(inventories.getItemStack("settings.mechanics.items.mechanic"), new String[][]{{"%mechanic_name%", MechanicType.TRIDENT.toString()}}));
        addLeftAction(18, (player, i) -> plugin.getInventoryManager().openInventory(player, new TridentSettingsInventory(plugin)));
        // WitherSkull
        addItem(19, () -> Yaml.replace(inventories.getItemStack("settings.mechanics.items.mechanic"), new String[][]{{"%mechanic_name%", MechanicType.WITHER_SKULL.toString()}}));
        addLeftAction(19, (player, i) -> plugin.getInventoryManager().openInventory(player, new WitherSkullSettingsInventory(plugin)));
    }
}