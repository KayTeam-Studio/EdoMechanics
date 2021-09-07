package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.LingeringPotionMechanicEvent;
import org.kayteam.edomechanics.events.WitherSkullMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.DragonFireballMechanic;
import org.kayteam.edomechanics.mechanics.mechanics.LingeringPotionMechanic;

public class LingeringPotionMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public LingeringPotionMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLingeringPotionMechanic(LingeringPotionMechanicEvent event){
        new LingeringPotionMechanic(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
