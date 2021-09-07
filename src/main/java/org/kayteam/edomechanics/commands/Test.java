package org.kayteam.edomechanics.commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.kayteam.edomechanics.EdoMechanics;
import org.kayteam.edomechanics.mechanics.MechanicType;
import org.kayteam.kayteamapi.command.SimpleCommand;

import java.util.ArrayList;
import java.util.List;

public class Test extends SimpleCommand {

    private final EdoMechanics plugin;

    public Test(EdoMechanics plugin) {
        super(plugin, "test");
        this.plugin = plugin;
    }

    @Override
    public void onPlayerExecute(Player player, String[] arguments) {
        if(arguments.length>0){
            try{
                MechanicType mechanicType = MechanicType.valueOf(arguments[0]);
                player.getInventory().setItemInMainHand(plugin.getMechanicManager().setItemMechanic(player.getInventory().getItemInMainHand(), mechanicType));
            }catch (Exception e){}
        }
    }

    @Override
    public List<String> onPlayerTabComplete(Player player, Command command, String[] args) {
        List<String> tabs = new ArrayList<>();
        if(args.length == 1){
            for(MechanicType mechanicType : MechanicType.values()){
                tabs.add(mechanicType.toString());
            }
            return tabs;
        }
        return null;
    }
}
