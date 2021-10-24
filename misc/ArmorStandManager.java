package fr.skyreth.skyutils.misc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import fr.skyreth.skyutils.Main;

public class ArmorStandManager
{
	private Player p;
	private ArmorStand stand; 
	private Main main;
	private boolean asclick;
	private BodyPart part;
	private Orientation or;

	public ArmorStandManager(Player p, ArmorStand stand, Main main)
	{
		this.stand = stand;
		this.p = p;
		this.main = main;
	}
	
	public Orientation getOr() {
		return or;
	}

	public void setOr(Orientation or) {
		this.or = or;
	}
	
	public boolean HasClick() {
		return asclick;
	}
	
	public void setAsclick(boolean asclick) {
		this.asclick = asclick;
	}

	public ArmorStand getStand() {
		return stand;
	}
	
	public BodyPart getPart() {
		return part;
	}

	public void setPart(BodyPart part) {
		this.part = part;
	}
	
	public void OpenMenu()
	{
		Inventory inv = Bukkit.createInventory(null, 54, "§6Armor Stand Menu");
		
		inv.setItem(24, main.getUtil().CreateItemCustom("Inventory Content", Material.CHAINMAIL_CHESTPLATE, 1));
		inv.setItem(37, main.getUtil().CreateItemCustom("Configuration", Material.TRIPWIRE_HOOK, 1));
		inv.setItem(43, main.getUtil().CreateItemCustom("§4Detruire", Material.BARRIER, 1));
		main.getUtil().fullInventory(inv, Material.ORANGE_STAINED_GLASS_PANE);
		
		p.openInventory(inv);
	}
	
	public void OpenConfigMenu()
	{
		Inventory inv = Bukkit.createInventory(null, 27, "§6Armor Stand Config");
		main.getUtil().fullInventory(inv, Material.ORANGE_STAINED_GLASS_PANE);
		inv.setItem(10, main.getUtil().CreateItemCustom("§6Bras:", Material.ARMOR_STAND, 1));
		inv.setItem(19, isEnable(stand.hasArms()));
		inv.setItem(11, main.getUtil().CreateItemCustom("§6Invisiblite:", Material.POTION, 1));
		inv.setItem(20, isEnable(stand.isInvisible()));
		inv.setItem(12, main.getUtil().CreateItemCustom("§6Gravite:", Material.SAND, 1));
		inv.setItem(21, isEnable(stand.hasGravity()));
		inv.setItem(13, main.getUtil().CreateItemCustom("§6Base Plate:", Material.STONE_PRESSURE_PLATE, 1));
		inv.setItem(22, isEnable(stand.hasBasePlate()));
		inv.setItem(14, main.getUtil().CreateItemCustom("§6Nom Visible:", Material.NAME_TAG, 1));
		inv.setItem(23, isEnable(stand.isCustomNameVisible()));
		inv.setItem(14, main.getUtil().CreateItemCustom("§6Petite:", Material.RABBIT_HIDE, 1));
		inv.setItem(23, isEnable(stand.isSmall()));
		inv.setItem(26, main.getUtil().CreateItemCustom("§6Rotation Menu", Material.COMPARATOR, 1));
		inv.setItem(17, main.getUtil().CreateItemCustom("§6Nom", Material.ANVIL, 1));
		
		p.openInventory(inv);
	}
	
	public void OpenBodyMenu()
	{
		Inventory inv = Bukkit.createInventory(null, 27, "§6Partie Selection");
		
		main.getUtil().fullInventory(inv, Material.ORANGE_STAINED_GLASS_PANE);
		inv.setItem(10, main.getUtil().CreateItemCustom("Tete", Material.ARMOR_STAND, 2));
		inv.setItem(12, main.getUtil().CreateItemCustom("Corp", Material.ARMOR_STAND, 3));
		inv.setItem(14, main.getUtil().CreateItemCustom("Bras", Material.ARMOR_STAND, 4));
		inv.setItem(16, main.getUtil().CreateItemCustom("Jambe", Material.ARMOR_STAND, 5));

		
		p.openInventory(inv);
	}
	
