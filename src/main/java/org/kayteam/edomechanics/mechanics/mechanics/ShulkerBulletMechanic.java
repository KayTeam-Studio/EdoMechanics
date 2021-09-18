package org.kayteam.edomechanics.mechanics.mechanics;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.mechanics.Mechanic;
import org.kayteam.edomechanics.mechanics.MechanicType;

public class ShulkerBulletMechanic extends Mechanic {

    public ShulkerBulletMechanic(EdoMechanics plugin, Player player, ItemStack itemUsed) {
        super(plugin, MechanicType.SHULKER_BULLET, player, itemUsed);
    }

    @Override
    public void actions() {
        Player player = getPlayer();
        Location location = player.getEyeLocation();
        World world = player.getWorld();
        Vector direction = location.getDirection().multiply(2);
        ShulkerBullet shulkerBullet = world.spawn(location.add(direction.getX(), direction.getY(), direction.getZ()), ShulkerBullet.class);
        shulkerBullet.setShooter(getPlayer());
        shulkerBullet.setVelocity(direction);
        shulkerBullet.setMetadata(shulkerBullet.getName(), new FixedMetadataValue(getPlugin(), "ShulkerBulletMechanic"));
    }

}