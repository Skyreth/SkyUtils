package fr.skyreth.skyutils;

import java.util.ArrayList;
import java.util.EnumSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import com.codingforcookies.armorequip.ArmorEquipEvent;
import com.codingforcookies.armorequip.ArmorEquipEvent.EquipMethod;

import fr.skyreth.skyutils.utils.PlayerDataManager;

public class SPListener implements Listener
{
	private Main main;
	private PlayerDataManager pdm;
	
	public SPListener(Main main)
	{
		this.main = main;
		this.pdm = main.getPlayerData();
	}
	
	@EventHandler
	private void onMobDeath(EntityDeathEvent e) 
	{
		if(e.getEntity().getKiller() instanceof Player) {
			Player p = e.getEntity().getKiller();
			ItemStack it = p.getInventory().getItemInMainHand();
			
			if(main.getUtil().checkCustomItem(it, "§2Mob Collector") && !it.getItemMeta().hasLore()) {
				ItemStack its = it.clone();
				its.setAmount(1);
				p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
				p.getWorld().dropItem(e.getEntity().getLocation(), main.getUtil().addCustomLoreToItem(its, "§2Mob Collector", ("§a"+e.getEntityType().toString())));
			}
		}
	}
	
	@EventHandler
	private void onCraft(PrepareItemCraftEvent e) 
	{
		CraftingInventory inv = e.getInventory();
		
		for(ItemStack stack : inv.getStorageContents()) 
		{
			if(inv.getResult() != null && (!inv.getResult().equals(stack)) && stack.hasItemMeta() && stack.getItemMeta().hasDisplayName() && stack.getItemMeta().getDisplayName().contains("§")) {
				inv.setResult(null);
			}
		}
	}
	
