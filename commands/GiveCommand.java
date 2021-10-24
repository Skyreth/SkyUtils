package fr.skyreth.skyutils.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.skyreth.skyutils.CustomItem;
import fr.skyreth.skyutils.Main;

public class GiveCommand implements CommandExecutor, TabCompleter 
{
	private Main main;
	
	public GiveCommand(Main main)
	{
		this.main = main;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if(sender instanceof Player) {
			if(cmd.getName().equalsIgnoreCase("givec")) {
				if(args.length <= 1)
				{
					return main.getItems().getUnlocalizedNames();
				}
			}
		}
		
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("givec") && p.isOp())
			{
				if(args.length <= 1)
				{
					CustomItem it = main.getItems().getCustomItemByName(args[0]);
						
					if(it != null) {
						p.getInventory().addItem(it.getItem());
						p.updateInventory();
						p.sendMessage(" >> §l"+it.getUnlocalizedName()+"§r vous a ete give !");
						return true;
					}
					else {
						p.sendMessage("§4Impossible de trouver cet item !");
						return false;
					}
				}
				else
				{
					CustomItem it = main.getItems().getCustomItemByName(args[0]);
					int amount = Integer.parseInt(args[1]);
					
					if(it != null) {
						p.getInventory().addItem(it.getItem(amount));
						p.updateInventory();
						p.sendMessage(" >> §l"+amount+" "+it.getUnlocalizedName()+"§r vous ont ete give !");
						return true;
					}
					else {
						p.sendMessage("&4Impossible de trouver cet item !");
						return false;
					}
				}
			}
		}
		
		return false;
	}

}
