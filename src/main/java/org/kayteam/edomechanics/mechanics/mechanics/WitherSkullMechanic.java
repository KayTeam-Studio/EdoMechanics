package org.kayteam.edomechanics.mechanics.mechanics;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.mechanics.Mechanic;
import org.kayteam.edomechanics.mechanics.MechanicType;

public class WitherSkullMechanic extends Mechanic {

    private final EdoMechanics plugin;

    public WitherSkullMechanic(EdoMechanics plugin, Player player, ItemStack itemUsed) {
        super(plugin, MechanicType.WITHER_SKULL, player, itemUsed);
        this.plugin = plugin;
    }

    @Override
    public void actions() {
        Player player = getPlayer();
        Location location = player.getEyeLocation();
        World world = player.getWorld();
        Location targetLocation = getPlayer().getEyeLocation();
        Vector direction = targetLocation.getDirection().multiply(2);
        WitherSkull witherSkull = world.spawn(location.add(direction.getX(), direction.getY(), direction.getZ()), WitherSkull.class);
        witherSkull.setShooter(getPlayer());
        witherSkull.setVelocity(direction);
        witherSkull.setMetadata(witherSkull.getName(), new FixedMetadataValue(plugin, "WitherSkullMechanic"));
    }

}