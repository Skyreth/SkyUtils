package fr.skyreth.skyutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.skyreth.skyutils.Main;
import fr.skyreth.skyutils.utils.PlayerDataManager;

public class DelhomeCommand implements CommandExecutor
{
	Main main;
	PlayerDataManager data;
	
	public DelhomeCommand(Main main)
	{
		this.main = main;
		this.data = main.getPlayerData();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args)  
	{
		if(sender instanceof Player)
        {
            Player p = (Player) sender;
            
	        if(alias.equalsIgnoreCase("delhome"))
	        {  
	            if(args.length == 0)
		            p.performCommand("delhome help");
	            else 
	            {
	            	if(args[0].equalsIgnoreCase("help"))
	            	{
	            		if(p.isOp())
	            		{
	            			p.sendMessage("--------------[§1Sky§eHome§4Admin§r]--------------");
		            		p.sendMessage("");
		            		p.sendMessage("- /delhome <joueur> <nom>");
		            		p.sendMessage("- /delhome reset <joueur>");
		            		p.sendMessage("");
	            		}
	            			
	            		p.sendMessage("--------------[§1Sky§eHome§r]--------------");
	            		p.sendMessage("");
	            		p.sendMessage("- /delhome help");
	            		p.sendMessage("- /delhome <nom>");
	            		p.sendMessage("");
	            		p.sendMessage("--------------[§1Sky§eHome§r]--------------");
	            			return true;
	            	}
	            	else if(args.length == 2 && p.isOp())
		            {
	            		OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
    					
	            		if(target != null && target.hasPlayedBefore())
	            		{
		            		if(data.hasSimilarName(target.getUniqueId(), args[1]))
		            		{
		            			data.DelHome(target.getUniqueId(), args[1]);
		            			p.sendMessage("Home Supprimer !");
		            		}
		            		else
		            			p.sendMessage("§4Le Joueur ne possede pas de home nommer "+args[1]);
		            	}
		            	else
		            	{
		            		p.sendMessage("§4Joueur Introuvable");
		            		return true;
		            	}
		            }
		            else
		            {
		            	if(data.hasSimilarName(p.getUniqueId(), args[0]))
		            	{
	            			data.DelHome(p.getUniqueId(), args[0]);
	            			p.sendMessage("Home supprimer !");
		            	}
	            		else
	            			p.sendMessage("§4Vous ne possedez pas de home nommer "+args[0]);
		            }
	            }
            }
            else
            {
            	p.sendMessage("§4§lErreur§r§4 vous ne possedez pas la permission !");
            }
        }
		return false;
	}
}
