package fr.skyreth.skyutils.enchant;

import java.util.ArrayList;

public class Enchants
{
	EnchantCreator smelting = new EnchantCreator("Smelting", 60, 1, Tool.PICKAXE);
	EnchantCreator night = new EnchantCreator("Night Vision", 47, 1, Tool.ARMOR);
	EnchantCreator lava = new EnchantCreator("Fire Walker", 35, 1, Tool.ARMOR);
	EnchantCreator magnet = new EnchantCreator("Magnet", 40, 1, Tool.ALL);
	EnchantCreator light = new EnchantCreator("Lightning", 50, 1, Tool.SWORD);
	EnchantCreator replant = new EnchantCreator("Replanted", 50, 1, Tool.HOE);
	
	EnchantCreator[] enchants = new EnchantCreator[] {smelting, replant, night, lava, magnet, light};
	
	public Boolean isEnchant(String name)
	{		
		for(int i=0;i<enchants.length;i++) {
			if(enchants[i].getName().equals(name)) return true;
		}
		
		return false;
	}
	
	public EnchantCreator getEnchantByNames(String name) {		
		for(int i=0;i<enchants.length;i++) {
			String enc = enchants[i].getName().replaceAll(" ", "");
			String ec = name.replaceAll("I", "");
			String fin = ec.replaceAll(" ", "");
			
			if(enc.equalsIgnoreCase(fin)) {
				return enchants[i];
			}
		}
		
		return null;
	}
	
	public ArrayList<String> getEnchantsNames() {
		ArrayList<String> names = new ArrayList<String>();
		
		for(int i=0;i<enchants.length;i++) {
			names.add(enchants[i].getName());
		}
		
		return names;
	}
}
