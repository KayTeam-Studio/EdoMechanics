package org.kayteam.edomechanics.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.ArrowProjectileMechanicEvent;
import org.kayteam.edomechanics.events.ShulkerBulletMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.EggProjectile;
import org.kayteam.edomechanics.mechanics.mechanics.ShulkerBullet;

public class ShulkerBulletMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public ShulkerBulletMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onShulkerBulletMechanic(ShulkerBulletMechanicEvent event){
        new ShulkerBullet(plugin, event.getPlayer(), event.getItemUsed()).actions();
    }
}
