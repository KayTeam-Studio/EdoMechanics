package org.kayteam.edomechanics.listeners;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.*;
import org.kayteam.edomechanics.mechanics.MechanicType;

public class PlayerInteractListener implements Listener {

    private final EdoMechanics plugin;

    public PlayerInteractListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getItem() != null){
            NBTItem nbtItem = new NBTItem(event.getItem());
            if(nbtItem.getKeys().contains("mechanics")){
                for(String mechanicName : nbtItem.getStringList("mechanics")){
                    MechanicType mechanicType = MechanicType.valueOf(mechanicName);
                    switch (mechanicType){
                        case EGG:{
                            event.setCancelled(true);
                            plugin.getServer().getPluginManager().callEvent(new EggProjectileMechanicEvent(player, event.getItem()));
                            break;
                        }
                        case SHULKER_BULLET:{
                            event.setCancelled(true);
                            plugin.getServer().getPluginManager().callEvent(new ShulkerBulletMechanicEvent(player, event.getItem()));
                            break;
                        }
                        case POTION_EFFECT:{
                            event.setCancelled(true);
                            plugin.getServer().getPluginManager().callEvent(new PotionEffectMechanicEvent(player, event.getItem()));
                            break;
                        }
                        case SNOWBALL:{
                            event.setCancelled(true);
                            plugin.getServer().getPluginManager().callEvent(new SnowballProjectileMechanicEvent(player, event.getItem()));
                            break;
                        }
                        case LIGHTNING:{
                            event.setCancelled(true);
                            plugin.getServer().getPluginManager().callEvent(new LightningMechanicEvent(player, event.getItem()));
                            break;
                        }
                        case WITHER_SKULL:{
                            event.setCancelled(true);
                            plugin.getServer().getPluginManager().callEvent(new WitherSkullMechanicEvent(player, event.getItem()));
                            break;
                        }
                        case FIREBALL:{
                            event.setCancelled(true);
                            plugin.getServer().getPluginManager().callEvent(new FireballMechanicEvent(player, event.getItem()));
                            break;
                        }
                        case ARROW:{
                            event.setCancelled(true);
                            plugin.getServer().getPluginManager().callEvent(new ArrowProjectileMechanicEvent(player, event.getItem()));
                            break;
                        }
                    }
                }
            }
        }
    }
}
