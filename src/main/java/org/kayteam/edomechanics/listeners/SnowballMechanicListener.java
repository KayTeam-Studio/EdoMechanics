package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.SnowballMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.SnowballMechanic;

public class SnowballMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public SnowballMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSnowballProjectileMechanic(SnowballMechanicEvent event){
        new SnowballMechanic(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
