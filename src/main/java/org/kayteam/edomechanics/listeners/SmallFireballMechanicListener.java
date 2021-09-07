package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.SmallFireballMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.FireballMechanic;
import org.kayteam.edomechanics.mechanics.mechanics.SmallFireballMechanic;

public class SmallFireballMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public SmallFireballMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSmallFireballMechanic(SmallFireballMechanicEvent event){
        new SmallFireballMechanic(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
