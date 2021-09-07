package org.kayteam.edomechanics.mechanics.mechanics;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.mechanics.Mechanic;
import org.kayteam.edomechanics.mechanics.MechanicType;

public class SplashPotionMechanic extends Mechanic {

    public SplashPotionMechanic(EdoMechanics plugin, Player player, ItemStack itemUsed) {
        super(plugin, MechanicType.SPLASH_POTION, player, itemUsed);
    }

    @Override
    public void actions() {
        Location targetLocation = getPlayer().getEyeLocation();
        Vector direction = targetLocation.getDirection().multiply(2);
        final Projectile projectile = getPlayer().getWorld().spawn(getPlayer().getEyeLocation().add(
                        direction.getX(), direction.getY(), direction.getZ()),
                getPlugin().getMechanicManager().getProjectileTypes().get(MechanicType.SPLASH_POTION));
        projectile.setShooter(getPlayer());
        projectile.setVelocity(direction);
    }
}
