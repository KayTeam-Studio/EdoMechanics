package org.kayteam.edomechanics.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.FireballMechanicEvent;
import org.kayteam.edomechanics.mechanics.mechanics.EggMechanic;
import org.kayteam.edomechanics.mechanics.mechanics.FireballMechanic;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.HashMap;
import java.util.UUID;

public class FireballMechanicListener implements Listener {

    private final EdoMechanics plugin;
    private final HashMap<UUID, Long> lasts = new HashMap<>();

    public FireballMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFireballMechanic(FireballMechanicEvent event){
        Yaml settings = plugin.getSettings();
        Yaml messages = plugin.getMessages();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (player.hasPermission(settings.getString("mechanics.fireball.permissionToUse"))) {
            if (!player.hasPermission(settings.getString("mechanics.fireball.permissionToUse"))) {
                if (lasts.containsKey(uuid)) {
                    int cooldown = settings.getInt("mechanics.fireball.cooldown");
                    long last = lasts.get(uuid) / 1000;
                    long current = System.currentTimeMillis() / 1000;
                    long transcurre = current - last;
                    if (transcurre < cooldown) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                new TextComponent(ChatColor.translateAlternateColorCodes('&',
                                        (messages.getString("mechanics.fireball.inCooldown")
                                                .replaceAll("%seconds%", (cooldown - transcurre) + "")))));
                        return;
                    }
                }
            }
            new FireballMechanic(plugin, event.getPlayer(), event.getItemUsed()).actions();
            if (!player.hasPermission(settings.getString("mechanics.fireball.permissionToUse"))) {
                lasts.put(uuid, System.currentTimeMillis());
            }
        } else {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    new TextComponent(ChatColor.translateAlternateColorCodes('&',
                            (messages.getString("mechanics.fireball.noPermission")))));
        }
    }
}
