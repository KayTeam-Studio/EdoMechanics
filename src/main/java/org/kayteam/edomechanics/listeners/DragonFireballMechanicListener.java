package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.DragonFireballMechanicEvent;
import org.kayteam.edomechanics.events.WitherSkullMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.DragonFireballMechanic;
import org.kayteam.edomechanics.mechanics.mechanics.WitherSkullMechanic;

public class DragonFireballMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public DragonFireballMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDragonFireballMechanic(DragonFireballMechanicEvent event){
        new DragonFireballMechanic(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
