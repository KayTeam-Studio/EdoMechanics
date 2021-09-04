package org.kayteam.edomechanics.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class WitherSkullMechanicEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private boolean cancelled = false;

    private final Player player;
    private final ItemStack itemUsed;

    public WitherSkullMechanicEvent(Player player, ItemStack itemUsed) {
        this.player = player;
        this.itemUsed = itemUsed;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItemUsed() {
        return itemUsed;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
