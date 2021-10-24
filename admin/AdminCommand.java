package fr.skyreth.skyutils.admin;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.skyreth.skyutils.Main;

public class AdminCommand implements CommandExecutor
{
	private Main main;
	private AdminMode admin;
	
	public AdminCommand(Main main) 
	{
		this.main = main;
		this.admin = main.getAdminMode();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args)  
	{
		if(sender instanceof Player)
        {
            Player p = (Player) sender;
            if(p.isOp())
            {
	            if(alias.equalsIgnoreCase("admin"))
	            {  
	            	if(args.length == 0)
	            	{
	            		if(!admin.isOnAdminMode(p))
	            		{
			            	p.sendMessage("[§1Sky §4Admin§r]: Mode Admin Active");
			            	admin.setPlayerAdminMode(p);
			            	p.setAllowFlight(true);
			            	p.setFlying(true);
	            		}
			            else
			            {
			            	p.sendMessage("[§1Sky §4Admin§r]: Mode Admin Desactiver");
			            	admin.setPlayerNormal(p);
			            	p.setAllowFlight(false);
			            	p.setFlying(false);
			            }	
	            	}
	            	else if(!args[0].isEmpty())
	            	{
	            		if(args[0].equalsIgnoreCase("help"))
	            		{
	            			p.sendMessage("--------------[§1Sky§4Admin§r]--------------");
	            			p.sendMessage("");
	            			p.sendMessage("- /admin vanish");
	            			p.sendMessage("- /admin gui");
	            			p.sendMessage("- /admin invsee <Pseudo>");
	            			p.sendMessage("- /admin health <Pseudo>");
	            			p.sendMessage("");
	            			p.sendMessage("--------------[§1Sky§4Admin§r]--------------");
	            		}
	            		else if(args[0].equalsIgnoreCase("gui"))
	            		{
	            			openAdminGui(p);
	            		}
	            		else if(args[0].equalsIgnoreCase("vanish"))
	            		{
	            			if(!admin.isVanish(p)) admin.vanishPlayer(p);
	            			else admin.unvanishPlayer(p);
	            		}
	            		else if(args[0].equalsIgnoreCase("health"))
	            		{
	            			if(args[1].length() > 0 && args[1] != null)
		            		{
	            				Player target = main.getUtil().getPlayerByName(p, args[1]);
	            				int health = Integer.parseInt(args[2]);
	            				
	            				if(target != null && health >= 20)
	            				{
	            					target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
	            					target.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
	            					p.sendMessage("La vie de "+target.getName()+" a ete mise a "+health);
	            				}
	            				else
	            					p.sendMessage("§4Joueur Introuvable");
		            		}
	            			else
	            			{
	            				p.sendMessage("§4Aucun Joueur Viser !");
	            				p.sendMessage("/admin health <Joueur> <amount>");
	            			}
	            		}
	            		else if(args[0].equalsIgnoreCase("invsee"))
	            		{
	            			if(args[1].length() > 0 && args[1] != null)
		            		{
	            				Player target = main.getUtil().getPlayerByName(p, args[1]);
	            				
	            				if(target != null)
	            					p.openInventory(target.getInventory());
	            				else
	            					p.sendMessage("§4Joueur Introuvable");
		            		}
	            			else
	            			{
	            				p.sendMessage("§4Aucun Joueur Viser !");
	            				p.sendMessage("/admin invsee <Joueur>");
	            			}
	            		}
	            		else if(args[0].equalsIgnoreCase("fakeb"))
	            		{
	            			if(args[1].length() > 0 && args[1] != null)
		            		{
	            				Material data = Material.getMaterial(args[1]);
	            				
	            				if(data != null)
	            				{
		            				p.sendMessage("Block changer");
		            				p.sendBlockChange(p.getLocation(), data.createBlockData());
	            				}
	            				else
	            					p.sendMessage("§4Mauvais Material !");
		            		}
	            			else
            					p.sendMessage("§4Syntax: fakeb <material>");
	            		}
	            		else if(args[0].equalsIgnoreCase("world"))
	            		{
	            			if(args[1].length() > 0 && args[1] != null)
		            		{
	            				if(args[1].equalsIgnoreCase("create"))
	            				{
	            					if(args[2].length() > 0 && args[2] != null)
	            					{
	            						WorldCreator wc = new WorldCreator(args[2]);
	            						wc.environment(World.Environment.NORMAL);
	        	            			wc.type(WorldType.FLAT);
	            						wc.createWorld();
	            					}
	            					else
	            						p.sendMessage("§4Syntax: world create <worldname>");
	            				}
	            				else if(args[1].equalsIgnoreCase("tp"))
	            				{
	            					if(args[2].length() > 0 && args[2] != null)
	            					{
	            						World world = Bukkit.getWorld(args[2]);
	            						
	            						if(world != null)
	            							p.teleport(world.getSpawnLocation(), TeleportCause.COMMAND);
	            						else
	            							p.sendMessage("§4Ce monde est introuvable !");
	            					}
	            					else
	            						p.sendMessage("§4Syntax: world tp <worldname>");
	            				}
	            				else if(args[1].equalsIgnoreCase("delete"))
	            				{
	            					if(args[2].length() > 0 && args[2] != null)
	            					{
	            						World world = Bukkit.getWorld(args[2]);
	            						
	            						if(world != null)
	            						{
	            							Bukkit.unloadWorld(world, false);
	        			            		p.sendMessage("Monde déchargé !");
	        			            		File del = world.getWorldFolder();
	        			            		if(deleteFolder(del))
	        			            		{
	        			            			p.sendMessage("Monde supprimer avec succes !");
	        			            		}
	            						}
	            						else
	            							p.sendMessage("§4Ce monde est introuvable !");
	            					}
	            					else
	            						p.sendMessage("§4Syntax: world delete <worldname>");
	            				}
		            		}
	            			else
            					p.sendMessage("§4Syntax: /admin world [delete, tp or create]");
	            		}
	            		else if(args[0].equalsIgnoreCase("removespawner"))
	            		{
	            			if(args[1].length() > 0 && args[1] != null)
		            		{
	            				Player target = main.getUtil().getPlayerByName(p, args[1]);
	            				
	            				if(target != null)
	            				{
	            					p.sendMessage("Vous avez retirez un spawner a "+target.getName());
	            					target.sendMessage(p.getName()+" vous a retirez un spawner !");
	            					main.getPlayerData().removeSpawner(target);
	            				}
		            		}
	            			else
	            			{
	            				p.sendMessage("§4Aucun Joueur Viser !");
	            				p.sendMessage("/admin removespawner <Joueur>");
	            			}
	            		}
	            	}
	            }
            }
            else
            	p.sendMessage("§4§lErreur§r§4 vous ne possedez pas la permission !");
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
	
	private void openAdminGui(Player p)
	{
		Inventory inv = Bukkit.createInventory(null, 54, "Admin GUI");
		main.getUtil().fullInventory(inv, Material.BLUE_STAINED_GLASS_PANE);
		inv.setItem(10, main.getUtil().CreateItemCustom("§fFly Mode: "+p.getAllowFlight(), Material.FEATHER, 1));
		
		if(p.getAllowFlight())
			inv.setItem(19, new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1));
		else
			inv.setItem(19, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
		
		inv.setItem(12, main.getUtil().CreateItemCustom("§fVanish Mod: "+admin.getVanished().contains(p), Material.BARRIER, 1));
		
		if(admin.getVanished().contains(p))
			inv.setItem(21, new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1));
		else
			inv.setItem(21, new ItemStack(Material.RED_STAINED_GLASS_PANE, 1));
		
		if(p.getName().equals("SkyrethTM"))
			inv.setItem(43, main.getUtil().CreateItemCustom("§fGameMode: "+p.getGameMode(), Material.GRASS_BLOCK, 1));
		
		p.openInventory(inv);
	}
}
