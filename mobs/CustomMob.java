package fr.skyreth.skyutils.mobs;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class CustomMob 
{
	private String name, undefname;
	private double health;
	private int range, xp;
	private Drops[] drops;
	private ItemStack[] inv;
	private EquipmentSlot[] equip;
	private EntityType type;
	private PotionEffect[] effects;
	private boolean follow, forcef;
	private Player target;
	private CustomMob mob;
	
	public CustomMob(String name, double health, int xp, EntityType type, EquipmentSlot[] equip, ItemStack[] inv, Drops[] drops, PotionEffect[] effects, boolean follow)
	{
		this.name = name;
		this.type = type;
		this.drops = drops;
		this.equip = equip;
		this.health = health;
		this.inv = inv;
		this.effects = effects;
		this.follow = follow;
		this.xp = xp;
		this.forcef = false;
	}
	
	public void setUndefName(String undef) {
		this.undefname = undef;
	}
	
	public EntityType getType() {
		return type;
	}

	public void setSpawnMob(CustomMob ent) {
		this.mob = ent;
	}
	
	public boolean hasSpawnMob() {
		return !(mob == null);
	}
	
	public CustomMob getSpawnMob() {
		return mob;
	}
	
	public boolean isForceFollowing() {
		return forcef;
	}
	
	public String getUndefName() {
		return undefname;
	}
	
	public void setForceFollow(int range, Player p) {
		this.target = p;
		this.forcef = !isForceFollowing();
		this.range = range;
	}
	
	public Player getTarget() {
		return target;
	}
	
	public int getDropeedXp() {
		return xp;
	}
	
	public Drops[] getDrops() {
		return drops;
	}
	
	public String getName() {
		return name;
	}

	public ItemStack[] getInv() {
		return inv;
	}

	public EquipmentSlot[] getEquip() {
		return equip;
	}

	public PotionEffect[] getEffects() {
		return effects;
	}

	public Entity SpawnEntity(Location loc)
	{
		Entity ent = loc.getWorld().spawnEntity(loc, type);
	        
	    if(ent instanceof LivingEntity)
	    {
	    	LivingEntity entity = (LivingEntity) ent;
	    	
	    	entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
	    	entity.setHealth(health);
	    	
	    	if(effects != null) {
	    		for(PotionEffect ef : effects) {
	    			entity.addPotionEffect(ef);
	    		}
	    	}
	    	
	    	if(name != null) {
	    		entity.setCustomNameVisible(true);
	    		entity.setCustomName(name);
	    	}
	    	
	    	
	    	if(equip != null && inv != null) {
	    		for(int i=inv.length;i!=0;i--) {
	    			
	    			if(inv[i] != null && equip[i] != null) {
		    			ItemStack it = inv[i];
		    			EquipmentSlot slot = equip[i];
		    			
		    			entity.getEquipment().setItem(slot, it);
	    			}
	    		}
	    	}
	    	
	    	entity.setRemoveWhenFarAway(false);
	    }
	        
	    return ent;
	}
}
