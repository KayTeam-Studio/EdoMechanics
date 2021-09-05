package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.WitherSkullMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.WitherSkull;

public class WitherSkullMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public WitherSkullMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWitherSkullMechanic(WitherSkullMechanicEvent event){
        new WitherSkull(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
