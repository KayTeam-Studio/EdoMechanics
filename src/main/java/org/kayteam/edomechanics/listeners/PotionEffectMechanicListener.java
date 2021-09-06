package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.PotionEffectMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.PotionEffectMechanic;

public class PotionEffectMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public PotionEffectMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPotionEffectMechanic(PotionEffectMechanicEvent event){
        new PotionEffectMechanic(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
