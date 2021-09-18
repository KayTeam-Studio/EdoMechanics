package org.kayteam.edomechanics.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.Objects;
import java.util.Random;

public class EntityDamageByEntityListener implements Listener {

    private final EdoMechanics plugin;

    public EntityDamageByEntityListener(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();
        EntityType entityType = entity.getType();
        switch (entityType) {
            case WITHER_SKULL: {
                if (Objects.requireNonNull(entity.getMetadata(entity.getName()).get(0).value()).toString().equals("WitherSkullMechanic")) {
                    Yaml setting = plugin.getSettings();
                    int min = setting.getInt("mechanics.witherSkull.minDamage");
                    int max = setting.getInt("mechanics.witherSkull.maxDamage");
                    Random random = new Random();
                    int damage = random.nextInt((max + 1) - min) + min;
                    event.setDamage(damage);
                }
            }
            case SHULKER_BULLET: {
                if (Objects.requireNonNull(entity.getMetadata(entity.getName()).get(0).value()).toString().equals("ShulkerBulletMechanic")) {
                    Yaml setting = plugin.getSettings();
                    int min = setting.getInt("mechanics.shulkerBullet.minDamage");
                    int max = setting.getInt("mechanics.shulkerBullet.maxDamage");
                    Random random = new Random();
                    int damage = random.nextInt((max + 1) - min) + min;
                    event.setDamage(damage);
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Entity entity = event.getEntity();
        EntityType entityType = entity.getType();
        switch (entityType) {
            case WITHER_SKULL: {
                if (Objects.requireNonNull(entity.getMetadata(entity.getName()).get(0).value()).toString().equals("WitherSkullMechanic")) {
                    Objects.requireNonNull(entity.getLocation().getWorld()).createExplosion(entity.getLocation(), 10);
                }
            }
        }
    }

}