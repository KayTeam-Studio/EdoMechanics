package org.kayteam.edomechanics.listeners;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.ArrowProjectileMechanicEvent;
import org.kayteam.edomechanics.events.PotionEffectMechanicEvent;

import java.util.logging.Level;

public class PotionEffectMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public PotionEffectMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPotionEffectMechanic(PotionEffectMechanicEvent event){
        Player player = event.getPlayer();
        ItemStack itemUsed = event.getItemUsed();
        NBTItem nbtItem = new NBTItem(itemUsed);
        for(String potionEffectLine : nbtItem.getStringList("mechanics.potionEffects")){
            String[] potionEffectLineSplit = potionEffectLine.split(":");
            try{
                PotionEffectType potionEffectType = PotionEffectType.getByName(potionEffectLineSplit[0]);
                int potionEffectDuration = Integer.parseInt(potionEffectLineSplit[2]);
                int potionEffectLevel = Integer.parseInt(potionEffectLineSplit[1]);
                if(potionEffectType != null){
                    player.addPotionEffect(new PotionEffect(potionEffectType, potionEffectDuration, potionEffectLevel));
                }else{
                    plugin.getLogger().log(Level.INFO, "An item has an invalid potion effect type.");
                }
            }catch (Exception e){
                plugin.getLogger().log(Level.WARNING,
                        "An error has occured trying to give potion effect "+potionEffectLineSplit[0]+" to player "+player.getName());
            }
        }
    }
}
