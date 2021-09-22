package org.kayteam.edomechanics.mechanics.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.mechanics.Mechanic;
import org.kayteam.edomechanics.mechanics.MechanicType;

import java.util.List;

public class PotionEffectMechanic extends Mechanic {

    public PotionEffectMechanic(EdoMechanics plugin, Player player, ItemStack itemUsed) {
        super(plugin, MechanicType.POTION_EFFECT, player, itemUsed);
    }

    @Override
    public void actions() {
        Player player = getPlayer();
        List<PotionEffect> potionEffects = getPlugin().getMechanicManager().getItemPotionEffects(getItemUsed());
        for(PotionEffect potionEffect : potionEffects){
            player.addPotionEffect(potionEffect);
        }
    }
}
