package fr.skyreth.skyutils.misc;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class CustomRecipe 
{
	private ItemStack result;
	private ArrayList<ItemStack> craft = new ArrayList<ItemStack>();
	
	public CustomRecipe(ItemStack result)
	{
		this.result = result;
	}
	
	public void setShape(ItemStack[] craft)
	{
		for(int i=0;i<craft.length;i++)
		{
			this.craft.add(craft[i]);
		}
	}
	
	public boolean isValidCraft(ArrayList<ItemStack> list)
	{
		for(int i=0;i!=list.size();i++)
		{
			if(craft.get(i) != null)
			{
				if(list.get(i)!=null)
				{
					if(!isSimilar(craft.get(i), list.get(i))) 
					{
						return false;
					}
				}
				else
				{
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean isSimilar(ItemStack perfect,ItemStack second){

        boolean similar = false;

        if(perfect == null || second == null){
            return similar;
        }

        boolean sameTypeId = (perfect.getType() == second.getType());
        boolean sameAmount = (perfect.getAmount() <= second.getAmount());
        boolean sameHasItemMeta = (perfect.hasItemMeta() == second.hasItemMeta());
        boolean sameEnchantments = (perfect.getEnchantments().equals(second.getEnchantments()));
        boolean hasNames = perfect.getItemMeta().hasDisplayName();
        boolean sameItemMeta = true;
        boolean sameName = true;

        if(sameHasItemMeta) {
            sameItemMeta = Bukkit.getItemFactory().equals(perfect.getItemMeta(), second.getItemMeta());
            
            if(hasNames)
            sameName = (perfect.getItemMeta().getDisplayName().equals(second.getItemMeta().getDisplayName()));
        }

        if(sameTypeId && sameAmount && sameHasItemMeta && sameEnchantments && sameItemMeta && sameName){
            similar = true;
        }

        return similar;

    }
	
	public ItemStack getCraftingResult()
	{
		return result;
	}
	
	public ArrayList<ItemStack> getCraft()
	{
		return craft;
	}
}
