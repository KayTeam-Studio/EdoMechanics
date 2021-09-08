package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.EggMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.EggMechanic;

public class EggMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public EggMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEggProjectileMechanic(EggMechanicEvent event){
        new EggMechanic(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
