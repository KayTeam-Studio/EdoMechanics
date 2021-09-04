package org.kayteam.edomechanics.mechanics.mechanics;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.mechanics.Mechanic;
import org.kayteam.edomechanics.mechanics.MechanicType;

public class EggProjectile extends Mechanic {

    public EggProjectile(EdoMechanics plugin, Player player, ItemStack itemUsed) {
        super(plugin, MechanicType.EGG_PROJECTILE, player, itemUsed);
    }

    @Override
    public void actions() {

    }
}
