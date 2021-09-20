package org.kayteam.edomechanics.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.events.PotionEffectMechanicEvent;
import org.kayteam.edomechanics.mechanics.MechanicType;
import org.kayteam.edomechanics.mechanics.mechanics.LightningMechanic;
import org.kayteam.edomechanics.mechanics.mechanics.PotionEffectMechanic;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.HashMap;
import java.util.UUID;

public class PotionEffectMechanicListener implements Listener {

    private final EdoMechanics plugin;
    private final HashMap<UUID, Long> lasts = new HashMap<>();

    public PotionEffectMechanicListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPotionEffectMechanic(PotionEffectMechanicEvent event){
        Yaml settings = plugin.getSettings();
        Yaml messages = plugin.getMessages();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (player.hasPermission(settings.getString("mechanics.potionEffect.permissionToUse"))) {
            if (!player.hasPermission(settings.getString("mechanics.potionEffect.permissionToBypassCooldown"))) {
                if (lasts.containsKey(uuid)) {
                    int cooldown = settings.getInt("mechanics.potionEffect.cooldown");
                    long last = lasts.get(uuid) / 1000;
                    long current = System.currentTimeMillis() / 1000;
                    long transcurre = current - last;
                    if (transcurre < cooldown) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                new TextComponent(ChatColor.translateAlternateColorCodes('&',
                                        (messages.getString("mechanics.inCooldown")
                                                .replaceAll("%seconds%", (cooldown - transcurre) + "")))));
                        return;
                    }
                }
            }
            new PotionEffectMechanic(plugin, event.getPlayer(), event.getItemUsed()).actions();
            if (!player.hasPermission(settings.getString("mechanics.potionEffect.permissionToBypassCooldown"))) {
                lasts.put(uuid, System.currentTimeMillis());
            }
        } else {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    new TextComponent(ChatColor.translateAlternateColorCodes('&',
                            (messages.getString("mechanics.noPermission", new String[][]{{"%mechanic_name%", MechanicType.POTION_EFFECT.toString()}})))));
        }
    }
}
