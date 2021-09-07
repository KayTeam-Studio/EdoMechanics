package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.ExpBottleMechanicEvent;
import org.kayteam.edomechanics.events.WitherSkullMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.ExpBottleMechanic;
import org.kayteam.edomechanics.mechanics.mechanics.WitherSkullMechanic;

public class ExpBottleMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public ExpBottleMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onExpBottleMechanic(ExpBottleMechanicEvent event){
        new ExpBottleMechanic(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