	public void OpenWayMenu(BodyPart bd)
	{
		this.part = bd;
		Inventory inv = Bukkit.createInventory(null, 27, "§6Orientation Menu de "+bd.toString());
		
		main.getUtil().fullInventory(inv, Material.ORANGE_STAINED_GLASS_PANE);
		inv.setItem(10, main.getUtil().CreateItemCustom("Gauche", Material.ARMOR_STAND, 2));
		inv.setItem(16, main.getUtil().CreateItemCustom("Droite", Material.ARMOR_STAND, 3));

		
		p.openInventory(inv);
	}

	public void OpenRotMenu(Orientation or)
	{
		Inventory inv = Bukkit.createInventory(null, 27, "§6Menu de rotation");
		EulerAngle angle = getBodyAngle(part, or);
		
		main.getUtil().fullInventory(inv, Material.ORANGE_STAINED_GLASS_PANE);
		
		inv.setItem(1, main.getUtil().CreateItemCustom("Augmenter", Material.GREEN_STAINED_GLASS_PANE, 5));
		inv.setItem(10, main.getUtil().addCustomLoreToItem(new ItemStack(Material.ARMOR_STAND, 2), "X", Double.toString(Math.toRadians(angle.getX()))));
		inv.setItem(19, main.getUtil().CreateItemCustom("Diminuer", Material.RED_STAINED_GLASS_PANE, 5));
		inv.setItem(4, main.getUtil().CreateItemCustom("Augmenter", Material.GREEN_STAINED_GLASS_PANE, 6));
		inv.setItem(13, main.getUtil().addCustomLoreToItem(new ItemStack(Material.ARMOR_STAND, 2), "Y", Double.toString(Math.toRadians(angle.getY()))));
		inv.setItem(22, main.getUtil().CreateItemCustom("Diminuer", Material.RED_STAINED_GLASS_PANE, 6));
		inv.setItem(7, main.getUtil().CreateItemCustom("Augmenter", Material.GREEN_STAINED_GLASS_PANE, 7));
		inv.setItem(16, main.getUtil().addCustomLoreToItem(new ItemStack(Material.ARMOR_STAND, 4), "Z", Double.toString(Math.toRadians(angle.getZ()))));
		inv.setItem(25, main.getUtil().CreateItemCustom("Diminuer", Material.RED_STAINED_GLASS_PANE, 7));

		
		p.openInventory(inv);
	}
	
	public void OpenArmorContent()
	{
		Inventory inv = Bukkit.createInventory(null, 54, "§6Armor Stand Inventory");
		main.getUtil().fullInventory(inv, Material.ORANGE_STAINED_GLASS_PANE);
			
		inv.setItem(13, stand.getEquipment().getHelmet());
		inv.setItem(22, stand.getEquipment().getChestplate());
		inv.setItem(31, stand.getEquipment().getLeggings());
		inv.setItem(40, stand.getEquipment().getBoots());
		inv.setItem(21, stand.getEquipment().getItemInMainHand());
		inv.setItem(23, stand.getEquipment().getItemInOffHand());
		
		p.openInventory(inv);
	}
	
	public EulerAngle getBodyAngle(BodyPart bd, Orientation or)
	{
		if(or == null)
		{
			if(bd.equals(BodyPart.BODY))
			{
				return stand.getBodyPose();
			}
			else if(bd.equals(BodyPart.HEAD))
			{
				return stand.getHeadPose();
			}
		}
		else
		{
			if(or.equals(Orientation.DROIT))
			{
				switch(bd)
				{
					case ARM:
						return stand.getRightArmPose();
					case LEG:
						return stand.getRightLegPose();
				default:
					break;
				}
			}
			else if(or.equals(Orientation.GAUCHE))
			{
				switch(bd)
				{
					case ARM:
						return stand.getLeftArmPose();
					case LEG:
						return stand.getLeftLegPose();
				default:
					break;
				}
			}
		}
		return null;
	}
	
	private ItemStack isEnable(boolean is)
	{
		if(is)
			return main.getUtil().CreateItemCustom("§2Enable", Material.REDSTONE, 1);
		else
			return main.getUtil().CreateItemCustom("§4Disable", Material.GUNPOWDER, 1);
	}
}
