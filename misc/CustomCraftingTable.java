package fr.skyreth.skyutils.misc;

import java.util.ArrayList;
import java.util.EnumSet;

import org.bukkit.Bukkit;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.skyreth.skyutils.Main;
import fr.skyreth.skyutils.utils.Utils;

public class CustomCraftingTable implements Listener
{
	private static ArrayList<CustomRecipe> crafts = new ArrayList<CustomRecipe>();
	private Main main;
	private Utils util;
	
	public CustomCraftingTable(Main main)
	{
		this.main = main;
		this.util = main.getUtil();
	}
	
	public void init()
	{
		// Result et init
		CustomRecipe es = new CustomRecipe(main.getItems().smelting_book.getItem());
		CustomRecipe fw = new CustomRecipe(main.getItems().fire_walker_book.getItem());
		CustomRecipe ma = new CustomRecipe(main.getItems().magnet_book.getItem());
		CustomRecipe rp = new CustomRecipe(main.getItems().replanted_book.getItem());
		CustomRecipe fly = new CustomRecipe(main.getItems().fly_potion.getItem());
		CustomRecipe fli = new CustomRecipe(main.getItems().phantom_spawn.getItem());
		CustomRecipe mag = new CustomRecipe(main.getItems().mage_spawn.getItem());
		CustomRecipe drg = new CustomRecipe(main.getItems().dragon_helmet.getItem());
		CustomRecipe drg1 = new CustomRecipe(main.getItems().dragon_chestplate.getItem());
		CustomRecipe drg2 = new CustomRecipe(main.getItems().dragon_legging.getItem());
		CustomRecipe drg3 = new CustomRecipe(main.getItems().dragon_boots.getItem());
		CustomRecipe drg4 = new CustomRecipe(main.getItems().dragon_wings.getItem());
		CustomRecipe night = new CustomRecipe(main.getItems().night_vision_book.getItem());

		
		// Ingredients 
		ItemStack coal = new ItemStack(Material.COAL_BLOCK, 1);
		ItemStack rod = new ItemStack(Material.BLAZE_ROD, 1);
		ItemStack lava = new ItemStack(Material.LAVA_BUCKET, 1);
		ItemStack water = new ItemStack(Material.WATER_BUCKET, 1);
		ItemStack bf = new ItemStack(Material.BLAST_FURNACE, 1);
		ItemStack ib = new ItemStack(Material.IRON_BLOCK, 10);
		ItemStack fur = new ItemStack(Material.FURNACE, 1);
		ItemStack anv = new ItemStack(Material.ANVIL, 1);
		ItemStack pot = new ItemStack(Material.GLASS_BOTTLE, 1);
		ItemStack diams = new ItemStack(Material.DIAMOND, 1);
		ItemStack diamond = new ItemStack(Material.DIAMOND_BLOCK, 1);
		ItemStack eme = new ItemStack(Material.EMERALD, 1);
		ItemStack se = new ItemStack(Material.SPIDER_EYE, 1);
		ItemStack egg = new ItemStack(Material.EGG, 1);
		ItemStack glow = new ItemStack(Material.GLOWSTONE, 64);
		ItemStack fir  = new ItemStack(Material.TNT, 10);
		ItemStack nh = new ItemStack(Material.NETHERITE_HELMET, 1);
		ItemStack nc = new ItemStack(Material.NETHERITE_CHESTPLATE, 1);
		ItemStack nl = new ItemStack(Material.NETHERITE_LEGGINGS, 1);
		ItemStack nb = new ItemStack(Material.NETHERITE_BOOTS, 1);
		ItemStack gb = new ItemStack(Material.GLOW_BERRIES, 10);
		ItemStack fb = new ItemStack(Material.HAY_BLOCK, 64);
		
		// Speciaux
		ItemStack cb = main.getItems().mage_powder.getItem();
		ItemStack cb2 = main.getItems().phantom_drop.getItem();
		ItemStack bc1 = main.getItems().book_epic.getItem();
		ItemStack bc = main.getItems().book_uncommun.getItem();
		ItemStack dh = main.getItems().dragon_heart.getItem();
		ItemStack dsf = main.getItems().dragon_skin.getItem(10);
		
		// Shapes
		rp.setShape(new ItemStack[] {fb, fb, fb, fb, bc, fb, fb, fb, fb});
		drg4.setShape(new ItemStack[] {dsf, dh, dsf, dh, new ItemStack(Material.ELYTRA, 1), dh, dh, dsf, dh});
		night.setShape(new ItemStack[] {glow, glow, glow, glow, diamond, glow, gb, new ItemStack(Material.GLOW_INK_SAC, 2), gb});
		drg.setShape(new ItemStack[] {dsf, dh, dsf, dh, nh, dh, dh, dsf, dh});
		drg1.setShape(new ItemStack[] {dsf, dh, dsf, dh, nc, dh, dh, dsf, dh});
		drg2.setShape(new ItemStack[] {dsf, dh, dsf, dh, nl, dh, dh, dsf, dh});
		drg3.setShape(new ItemStack[] {dsf, dh, dsf, dh, nb, dh, dh, dsf, dh});
		es.setShape(new ItemStack[] {fur, fur, fur, bf, bc1, bf, coal, coal, coal});
		fw.setShape(new ItemStack[] {rod, rod, rod, lava, bc, lava, water, water, water});
		ma.setShape(new ItemStack[] {ib, ib, ib, anv, anv, anv, anv, bc1, anv});
		fly.setShape(new ItemStack[] {null, bc, null, cb2, pot, cb, null, bc, null});
		fli.setShape(new ItemStack[] {fir, eme,fir, eme, egg, eme, fir, eme, fir});
		mag.setShape(new ItemStack[] {se, diams, se, diams, egg, diams, se, diams, se});
		
		crafts.add(rp);
		crafts.add(es);
		crafts.add(ma);
		crafts.add(fw);
		crafts.add(night);
		crafts.add(fli);
		crafts.add(fly);
		crafts.add(mag);
		crafts.add(drg);
		crafts.add(drg1);
		crafts.add(drg2);
		crafts.add(drg3);
		crafts.add(drg4);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		
        if(e.getClickedBlock() != null)
        {
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK && main.getUtil().checkCustomItem(p.getInventory().getItemInMainHand(), "§5Wizard Table"))
            	OpenCraftingTable(p);
        }
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) 
	{
		if(e.getView().getTitle().equals("§5Wizard Table"))
		{
			if(e.getCurrentItem() != null)
			{
				if(EnumSet.of(Material.PURPLE_STAINED_GLASS_PANE, Material.RED_STAINED_GLASS_PANE, Material.GRAY_STAINED_GLASS_PANE).contains(e.getCurrentItem().getType())){
					e.setCancelled(true);
				}
				else if(main.getUtil().checkCustomItem(e.getCurrentItem(), "§eRecipes"))
				{
					this.RecipeGui(e.getView().getPlayer());
					e.setCancelled(true);
				}
			}
		}
		if(e.getView().getTitle().equals("§eRecipes"))
		{
			if(e.getCurrentItem() != null)
			{
				if(EnumSet.of(Material.ORANGE_STAINED_GLASS_PANE, Material.GRAY_STAINED_GLASS_PANE, Material.CRAFTING_TABLE).contains(e.getCurrentItem().getType())) {
					e.setCancelled(true);
				}
				else
				{
					ItemStack it = e.getCurrentItem();
					
					for(int i=0;i<crafts.size();i++)
			    	{
			    		CustomRecipe rec = crafts.get(i);
			    		
			    		if(rec.getCraftingResult().isSimilar(it))
			    		{
			    			Inventory inv = Bukkit.createInventory(null, 27, "§6Recipe");
			    			int slot = -7;
			    			int item = 0;
			            	
			            	for(int c=3;c!=0;c--)
			            	{
			            
			            		slot+=9;
			            		for(int b=3;b!=0;b--)
			            		{
			            			inv.setItem(slot+(b-1), rec.getCraft().get(item));
			            			item++;
			            		}
			            	}
			            	
			            	util.fullInventory(inv, Material.PURPLE_STAINED_GLASS_PANE);
			            	
			            	inv.setItem(16, rec.getCraftingResult());
			            	inv.setItem(18, util.CreateItemCustom("§4<<", Material.KNOWLEDGE_BOOK, 1));
			            	
			            	e.getView().getPlayer().closeInventory();
			            	e.getView().getPlayer().openInventory(inv);
			    		}
			    	}
				}
				
				e.setCancelled(true);
			}
		}
		if(e.getView().getTitle().equals("§6Recipe"))
		{
			if(e.getCurrentItem() != null)
			{
				if(util.checkCustomItem(e.getCurrentItem(), "§4<<"))
				{
					this.RecipeGui(e.getView().getPlayer());
				}
				
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onItemMoveInventory(InventoryClickEvent e)
	{
		if(e.getView().getTitle().equals("§5Wizard Table"))
		{
			if(e.getCurrentItem() != null)
			{
				if(util.checkCustomItem(e.getCurrentItem(), "§0Crafter"))
				{
					for(int i=0;i<crafts.size();i++)
			    	{
			    		CustomRecipe rec = crafts.get(i);
			    		ArrayList<ItemStack> grid = getCraftingGridContent(e.getInventory());
			    		
			    		if(rec.isValidCraft(grid))
			    		{
			    			int cur = 0;
			    			ArrayList<ItemStack> citem = rec.getCraft();
			    			int s= -7;
			            	
			            	for(int c=3;c!=0;c--)
			            	{
			            		s+=9;
			            		for(int b=3;b!=0;b--)
			            		{
			            			if(e.getInventory().getItem(s+(b-1)) != null)
			            				e.getInventory().getItem(s+(b-1)).setAmount(e.getInventory().getItem(s+(b-1)).getAmount() - citem.get(cur).getAmount());
			            			
			            			cur++;
			            		}
			            	}
			            	
			    			e.getView().getPlayer().getInventory().addItem(rec.getCraftingResult());
			    		}
			    	}
					
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event)
	{
		if(event.getView().getTitle().equals("§5Wizard Table"))
		{
			for(ItemStack it : getCraftingGridContent(event.getInventory()))
			{
				if(it != null)
				{
					event.getPlayer().getInventory().addItem(it);
				}
			}
		}
	}
	
	private void OpenCraftingTable(Player p)
	{
		Inventory inv = Bukkit.createInventory(null, 27, "§5Wizard Table");
    	main.getUtil().fullInventory(inv, Material.PURPLE_STAINED_GLASS_PANE);
    	int slot = -7;
    	
    	for(int i=3;i!=0;i--)
    	{
    		slot+=9;
    		for(int b=3;b!=0;b--)
    		{
    			inv.setItem(slot+(b-1), new ItemStack(Material.AIR));
    		}
    	}
    	
    	inv.setItem(16, main.getUtil().CreateItemCustom("§0Crafter", Material.CRAFTING_TABLE, 1));
    	inv.setItem(18, main.getUtil().CreateItemCustom( "§eRecipes", Material.KNOWLEDGE_BOOK, 1));
    	
    	p.openInventory(inv);
	}
	
	private void RecipeGui(HumanEntity p)
	{
		Inventory inv = Bukkit.createInventory(null, 54, "§eRecipes");
		main.getUtil().setBorder(inv, new ItemStack(Material.ORANGE_STAINED_GLASS_PANE), inv.getSize());
		
		for(int i=0;i<crafts.size();i++)
    	{
    		CustomRecipe rec = crafts.get(i);
    		inv.addItem(rec.getCraftingResult());
    	}
		
		inv.setItem(4, new ItemStack(Material.CRAFTING_TABLE));
		util.fullInventory(inv, Material.GRAY_STAINED_GLASS_PANE);
		inv.setItem(49, main.getUtil().CreateItemCustom("§4RETOUR", Material.BARRIER, 1));
		
		p.closeInventory();
		p.openInventory(inv);
	}
	
	private ArrayList<ItemStack> getCraftingGridContent(Inventory inv)
	{
		ItemStack[] slots = inv.getStorageContents();
		ArrayList<ItemStack> current = new ArrayList<ItemStack>();
		
		int slot = -7;
    	
    	for(int i=3;i!=0;i--)
    	{
    		slot+=9;
    		for(int b=3;b!=0;b--)
    		{
    			current.add(slots[slot+(b-1)]);
    		}
    	}
    	
    	return current;
	}
}
