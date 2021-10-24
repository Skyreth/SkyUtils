package fr.skyreth.skyutils.mobs;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.skyreth.skyutils.Main;

public class MobSpawnCommand implements CommandExecutor, TabCompleter
{
	private Main main;
	
	public MobSpawnCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("mob") && p.isOp()) {
				if(main.getCustomMobs().getMob(args[0]) != null) {
					main.getCustomMobs().getMob(args[0]).SpawnEntity(p.getLocation());
					return true;
				}
				else
					p.sendMessage("&4Mob Introuvable !");
			}
		}
		
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if(sender instanceof Player) {
			if(cmd.getName().equalsIgnoreCase("mob")) {
				return main.getCustomMobs().getMobsUnlocalizedNames();
			}
		}
		
		return null;
	}
}
