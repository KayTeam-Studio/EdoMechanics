package org.kayteam.edomechanics;

import org.bukkit.plugin.java.JavaPlugin;
import org.kayteam.edomechanics.commands.EdoMechanicsCommand;
import org.kayteam.edomechanics.listeners.*;
import org.kayteam.edomechanics.mechanics.MechanicManager;
import org.kayteam.kayteamapi.BrandSender;
import org.kayteam.kayteamapi.input.InputManager;
import org.kayteam.kayteamapi.inventory.InventoryManager;
import org.kayteam.kayteamapi.yaml.Yaml;

public final class EdoMechanics extends JavaPlugin {

    // Files
    private final Yaml settings = new Yaml(this, "settings");
    public Yaml getSettings() {
        return settings;
    }
    private final Yaml inventories = new Yaml(this, "inventories");
    public Yaml getInventories() {
        return inventories;
    }
    private final Yaml messages = new Yaml(this, "messages");
    public Yaml getMessages() {
        return messages;
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

    @Override
    public void onEnable() {
        registerFiles();
        registerListeners();
        registerCommands();
        mechanicManager = new MechanicManager(this);
        BrandSender.sendBrandMessage(this, "&aEnabled");
    }

    @Override
    public void onDisable() {
        BrandSender.sendBrandMessage(this, "&cDisabled");
    }

    public void onReload() {
        settings.reloadFileConfiguration();
        inventories.reloadFileConfiguration();
        messages.reloadFileConfiguration();
    }

    private void registerFiles() {
        settings.registerFileConfiguration();
        inventories.registerFileConfiguration();
        messages.registerFileConfiguration();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(this), this);
        getServer().getPluginManager().registerEvents(new ArrowMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new DragonFireballMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new EggMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new FireballMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new LightningMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PotionEffectMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new ShulkerBulletMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new SmallFireballMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new SnowballMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new TridentMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(new WitherSkullMechanicListener(this), this);
        getServer().getPluginManager().registerEvents(inputManager, this);
        getServer().getPluginManager().registerEvents(inventoryManager, this);
    }

    public void registerCommands() {
        new EdoMechanicsCommand(this).registerCommand(this);
    }
}