	@EventHandler
	private void onPlayerClick(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		
        if(e.getClickedBlock() != null)
        {
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getAction() != null)
        	{
            	if(main.getUtil().checkCustomItem(p.getInventory().getItemInMainHand(), "§2Mob Collector") && p.getInventory().getItemInMainHand().getItemMeta().hasLore())
        		{
            		String name = p.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).replace("§a", "");
            		EntityType type = EntityType.valueOf(name);
            		
	            	if(e.getClickedBlock().getType() == Material.SPAWNER)
	            	{
	            		CreatureSpawner creatureSpawner = (CreatureSpawner) e.getClickedBlock().getState();
	            			
	            		if(EnumSet.of(EntityType.GHAST, EntityType.WITHER_SKELETON, EntityType.VILLAGER, EntityType.ENDER_CRYSTAL, EntityType.WITHER, EntityType.ENDER_DRAGON, EntityType.ARMOR_STAND, EntityType.UNKNOWN, EntityType.DRAGON_FIREBALL).contains(type)){
	            			p.sendMessage("§4Vous ne pouvez pas mettre cette ame sur ce spawner !");
	            		}
	            		else {
	            			try {creatureSpawner.setSpawnedType(type);} catch (Exception e1) {} 
	            			
		                     creatureSpawner.update();
		                     p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
	            		}
	            	}
	            	else
	        		{
	        			p.getWorld().spawnEntity(e.getClickedBlock().getLocation().add(0, 1, 0), type);
	        			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
	        		}
        		}
        	}
        }
	}
	
	@EventHandler
    public void onBlockBreak(BlockBreakEvent e) 
	{
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
        if(b.getType() == Material.SPAWNER && !b.getWorld().getName().equals("Minage")) {
        	if(b.hasMetadata("PLACED"))
        	{
        		if(p.getGameMode() != GameMode.CREATIVE)
        		{
	        		if(b.getMetadata("PLACED").get(0).value().equals(p.getUniqueId())) {
	        			pdm.removeSpawner(e.getPlayer());
	            		e.getPlayer().sendMessage("Spawners: "+pdm.getSpawners(e.getPlayer())+"/10");
	        		}
	        		else {
	        			e.setCancelled(true);
	        			p.sendMessage("§4Ce spawner ne vous appartient pas !");
	        		}
        		}
        		else
        		{
        			Player c = Bukkit.getPlayer(b.getMetadata("PLACED").get(0).value().toString());
        			
        			if(c != null) {
        				pdm.removeSpawner(c);
        			
        				if(c.isOnline())
        					c.sendMessage("Un spawner vous a ete retirez par un §4Admin§r !");
        			}
        		}
        	}
        	
        	Location location = e.getBlock().getLocation();
            CreatureSpawner creatureSpawner = (CreatureSpawner) e.getBlock().getState();
             
            ItemStack spawnerItem =  new ItemStack(e.getBlock().getType(), 1);
            ItemMeta spawnerMeta = spawnerItem.getItemMeta();
            spawnerMeta.setDisplayName(ChatColor.RESET + creatureSpawner.getSpawnedType().name() + " Spawner");

            ArrayList<String> lore = new ArrayList<String>();
            lore.add(creatureSpawner.getSpawnedType().name());
            spawnerMeta.setLore(lore);
            spawnerItem.setItemMeta(spawnerMeta);
             
            location.getWorld().dropItemNaturally(location, spawnerItem);

            e.setExpToDrop(0);
        }
 	}
	
	 @EventHandler
	    public void onWear(ArmorEquipEvent e) {
	        if (e.getNewArmorPiece() != null) 
	        {
	            if(e.getNewArmorPiece().hasItemMeta() && e.getNewArmorPiece().getItemMeta().hasDisplayName() && e.getNewArmorPiece().getItemMeta().getDisplayName().contains("§5Dragon")) {
	            	e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()+4);
	            }
	        }
	        if(e.getOldArmorPiece() != null) 
	        {
			    if(e.getOldArmorPiece().hasItemMeta() && e.getOldArmorPiece().getItemMeta().hasDisplayName() && e.getOldArmorPiece().getItemMeta().getDisplayName().contains("§5Dragon")) {
			    	if(!e.getMethod().equals(EquipMethod.DEATH)){
			    		e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()-4);
			    	}
			    }
	        }
	    }
	
	@EventHandler
	public void onHammerBreak(BlockBreakEvent event) 
	{
		if(event.getPlayer().getInventory().getItemInMainHand() != null && event.getPlayer().getInventory().getItemInMainHand().hasItemMeta())
		{
			ItemStack it = event.getPlayer().getInventory().getItemInMainHand();
			
			if(it.getItemMeta().hasDisplayName() && (it.getItemMeta().getDisplayName().equals("§8Netherite Hammer") && it.getType() == Material.NETHERITE_PICKAXE) || 
			(it.getItemMeta().getDisplayName().equals("§bDiamond Hammer") && it.getType() == Material.DIAMOND_PICKAXE))
			{
			    main.getUtil().HammerEffect(event.getBlock(), event.getPlayer(), event.getBlock().getLocation(), event.getBlock().getWorld(), it);
			    event.getBlock().getDrops().clear();
			}
		}
	}
	
	@EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
		
		Player p = e.getPlayer();
		Block b = e.getBlockPlaced();
		
		switch(b.getType())
		{
			case SPAWNER:
				if(!(p.getGameMode() == GameMode.CREATIVE))
	        	{
	        		if(pdm.hasSpawner(e.getPlayer()))
	        		{
			        	if(pdm.canPlaceAnother(e.getPlayer())) {
			        		e.getBlock().setMetadata("PLACED", new FixedMetadataValue(main, p.getUniqueId()));
				            CreatureSpawner creatureSpawner = (CreatureSpawner) b.getState();
				            try { creatureSpawner.setSpawnedType(EntityType.valueOf(e.getItemInHand().getItemMeta().getLore().get(0)));}
				            catch (Exception ex) {ex.printStackTrace();} 
				            creatureSpawner.update();
				            
				            pdm.addSpawner(e.getPlayer());
				            p.sendMessage("Spawners: "+pdm.getSpawners(p)+"/10");
			        	}
			        	else
			        	{
			        		e.setCancelled(true);
			        		p.sendMessage("Vous avez atteint la limite de spawner autoriser !");
			        	}
	        		}
	        		else
	        			pdm.addSpawner(p);
	        	}
	        	else
	        	{
	        		CreatureSpawner creatureSpawner = (CreatureSpawner) b.getState();
		            try {creatureSpawner.setSpawnedType(EntityType.valueOf(e.getItemInHand().getItemMeta().getLore().get(0)));} 
		            catch (Exception ex) { ex.printStackTrace(); } 
		            creatureSpawner.update();
	        	}
	        break;
	        case ANVIL:
	        	if(main.getUtil().checkCustomItem(e.getItemInHand(), "§5Netherite Anvil")) {
	        		e.getBlock().setMetadata("NETHERITEANVIL", new FixedMetadataValue(main, p.getUniqueId()));
	        	}
	        break;
		default:
			break;
		}
	}
}
