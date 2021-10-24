package fr.skyreth.skyutils.utils;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;

public class Human implements CommandExecutor, TabCompleter
{
	public void spawn(Location loc, World world, MinecraftServer server, UUID uuid, String name)
	{
		EntityPlayer npc = new EntityPlayer(server, ((CraftWorld) world).getHandle(), new GameProfile(uuid, name));
	//	Player npcPlayer = npc.getBukkitEntity().getPlayer();
		npc.setInvulnerable(true);
		npc.setLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getYaw(), loc.getPitch());
	//	npcPlayer.getInventory().setItemInMainHand(it);
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) 
	{
		return null;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return false;
	}
}
