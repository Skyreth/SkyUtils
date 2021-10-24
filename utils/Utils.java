package fr.skyreth.skyutils.utils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import fr.skyreth.skyutils.enchant.EnchantCreator;
import fr.skyreth.skyutils.enchant.Tool;
import net.minecraft.world.item.ItemArmor;
import net.minecraft.world.item.ItemAxe;
import net.minecraft.world.item.ItemHoe;
import net.minecraft.world.item.ItemPickaxe;
import net.minecraft.world.item.ItemSword;
import net.minecraft.world.item.ItemTool;

public class Utils 
{
	public void spawnText(Location loc, String text)
	{
		ArmorStand text1 = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		text1.setVisible(false);
		text1.setGravity(false);
		text1.setCustomName(text);
		text1.setCollidable(false);
		text1.setRemoveWhenFarAway(false);
		text1.setInvulnerable(true);
		text1.setTicksLived(3);
		text1.setCustomNameVisible(true);
		text1.setCanPickupItems(false);
	}
	
	public ItemStack SmeltingEffect(ItemStack c, ItemStack it, Player p)
	{
		ItemStack result = it;
		
		if(hasCustomEnchant(c, "Smelting I"))
		{
			if(getSmeltingResult(it) != null)
			{
				result = getSmeltingResult(it);
				
				if(c.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
					result.setAmount(generateRandomNumber(c.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS), 1));
				}
			}
		}
		if(hasCustomEnchant(c, "Magnet I") && !HasInventoryFull(p))
		{
			p.getInventory().addItem(result);
			return null;
		}
		
