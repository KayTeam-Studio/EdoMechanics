package org.kayteam.edomechanics.mechanics;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kayteam.edomechanics.EdoMechanics;

import java.util.ArrayList;
import java.util.List;

public class MechanicManager {

    private final EdoMechanics plugin;

    public MechanicManager(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    public ItemStack itemSetMechanics(ItemStack itemStack, List<MechanicType> mechanicTypeList){
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> itemLore = new ArrayList<>();
        for(String loreLine : (List<String>) plugin.getSettings().getList("items.mechanicsHeader")){
            itemLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
        }
        for(MechanicType mechanic : mechanicTypeList){
            itemLore.add(plugin.getSettings().getString("items.mechanic",
                    new String[][]{{"%mechanic_name%", mechanic.toString()}}));
        }
        for(String loreLine : (List<String>) plugin.getSettings().getList("items.mechanicsFooter")){
            itemLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
        }
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setObject("mechanics", mechanicTypeList);
        return nbtItem.getItem();
    }

    public ItemStack itemAddMechanic(ItemStack itemStack, List<MechanicType> mechanicTypeList){

    }

    public ItemStack itemRemoveMechanic(ItemStack itemStack, MechanicType mechanicType){

    }

    public ItemStack itemRemoveMechanic(ItemStack itemStack, List<MechanicType> mechanicTypeList){

    }
}
