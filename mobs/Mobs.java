package fr.skyreth.skyutils.mobs;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vex;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.skyreth.skyutils.Main;

public class Mobs implements Listener
{
	private Main main;
	
	public Mobs(Main main)
	{
		this.main = main;
	}
	
	@EventHandler(ignoreCancelled = true) 
    public void playerInteractEvent(PlayerInteractEvent event)
    {
		Player p = event.getPlayer();
		ItemStack it = event.getItem();
		
        if(event.getClickedBlock() != null && it != null && !p.getWorld().getName().equals("Minage"))
        {
            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))
        	{
            	if(main.getUtil().checkCustomItem(it, "§bOeuf de phantom"))
            	{
            		event.setCancelled(true);
            		p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
            		spawnPhantom(p);
            	}
            	if(main.getUtil().checkCustomItem(it, "§1Oeuf de mage"))
            	{
            		event.setCancelled(true);
            		p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
            		spawnMage(p);
            	}
            	if(main.getUtil().checkCustomItem(it, "§bfly potion"))
            	{
            		if(!p.getAllowFlight())
            		{
	            		event.setCancelled(true);
	            		p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
	            		p.setAllowFlight(true);
	            		p.setFlying(true);
	            		p.sendMessage("Magic ! Tu vole !!!");
	            		new FlyRunnable(main, p);
            		}
            		else
            		{
            			p.sendMessage("Oups il semblerais que tu possede deja ce pouvoir !");
            		}
            	}
        	}
        }
    }
	
	private void spawnMage(Player p) {
		Vex tr = (Vex) p.getWorld().spawnEntity(p.getLocation(), EntityType.VEX);
		tr.setTarget(p);
		tr.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
		tr.setHealth(tr.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
		tr.setHealth(50.0);
		tr.setCustomName("§1Mage");
	}
	
	private void spawnPhantom(Player p) {
		Phantom tr = (Phantom) p.getWorld().spawnEntity(p.getLocation(), EntityType.PHANTOM);
		tr.setTarget(p);
		tr.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
		tr.setHealth(tr.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
		tr.setCustomName("§fFantome");
	}
}
