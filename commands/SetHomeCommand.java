package fr.skyreth.skyutils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.skyreth.skyutils.Main;
import fr.skyreth.skyutils.utils.PlayerDataManager;

public class SetHomeCommand implements CommandExecutor
{
	Main main;
	PlayerDataManager data;
	
	public SetHomeCommand(Main main)
	{
		this.main = main;
		this.data = main.getPlayerData();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args)  
	{
		if(sender instanceof Player)
        {
            Player p = (Player) sender;
            
	        if(alias.equalsIgnoreCase("sethome"))
	        {  
	        	if(args.length == 0)
		            p.performCommand("sethome help");
	            else 
	            {
	            	if(args[0].equalsIgnoreCase("help"))
	            	{
	            		if(p.isOp())
	            		{
	            				p.sendMessage("--------------[§1Sky§eHome§r]--------------");
		            			p.sendMessage("");
		            			p.sendMessage("- /sethome <nom> <Joueur>");
		            			p.sendMessage("");
		            			p.sendMessage("--------------[§1Sky§eHome§r]--------------");
	            		}
	            			
	            		p.sendMessage("--------------[§1Sky§eHome§r]--------------");
	            		p.sendMessage("");
	            		p.sendMessage("- /sethome <nom>");
	            		p.sendMessage("- /sethome help");
	            		p.sendMessage("");
	            		p.sendMessage("--------------[§1Sky§eHome§r]--------------");
	            	}
	            	else
	            	{
	            		if(args.length == 2 && p.isOp())
	            		{
	            			Player target = main.getUtil().getPlayerByName(p, args[1]);
	            					
	            			if(target != null)
	            			{
	            				if(!data.hasSimilarName(target.getUniqueId(), args[0]))
	            				{
		            				data.setHome(target.getUniqueId(), p.getLocation(), args[0].toString());
		            				p.sendMessage("Home set pour "+target.getName());
	            				}
	            				else
	            				{
	            					p.sendMessage("§4Vous possedez deja un home sous ce nom !");
	            					p.sendMessage("§4/delhome "+args[1]+" "+target.getName()+" pour le supprimer .");
	            				}
	            			}
	            			else
	            			{
	            				p.sendMessage("§4Joueur Introuvable");
	            			}
	            		}
	            		else
	            		{
	            			if(!data.hasSimilarName(p.getUniqueId(), args[0]))
            				{
		            			data.setHome(p.getUniqueId(), p.getLocation(), args[0].toString());
		            			p.sendMessage("Home set !");
            				}
	            			else
            				{
            					p.sendMessage("§4Vous possedez deja un home sous ce nom !");
            					p.sendMessage("§7* /delhome "+args[0]+" pour le supprimer .");
            				}
	            		}
	            	}
	            }
	        }
        }
		return false;
	}
}
