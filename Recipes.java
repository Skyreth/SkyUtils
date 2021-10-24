package fr.skyreth.skyutils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Recipes 
{
	public static void registerCraft(Server serv, Main main)
	{
		ShapedRecipe mc = new ShapedRecipe(new NamespacedKey(main, "mobcollector"), main.getItems().mob_collector.getItem());
		ShapedRecipe na = new ShapedRecipe(new NamespacedKey(main, "netheriteanvil"), main.getItems().netherite_anvil.getItem());
		ShapedRecipe cc = new ShapedRecipe(new NamespacedKey(main, "wizardtable"), main.getItems().wizard_table.getItem());
		ShapedRecipe np = new ShapedRecipe(new NamespacedKey(main, "netherite_hammer"), main.getItems().netherite_hammer.getItem());
		ShapedRecipe dp = new ShapedRecipe(new NamespacedKey(main, "diamond_hammer"), main.getItems().diamond_hammer.getItem());
		ShapedRecipe pnj = new ShapedRecipe(new NamespacedKey(main, "villagers"), new ItemStack(Material.VILLAGER_SPAWN_EGG, 1));
		FurnaceRecipe leather = new FurnaceRecipe(new NamespacedKey(main, "cuirrotten"), new ItemStack(Material.LEATHER, 1), Material.ROTTEN_FLESH, 0f, 40);	
		
		cc.shape("ENE","DBD","OOO");
		cc.setIngredient('B', Material.ENCHANTING_TABLE);
		cc.setIngredient('D', Material.DIAMOND);
		cc.setIngredient('N', Material.NETHER_STAR);
		cc.setIngredient('E', Material.EMERALD);
		cc.setIngredient('O', Material.OBSIDIAN);
		
		na.shape("BBB"," I "," I ");
		na.setIngredient('B', Material.NETHERITE_BLOCK);
		na.setIngredient('I', Material.NETHERITE_INGOT);
		
		np.shape("NNN"," P "," S ");
		np.setIngredient('N', Material.NETHERITE_BLOCK);
		np.setIngredient('P', Material.NETHERITE_PICKAXE);
		np.setIngredient('S', Material.STICK);
		
		dp.shape("NNN"," P "," S ");
		dp.setIngredient('N', Material.DIAMOND_BLOCK);
		dp.setIngredient('P', Material.DIAMOND_PICKAXE);
		dp.setIngredient('S', Material.STICK);
		
		pnj.shape(" E ","EGE"," E ");
		pnj.setIngredient('E', Material.EMERALD);
		pnj.setIngredient('G', Material.EGG);
		
		mc.shape("III","IEI","ISI");
		mc.setIngredient('I', Material.IRON_BARS);
		mc.setIngredient('S', Material.STICK);
		mc.setIngredient('E', Material.EMERALD);
		
		serv.addRecipe(na);
		serv.addRecipe(leather);
		serv.addRecipe(pnj);
		serv.addRecipe(cc);
		serv.addRecipe(mc);
		serv.addRecipe(np);
		serv.addRecipe(dp);
	}
}
