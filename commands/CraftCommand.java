package fr.skyreth.skyutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class CraftCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if(sender instanceof Player) {
			Player p = (Player) sender;
			Inventory inv = Bukkit.createInventory(p, InventoryType.WORKBENCH);
			p.openInventory(inv);
			p.openWorkbench(null, true);
		}
		return false;
	}

}