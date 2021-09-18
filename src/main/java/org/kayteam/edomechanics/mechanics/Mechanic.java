package org.kayteam.edomechanics.mechanics;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;

public abstract class Mechanic {

    private final EdoMechanics plugin;
    private final MechanicType mechanicType;
    private final Player player;
    private final ItemStack itemUsed;

    public Mechanic(EdoMechanics plugin, MechanicType mechanicType, Player player, ItemStack itemUsed) {
        this.plugin = plugin;
        this.mechanicType = mechanicType;
        this.player = player;
        this.itemUsed = itemUsed;
    }

    public EdoMechanics getPlugin() {
        return plugin;
    }

    public MechanicType getMechanicType() {
        return mechanicType;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItemUsed() {
        return itemUsed;
    }

    public abstract void actions();

}