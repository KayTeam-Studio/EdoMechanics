package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.TridentMechanicEvent;
import org.kayteam.edomechanics.events.WitherSkullMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.TridentMechanic;
import org.kayteam.edomechanics.mechanics.mechanics.WitherSkullMechanic;

public class TridentMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public TridentMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTridentMechanic(TridentMechanicEvent event){
        new TridentMechanic(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
