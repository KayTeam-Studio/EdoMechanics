package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.ArrowProjectileMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.ArrowProjectile;

public class ArrowProjectileMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public ArrowProjectileMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onArrowProyectileMechanic(ArrowProjectileMechanicEvent event){
        new ArrowProjectile(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
