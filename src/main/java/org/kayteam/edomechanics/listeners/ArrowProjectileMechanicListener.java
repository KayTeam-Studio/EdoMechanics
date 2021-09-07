package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.ArrowMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.ArrowMechanic;

public class ArrowProjectileMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public ArrowProjectileMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onArrowMechanic(ArrowMechanicEvent event){
        new ArrowMechanic(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
