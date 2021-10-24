package fr.skyreth.skyutils.mobs;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.skyreth.skyutils.Main;

public class CustomMobRegistry implements Listener
{
	private CustomMob zombie, creeper, skeleton, wizard, mage, fantome, enderman;
	private ArrayList<CustomMob> mobs = new ArrayList<CustomMob>();
	private Main main;
	
	public CustomMobRegistry(Main main)
	{
		this.main = main;
		this.init();
	} 
	
	public void init()
	{
		mage = new CustomMob("§1Mage", 100.0, 10, EntityType.VEX, null, null, new Drops[] {new Drops(main.getUtil().CreateItemCustom("§ePoudre de Mage", Material.GLOWSTONE_DUST, 1),1,1,100)}, null, true);
		fantome = new CustomMob("§fFantome", 100.0, 10, EntityType.PHANTOM, null, null, new Drops[] {new Drops(main.getUtil().CreateItemCustom("§3Ecaille de fantome", Material.PHANTOM_MEMBRANE, 1),1,1,100)}, null, true);
		enderman = new CustomMob("§aExperimented §5Enderman", 80.0, 0, EntityType.ENDERMAN, null, null, new Drops[] {new Drops(new ItemStack(Material.EXPERIENCE_BOTTLE), 32, 64, 100)}, null, false);
		zombie = new CustomMob("§2Zombie Boss", 200.0, 1, EntityType.ZOMBIE, 
		new EquipmentSlot[] {
			EquipmentSlot.HAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET 
		}, 
		new ItemStack[] {
			new ItemStack(Material.NETHERITE_SWORD), new ItemStack(Material.NETHERITE_HELMET), new ItemStack(Material.NETHERITE_CHESTPLATE), new ItemStack(Material.NETHERITE_LEGGINGS), new ItemStack(Material.NETHERITE_BOOTS)
		}, 
		new Drops[] {
			new Drops(main.getUtil().addCustomLoreToItem(new ItemStack(Material.ENCHANTED_BOOK, 1),"§5Boss Book", "§7Type:§r"+ChatColor.LIGHT_PURPLE+" Epic"), 1, 1, 100)
		}, 
		new PotionEffect[] {
			new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 10000, 15), new PotionEffect(PotionEffectType.SPEED, 10000, 5)			
		}, true);
		
		creeper = new CustomMob("§2Creeper Boss", 50.0, 1, EntityType.CREEPER, null, null, 
		new Drops[] {
			new Drops(main.getUtil().addCustomLoreToItem(new ItemStack(Material.ENCHANTED_BOOK), "§6Enchanted Book", "§7Lightning"), 1, 1, 100)
		}, null, true);
		
