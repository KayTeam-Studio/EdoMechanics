package org.kayteam.edomechanics.mechanics.mechanics;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.mechanics.Mechanic;
import org.kayteam.edomechanics.mechanics.MechanicType;

public class Fireball extends Mechanic {

    public Fireball(EdoMechanics plugin, Player player, ItemStack itemUsed) {
        super(plugin, MechanicType.FIREBALL, player, itemUsed);
    }

    @Override
    public void actions() {
        Location initialLocation = getPlayer().getLocation();
        Location targetLocation = getPlayer().getEyeLocation();
        org.bukkit.entity.Fireball fireball = getPlayer().launchProjectile(org.bukkit.entity.Fireball.class);
        Vector from = new Vector(initialLocation.getX(), initialLocation.getY(), initialLocation.getZ());
        Vector to = new Vector(targetLocation.getX(), targetLocation.getY(), targetLocation.getZ());
        Vector vector = to.subtract(from);
        Vector direction = to.subtract(from);
        direction.normalize();
        direction.multiply(2);
        fireball.setVelocity(direction);
        fireball.setShooter();
        initialLocation.getWorld().spawnArrow(initialLocation, vector, (float) 1, (float) 0, fireball);
    }
}
