package fr.skyreth.skyutils.misc;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;

import fr.skyreth.skyutils.Main;

public class ArmorStandListener implements Listener
{
	private Main main;
	private HashMap<UUID, ArmorStandManager> managers = new HashMap<>();
	
	public ArmorStandListener(Main main)
	{
		this.main = main;
	}
	
	@EventHandler
	private void armorStandBreak(EntityDamageByEntityEvent e)	{
		if(e.getDamager() instanceof Player && e.getEntityType() == EntityType.ARMOR_STAND)
		{
			ArmorStandManager manag = new ArmorStandManager((Player)e.getDamager(), (ArmorStand) e.getEntity(), main);
			manag.OpenMenu();
			managers.put(e.getDamager().getUniqueId(), manag);
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void OnInteractAtEntity(PlayerInteractAtEntityEvent e) {
		if(e.getRightClicked().getType().equals(EntityType.ARMOR_STAND))
		{
			e.setCancelled(true);
			ArmorStandManager manag = new ArmorStandManager(e.getPlayer(), (ArmorStand) e.getRightClicked(), main);
			manag.OpenMenu();
			managers.put(e.getPlayer().getUniqueId(), manag);
		}
	}
	
	@EventHandler
	private void onInventoryClick(InventoryClickEvent e) 
	{
		if(e.getView().getTitle().equals("§6Armor Stand Menu"))
		{
			ArmorStandManager manager = managers.get(e.getView().getPlayer().getUniqueId());
			
			if(e.getCurrentItem() != null)
			{
				if(e.getCurrentItem().getType().equals(Material.CHAINMAIL_CHESTPLATE))
				{
					manager.setAsclick(true);
					manager.OpenArmorContent();
				}
				if(e.getCurrentItem().getType().equals(Material.TRIPWIRE_HOOK))
				{
					manager.setAsclick(true);
					manager.OpenConfigMenu();
				}
				if(e.getCurrentItem().getType().equals(Material.BARRIER))
				{
					manager.getStand().remove();
					e.getView().getPlayer().closeInventory();
					managers.remove(e.getView().getPlayer().getUniqueId());
				}

				e.setCancelled(true);
			}
		}
		if(e.getView().getTitle().equals("§6Armor Stand Inventory"))
		{
			if(e.getCurrentItem() != null)
			{
				if(e.getCurrentItem().getType().equals(Material.ORANGE_STAINED_GLASS_PANE))
				{
					e.setCancelled(true);
				}
			}
		}
		if(e.getView().getTitle().equals("§6Armor Stand Config"))
		{
			ArmorStandManager manager = managers.get(e.getView().getPlayer().getUniqueId());
			ArmorStand stand = manager.getStand();
			
			if(e.getCurrentItem() != null)
			{
				e.setCancelled(true);
				
				if(main.getUtil().checkCustomItem(e.getCurrentItem(), "§6Bras:"))
				{
					stand.setArms(!stand.hasArms());
					manager.OpenConfigMenu();
				}
				
				if(main.getUtil().checkCustomItem(e.getCurrentItem(), "§6Invisiblite:"))
				{
					stand.setVisible(stand.isInvisible());
					manager.OpenConfigMenu();
				}
				if(main.getUtil().checkCustomItem(e.getCurrentItem(), "§6Gravite:"))
				{
					stand.setGravity(!stand.hasGravity());
					manager.OpenConfigMenu();
				}
				if(main.getUtil().checkCustomItem(e.getCurrentItem(), "§6Base Plate:"))
				{
					stand.setBasePlate(!stand.hasBasePlate());
					manager.OpenConfigMenu();
				}
				if(main.getUtil().checkCustomItem(e.getCurrentItem(), "§6Nom Visible:"))
				{
					stand.setCustomNameVisible(!stand.isCustomNameVisible());
					manager.OpenConfigMenu();
				}
				if(main.getUtil().checkCustomItem(e.getCurrentItem(), "§6Petite:"))
				{
					stand.setSmall(!stand.isSmall());
					manager.OpenConfigMenu();
				}
				if(main.getUtil().checkCustomItem(e.getCurrentItem(), "§6Rotation Menu"))
				{
					manager.OpenBodyMenu();
				}
			}
		}
		if(e.getView().getTitle().equals("§6Partie Selection"))
		{
			ArmorStandManager manager = managers.get(e.getView().getPlayer().getUniqueId());
			
			if(e.getCurrentItem() != null)
			{
				if(e.getCurrentItem().getAmount() >= 4)
				{
					BodyPart part = getBodyPartByInt(e.getCurrentItem().getAmount());
					manager.setPart(part);
					manager.OpenWayMenu(part);
				}
				else if(e.getCurrentItem().getAmount() < 4 && e.getCurrentItem().getAmount() >= 2)
				{
					BodyPart part = getBodyPartByInt(e.getCurrentItem().getAmount());
					manager.setPart(part);
					manager.OpenRotMenu(null);
				}
					
				e.setCancelled(true);
			}
		}
		if(e.getView().getTitle().contains("§6Orientation Menu de "))
		{
			ArmorStandManager manager = managers.get(e.getView().getPlayer().getUniqueId());
			
			if(manager.getPart() != null)
			{
				if(e.getCurrentItem() != null)
				{
					if(e.getCurrentItem().getAmount() > 1)
					{
						Orientation or = getRotByInt(e.getCurrentItem().getAmount());
						manager.OpenRotMenu(or);
						manager.setOr(or);
					}
						
					e.setCancelled(true);
				}
			}
		}
		if(e.getView().getTitle().equals("§6Menu de rotation"))
		{
			if(e.getCurrentItem() != null)
			{
				if(e.getCurrentItem().getType().equals(Material.GREEN_STAINED_GLASS_PANE))
				{
					ArmorStandManager manager = managers.get(e.getView().getPlayer().getUniqueId());
					ArmorStand stand = manager.getStand();
					EulerAngle angle = manager.getBodyAngle(manager.getPart(), manager.getOr());
					double i = Math.toRadians(1.0);
					
					switch(e.getRawSlot())
					{
						case 1:
							int slot = (e.getRawSlot()+9);
							ItemStack c = e.getInventory().getItem(slot);
							setAngle(stand, manager.getOr(), manager.getPart(), angle.add(i,0,0));
							e.getInventory().setItem(slot, main.getUtil().addCustomLoreToItem(c.clone(), c.getItemMeta().getDisplayName(), Double.toString(Math.toRadians(angle.getX()+i))));
							break;
						case 4:
							int slot2 = (e.getRawSlot()+9);
							ItemStack c2 = e.getInventory().getItem(slot2);
							setAngle(stand, manager.getOr(), manager.getPart(), angle.add(0,i,0));
							e.getInventory().setItem(slot2, main.getUtil().addCustomLoreToItem(c2.clone(), c2.getItemMeta().getDisplayName(), Double.toString(Math.toRadians(angle.getY()+i))));
							break;
						case 7:
							int slot3 = (e.getRawSlot()+9);
							ItemStack c3 = e.getInventory().getItem(slot3);
							setAngle(stand, manager.getOr(), manager.getPart(), angle.add(0,0,i));
						    e.getInventory().setItem(slot3, main.getUtil().addCustomLoreToItem(c3.clone(), c3.getItemMeta().getDisplayName(), Double.toString(Math.toRadians(angle.getZ()+i))));
							break;
					}

				}
				if(e.getCurrentItem().getType().equals(Material.RED_STAINED_GLASS_PANE))
				{
					ArmorStandManager manager = managers.get(e.getView().getPlayer().getUniqueId());
					ArmorStand stand = manager.getStand();
					EulerAngle angle = manager.getBodyAngle(manager.getPart(), manager.getOr());
					double i = Math.toRadians(-1.0);
					
					switch(e.getRawSlot())
					{
						case 19:
							int slot = (e.getRawSlot()-9);
							ItemStack c = e.getInventory().getItem(slot);
							setAngle(stand, manager.getOr(), manager.getPart(), angle.add(i, 0, 0));
							e.getInventory().setItem(slot, main.getUtil().addCustomLoreToItem(c.clone(), c.getItemMeta().getDisplayName(), Double.toString(Math.toRadians(angle.getX()+i))));
							break;
						case 22:
							int slot2 = (e.getRawSlot()-9);
							ItemStack c2 = e.getInventory().getItem(slot2);
							setAngle(stand, manager.getOr(), manager.getPart(), angle.add(0, i, 0));
							e.getInventory().setItem(slot2, main.getUtil().addCustomLoreToItem(c2.clone(), c2.getItemMeta().getDisplayName(), Double.toString(Math.toRadians(angle.getY()+i))));
							break;
						case 25:
							int slot3 = (e.getRawSlot()-9);
							ItemStack c3 = e.getInventory().getItem(slot3);
							setAngle(stand, manager.getOr(), manager.getPart(), angle.add(0, 0, i));
							e.getInventory().setItem(slot3, main.getUtil().addCustomLoreToItem(c3.clone(), c3.getItemMeta().getDisplayName(), Double.toString(Math.toRadians(angle.getZ()+i))));
							break;
					}
				}
				
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler 
	private void onInventoryClose(InventoryCloseEvent e)
	{
		if(e.getView().getTitle().equals("§6Armor Stand Inventory"))
		{
			if(managers.containsKey(e.getPlayer().getUniqueId()))
			{
				ArmorStand stand = managers.get(e.getPlayer().getUniqueId()).getStand();
				
				stand.getEquipment().setHelmet(e.getInventory().getItem(13));
				stand.getEquipment().setChestplate(e.getInventory().getItem(22));
				stand.getEquipment().setLeggings(e.getInventory().getItem(31));
				stand.getEquipment().setBoots(e.getInventory().getItem(40));
				stand.getEquipment().setItemInMainHand(e.getInventory().getItem(21));
				stand.getEquipment().setItemInOffHand(e.getInventory().getItem(23));
			}
		}
		if(e.getView().getTitle().contains("§6Armor Stand"))
		{
			if(managers.containsKey(e.getPlayer().getUniqueId()))
			{
				ArmorStandManager manag = managers.get(e.getPlayer().getUniqueId());
				
				if(!manag.HasClick())
					managers.remove(e.getPlayer().getUniqueId());
			}
		}
		if(e.getView().getTitle().equals("§6Menu de rotation"))
		{
			if(managers.containsKey(e.getPlayer().getUniqueId()))
			{
				managers.remove(e.getPlayer().getUniqueId());
			}
		}
	}
	
	private void setAngle(ArmorStand stand, Orientation or, BodyPart bd, EulerAngle angle)
	{
		if(or ==  null)
		{
			if(bd.equals(BodyPart.BODY))
			{
				stand.setBodyPose(angle);
			}
			else if(bd.equals(BodyPart.HEAD))
			{
				stand.setHeadPose(angle);
			}
		}
		else
		{
			if(or.equals(Orientation.DROIT))
			{
				switch(bd)
				{
					case ARM:
						 stand.setRightArmPose(angle);
						 break;
					case LEG:
						stand.setRightLegPose(angle);
						break;
				default:
					break;
				}
			}
			else if(or.equals(Orientation.GAUCHE))
			{
				switch(bd)
				{
					case ARM:
						stand.setLeftArmPose(angle);
					case LEG:
						stand.setLeftLegPose(angle);
				default:
					break;
				}
			}
		}
	}
	
	private BodyPart getBodyPartByInt(int n)
	{
		switch(n)
		{
			case 2:
				return BodyPart.HEAD;
			case 3:
				return BodyPart.BODY;
			case 4:
				return BodyPart.ARM;
			case 5:
				return BodyPart.LEG;
		}
		
		return null;
	}
	
	private Orientation getRotByInt(int n)
	{
		switch(n)
		{
			case 2:
				return Orientation.GAUCHE;
			case 3:
				return Orientation.DROIT;
		}
		
		return null;
	}
}
