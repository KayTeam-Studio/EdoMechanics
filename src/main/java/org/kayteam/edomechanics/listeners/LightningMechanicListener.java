package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.LightningMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.EggProjectile;

public class LightningMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public LightningMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLightningMechanic(LightningMechanicEvent event){
        new EggProjectile(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
