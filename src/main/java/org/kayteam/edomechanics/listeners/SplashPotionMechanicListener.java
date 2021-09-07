package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.SplashPotionMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.SplashPotionMechanic;

public class SplashPotionMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public SplashPotionMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSplashPotionMechanic(SplashPotionMechanicEvent event){
        new SplashPotionMechanic(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
