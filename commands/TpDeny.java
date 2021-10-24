package fr.skyreth.skyutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.skyreth.skyutils.Main;

public class TpDeny implements CommandExecutor
{
	private Main main;
	private TpaRunnable tpa;
	private TpaHereRunnable tpahere;
	
	public TpDeny(Main main) 
	{
		this.main = main;
		this.tpa = main.getTpaRunnable();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args)  
	{
		if(sender instanceof Player)
        {
            Player p = (Player) sender;
            
	        if(alias.equalsIgnoreCase("tpdeny"))
	        {  
	        	if(args.length != 0)
	        	{
	        		if(tpa.HasRequest(p))
	        		{
		        		Player target = Bukkit.getPlayerExact(args[0]);
			        	
			        	if(target != null)
			        	{
			        		tpa.Deny(target, p);
			        	}
			        	else
			        	{
			        		p.sendMessage("§4Joueur Introuvable !");
			        	}
	        		}
	        		else if(tpahere.HasRequest(p))
	        		{
		        		Player target = Bukkit.getPlayerExact(args[0]);
			        	
			        	if(target != null)
			        	{
			        		tpahere.Deny(target, p);
			        	}
			        	else
			        	{
			        		p.sendMessage("§4Joueur Introuvable !");
			        	}
	        		}
	        		else
	        		{
	        			p.sendMessage("§4Vous n avez pas de requetes !");
	        		}
	            }
	        	else
	        	{
	        		p.sendMessage("§4Syntax Erroner !");
	        		p.sendMessage("/tpdeny <Joueur>");
	        	}
            }
        }
		return false;
	}
}
