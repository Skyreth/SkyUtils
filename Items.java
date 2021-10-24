package fr.skyreth.skyutils;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.skyreth.skyutils.utils.Utils;

public class Items 
{
	private ArrayList<CustomItem> items = new ArrayList<CustomItem>();
	public CustomItem mob_collector, diamond_hammer, book_uncommun, book_lengendary, book_epic, dragon_skin, dragon_heart, netherite_anvil,
	wizard_table, netherite_hammer, smelting_book, fire_walker_book, magnet_book, replanted_book, fly_potion, phantom_spawn, mage_spawn,
	dragon_helmet, dragon_chestplate, dragon_wings, dragon_legging, dragon_boots, night_vision_book, mage_powder, phantom_drop;
	
	public void init(Main main)
	{
		Utils util = main.getUtil();
		
		this.dragon_skin = new CustomItem(util.CreateItemCustom("§5Dragon §r§0Skin", Material.NETHER_STAR, 1));
		this.dragon_heart = new CustomItem(util.CreateItemCustom("§5Dragon §r§cHeart", Material.RED_DYE, 1));
		this.mob_collector = new CustomItem(util.CreateItemCustom("§2Mob Collector", Material.STICK, 1));
		this.netherite_anvil = new CustomItem(util.CreateItemCustom("§5Netherite Anvil", Material.ANVIL, 1));
		this.wizard_table = new CustomItem(util.CreateItemCustom("§5Wizard Table", Material.NETHER_STAR, 1));
		this.netherite_hammer = new CustomItem(util.CreateItemCustom("§8Netherite Hammer", Material.NETHERITE_PICKAXE, 1));
		this.diamond_hammer = new CustomItem(util.CreateItemCustom("§bDiamond Hammer", Material.DIAMOND_PICKAXE, 1));
		this.smelting_book = new CustomItem(util.addCustomLoreToItem(new ItemStack(Material.ENCHANTED_BOOK), "§6Enchanted Book", "§7Smelting I"));
		this.fire_walker_book = new CustomItem(util.addCustomLoreToItem(new ItemStack(Material.ENCHANTED_BOOK), "§6Enchanted Book", "§7Fire Walker I"));
		this.magnet_book = new CustomItem(util.addCustomLoreToItem(new ItemStack(Material.ENCHANTED_BOOK), "§6Enchanted Book", "§7Magnet I"));
		this.replanted_book = new CustomItem(util.addCustomLoreToItem(new ItemStack(Material.ENCHANTED_BOOK), "§6Enchanted Book", "§7Replanted I"));
		this.fly_potion = new CustomItem(util.addCustomLoreToItem(new ItemStack(Material.GLASS_BOTTLE), "§bfly potion", "§7Duree: 5min"));
		this.phantom_spawn = new CustomItem(util.CreateItemCustom("§bOeuf de phantom", Material.PHANTOM_SPAWN_EGG, 1));
		this.mage_spawn = new CustomItem(util.CreateItemCustom("§1Oeuf de mage", Material.WANDERING_TRADER_SPAWN_EGG, 1));
		this.dragon_helmet = new CustomItem(util.addCustomLoreToItem(new ItemStack(Material.NETHERITE_HELMET, 1), "§5Dragon Helmet", "§c+ ❤❤"));
		this.dragon_chestplate = new CustomItem(util.addCustomLoreToItem(new ItemStack(Material.NETHERITE_CHESTPLATE, 1), "§5Dragon ChestPlate", "§c+ ❤❤"));
		this.dragon_legging = new CustomItem(util.addCustomLoreToItem(new ItemStack(Material.NETHERITE_LEGGINGS, 1), "§5Dragon Leggings", "§c+ ❤❤"));
		this.dragon_boots = new CustomItem(util.addCustomLoreToItem(new ItemStack(Material.NETHERITE_BOOTS, 1), "§5Dragon Boots", "§c+ ❤❤"));
		this.dragon_wings = new CustomItem(util.addCustomLoreToItem(new ItemStack(Material.ELYTRA, 1), "§5Dragon Wings", "§c+ ❤❤"));
		this.night_vision_book = new CustomItem(util.addCustomLoreToItem(new ItemStack(Material.ENCHANTED_BOOK), "§6Enchanted Book", "§7Night Vision"));
		this.mage_powder = new CustomItem(util.CreateItemCustom("§ePoudre de Mage", Material.GLOWSTONE_DUST, 1));
		this.phantom_drop = new CustomItem(util.CreateItemCustom("§3Ecaille de fantome", Material.PHANTOM_MEMBRANE, 1));
		this.book_epic = new CustomItem(util.addCustomLoreToItem(new ItemStack(Material.ENCHANTED_BOOK), "§5Boss Book", "§7Type:§r"+ChatColor.LIGHT_PURPLE+" Epic"));
		this.book_uncommun = new CustomItem(util.addCustomLoreToItem(new ItemStack(Material.ENCHANTED_BOOK), "§5Boss Book", "§7Type:§r"+ChatColor.DARK_GREEN+" Uncommun"));
		
		this.register();
	}
	
	public void register()
	{
		this.dragon_skin.registerItem(items, "dragon_skin");
		this.netherite_anvil.registerItem(items, "netherite_anvil");
		this.mob_collector.registerItem(items, "mob_collector");
		this.dragon_heart.registerItem(items, "dragon_heart");
		this.wizard_table.registerItem(items, "wizard_table");
		this.netherite_hammer.registerItem(items, "netherite_hammer");
		this.diamond_hammer.registerItem(items, "diamond_hammer");
		this.phantom_spawn.registerItem(items, "phantom_spawn_egg");
		this.smelting_book.registerItem(items, "smelting_book");
		this.fire_walker_book.registerItem(items, "fire_walker_book");
		this.magnet_book.registerItem(items, "magnet_book");
		this.replanted_book.registerItem(items, "replanted_book");
		this.night_vision_book.registerItem(items, "night_vision_book");
		this.fly_potion.registerItem(items, "fly_potion");
		this.mage_spawn.registerItem(items, "mage_spawn_egg");
		this.dragon_helmet.registerItem(items, "dragon_helmet");
		this.dragon_chestplate.registerItem(items, "dragon_chestplate");
		this.dragon_wings.registerItem(items, "dragon_wings");
		this.dragon_legging.registerItem(items, "dragon_legging");
		this.dragon_boots.registerItem(items, "dragon_boots");
		this.mage_powder.registerItem(items, "mage_powder");
		this.phantom_drop.registerItem(items, "phantom_shell");
		this.book_epic.registerItem(items, "epic_book");
		this.book_uncommun.registerItem(items, "uncommun_book");
	}
	
	public ArrayList<ItemStack> getItems() {
		ArrayList<ItemStack> cit = new ArrayList<ItemStack>();
		
		for(CustomItem it : items) {
			cit.add(it.getItem());
		}
		
		return cit;
	}
	
	public ArrayList<String> getUnlocalizedNames()
	{
		ArrayList<String> names = new ArrayList<String>();
		
		for(CustomItem it : getItemsCustom()) {
			names.add(it.getUnlocalizedName());
		}
		
		return names;
	}
	
	public CustomItem getCustomItemByName(String name)
	{
		for(CustomItem cur : items) {
			if(cur.getUnlocalizedName().equals(name)) {
				return cur;
			}
		}
		
		return null;
	}
	
	public ArrayList<CustomItem> getItemsCustom() {
		return items;
	}
}
