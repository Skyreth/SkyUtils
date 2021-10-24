package fr.skyreth.skyutils.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.skyreth.skyutils.Connexion;
import fr.skyreth.skyutils.Main;

public class PlayerDataManager
{
	private Connexion con;
	
	public PlayerDataManager(Main main)
	{
		this.con = main.getDataPlayer();
	}
	
	// ----------------[Spawners]----------------------
	
	public int getSpawners(Player p)
	{
		String result = con.selectWhere("spawner","UUID",p.getUniqueId().toString(), "SPAWNERS");
		if(result != null && result != "nodata")
			return Integer.parseInt(result);
		else
			return 10;
	}
	
	public boolean hasSpawner(Player p)
	{
		String result = con.selectWhere("spawner","UUID",p.getUniqueId().toString(), "SPAWNERS");
		if(result != null && result != "nodata")
			return true;
		else
			return false;
	}
	
	public void addSpawner(Player p)
	{
		if(!hasSpawner(p))
			con.insert2("INSERT INTO spawner(UUID, SPAWNERS) VALUES(?,?)", p.getUniqueId().toString(), String.valueOf(1));
		else
			con.replaceDataWhere("spawner", "SPAWNERS", p.getUniqueId().toString(), String.valueOf(getSpawners(p)+1), "UUID");
	}
	
	public void removeSpawner(Player p) {
		if(hasSpawner(p) && getSpawners(p) > 0)
			con.replaceDataWhere("spawner", "SPAWNERS", p.getUniqueId().toString(), String.valueOf(getSpawners(p)-1), "UUID");
	}
	
	public boolean canPlaceAnother(Player p)
	{
		if(!(getSpawners(p) >= 10))
			return true;
		
		return false;
	}
	
	// ----------------[Homes]----------------------
	
	public void setHome(UUID uuid, Location loc, String name) {
		con.insert3("INSERT INTO homes(UUID, LOCATION, NAME) VALUES(?,?,?)", uuid.toString(), convertLocToString(loc), name);
	} 
	
	public boolean hasSimilarName(UUID uuid, String name)
	{
		String result = con.selectWhere2condition("homes","UUID",uuid.toString(), "Name", name, "Name");
		
		if(result != null && !result.equals("nodata"))
			if(result.equalsIgnoreCase(name)) return true;
		
		return false;
	}
	
	public boolean hasHomes(UUID uuid)
	{
		String result = con.selectWhere2("homes","UUID",uuid.toString(), "LOCATION", "Name");
		
		if(result != null && result != "nodata")
			return true;
		else
			return false;
	}
	
	public String getHomesNames(UUID uuid) {
		return con.getAllCells("homes","NAME","UUID", uuid.toString());
	}
	
	public Location getHome(UUID uuid, String name)
	{
		String result = con.selectWhere2condition("homes","UUID",uuid.toString(), "Name", name, "LOCATION");
		
		if(result != null && result != "nodata")
			return convertStringToLoc(result);
		else
			return null;
	}
	
	public void DelHome(UUID uuid, String name) {
		con.delete("homes", "Name", name);
	}
	
	private String convertLocToString(Location loc) {
		return loc.getWorld().getName()+" "+loc.getX()+" "+loc.getY()+" "+loc.getZ();
	}
	
	private Location convertStringToLoc(String l)
	{
		String[] list = l.split("\\s+");
		
		return new Location(Bukkit.getWorld(list[0]), Double.parseDouble(list[1]), Double.parseDouble(list[2]), Double.parseDouble(list[3]));
	}
}
