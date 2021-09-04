package org.kayteam.edomechanics.mechanics.mechanics;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.mechanics.Mechanic;
import org.kayteam.edomechanics.mechanics.MechanicType;

public class ArrowProjectile extends Mechanic {

    public ArrowProjectile(EdoMechanics plugin, Player player, ItemStack itemUsed) {
        super(plugin, MechanicType.ARROW_PROJECTILE, player, itemUsed);
    }

    @Override
    public void actions() {

    }
}
