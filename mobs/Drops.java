package fr.skyreth.skyutils.mobs;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Drops
{
	private ItemStack item;
	private int min, max, chance, prob;

	public Drops(ItemStack it, int min, int max, int prob) { 
		this.item = it;
		this.min = min;
		this.max = max;
		this.prob = prob;
	}
	
	public ItemStack getItemWithoutProb() {
		return this.item;
	}
	
	public ItemStack getItem() 
	{
		int rand = generateRandomNumber(0,100);
		if(rand <= prob) {
			ItemStack it = this.item;
			it.setAmount(generateRandomNumber(min, max));
			return it;
		}
		
		return new ItemStack(Material.AIR);
	}
	
	private int generateRandomNumber(int ma, int mi) {
		int al = mi + (int)(Math.random() * ((ma - mi) + mi));
		return al;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}
}
