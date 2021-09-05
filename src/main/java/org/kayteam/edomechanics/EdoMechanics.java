package org.kayteam.edomechanics;

import org.bukkit.plugin.java.JavaPlugin;
import org.kayteam.edomechanics.listeners.*;
import org.kayteam.edomechanics.mechanics.MechanicManager;
import org.kayteam.kayteamapi.BrandSender;
import org.kayteam.kayteamapi.input.InputManager;
import org.kayteam.kayteamapi.inventory.InventoryManager;
import org.kayteam.kayteamapi.yaml.Yaml;

public final class EdoMechanics extends JavaPlugin {

    private final Yaml settings = new Yaml(this, "settings");
    private final Yaml inventories = new Yaml(this, "inventories");
    private final Yaml messages = new Yaml(this, "messages");

    @Override
    public void onEnable() {
        registerFiles();
        registerListeners();
        mechanicManager = new MechanicManager(this);
        BrandSender.sendBrandMessage(this, "&aEnabled");
    }

    // Mechanic Manager
    private MechanicManager mechanicManager;
    public MechanicManager getMechanicManager(){
        return mechanicManager;
    }

    // Inventory Manager
    private final InventoryManager inventoryManager = new InventoryManager(this);
    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    // Input Manager
    private final InputManager inputManager = new InputManager();
    public InputManager getInputManager() {
        return inputManager;
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ArrowProjectileMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new EggProjectileMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new LightningMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new WitherSkullMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new PotionEffectMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new ShulkerBulletMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new SnowballProjectileMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new WitherSkullMechanicListener(this), this);
    }

    private void registerFiles() {
        settings.registerFileConfiguration();
        inventories.registerFileConfiguration();
        messages.registerFileConfiguration();
    }

    public Yaml getSettings() {
        return settings;
    }

    public Yaml getInventories() {
        return inventories;
    }

    public Yaml getMessages() {
        return messages;
    }

    @Override
    public void onDisable() {
        BrandSender.sendBrandMessage(this, "&cDisabled");
    }
}
