package org.kayteam.edomechanics.mechanics.mechanics;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.mechanics.Mechanic;
import org.kayteam.edomechanics.mechanics.MechanicType;

public class LightningMechanic extends Mechanic {

    public LightningMechanic(EdoMechanics plugin, Player player, ItemStack itemUsed) {
        super(plugin, MechanicType.LIGHTNING, player, itemUsed);
    }

    @Override
    public void actions() {
        getPlayer().getLocation().getWorld().strikeLightning(getPlayer().getTargetBlock(null,600).getLocation());
    }
}
