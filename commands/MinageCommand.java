package fr.skyreth.skyutils.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class MinageCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			
			if(alias.equalsIgnoreCase("minage"))
	        {  
	            if(args.length == 0)
	            {
	            	if(p.getWorld().getName().equals("Minage"))
	            		p.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
	            	else
	            	{
	            		World world = Bukkit.getWorld("Minage");
	            		
	            		if(world != null)
	            			p.teleport(world.getSpawnLocation(), TeleportCause.PLUGIN);
	            		else
	            		{
	            			WorldCreator wc = new WorldCreator("Minage");
	            			wc.environment(World.Environment.NORMAL);
	            			wc.type(WorldType.NORMAL);
	            			wc.createWorld();
	            			p.performCommand("minage");
	            		}
	            	}
	            }
	            else 
	            {
	            	if(args[0].equalsIgnoreCase("help"))
	            	{
	            		if(p.isOp())
	            		{
	            			p.sendMessage("--------------[§1Sky§eHome§aMinage§r]--------------");
		            		p.sendMessage("");
		            		p.sendMessage("- /minage reset");
		            		p.sendMessage("");
	            		}
	            			
	            		p.sendMessage("--------------[§1Sky§aMinage§r]--------------");
	            		p.sendMessage("");
	            		p.sendMessage("- /minage");
	            		p.sendMessage("- /minage top");
	            		p.sendMessage("");
	            		p.sendMessage("--------------[§1Sky§aMinage§r]--------------");
	            		return true;
	            	}
	            	else if(args[0].equalsIgnoreCase("top"))
		            {
	            		if(p.getWorld().getName().equals("Minage"))
	            			p.teleport(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getWorld().getHighestBlockYAt(p.getLocation()), p.getLocation().getBlockZ()), TeleportCause.COMMAND);
	            		else
	            			p.sendMessage("Bien essayer petit malin ;) !");
		            }
	            	else if(args[0].equalsIgnoreCase("reset"))
		            {
	            		if(p.isOp())
	            		{
			            	World world = Bukkit.getWorld("Minage");
			            		
			            	if(world != null)
			            	{
			            		Bukkit.unloadWorld(world, false);
			            		p.sendMessage("Monde déchargé !");
			            		File del = world.getWorldFolder();
			            		if(deleteFolder(del))
			            		{
			            			p.sendMessage("Le Monde Minage a ete supprimer avec succes !");
			            			WorldCreator wc = new WorldCreator("Minage");
			            			wc.environment(World.Environment.NORMAL);
			            			wc.type(WorldType.NORMAL);
			            			wc.createWorld();
			            			p.sendMessage("Le Monde Minage a ete generer avec succes !");
			            		}
			            		else
			            			p.sendMessage("§4Le Monde Minage n'a pas ete supprimer !");
			            	}
			            	else
			            		p.sendMessage("Le Minage n'a pas encore été générer !");
	            		}
	            		else
	            		{
	            			p.sendMessage("Le saint pouvoir n'est pas tiens !");
	            		}
		            }
	            }
	        }
		}
		return false;
	}
	
	private boolean deleteFolder(File path) {
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteFolder(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(path.delete());
	}
}