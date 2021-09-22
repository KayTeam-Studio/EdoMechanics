package org.kayteam.edomechanics.mechanics;

import com.google.common.collect.ImmutableMap;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kayteam.edomechanics.EdoMechanics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class MechanicManager {

    private final EdoMechanics plugin;

    private final Map<MechanicType, Class<? extends Projectile>> projectileTypes;

    public MechanicManager(EdoMechanics plugin) {
        this.plugin = plugin;
        final ImmutableMap.Builder<MechanicType, Class<? extends Projectile>> builder = ImmutableMap.<MechanicType, Class<? extends Projectile>>builder()
                .put(MechanicType.ARROW, Arrow.class)
                .put(MechanicType.DRAGON_FIREBALL, DragonFireball.class)
                .put(MechanicType.EGG, Egg.class)
                .put(MechanicType.FIREBALL, org.bukkit.entity.Fireball.class)
                .put(MechanicType.SHULKER_BULLET, ShulkerBullet.class)
                .put(MechanicType.SMALL_FIREBALL, SmallFireball.class)
                .put(MechanicType.SNOWBALL, Snowball.class)
                .put(MechanicType.TRIDENT, Trident.class)
                .put(MechanicType.WITHER_SKULL, WitherSkull.class);
        projectileTypes = builder.build();
    }

    public Map<MechanicType, Class<? extends Projectile>> getProjectileTypes(){
        return projectileTypes;
    }

    public ItemStack removePotionEffect(ItemStack itemStack, PotionEffectType potionEffectType){
        List<PotionEffect> potionEffects = getItemPotionEffects(itemStack);
        potionEffects.removeIf(potionEffect -> potionEffect.getType().getName().equals(potionEffectType.getName()));
        ItemStack newItem = setItemPotionEffects(itemStack, potionEffects);
        return updateItem(newItem);
    }

    public ItemStack clearItemPotionEffects(ItemStack itemStack){
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setObject("potionEffects", null);
        List<MechanicType> mechanicTypes = getItemMechanics(nbtItem.getItem());
        return setItemMechanics(nbtItem.getItem(), mechanicTypes);
    }

    public ItemStack addItemPotionEffects(ItemStack itemStack, List<PotionEffect> potionEffects){
        List<PotionEffect> potionEffectList = (List<PotionEffect>) getItemPotionEffects(itemStack);
        potionEffectList.addAll(potionEffects);
        return setItemPotionEffects(itemStack, potionEffectList);
    }

    public ItemStack addItemPotionEffect(ItemStack itemStack, PotionEffect potionEffect){
        List<PotionEffect> potionEffects = getItemPotionEffects(itemStack);
        potionEffects.add(potionEffect);
        return setItemPotionEffects(itemStack, potionEffects);
    }

    public List<PotionEffect> getItemPotionEffects(ItemStack itemStack){
        List<PotionEffect> potionEffects = new ArrayList<>();
        NBTItem nbtItem = new NBTItem(itemStack);
        if(nbtItem.getObject("potionEffects", List.class) != null){
            for(Object potionEffectLine : nbtItem.getObject("potionEffects", List.class)){
                String[] potionEffectLineSplit = ((String) potionEffectLine).split(":");
                try{
                    PotionEffectType potionEffectType = PotionEffectType.getByName(potionEffectLineSplit[0]);
                    int potionEffectDuration = Integer.parseInt(potionEffectLineSplit[1])*20;
                    int potionEffectLevel = Integer.parseInt(potionEffectLineSplit[2])-1;
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
        }
        return potionEffects;
    }

    public ItemStack setItemPotionEffects(ItemStack itemStack, List<PotionEffect> potionEffects){
        ItemStack newItem = itemStack;
        List<MechanicType> mechanicTypes = getItemMechanics(newItem);
        if(!mechanicTypes.contains(MechanicType.POTION_EFFECT)){
            newItem = addItemMechanic(newItem, MechanicType.POTION_EFFECT);
        }
        NBTItem nbtItem = new NBTItem(newItem);
        List<String> potionStringList = new ArrayList<>();
        nbtItem.setObject("potionEffects", null);
        for(PotionEffect potionEffect : potionEffects){
            potionStringList.add(potionEffect.getType().getName()+":"+potionEffect.getDuration()+":"+potionEffect.getAmplifier());
        }
        nbtItem.setObject("potionEffects", potionStringList);
        return updateItem(nbtItem.getItem());
    }

    public ItemStack setItemPotionEffect(ItemStack itemStack, PotionEffect potionEffect){
        List<PotionEffect> potionEffectList = new ArrayList<>();
        potionEffectList.add(potionEffect);
        return setItemPotionEffects(itemStack, potionEffectList);
    }

    public ItemStack updateItem(ItemStack itemStack){
        List<String> itemLore = new ArrayList<>();
        List<String> mechanicsStringList = new ArrayList<>();
        List<MechanicType> mechanicTypeList = getItemMechanics(itemStack);
        for(String loreLine : plugin.getSettings().getStringList("items.mechanicsHeader")){
            itemLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
        }
        for(MechanicType mechanic : mechanicTypeList){
            if(!mechanic.equals(MechanicType.POTION_EFFECT)){
                itemLore.add(ChatColor.translateAlternateColorCodes('&', plugin.getSettings().getString("items.mechanic",
                        new String[][]{{"%mechanic_name%", mechanic.toString()}})));
            }
            mechanicsStringList.add(mechanic.toString());
        }
        List<PotionEffect> potionEffects = getItemPotionEffects(itemStack);
        for(PotionEffect potionEffect : potionEffects){
            itemLore.add(ChatColor.translateAlternateColorCodes('&', plugin.getSettings().getString("items.potionEffect",
                    new String[][] {
                    {"%effect_name%", potionEffect.getType().getName()},
                    {"%effect_duration%", String.valueOf(potionEffect.getDuration()/20)},
                    {"%effect_level%", String.valueOf(potionEffect.getAmplifier()+2)}})));
        }
        for(String loreLine : plugin.getSettings().getStringList("items.mechanicsFooter")){
            itemLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
        NBTItem nbtItem = new NBTItem(itemStack);
        while(getItemMechanics(nbtItem.getItem()).size() != 0){
            nbtItem.setObject("mechanics", null);
        }
        nbtItem.setObject("mechanics", mechanicsStringList);
        return nbtItem.getItem();
    }

    public ItemStack setItemMechanics(ItemStack itemStack, List<MechanicType> mechanicTypeList){
        List<String> mechanicsStringList = new ArrayList<>();
        for(MechanicType mechanic : mechanicTypeList){
            mechanicsStringList.add(mechanic.toString());
        }
        NBTItem nbtItem = new NBTItem(itemStack);
        while(getItemMechanics(nbtItem.getItem()).size() != 0){
            nbtItem.setObject("mechanics", null);
        }
        nbtItem.setObject("mechanics", mechanicsStringList);
        return updateItem(nbtItem.getItem());
    }

    public ItemStack setItemMechanic(ItemStack itemStack, MechanicType mechanicType){
        List<MechanicType> mechanicTypes = new ArrayList<>();
        mechanicTypes.add(mechanicType);
        return setItemMechanics(itemStack, mechanicTypes);
    }

    public List<MechanicType> getItemMechanics(ItemStack itemStack){
        NBTItem nbtItem = new NBTItem(itemStack);
        List<MechanicType> mechanicTypes = new ArrayList<>();
        if(nbtItem.getObject("mechanics", List.class) != null){
            for(Object mechanicName : nbtItem.getObject("mechanics", List.class)){
                mechanicTypes.add(MechanicType.valueOf((String) mechanicName));
            }
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
        while(mechanicTypes.contains(mechanicType)){
            mechanicTypes.remove(mechanicType);
        }
        Bukkit.getLogger().info("Remove "+mechanicTypes.toString());
        return setItemMechanics(itemStack, mechanicTypes);
    }

    public ItemStack removeItemMechanics(ItemStack itemStack, List<MechanicType> mechanicTypeList){
        List<MechanicType> mechanicTypes = getItemMechanics(itemStack);
        mechanicTypes.removeAll(mechanicTypeList);
        return setItemMechanics(itemStack, mechanicTypes);
    }
}
