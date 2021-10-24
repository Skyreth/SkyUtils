package fr.skyreth.skyutils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.MoistureChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import fr.skyreth.skyutils.commands.Grade;

public class Listener implements org.bukkit.event.Listener 
{
	private Main main;
	private Connexion con;
	private HashMap<UUID, Location> locations = new HashMap<UUID, Location>();
	
	public Listener(Main main) {
		this.main = main;
		this.con = main.getDataPlayer();
	}
	
	@EventHandler
    public void playerInteractEvent(PlayerInteractEvent event)
    {
		Player p = event.getPlayer();
		
        if(event.getClickedBlock() != null)
            if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
            	if(event.getClickedBlock().getBlockData() instanceof Bed)
	        		if(p.getWorld().getTime() >= 12542 && p.getWorld().getTime() <= 23460)
	        			p.getWorld().setTime(23460);
    }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		UUID u = p.getUniqueId();
		
		if(!Grade.hasGrade(p.getUniqueId(), main.getDataPlayer()))
		{
			if(!p.isOp())
				Grade.setGrade(u, "§2", "Joueur", con);
			else
				Grade.setGrade(u, "§4", "Admin", con);
		}
		
		p.setPlayerListName("["+Grade.getGradeColor(p.getUniqueId(), con)+Grade.getGrade(p.getUniqueId(), con)+"§r] "+p.getDisplayName()+"                  ");
		p.setGameMode(GameMode.SURVIVAL);
		resetLife(p);
	}
	
	@EventHandler
	private void onPlayerDamage(EntityDamageEvent e)
	{
		if(e.getEntity() instanceof Player)
			if(e.getEntity().getWorld().getName().equals("Minage"))
				e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		
		if(locations.containsKey(p.getUniqueId()))
			locations.remove(p.getUniqueId());
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e)
	{
		locations.put(e.getEntity().getUniqueId(), e.getEntity().getLocation());
		e.setDroppedExp(0);
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		Location loc = locations.get(p.getUniqueId());
		
		p.getInventory().addItem(main.getUtil().addCustomLoreToItem(new ItemStack(Material.COMPASS), "Death Pointer", loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ()+" "+loc.getWorld().getName()));
		p.setCompassTarget(loc);
		p.updateInventory();
		locations.remove(p.getUniqueId());
		p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);
		p.setHealth(20.0);
	}
	
	@EventHandler
	public void chatFormat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		e.setFormat("["+Grade.getGradeColor(p.getUniqueId(), con)+Grade.getGrade(p.getUniqueId(), con)+"§r] "+p.getDisplayName()+ChatColor.GOLD+" > §r"+e.getMessage().replaceAll("&", "§"));
	}
	
	@EventHandler
	private void onMobDeath(EntityDeathEvent e)
	{
		if(EnumSet.of(EntityType.EVOKER, EntityType.VINDICATOR).contains(e.getEntityType()))
		{
			if(!e.getDrops().isEmpty()) {
				e.getDrops().clear();
				int chance = main.getUtil().generateRandomNumber(100, 0);
				
				if(chance == 15)
					e.getDrops().add(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
				if(chance >= 10)
					e.getDrops().add(new ItemStack(Material.EMERALD, 1));
			}
		}
	}
	
	private void resetLife(Player p)
	{
		double healt = 20.0;
		
		if (p.getInventory().getArmorContents() != null) {
			for(ItemStack it : p.getInventory().getArmorContents()) {
				if(it != null && !it.getType().equals(Material.AIR)) {
					if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getItemMeta().getDisplayName().contains("§5Dragon")) {
						healt += 4.0;
					}
				}
			}			
			p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(healt);
        }
	}
	
	@EventHandler
	private void OnFarmLandChange(MoistureChangeEvent e)
	{
		if(e.getBlock().getWorld().getName().equals("Quest")) {
			e.setCancelled(true);
		}
	}
}
