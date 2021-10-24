package fr.skyreth.skyutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.skyreth.skyutils.Main;

public class TpaCommand implements CommandExecutor
{
	private Main main;
	private TpaRunnable tpa;
	
	public TpaCommand(Main main) 
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
            
	        if(alias.equalsIgnoreCase("tpa"))
	        {  
	        	if(args.length == 0)
	        	{
	            	p.sendMessage("§4Syntaxe Erroner !");	
	            	p.sendMessage("tpa <Joueur>");
	            }
		        else
			    {
		        	Player target = Bukkit.getPlayerExact(args[0]);
		        	
		        	if(target != null)
		        		tpa.run(p, target);
		        	else
		        		p.sendMessage("§4Joueur Introuvable !");
			    }	
            }
        }
		return false;
	}
}
