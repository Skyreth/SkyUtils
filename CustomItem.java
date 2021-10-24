package fr.skyreth.skyutils;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class CustomItem 
{
	private String name;
	private ItemStack it;
	
	public CustomItem(ItemStack stack) {
		this.it = stack;
	}
	
	public void registerItem(ArrayList<CustomItem> items, String unlocalized) {
		items.add(this);
		this.name = unlocalized;
	}

	public String getUnlocalizedName() {
		return name;
	}

	public ItemStack getItem() {
		return it;
	}
	
	public ItemStack getItem(int amount) {
		ItemStack current = this.it.clone();
		current.setAmount(amount);
		return current;
	}
}
