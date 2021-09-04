package org.kayteam.edomechanics.mechanics.mechanics;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.mechanics.Mechanic;
import org.kayteam.edomechanics.mechanics.MechanicType;

public class PotionEffect extends Mechanic {

    public PotionEffect(EdoMechanics plugin, Player player, ItemStack itemUsed) {
        super(plugin, MechanicType.POTION_EFFECT, player, itemUsed);
    }

    @Override
    public void actions() {
        Player player = getPlayer();
        for(org.bukkit.potion.PotionEffect potionEffect : getPlugin().getMechanicManager().getItemPotionEffects(getItemUsed())){
            player.addPotionEffect(potionEffect);
        }
    }
}
