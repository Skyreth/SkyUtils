package fr.skyreth.skyutils.enchant;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.skyreth.skyutils.Main;

public class EnchantCreator
{
	private String name;
	private int xp, maxlvl;
	private Tool type;
	
	public EnchantCreator(String name, int xp, int maxlvl, Tool type)
	{
		this.name = name;
		this.type = type;
		this.xp = xp;
	}

	public int getMaxlvl() {
		return maxlvl;
	}

	public String getName() {
		return ChatColor.translateAlternateColorCodes('§', "§7"+name);
	}
	
	public String getOriginalName() {
		return name;
	}

	public int getXp() {
		return xp;
	}
	
	public Tool getTool() {
		return type;
	}
	
	
	public void openMenu(Player p, EnchantCreator c, Main main) {
		Inventory inv = Bukkit.createInventory(null, 54, "§0Enchant GUI-"+c.getName());
		int slot = 0;
		
		
		if(c.getTool().equals(Tool.ARMOR))
		{
			slot = 13;
			ItemStack[] is = p.getInventory().getArmorContents();
			
			for(int i=0;i!=is.length;i++)
			{
				if(is[i] != null)
					inv.setItem(slot, is[i]);
				else
					inv.setItem(slot, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1));
				
				slot+=9;
			}
		}
		else if(c.getTool().equals(Tool.TOOLS) || c.getTool().equals(Tool.ALL) || c.getTool().equals(Tool.SWORD) || c.getTool().equals(Tool.BOW) || c.getTool().equals(Tool.HOE) || c.getTool().equals(Tool.PICKAXE))
		{
			slot = 9;
			ItemStack[] is = p.getInventory().getStorageContents();
			
			for(int i=0;i!=is.length;i++) {
				if(is[i] != null)
					inv.setItem(slot, p.getInventory().getItem(i));
				else
					inv.setItem(slot, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1));
				
				slot++;
			}
		}
			
		main.getUtil().fullInventory(inv, Material.PURPLE_STAINED_GLASS_PANE);
		
		p.openInventory(inv);
	}
}