		return result;
	}
	
	public boolean hasCustomEnchant(ItemStack it, String ec)
	{
		return (it != null && it.hasItemMeta() && it.getItemMeta().hasLore() && it.getItemMeta().getLore().contains("§7"+ec));
	}
	
	public boolean isAlreadyEnchanted(ItemStack it, EnchantCreator ec)
	{
		if(it != null)
		{
			if(it.hasItemMeta())
			{
				if(it.getItemMeta().hasLore())
					return (it.getItemMeta().getLore().contains(ec.getName()+" I"));
				else
					return false;
			}
			else
				return false;
		}
		
		return true;
	}
	
	public boolean hisPlayerOnline(Player sender, String name)
	{
		Player target = Bukkit.getPlayerExact(name);
		
		if(target == null) 
		{
			sender.sendMessage("Le joueur "+name+" n est pas en ligne !");
			return false;
		}
		else
			return true;
	}
	
	public ItemMeta DamageItem(ItemStack it, int damage)
	{
		int unbreaking = 1;
		Damageable meta = (Damageable) it.getItemMeta();
			
		if(it.containsEnchantment(Enchantment.DURABILITY))
			unbreaking = it.getEnchantmentLevel(Enchantment.DURABILITY);
			
		if(damage <= meta.getDamage())
		{
			meta.setDamage(meta.getDamage() + (6 / unbreaking));
			return meta;
		}
		else
			return null;
	}
	
	public Player getPlayerByName(Player sender, String name)
	{
		if(hisPlayerOnline(sender, name)) return Bukkit.getPlayerExact(name);
		
		return null;
	}
	
	public void setBorder(Inventory inv, ItemStack border, int size)
	{
		for(int i=0;i<9;i++)
		{
    		if(inv.getItem(i) == null)
    			inv.setItem(i, border);
		}
		
		for(int i=(size-9);i<size;i++)
		{
    		if(inv.getItem(i) == null)
    			inv.setItem(i, border);
		}
		
		for(int i=9;i<size-9;i+=9)
		{
    		if(inv.getItem(i) == null)
	    	{
    			inv.setItem(i, border);
    			if(inv.getItem(i+8) == null)
    	    	{
    				inv.setItem(i+8, border);
    	    	}
    		}
		}
	}
	
	public boolean HasInventoryFull(Player p)
	{    
		return p.getInventory().firstEmpty() == -1;           
	}
	
	public ItemMeta addLore(ItemStack item, String name) 
	{
		ArrayList<String> newLores = new ArrayList<String>();
		
		if (item.hasItemMeta()) {
		    if (item.getItemMeta().hasLore()) {
		        newLores.addAll(item.getItemMeta().getLore());
		    }
		}
		
		newLores.add(ChatColor.translateAlternateColorCodes('&', name));
		
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setLore(newLores);
		
		return itemMeta;
	}
	
	public ItemStack getSmeltingResult(ItemStack item)
	{
		Iterator<Recipe> iter = Bukkit.recipeIterator();
		
		while (iter.hasNext()) {
		   Recipe recipe = iter.next();
		   if (!(recipe instanceof FurnaceRecipe)) continue;
		   if (((FurnaceRecipe) recipe).getInput().getType() != item.getType()) continue;
		   return recipe.getResult();
		}
		
		return null;
	}
	
	public Boolean isType(ItemStack item, Tool type)
	{
		switch(type)
		{
			case ARMOR:
				return (CraftItemStack.asNMSCopy(item).getItem() instanceof ItemArmor);
			case ALL:
				return (item.getType() == Material.BOW || CraftItemStack.asNMSCopy(item).getItem() instanceof ItemSword || CraftItemStack.asNMSCopy(item).getItem() instanceof ItemAxe || CraftItemStack.asNMSCopy(item).getItem() instanceof ItemHoe || CraftItemStack.asNMSCopy(item).getItem() instanceof ItemTool);
			case SWORD:
				return (CraftItemStack.asNMSCopy(item).getItem() instanceof ItemSword);
			case TOOLS:
				return (CraftItemStack.asNMSCopy(item).getItem() instanceof ItemAxe || CraftItemStack.asNMSCopy(item).getItem() instanceof ItemHoe || CraftItemStack.asNMSCopy(item).getItem() instanceof ItemTool);
			case TOOL:
				return (CraftItemStack.asNMSCopy(item).getItem() instanceof ItemTool);
			case PICKAXE:
				return (CraftItemStack.asNMSCopy(item).getItem() instanceof ItemPickaxe);
			case HOE:
				return (CraftItemStack.asNMSCopy(item).getItem() instanceof ItemHoe);
		default:
			return false;
		}
	}
	
	public boolean CheckItems(Player player, ItemStack costStack)
    {
        int cost = costStack.getAmount();
        boolean hasEnough=false;
        for (ItemStack invStack : player.getInventory().getContents())
        {
            if(invStack == null)
                continue;
            if (invStack.getType() == costStack.getType()) {

                int inv = invStack.getAmount();
                if (cost - inv >= 0) {
                    cost = cost - inv;
                } else {
                    hasEnough=true;
                    break;
                }
            }
        }
        return hasEnough;
    }
	
    public boolean ConsumeItems(Player player, ItemStack costStack)
    {
        if (!CheckItems(player,costStack)) return false;

        for (ItemStack invStack : player.getInventory().getContents())
        {
            if(invStack == null)
                continue;

            if (invStack.getType() == costStack.getType()) {
                int inv = invStack.getAmount();
                int cost = costStack.getAmount();
                if (cost - inv >= 0) {
                    costStack.setAmount(cost - inv);
                    player.getInventory().remove(invStack);
                } else {
                    costStack.setAmount(0);
                    invStack.setAmount(inv - cost);
                    break;
                }
            }
        }
        return true;
    }
	
	public int generateRandomNumber(int max, int min)
	{
		int al = min + (int)(Math.random() * ((max - min) + min));
		return al;
	}
	
	public void fullInventory(Inventory inv, Material mat)
	{
		for(int i=0;i<inv.getSize();i++)
    	{
    		if(inv.getItem(i) == null)
    		{
    			ItemStack air = new ItemStack(mat, 1);
    	    	ItemMeta name = air.getItemMeta();
    	    	name.setDisplayName("§f§kBonjo");
    	    	air.setItemMeta(name);
    			inv.setItem(i, air);
    		}
    	}
	}
	
	public ItemStack addEnchantToItem(ItemStack stack, Enchantment[] enchant, int[] level)
	{    
		ItemMeta meta = stack.getItemMeta();
		
		for(int i=0;i<enchant.length;i++)
		{
			meta.addEnchant(enchant[i], level[i], true);
		}
		stack.setItemMeta(meta);
		return stack;
	}
	
	public ItemStack addCustomLoreToItem(ItemStack stack, String name, String string) {    
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(string);
		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public ItemStack CreateItemCustom(String name, Material mat, int amount) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta data = item.getItemMeta();
		data.setDisplayName(name);
		item.setItemMeta(data);
		return item;
	}
	
	public Boolean checkCustomItem(ItemStack item, String Name) {
		return (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(Name));
	}
	
	public String getHeart(Player p)
	{
		String result ="";
		for(int i=((int) p.getHealth()); i!=0;i--) {
			result+=(ChatColor.RED+"❤️");
		}
		
		return result;
	}

	public void HammerEffect(Block b, Player p, Location loc, World world, ItemStack it)
	{
		for (int x = loc.getBlockX() - 1; x <= loc.getBlockX() + 1; x++) {
            for (int y = loc.getBlockY() - 1; y <= loc.getBlockY() + 1; y++) {
              for (int z = loc.getBlockZ() - 1; z <= loc.getBlockZ() + 1; z++){ 
            	  Block bc = world.getBlockAt(x,y,z);
					if(!EnumSet.of(Material.OBSIDIAN, Material.CRYING_OBSIDIAN, Material.BEDROCK, Material.END_PORTAL_FRAME, Material.END_PORTAL, Material.NETHER_PORTAL).contains(bc.getType())) {
						for(ItemStack drop : bc.getDrops()) {
							ItemStack c = SmeltingEffect(it, drop, p);
							if(c != null) {
								world.dropItemNaturally(loc, c);
							}
						}
						
						bc.setType(Material.AIR);
					}
				}
			}
		}
		p.updateInventory();
	}
}
