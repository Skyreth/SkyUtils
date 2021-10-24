package fr.skyreth.skyutils.enchant;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codingforcookies.armorequip.ArmorEquipEvent;

import fr.skyreth.skyutils.Main;

public class EnchantManager implements Listener
{
	private Main main;
	private Enchants enchants = new Enchants();
	
	public EnchantManager(Main main) {
		this.main = main;
	}
	
	@EventHandler(ignoreCancelled = true) 
    public void playerInteractEvent(PlayerInteractEvent event)
    {
		Player p = event.getPlayer();
		ItemStack it = event.getItem();
		
        if(event.getAction()==Action.RIGHT_CLICK_BLOCK || event.getAction()==Action.RIGHT_CLICK_AIR) {
            if(it != null) {
            	if(it.getType() == Material.ENCHANTED_BOOK && main.getUtil().checkCustomItem(it, "§6Enchanted Book"))
            	{
            		if(it.hasItemMeta() && it.getItemMeta().hasLore()) {
            			String enchant = it.getItemMeta().getLore().get(0);
	            		EnchantCreator ec = enchants.getEnchantByNames(enchant);
	
	            		if(ec != null)
	            			ec.openMenu(p, ec, main);
            		}
            	}
            }
        }
    }
	
	@EventHandler
	private void onInteract(PlayerInteractEvent e) 
	{
		Block b = e.getClickedBlock();
		ItemStack it = e.getItem();
		
		if(b != null && it != null)
		{
			if(main.getUtil().hasCustomEnchant(it, "Replanted I"))
			{
				if(EnumSet.of(Material.WHEAT, Material.CARROTS, Material.BEETROOT, Material.POTATOES).contains(b.getType()))
				{
					ItemStack cons = b.getDrops().iterator().next().clone();
					cons.setAmount(1);
					
					if(main.getUtil().CheckItems(e.getPlayer(), cons))
					{
						if(isFullyGrown(b))
						{
							Material type = b.getType();
							List<Item> dropped = new ArrayList<Item>();
							b.getDrops().forEach(item -> {dropped.add(b.getWorld().dropItemNaturally(b.getLocation(), item));});
							BlockDropItemEvent event = new BlockDropItemEvent(b, b.getState(), e.getPlayer(), null);
							Bukkit.getPluginManager().callEvent(event);
							b.setType(type);
							main.getUtil().ConsumeItems(e.getPlayer(), cons);
						}
					}
				}
			}
		}
	}
	
	private boolean isFullyGrown(Block block) {
		BlockData bdata = block.getBlockData();
        Ageable age = (Ageable) bdata;
        return (age.getAge() == age.getMaximumAge());
    }
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) 
	{
		HumanEntity p = e.getView().getPlayer();
		ItemStack it = e.getCurrentItem();
			
		if(e.getView().getTitle().contains("§0Enchant GUI") && it != null)
		{
			if(p instanceof Player)
			{
				Player player = (Player)p;
				String[] title = e.getView().getTitle().split("-");
				EnchantCreator ec = enchants.getEnchantByNames(title[1]);
				
				if(ec != null && main.getUtil().isType(it, ec.getTool()))
				{
					if(!main.getUtil().isAlreadyEnchanted(it, ec))
					{
						if(player.getLevel() >= ec.getXp())
						{
							ItemStack stack = it.clone();
							stack.setItemMeta(main.getUtil().addLore(it, "§7"+ec.getName()+" I"));
							
							if(!main.getUtil().isType(stack, Tool.ARMOR)) {
								p.getInventory().setItem(e.getSlot()-9, stack);
							}
							else
							{
								switch (e.getSlot()) {
								case 13:
									p.getInventory().setBoots(stack);
									break;
								case 22:
									p.getInventory().setLeggings(stack);
									break;
								case 31:
									p.getInventory().setChestplate(stack);
									break;
								case 40:
									p.getInventory().setHelmet(stack);
									break;
	
								default:
									break;
								}
							}
							
							p.getInventory().setItemInMainHand(null);
							player.setLevel(player.getLevel()-ec.getXp());
							p.sendMessage(ChatColor.DARK_GRAY+" * Item enchanter avec succes ! * ");
							p.closeInventory();
						}
						else {
							p.sendMessage(ChatColor.DARK_GRAY+" * vous ne possedez pas l experience requise !§r ("+ChatColor.DARK_GREEN+ec.getXp()+"LVL§r) * ");
						}
					}
					else {
						p.sendMessage(ChatColor.DARK_RED+"Cette item possede deja cette enchant !");
					}
				}
				else {
					p.sendMessage(ChatColor.DARK_RED+"Vous ne pouvez pas enchanter cette item !");
				}
			}
				
			e.setCancelled(true);
		}
	}
	
	@EventHandler 
	public void onArmorEquip(ArmorEquipEvent e)
	{
		if(e.getNewArmorPiece() != null)
		{
			if(main.getUtil().hasCustomEnchant(e.getNewArmorPiece(), "Night Vision I")) {
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 3));
			}
		}
		else if(e.getOldArmorPiece() != null)
		{
			if(main.getUtil().hasCustomEnchant(e.getOldArmorPiece(), "Night Vision I")) {
				e.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION);
			}
		}
	}
	
	@EventHandler
	private void onMobDeath(EntityDeathEvent e)
	{
		if(e.getEntity().getKiller() instanceof Player)
		{
			Player p = (Player) e.getEntity().getKiller();
			
			if(p.getInventory().getItemInMainHand() != null)
			{
				ItemStack it = p.getInventory().getItemInMainHand();
				
				if(main.getUtil().hasCustomEnchant(it, "Magnet I")) {
					List<ItemStack> items = e.getDrops();
					
					for(int i=0;i<items.size();i++) {
						if(!main.getUtil().HasInventoryFull(p)) {
							p.getInventory().addItem(items.get(i));
							e.getDrops().remove(i);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		ItemStack boot = e.getPlayer().getInventory().getBoots();
		
		if(main.getUtil().hasCustomEnchant(boot, "Fire Walker I"))
		{
			Location location = e.getTo().getBlock().getRelative(BlockFace.DOWN).getLocation();
			
			for (int x = (int)location.getX()-1; x <= (int)location.getX()+1; x++) 
			{
		        for (int z = (int)location.getZ()-1; z <= (int)location.getZ()+1; z++) 
		        {
		        	Block block = location.getWorld().getBlockAt(x, (int)location.getY(), z);
		        	
		        	if(block.getType() == Material.LAVA) {
		        		block.setType(Material.OBSIDIAN);
		        	}
		        }
		    }
		}
	}
	
	@EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			
			if(p.getInventory().getItemInMainHand() != null) 
				if(main.getUtil().hasCustomEnchant(p.getInventory().getItemInMainHand(), "Lightning I")) 
					p.getWorld().strikeLightningEffect(e.getEntity().getLocation());
		}
	}
	
	@EventHandler
    public void onBlockDropItem(BlockDropItemEvent e) 
	{
		Player p = e.getPlayer();
		
		if(p.getInventory().getItemInMainHand() != null && !e.getBlock().getType().equals(Material.SPAWNER))
		{
			ItemStack c = p.getInventory().getItemInMainHand();
		    
			for(int i=0;i<e.getItems().size();i++)
			{
				ItemStack it = e.getItems().get(i).getItemStack();
				ItemStack result = main.getUtil().SmeltingEffect(c, it, p);
				
				if(result != null) {
					e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), result);
					e.getItems().remove(i);
				}
				else
				{
					e.setCancelled(true);
				}
			}	
		}
 	}
}
