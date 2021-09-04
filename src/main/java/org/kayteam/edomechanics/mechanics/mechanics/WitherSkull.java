package org.kayteam.edomechanics.mechanics.mechanics;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.mechanics.Mechanic;
import org.kayteam.edomechanics.mechanics.MechanicType;

public class WitherSkull extends Mechanic {

    public WitherSkull(EdoMechanics plugin, Player player, ItemStack itemUsed) {
        super(plugin, MechanicType.WITHER_SKULL, player, itemUsed);
    }

    @Override
    public void actions() {

    }
}
