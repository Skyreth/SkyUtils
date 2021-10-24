package fr.skyreth.skyutils.jobs;

import java.util.EnumSet;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.skyreth.skyutils.Main;

public class JobListener implements Listener
{
	private Main main;
	private Apiculteur api;
	
	public JobListener(Main main)
	{
		this.main = main;
		api = main.getApiculturInstance();
	}
	
	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent e)
	{
		Action action = e.getAction();
		Player p = e.getPlayer();
		
		if(action != null)
		{
			if(action == Action.RIGHT_CLICK_BLOCK)
			{
				Block block = e.getClickedBlock();
				if(EnumSet.of(Material.BEE_NEST, Material.BEEHIVE).contains(block.getType())) {
					if(block.getBlockData() instanceof Beehive) {
						if(e.getItem() != null) {
							if(EnumSet.of(Material.SHEARS, Material.GLASS_BOTTLE).contains(e.getItem().getType())) {
								Beehive hive = (Beehive) block.getBlockData();
								
								if(hive.getHoneyLevel() >= 3) {
									api.addExperience(p, 1f);
								}
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	private void onAnimalBreed(EntityBreedEvent e)
	{
		if(e.getBreeder() instanceof Player)
		{
			Player p = (Player) e.getBreeder();
			
			if(e.getEntityType() == EntityType.BEE) {
				api.addExperience(p, 0.5f);
			}
		}
	}
}
