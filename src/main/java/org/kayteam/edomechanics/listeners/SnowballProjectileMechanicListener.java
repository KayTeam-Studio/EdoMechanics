package org.kayteam.edomechanics.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.ArrowProjectileMechanicEvent;
import org.kayteam.edomechanics.events.SnowballProjectileMechanicEvent;

public class SnowballProjectileMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public SnowballProjectileMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSnowballProjectileMechanic(SnowballProjectileMechanicEvent event){
        Player player = event.getPlayer();
        ItemStack itemUsed = event.getItemUsed();
        Location eyeLocation = player.getEyeLocation();

    }
}
