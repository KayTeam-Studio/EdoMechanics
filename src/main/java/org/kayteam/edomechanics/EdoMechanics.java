package org.kayteam.edomechanics;

import org.bukkit.plugin.java.JavaPlugin;
import org.kayteam.kayteamapi.BrandSender;
import org.kayteam.kayteamapi.yaml.Yaml;

public final class EdoMechanics extends JavaPlugin {

    private final Yaml settings = new Yaml(this, "settings");
    private final Yaml inventories = new Yaml(this, "inventories");
    private final Yaml messages = new Yaml(this, "messages");

    @Override
    public void onEnable() {
        registerFiles();
        BrandSender.sendBrandMessage(this, "&aEnabled");
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
