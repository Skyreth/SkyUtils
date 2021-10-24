package fr.skyreth.skyutils.commands;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.skyreth.skyutils.Connexion;
import fr.skyreth.skyutils.Main;

public class Grade implements CommandExecutor
{
	private Main main;
	private Connexion connexion;
	
	public Grade(Main main)
	{
		this.main = main;
		this.connexion = main.getDataPlayer();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args)  
	{
		if(sender instanceof Player)
        {
            Player p = (Player) sender;
            if(p.isOp())
            {
	            if(alias.equalsIgnoreCase("grade"))
	            {  
	            	if(args.length == 0)
		            	p.performCommand("grade help");
	            	else 
	            	{
	            		if(args[0].equalsIgnoreCase("help"))
	            		{
	            			p.sendMessage("--------------[§1Sky§eGrade§r]--------------");
	            			p.sendMessage("");
	            			p.sendMessage("- /grade set <pseudo> <couleur> <grade>");
	            			p.sendMessage("- /grade reset <pseudo>");
	            			p.sendMessage("");
	            			p.sendMessage("--------------[§1Sky§eGrade§r]--------------");
	            		}
		            	if(args[0].equalsIgnoreCase("set"))
		            	{
		            		if(args[1].length() > 0 && args[1] != null)
		            		{
		            			if(args[2].length() > 0 && args[2] != null)
		            			{
		            				if(args[3].length() > 0 && args[3] != null) 
			            			{
				            			Player target = main.getUtil().getPlayerByName(p, args[1]);
				            			
				            			if(!hasGrade(target.getUniqueId(), connexion))
				            			{
				            				p.sendMessage("§2grade update pour "+ChatColor.BOLD+args[1]+" !");
				            				setGrade(target.getUniqueId(), args[2], args[3], connexion);
				            			}
				            			else
				            			{
				            				replaceGrade(target.getUniqueId(), args[3], args[2], connexion);
				            				p.sendMessage("§2grade update pour "+ChatColor.BOLD+args[1]+" !");
				            			}
			            			}
		            				else
				            			p.sendMessage("§4§lErreur §r§4vous devez entre un grade !");
		            			}
		            			else
			            			p.sendMessage("§4§lErreur §r§4vous devez entre une couleur !");
		            		}
		            		else
		            			p.sendMessage("§4§lErreur §r§4vous avez entrez un pseudo vide !");
		            	}
		            	else if(args[0].equalsIgnoreCase("reset"))
		            	{
		            		if(args[1].length() > 0 && args[1] != null)
		            		{
		            			Player target = main.getUtil().getPlayerByName(p, args[1]);
		            		
		            			if(hasGrade(target.getUniqueId(), connexion))
		            			{
		            				p.sendMessage("§2Le grade de "+ChatColor.BOLD+args[1]+" a ete reset !");
		            				connexion.replaceDataWhere("grade", "GRADE", target.getUniqueId().toString(), "Joueur", "UUID");
		            				connexion.replaceDataWhere("grade", "COLOR", target.getUniqueId().toString(), "§2", "UUID");
		            			}
		            			else
		            				p.sendMessage("§4§lErreur vous devez possedez un grade !");
		            		}
		            		else
		            			p.sendMessage("§4§lErreur §r§4vous avez entrez un pseudo vide !");
		            	}
	            	}
	            }
            }
            else
            {
            	p.sendMessage("§4§lErreur§r§4 vous ne possedez pas la permission !");
            }
        }
		return false;
	}
	
	public static String getGrade(UUID uuid, Connexion connexion)
	{
		String result = connexion.selectWhere("grade","UUID",uuid.toString(), "GRADE");
		if(result != null && result != "nodata")
			return result;
		else
			return "Joueur";
	} 
	
	public static String getGradeColor(UUID uuid, Connexion connexion)
	{
		String result = connexion.selectWhere("grade","UUID",uuid.toString(), "COLOR");
		if(result != null && result != "nodata")
			return result;
		else
			return "§2";
	} 
	
	public static void replaceGrade(UUID uuid, String grade, String color, Connexion connexion)
	{
		connexion.replaceDataWhere("grade", "GRADE",uuid.toString(), grade, "UUID");
		connexion.replaceDataWhere("grade", "COLOR", uuid.toString(), ChatColor.translateAlternateColorCodes('&', color), "UUID");
	}
	
	public static void setGrade(UUID uuid, String color, String grade, Connexion connexion)
	{
		connexion.insert3("INSERT INTO grade(UUID, COLOR, GRADE) VALUES(?,?,?)", uuid.toString(), ChatColor.translateAlternateColorCodes('&', color), grade);
	} 
	
	public static Boolean hasGrade(UUID uuid, Connexion connexion)
	{
		String result = connexion.selectWhere2("grade","UUID",uuid.toString(), "Grade", "COLOR");
		if(result != null && result != "nodata")
			return true;
		else
			return false;
	} 
}
