package fr.skyreth.skyutils.mobs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import fr.skyreth.skyutils.Main;

public class FlyRunnable
{
	private BukkitTask task;
	private int i = 300;
	
	public FlyRunnable(Main main, Player p)
	{
		task = Bukkit.getScheduler().runTaskTimer(main, () -> 
		{
			if(i == 60)
	    		p.sendMessage("Attention il vous reste 1 min de fly !");
	    	if(i <= 10)
	    		p.sendMessage("Attention il vous reste "+i+" secondes de fly !");
	        if(i == 0) {
	        	p.setFlying(false);
	        	p.setAllowFlight(false);
	        	cancelTask();
	        }
	        
	        --i;
        }, 20L, 20L);
	}
	
	public void cancelTask()
	{
		if(task != null)
			task.cancel();
	}
}
