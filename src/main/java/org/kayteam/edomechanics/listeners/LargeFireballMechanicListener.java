package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.LargeFireballMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.LargeFireballMechanic;

public class LargeFireballMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public LargeFireballMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFireballMechanic(LargeFireballMechanicEvent event){
        new LargeFireballMechanic(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
