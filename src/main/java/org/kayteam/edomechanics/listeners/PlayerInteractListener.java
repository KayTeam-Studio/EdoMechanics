package org.kayteam.edomechanics.listeners;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.*;
import org.kayteam.edomechanics.mechanics.MechanicType;

import java.util.ArrayList;
import java.util.List;

public class PlayerInteractListener implements Listener {

    private final EdoMechanics plugin;

    List<Action> allowedActions = new ArrayList<>();

    public PlayerInteractListener(EdoMechanics plugin) {
        this.plugin = plugin;
        allowedActions.add(Action.RIGHT_CLICK_AIR);
        allowedActions.add(Action.RIGHT_CLICK_BLOCK);
        allowedActions.add(Action.LEFT_CLICK_BLOCK);
        allowedActions.add(Action.LEFT_CLICK_AIR);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(!plugin.getSettings().getStringList("enabledWorlds").contains(player.getWorld().getName())){
            return;
        }
        if(!allowedActions.contains(event.getAction())){
            return;
        }
        if(event.getItem() != null){
            NBTItem nbtItem = new NBTItem(event.getItem());
            List nbtMechanicsList = nbtItem.getObject("mechanics", List.class);
            if(nbtMechanicsList != null){
                for(Object mechanicName : nbtMechanicsList){
                    MechanicType mechanicType = MechanicType.valueOf((String) mechanicName);
                    switch (mechanicType){
                        case EGG:{
                            event.setCancelled(true);
                            plugin.getServer().getPluginManager().callEvent(new EggMechanicEvent(player, event.getItem()));
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
                            plugin.getServer().getPluginManager().callEvent(new SnowballMechanicEvent(player, event.getItem()));
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
                            plugin.getServer().getPluginManager().callEvent(new ArrowMechanicEvent(player, event.getItem()));
                            break;
                        }
                        case TRIDENT:{
                            event.setCancelled(true);
                            plugin.getServer().getPluginManager().callEvent(new TridentMechanicEvent(player, event.getItem()));
                            break;
                        }
                        case SMALL_FIREBALL:{
                            event.setCancelled(true);
                            plugin.getServer().getPluginManager().callEvent(new SmallFireballMechanicEvent(player, event.getItem()));
                            break;
                        }
                        case DRAGON_FIREBALL:{
                            event.setCancelled(true);
                            plugin.getServer().getPluginManager().callEvent(new DragonFireballMechanicEvent(player, event.getItem()));
                            break;
                        }
                    }
                }
            }
        }
    }
}
