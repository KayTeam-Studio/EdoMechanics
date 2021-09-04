package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.FireballMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.Fireball;

public class FireballMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public FireballMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFireballMechanic(FireballMechanicEvent event){
        new Fireball(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
