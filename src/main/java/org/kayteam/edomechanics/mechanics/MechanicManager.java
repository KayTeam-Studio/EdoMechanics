package org.kayteam.edomechanics.mechanics;

import com.google.common.collect.ImmutableMap;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
                .put(MechanicType.FIREBALL, org.bukkit.entity.Fireball.class)
                .put(MechanicType.SMALL_FIREBALL, SmallFireball.class)
                .put(MechanicType.LARGE_FIREBALL, LargeFireball.class)
                .put(MechanicType.ARROW, Arrow.class)
                .put(MechanicType.WITHER_SKULL, WitherSkull.class)
                .put(MechanicType.EGG, Egg.class)
                .put(MechanicType.SNOWBALL, Snowball.class)
                .put(MechanicType.EXP_BOTTLE, ThrownExpBottle.class)
                .put(MechanicType.DRAGON_FIREBALL, DragonFireball.class)
                .put(MechanicType.SPLASH_POTION, SplashPotion.class)
                .put(MechanicType.LINGERING_POTION, LingeringPotion.class)
                .put(MechanicType.TRIDENT, Trident.class)
                .put(MechanicType.SHULKER_BULLET, ShulkerBullet.class);
        projectileTypes = builder.build();
    }

    public Map<MechanicType, Class<? extends Projectile>> getProjectileTypes(){
        return projectileTypes;
    }

    public ItemStack clearItemPotionEffects(ItemStack itemStack){
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setObject("mechanics.potionEffects", null);
        List<MechanicType> mechanicTypes = getItemMechanics(nbtItem.getItem());
        return setItemMechanics(nbtItem.getItem(), mechanicTypes);
    }

    public ItemStack addItemPotionEffects(ItemStack itemStack, List<PotionEffect> potionEffects){
        List<PotionEffect> potionEffectList = getItemPotionEffects(itemStack);
        potionEffectList.addAll(potionEffects);
        return setItemPotionEffects(itemStack, potionEffectList);
    }

    public ItemStack addItemPotionEffect(ItemStack itemStack, PotionEffect potionEffect){
        List<PotionEffect> potionEffects = new ArrayList<>();
        potionEffects.add(potionEffect);
        return setItemPotionEffects(itemStack, potionEffects);
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
        List<MechanicType> mechanicTypes = getItemMechanics(itemStack);
        if(!mechanicTypes.contains(MechanicType.POTION_EFFECT)){
            itemStack = addItemMechanic(itemStack, MechanicType.POTION_EFFECT);
        }
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
        List<String> mechanicsStringList = new ArrayList<>();
        for(String loreLine : plugin.getSettings().getStringList("items.mechanicsHeader")){
            itemLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
        }
        for(MechanicType mechanic : mechanicTypeList){
            itemLore.add(ChatColor.translateAlternateColorCodes('&', plugin.getSettings().getString("items.mechanic",
                    new String[][]{{"%mechanic_name%", mechanic.toString()}})));
            mechanicsStringList.add(mechanic.toString());
        }
        for(String loreLine : plugin.getSettings().getStringList("items.mechanicsFooter")){
            itemLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
        }
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setObject("mechanics", mechanicsStringList);
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
        mechanicTypes.remove(mechanicType);
        return setItemMechanics(itemStack, mechanicTypes);
    }

    public ItemStack removeItemMechanics(ItemStack itemStack, List<MechanicType> mechanicTypeList){
        List<MechanicType> mechanicTypes = getItemMechanics(itemStack);
        mechanicTypes.removeAll(mechanicTypeList);
        return setItemMechanics(itemStack, mechanicTypes);
    }
}
