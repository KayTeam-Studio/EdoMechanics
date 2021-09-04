package org.kayteam.edomechanics.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.ArrowProjectileMechanicEvent;
import org.kayteam.edomechanics.events.FireballMechanicEvent;

public class FireballMechanicListener implements Listener {

    private final EdoMechanics plugin;

    public FireballMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFireballMechanic(FireballMechanicEvent event){
        Player player = event.getPlayer();
        ItemStack itemUsed = event.getItemUsed();
        Location eyeLocation = player.getEyeLocation();
        double speed = 2;
        final Vector direction = eyeLocation.getDirection().multiply(speed);
        final Projectile projectile = player.getWorld().spawn(eyeLocation.add(direction.getX(), direction.getY(), direction.getZ()), Fireball);
        projectile.setShooter(player);
        projectile.setVelocity(direction);
    }
}
