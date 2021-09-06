package org.kayteam.edomechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.ShulkerBulletMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.ShulkerBulletMechanic;

public class ShulkerBulletMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public ShulkerBulletMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onShulkerBulletMechanic(ShulkerBulletMechanicEvent event){
        new ShulkerBulletMechanic(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
