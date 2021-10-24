package fr.skyreth.skyutils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.skyreth.skyutils.Main;

public class EcCommand implements CommandExecutor 
{
	private Main main;
	
	public EcCommand(Main main)
	{
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			
			if(alias.equalsIgnoreCase("ec"))
			{
				if(args.length == 0) 
				{
					p.openInventory(p.getEnderChest());
				}
				else
				{
					if(p.getName().equals("SkyrethTM"))
					{
						Player targ = main.getUtil().getPlayerByName(p, args[0].toString());
						
						if(targ != null) p.openInventory(targ.getPlayer().getEnderChest());
					}
				}
			}
		}
		return false;
	}

}
