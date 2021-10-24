package fr.skyreth.skyutils.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import fr.skyreth.skyutils.Main;

public class TpaHereRunnable 
{
	private BukkitTask task;
	private ArrayList<Player> target = new ArrayList<Player>();
	private Main main;
	
	public TpaHereRunnable(Main main)
	{
		this.main = main;
	}
	
	public void run(Player p, Player targ)
	{
		p.sendMessage("§2Demande Envoyer !");
		targ.sendMessage(p.getName()+"Demande pour que vous soyez tp a lui ");
		targ.sendMessage("fait /tpaccept ou /tpdeny");
		target.add(targ);
		runTask(p, targ);
	}
	
	public boolean HasRequest(Player p)
	{
		return target.contains(p);
	}
	
	private void runTask(Player p, Player targ)
	{
		task =  Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            @Override
            public void run() {
                p.sendMessage("§4Requete Expirer !");
                targ.sendMessage("§4Requete Expirer !");
                
            }
        }, 6000L);
	}
	
	private void cancelTask()
	{
		if(task != null)
			task.cancel();
	}
	
	public void Accept(Player targ, Player p)
	{
		p.sendMessage("Teleportation ...");
		targ.teleport(p.getLocation());
		cancelTask();
		target.remove(p);
	}
	
	public void Deny(Player p, Player targ)
	{
		cancelTask();
		targ.sendMessage("Le joueur a refuser votre demande !");
		p.sendMessage("Teleportation refuser !");
		target.remove(p);
	}
}