		skeleton = new CustomMob("§fSkeleton Compressed", 100.0, 1, EntityType.WITHER_SKELETON,
		new EquipmentSlot[] {
			EquipmentSlot.HAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET 
		}, 
		new ItemStack[] {
			new ItemStack(Material.NETHERITE_SWORD), new ItemStack(Material.DIAMOND_HELMET), new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_BOOTS)
		},
		new Drops[] {
				new Drops(main.getUtil().addCustomLoreToItem(new ItemStack(Material.ENCHANTED_BOOK), "§5Boss Book", "§7Type:§r"+ChatColor.DARK_GREEN+" Uncommun"), 1, 1, 100)
		}, 
		new PotionEffect[] {
				new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 10000, 15)		
		}, true);
		
		wizard = new CustomMob("§1Wizard Boss", 15000.0, 1, EntityType.VINDICATOR,
		new EquipmentSlot[] {
				EquipmentSlot.HAND
		}, 
		new ItemStack[] {
				main.getUtil().addEnchantToItem(new ItemStack(Material.STICK), new Enchantment[] {Enchantment.DAMAGE_ALL}, new int[10])
		},
		new Drops[] {
				new Drops(main.getUtil().addCustomLoreToItem(new ItemStack(Material.ENCHANTED_BOOK, 1),"§5Boss Book", "§7Type:§r"+ChatColor.GOLD+" Legendary"), 1, 1, 100)
		}, 
		new PotionEffect[] {
				new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 10000, 15)		
		}, true);
		
		mage.setUndefName("mage");
		creeper.setUndefName("creeper_boss");
		zombie.setUndefName("zombie_boss");
		wizard.setUndefName("wizard_boss");
		fantome.setUndefName("fantome");
		enderman.setUndefName("enderman_experienced");
		skeleton.setUndefName("skeleton_boss");
		
		
		mobs.add(creeper);
		mobs.add(zombie);
		mobs.add(fantome);
		mobs.add(mage);
		mobs.add(wizard);
		mobs.add(enderman);
		mobs.add(skeleton);
	}
	
	public ArrayList<CustomMob> getMobs() {
		return mobs;
	}
	
	public ArrayList<String> getMobsNames()
	{
		ArrayList<String> names = new ArrayList<String>();
		
		for(CustomMob mob : mobs)
			names.add(mob.getName());
		
		return names;
	}
	
	public ArrayList<String> getMobsUnlocalizedNames()
	{
		ArrayList<String> names = new ArrayList<String>();
		
		for(CustomMob mob : mobs)
			names.add(mob.getUndefName());
		
		return names;
	}
	
	public CustomMob getMob(String name)
	{
		for(CustomMob mob : mobs)
		{
			if(mob.getUndefName().equalsIgnoreCase(name)) {
				return mob;
			}
		}
		
		return null;
	}
	
	@EventHandler
	private void onMobSpawn(EntitySpawnEvent e)
	{
		switch(e.getEntityType())
		{
			case ENDER_DRAGON:
				EnderDragon ent = (EnderDragon) e.getEntity();
				int rand = main.getUtil().generateRandomNumber(5, 1);
				
				switch(rand)
				{
					case 1:
						ent.setCustomNameVisible(true);
						e.getEntity().setCustomName("§5Dragon §2Uncommun");
						ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(800);
						ent.setHealth(ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
						break;
					case 2:
						ent.setCustomNameVisible(true);
						e.getEntity().setCustomName("§5Dragon §6Legendary");
						ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(3200);
						ent.setHealth(ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
						break;
					case 3:
						ent.setCustomNameVisible(true);
						e.getEntity().setCustomName("§5Dragon Epic");
						ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1600);
						ent.setHealth(ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
						break;
				}
				break;
		   case ENDERMAN:
				Enderman ent1 = (Enderman) e.getEntity();
				int rand1 = main.getUtil().generateRandomNumber(800, 1);
				
				if(rand1 == 752)
				{
					ent1.setCustomNameVisible(true);
					ent1.setCustomName("§aExperimented §5Enderman");ent1.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(80);
					ent1.setHealth(ent1.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
				}
			break;
		default:
			break;
		}
	}
	
	@EventHandler
	private void onMobBossDeath(EntityDeathEvent e)
	{
		if(e.getEntity().getKiller() instanceof Player)
		{
			Player p = (Player) e.getEntity().getKiller();
			EntityType type = e.getEntityType();
			LivingEntity ent = e.getEntity();
			
			if(!p.getWorld().getName().equals("Minage"))
			{
				switch(type)
				{
					case SKELETON:
						if(skeleton.getName().equals(ent.getName()))
						{
							if(main.getUtil().generateRandomNumber(250, 1) == 240) {
								skeleton.SpawnEntity(ent.getLocation());
								skeleton.setForceFollow(8, p);
							}
						}
						break;
					case ZOMBIE:
						if(zombie.getName().equals(ent.getName()))
						{
							if(main.getUtil().generateRandomNumber(800, 1) == 450) {
								zombie.SpawnEntity(ent.getLocation());
								zombie.setForceFollow(8, p);
							}
						}
						break;
					case CREEPER:
						if(creeper.getName().equals(ent.getName()))
						{
							if(main.getUtil().generateRandomNumber(800, 1) == 250) {
								creeper.SpawnEntity(ent.getLocation());
								creeper.setForceFollow(10, p);
							}
						}
						break;
					case WITCH:
						if(wizard.getName().equals(ent.getName()))
						{
							if(main.getUtil().generateRandomNumber(10000, 1) == 250)
							{
								wizard.SpawnEntity(ent.getLocation());
								wizard.setForceFollow(10, p);
							}
						}
						break;
				default:
					break;
				}
			}
		}
	}
	
	@EventHandler
	private void onMobDeath(EntityDeathEvent e)
	{
		if(e.getEntity().getKiller() instanceof Player)
		{
			Player p = (Player) e.getEntity().getKiller();
			EntityType type = e.getEntityType();
			LivingEntity ent = e.getEntity();
			
			if(!p.getWorld().getName().equals("Minage"))
			{
				switch(type)
				{
					case ENDERMAN:
						if(ent.getCustomName() != null && ent.getCustomName().equals(enderman.getName()))
						{
							e.setDroppedExp(e.getDroppedExp()*8);
							e.getDrops().clear();
					
							if(enderman.getDrops() != null)
								for(Drops dp : enderman.getDrops())
									e.getDrops().add(dp.getItem());
						}
					case VEX:
						if(ent.getCustomName() != null && ent.getCustomName().equals(mage.getName()))
						{
							e.getDrops().clear();
							
							if(mage.getDrops() != null)
								for(Drops dp : mage.getDrops())
									e.getDrops().add(dp.getItem());
						}
					case PHANTOM:
						if(ent.getCustomName() != null && ent.getCustomName().equals(fantome.getName()))
						{
							e.getDrops().clear();
							
							if(fantome.getDrops() != null)
								for(Drops dp : fantome.getDrops())
									e.getDrops().add(dp.getItem());
						}
					case ENDER_DRAGON:
						if(ent.getCustomName() != null)
						{
							if(ent.getCustomName() != null && ent.getCustomName().equals("§5Dragon §2Uncommun")) {
								e.getDrops().add(main.getItems().dragon_skin.getItem(main.getUtil().generateRandomNumber(3, 1)));
								e.setDroppedExp(e.getDroppedExp()*2);
								
								if(main.getUtil().generateRandomNumber(8, 1) == 3)
									e.getDrops().add(main.getItems().dragon_heart.getItem());
							}
							else if(ent.getCustomName() != null && ent.getCustomName().equals("§5Dragon Epic")) {
								e.getDrops().add(main.getItems().dragon_skin.getItem(main.getUtil().generateRandomNumber(5, 2)));
								e.setDroppedExp(e.getDroppedExp()*3);
								
								if(main.getUtil().generateRandomNumber(4, 1) == 1)
									e.getDrops().add(main.getItems().dragon_heart.getItem());
							}
							else if(ent.getCustomName() != null && ent.getCustomName().equals("§5Dragon §6Legendary")) {
								e.getDrops().add(main.getItems().dragon_skin.getItem(main.getUtil().generateRandomNumber(7, 4)));
								e.setDroppedExp(e.getDroppedExp()*4);
								
								if(main.getUtil().generateRandomNumber(2, 1) == 1)
									e.getDrops().add(main.getItems().dragon_heart.getItem(main.getUtil().generateRandomNumber(2, 1)));
							}
						}
					case ZOMBIE:
						if(ent.getCustomName() != null && ent.getCustomName().equals(zombie.getName()))
						{
							e.getDrops().clear();
							
							if(zombie.getDrops() != null) {
								for(Drops dp : zombie.getDrops())
									e.getDrops().add(dp.getItem());
							}
						}
					case WITHER_SKELETON:
						if(ent.getCustomName() != null && ent.getCustomName().equals(skeleton.getName()))
						{
							e.getDrops().clear();
							
							for(int i=8;i!=0;i--) {
								ent.getWorld().spawnEntity(ent.getLocation(), ent.getType());
							}
							
							if(skeleton.getDrops() != null)
								for(Drops dp : skeleton.getDrops())
									e.getDrops().add(dp.getItem());
						}
					case CREEPER:
						if(ent.getCustomName() != null && ent.getCustomName().equals(creeper.getName()))
						{
							e.getDrops().clear();
							
							for(int i=5;i!=0;i--) {
								ent.getWorld().spawnEntity(ent.getLocation(), ent.getType());
							}
							
							if(creeper.getDrops() != null)
								for(Drops dp : creeper.getDrops())
									e.getDrops().add(dp.getItem());
						}
				default:
					break;
				}
			}
		}
	}
}
