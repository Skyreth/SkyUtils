package fr.skyreth.skyutils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class SpawnCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			
			if(p.getBedSpawnLocation() != null){
				p.teleport(p.getBedSpawnLocation());
			}
			else{
				p.sendMessage(ChatColor.DARK_RED+"Vous n avez pas de lit disponnible !");
				p.teleport(p.getWorld().getSpawnLocation());
			}
		}
		return false;
	}

}
