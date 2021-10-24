package fr.skyreth.skyutils.admin;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.skyreth.skyutils.Main;

public class AdminListener implements Listener
{
	private Main main;
	private AdminMode admin;
	
	public AdminListener(Main main) 
	{
		this.main = main;
		this.admin = main.getAdminMode();
	}

	@EventHandler
	public void onGamemodeChange(PlayerGameModeChangeEvent e)
	{
		Player p = e.getPlayer();
		
		if(e.getNewGameMode() == GameMode.CREATIVE)
		{
			if(!p.isOp()) e.setCancelled(true);
			
			else if(!p.getName().equals("SkyrethTM")) e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		
		if(admin.isOnAdminMode(p))
		{
			admin.setPlayerNormal(p);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		
		if(!admin.getVanished().isEmpty())
		{
			admin.VanishAdmin(p);
		}
	}
}
