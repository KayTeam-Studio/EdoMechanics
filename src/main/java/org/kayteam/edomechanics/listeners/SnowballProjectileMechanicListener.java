package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.SnowballProjectileMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.SnowballProjectile;

public class SnowballProjectileMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public SnowballProjectileMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSnowballProjectileMechanic(SnowballProjectileMechanicEvent event){
        new SnowballProjectile(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
