package org.kayteam.edomechanics.listeners;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.*;
import org.kayteam.edomechanics.mechanics.MechanicType;

import java.util.List;

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
            for(Object mechanicName : nbtItem.getObject("mechanics", List.class)){
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
                    case LARGE_FIREBALL:{
                        event.setCancelled(true);
                        plugin.getServer().getPluginManager().callEvent(new LargeFireballMechanicEvent(player, event.getItem()));
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
                    case EXP_BOTTLE:{
                        event.setCancelled(true);
                        plugin.getServer().getPluginManager().callEvent(new ExpBottleMechanicEvent(player, event.getItem()));
                        break;
                    }
                    case SPLASH_POTION:{
                        event.setCancelled(true);
                        plugin.getServer().getPluginManager().callEvent(new SplashPotionMechanicEvent(player, event.getItem()));
                        break;
                    }
                    case LINGERING_POTION:{
                        event.setCancelled(true);
                        plugin.getServer().getPluginManager().callEvent(new LingeringPotionMechanicEvent(player, event.getItem()));
                        break;
                    }
                }
            }
        }
    }
}
