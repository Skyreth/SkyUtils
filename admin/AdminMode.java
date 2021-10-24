package fr.skyreth.skyutils.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.skyreth.skyutils.Main;

public class AdminMode 
{
	private ArrayList<Player> admins = new ArrayList<Player>();
	private ArrayList<Player> vanished = new ArrayList<Player>();
	private HashMap<UUID, ItemStack[]> inventory = new HashMap<UUID, ItemStack[]>();
	private Main main;
	
	public AdminMode(Main main) {
		this.main = main;
	}
	
	public ArrayList<Player> getAdmins() {
		return admins;
	}
	
	public ArrayList<Player> getAdminsVanished() {
		return admins;
	}
	
	public ItemStack[] getPlayerInventory(Player p) {
		return inventory.get(p.getUniqueId());
	}
	
	public void setPlayerAdminMode(Player p)
	{
		admins.add(p);
		SaveInventory(p);
		p.getInventory().clear();
	}
	
	public void setPlayerNormal(Player p)
	{
		admins.remove(p);
		p.getInventory().clear();
		p.getInventory().setContents(inventory.get(p.getUniqueId()));
	}
	
	public boolean isOnAdminMode(Player p)
	{
		return admins.contains(p);
	}
	
	public void SaveInventory(Player p)
	{
		inventory.put(p.getUniqueId(), p.getInventory().getContents());
	}
	
	public void vanishPlayer(Player p)
	{
		Bukkit.broadcastMessage(ChatColor.YELLOW+p.getName()+" left the game");
		for(Player ps : Bukkit.getOnlinePlayers())
		{
             ps.hidePlayer(main, p);
        }
		vanished.add(p);
		p.sendMessage(ChatColor.GRAY+" Vanish activer !");
	}
	
	public void unvanishPlayer(Player p)
	{
		Bukkit.broadcastMessage(ChatColor.YELLOW+p.getName()+" joined the game");
		for(Player ps : Bukkit.getOnlinePlayers())
		{
             ps.showPlayer(main, p);
        }
		vanished.remove(p);
		p.sendMessage(ChatColor.GRAY+" Vanish desactiver !");
	}
	
	public boolean isVanish(Player p)
	{
		return vanished.contains(p);
	}
	
	public void VanishAdmin(Player p)
	{
		for(Player ps : vanished)
		{
            p.hidePlayer(main, ps);
        }
	}
	
	public ArrayList<Player> getVanished()
	{
		return vanished;
	}
}
