package org.kayteam.edomechanics.mechanics;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kayteam.edomechanics.EdoMechanics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class MechanicManager {

    private final EdoMechanics plugin;

    public MechanicManager(EdoMechanics plugin) {
        this.plugin = plugin;
    }

    public List<PotionEffect> getItemPotionEffects(ItemStack itemStack){
        List<PotionEffect> potionEffects = new ArrayList<>();
        NBTItem nbtItem = new NBTItem(itemStack);
        for(String potionEffectLine : nbtItem.getStringList("mechanics.potionEffects")){
            String[] potionEffectLineSplit = potionEffectLine.split(":");
            try{
                PotionEffectType potionEffectType = PotionEffectType.getByName(potionEffectLineSplit[0]);
                int potionEffectDuration = Integer.parseInt(potionEffectLineSplit[2]);
                int potionEffectLevel = Integer.parseInt(potionEffectLineSplit[1]);
                if(potionEffectType != null){
                    potionEffects.add(new org.bukkit.potion.PotionEffect(potionEffectType, potionEffectDuration, potionEffectLevel));
                }else{
                    plugin.getLogger().log(Level.INFO, "An item has an invalid potion effect type.");
                }
            }catch (Exception e){
                plugin.getLogger().log(Level.WARNING,
                        "An error has occured trying to get potion effect "+potionEffectLineSplit[0]);
            }
        }
        return potionEffects;
    }

    public ItemStack setItemPotionEffects(ItemStack itemStack, List<PotionEffect> potionEffects){
        NBTItem nbtItem = new NBTItem(itemStack);
        List<String> potionStringList = new ArrayList<>();
        nbtItem.setObject("mechanics.potionEffects", null);
        for(PotionEffect potionEffect : potionEffects){
            potionStringList.add(potionEffect.toString()+":"+potionEffect.getDuration()+":"+potionEffect.getAmplifier());
        }
        nbtItem.setObject("mechanics.potionEffects", potionStringList);
        return nbtItem.getItem();
    }

    public ItemStack setItemPotionEffect(ItemStack itemStack, PotionEffect potionEffect){
        List<PotionEffect> potionEffectList = new ArrayList<>();
        potionEffectList.add(potionEffect);
        return setItemPotionEffects(itemStack, potionEffectList);
    }

    public ItemStack setItemMechanics(ItemStack itemStack, List<MechanicType> mechanicTypeList){
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> itemLore = new ArrayList<>();
        for(String loreLine : plugin.getSettings().getStringList("items.mechanicsHeader")){
            itemLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
        }
        for(MechanicType mechanic : mechanicTypeList){
            itemLore.add(plugin.getSettings().getString("items.mechanic",
                    new String[][]{{"%mechanic_name%", mechanic.toString()}}));
        }
        for(String loreLine : plugin.getSettings().getStringList("items.mechanicsFooter")){
            itemLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
        }
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setObject("mechanics", mechanicTypeList);
        return nbtItem.getItem();
    }

    public ItemStack setItemMechanic(ItemStack itemStack, MechanicType mechanicType){
        List<MechanicType> mechanicTypes = new ArrayList<>();
        mechanicTypes.add(mechanicType);
        return setItemMechanics(itemStack, mechanicTypes);
    }

    public List<MechanicType> getItemMechanics(ItemStack itemStack){
        NBTItem nbtItem = new NBTItem(itemStack);
        List<MechanicType> mechanicTypes = new ArrayList<>();
        for(String mechanicName : nbtItem.getStringList("mechanics")){
            mechanicTypes.add(MechanicType.valueOf(mechanicName));
        }
        return mechanicTypes;
    }

    public ItemStack addItemMechanic(ItemStack itemStack, MechanicType mechanicType){
        List<MechanicType> mechanicTypes = getItemMechanics(itemStack);
        mechanicTypes.add(mechanicType);
        return setItemMechanics(itemStack, mechanicTypes);
    }

    public ItemStack addItemMechanics(ItemStack itemStack, List<MechanicType> mechanicTypeList){
        List<MechanicType> mechanicTypes = getItemMechanics(itemStack);
        mechanicTypes.addAll(mechanicTypeList);
        return setItemMechanics(itemStack, mechanicTypes);
    }

    public ItemStack removeItemMechanic(ItemStack itemStack, MechanicType mechanicType){
        List<MechanicType> mechanicTypes = getItemMechanics(itemStack);
        mechanicTypes.remove(mechanicType);
        return setItemMechanics(itemStack, mechanicTypes);
    }

    public ItemStack removeItemMechanics(ItemStack itemStack, List<MechanicType> mechanicTypeList){
        List<MechanicType> mechanicTypes = getItemMechanics(itemStack);
        mechanicTypes.removeAll(mechanicTypeList);
        return setItemMechanics(itemStack, mechanicTypes);
    }
}
