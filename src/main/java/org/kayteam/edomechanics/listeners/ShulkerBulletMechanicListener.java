package org.kayteam.edomechanics.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.ShulkerBulletMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.ShulkerBulletMechanic;
import org.kayteam.edomechanics.mechanics.mechanics.WitherSkullMechanic;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.HashMap;
import java.util.UUID;

public class ShulkerBulletMechanicListener implements Listener {

    private final EdoMechanics plugin;
    private final HashMap<UUID, Long> lasts = new HashMap<>();

    public ShulkerBulletMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onShulkerBulletMechanic(ShulkerBulletMechanicEvent event){
        Yaml settings = plugin.getSettings();
        Yaml messages = plugin.getMessages();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (player.hasPermission(settings.getString("mechanics.shulkerBullet.permissionToUse"))) {
            if (!player.hasPermission(settings.getString("mechanics.shulkerBullet.permissionToUse"))) {
                if (lasts.containsKey(uuid)) {
                    int cooldown = settings.getInt("mechanics.shulkerBullet.cooldown");
                    long last = lasts.get(uuid) / 1000;
                    long current = System.currentTimeMillis() / 1000;
                    long transcurre = current - last;
                    if (transcurre < cooldown) {
                        messages.sendMessage(player, "mechanics.shulkerBullet.inCooldown", new String[][] {{"%seconds%", (cooldown - transcurre) + ""}});
                        return;
                    }
                }
            }
            new ShulkerBulletMechanic(plugin, event.getPlayer(), event.getItemUsed()).actions();
            if (!player.hasPermission(settings.getString("mechanics.shulkerBullet.permissionToUse"))) {
                lasts.put(uuid, System.currentTimeMillis());
            }
        } else {
            messages.sendMessage(player, "mechanics.shulkerBullet.noPermission");
        }
    }
}
