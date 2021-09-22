package org.kayteam.edomechanics.inventories.editor;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.inventories.EdoMechanicsInventory;
import org.kayteam.kayteamapi.inventory.InventoryBuilder;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.List;

public class WorldEditorInventory extends InventoryBuilder {

    public WorldEditorInventory(EdoMechanics plugin, int page) {
        super(plugin.getInventories().getString("worldEditor.title"), 6);
        Yaml settings = plugin.getSettings();
        Yaml inventories = plugin.getInventories();
        fillItem(() -> inventories.getItemStack("worldEditor.items.panel"), new int[] {1, 6});
        // Return
        addItem(0, () -> inventories.getItemStack("worldEditor.items.back"));
        addLeftAction(0, (player, i) -> plugin.getInventoryManager().openInventory(player, new EdoMechanicsInventory(plugin)));
        // Close
        addItem(8, () -> inventories.getItemStack("worldEditor.items.close"));
        addLeftAction(8, (player, i) -> player.closeInventory());
        // Worlds
        List<World> worlds = plugin.getServer().getWorlds();
        for (int i = 9; i < 45; i++) {
            int index = ((page * (4 * 9)) - (4 * 9)) + (i - 9);
            if (index < worlds.size()) {
                World world = worlds.get(index);
                addItem(i, () -> {
                    List<String> enabledWorlds = settings.getStringList("enabledWorlds");
                    String status;
                    if (enabledWorlds.contains(world.getName())) {
                        return Yaml.replace(inventories.getItemStack("worldEditor.items.worldEnabled"), new String[][] {{"%world%", world.getName()}});
                    } else {
                        return Yaml.replace(inventories.getItemStack("worldEditor.items.worldDisabled"), new String[][] {{"%world%", world.getName()}});
                    }
                });
                setUpdatable(i, true);
                setUpdateInterval(i, 4);
                addLeftAction(i, (player, slot) -> {
                    List<String> enabledWorlds = settings.getStringList("enabledWorlds");
                    if (enabledWorlds.contains(world.getName())) {
                        while (enabledWorlds.contains(world.getName())) {
                            enabledWorlds.remove(world.getName());
                        }
                    } else {
                        enabledWorlds.add(world.getName());
                    }
                    settings.set("enabledWorlds", enabledWorlds);
                    settings.saveFileConfiguration();
                });
            }
        }
        // Previous Page
        if (page > 1) {
            addItem(45, () -> inventories.getItemStack("worldEditor.items.previousPage"));
            addLeftAction(45, ((player, s) -> plugin.getInventoryManager().openInventory(player, new WorldEditorInventory(plugin, page - 1))));
        }
        // Next Page
        if (worlds.size() > (page * (4 * 9))) {
            addItem(53, () -> inventories.getItemStack("worldEditor.items.nextPage"));
            addLeftAction(53, ((player, s) -> plugin.getInventoryManager().openInventory(player, new WorldEditorInventory(plugin, page + 1))));
        }
    }
}
