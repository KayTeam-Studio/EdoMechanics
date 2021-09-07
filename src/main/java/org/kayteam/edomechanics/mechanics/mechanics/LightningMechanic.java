package org.kayteam.edomechanics.mechanics.mechanics;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.mechanics.Mechanic;
import org.kayteam.edomechanics.mechanics.MechanicType;

import java.util.Objects;

public class LightningMechanic extends Mechanic {

    public LightningMechanic(EdoMechanics plugin, Player player, ItemStack itemUsed) {
        super(plugin, MechanicType.LIGHTNING, player, itemUsed);
    }

    @Override
    public void actions() {
        Objects.requireNonNull(getPlayer().getEyeLocation().getWorld()).strikeLightning(getPlayer().getEyeLocation());
    }
}
