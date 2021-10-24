package fr.skyreth.skyutils.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import fr.skyreth.skyutils.Main;
import fr.skyreth.skyutils.utils.PlayerDataManager;

public class HomeCommand implements CommandExecutor, TabCompleter
{
	Main main;
	PlayerDataManager data;
	
	public HomeCommand(Main main)
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
            
	        if(alias.equalsIgnoreCase("home"))
	        {  
	            if(args.length == 0)
		            p.performCommand("home help");
	            else 
	            {
	            	if(args[0].equalsIgnoreCase("help"))
	            	{
	            		if(p.isOp())
	            		{
	            			p.sendMessage("--------------[§1Sky§eHome§4Admin§r]--------------");
		            		p.sendMessage("");
		            		p.sendMessage("- /home list <joueur>");
		            		p.sendMessage("- /home <joueur> <nom>");
		            		p.sendMessage("");
	            		}
	            			
	            		p.sendMessage("--------------[§1Sky§eHome§r]--------------");
	            		p.sendMessage("");
	            		p.sendMessage("- /home list");
	            		p.sendMessage("- /home <nom>");
	            		p.sendMessage("");
	            		p.sendMessage("--------------[§1Sky§eHome§r]--------------");
	            		return true;
	            	}
	            	else if(args[0].equalsIgnoreCase("list"))
		            {
		            	if(args.length == 2 && p.isOp())
		            	{
		            		OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            					
		            		if(target != null && target.hasPlayedBefore())
		            		{
		            			if(data.hasHomes(p.getUniqueId()))
			            		{
		            				p.sendMessage("--------------[§1Sky§eHome§4Admin§r]--------------");
		            				p.sendMessage("Homes de "+target.getName()+":");
		            				data.getHomesNames(target.getUniqueId());
		            				p.sendMessage("-> "+data.getHomesNames(target.getUniqueId()));
		            				p.sendMessage("--------------[§1Sky§eHome§4Admin§r]--------------");
		            				return true;
			            		}
		            			else
			            		{
			            			p.sendMessage("§4Le Joueur cible ne possede aucun home !");
			            			return true;
			            		}
		            		}
		            		else
		            		{
		            			p.sendMessage("§4Joueur Introuvable");
		            			return true;
		            		}
		            	}
		            	else
		            	{
		            		if(data.hasHomes(p.getUniqueId()))
		            		{
		            			p.sendMessage("--------------[§1Sky§eHome§r]--------------");
	            				p.sendMessage("-> "+data.getHomesNames(p.getUniqueId()));
	            				p.sendMessage("--------------[§1Sky§eHome§r]--------------");
		            			return true;
		            		}
		            		else
		            		{
		            			p.sendMessage("§4Vous ne possedez aucun homes !");
		            			p.sendMessage("§7* Faites /sethome <nom> pour en mettre un *");
		            			return true;
		            		}
		            	}
		            }
		            else
		            {
		            	if(args.length == 2 && p.isOp())
		            	{
		            		OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
		            					
		            		if(target != null)
		            		{
		            			Location loc = data.getHome(target.getUniqueId(), args[1]);
		            				
		            			if(loc != null){
		            				Chunk c = p.getWorld().getChunkAt(p.getLocation());
		            				
		            				if(!c.isForceLoaded() && c.isLoaded())
		            				{
		            					p.getWorld().getChunkAt(p.getLocation()).unload();
		            					p.teleport(loc, TeleportCause.COMMAND);
		            					p.sendMessage("Teleportation au home nommer §l"+args[1]+"§r de "+target.getName());
		            				}
		            				return true;
		            			}
		            			else{
		            				p.sendMessage("Ce home n existe pas !");
		            				return true;
		            			}
		            		}
		            		else{
		            			p.sendMessage("§4Joueur Introuvable");
		            			return true;
		            		}
		            	}
		            	else
		            	{
		            		Location loc = data.getHome(p.getUniqueId(), args[0]);
		            			
		            		if(loc != null){
	            				p.teleport(loc);
	            				p.sendMessage("Teleportation ...");
	            				return true;
	            			}
		            		else{
	            				p.sendMessage("Ce home n existe pas !");
	            				return true;
	            			}
		            	}
		            }
	            }
	        }
        }
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("home"))
			{
				String[] homes = data.getHomesNames(p.getUniqueId()).split(",");
				ArrayList<String> com = new ArrayList<>(Arrays.asList(homes));
				com.add("list");
				return com;			
			}
		}
		
		return null;
	}
	
	
}
