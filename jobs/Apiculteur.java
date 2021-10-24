package fr.skyreth.skyutils.jobs;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import fr.skyreth.skyutils.Connexion;
import fr.skyreth.skyutils.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Apiculteur 
{
	private Connexion con;
	
	public Apiculteur(Main main){
		this.con = main.getDataPlayer();
	}
	
	public void addLevel(Player p) {
		int level = (getCurrentLevel(p.getUniqueId())+1);
		
		if(level <= 4)
		{
			switch(level)
			{
				case 1:
					Bukkit.broadcastMessage(ChatColor.WHITE+p.getName()+" a réalisé le progrès "+ChatColor.GREEN+"§l[Apiculteur Novice!]");
					break;
				case 2:
					p.sendMessage("§8------------------["+ChatColor.GREEN+"DEBLOQUER§r§8]-------------------------");
					p.sendMessage("§7* -15% sur les dégats de chute !");
					p.sendMessage("§8------------------------------------------------------");
					break;
				case 3:
					Bukkit.broadcastMessage(ChatColor.WHITE+p.getName()+" a réalisé le progrès "+ChatColor.GREEN+"§l[Apiculteur Intermédiaire!]");
					p.sendMessage("------------------["+ChatColor.GREEN+"DEBLOQUER§r]-------------------------");
					p.sendMessage("§7* -20% sur les dégats de chute ");
					p.sendMessage("§7* 1 chance sur 10 de pas prendre les dégats de chute ");
					p.sendMessage("§8------------------------------------------------------");
					break;
				case 4:
					Bukkit.broadcastMessage(ChatColor.WHITE+p.getName()+" a réalisé le progrès "+ChatColor.GREEN+"§5[Apiculteur Expérimenter!]");
					p.sendMessage("------------------["+ChatColor.GREEN+"DEBLOQUER§r]-------------------------");
					p.sendMessage("§7* -25% sur les dégats de chute ");
					p.sendMessage("§7* 1 chance sur 5 de pas prendre les dégats de chute ");
					p.sendMessage("§8------------------------------------------------------");
					break;
			}
			
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.7f, 0.7f);
			p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Niveau "+(level)+" débloqué"));
			p.sendMessage("* Vous etes desormais niveau "+level+" en apiculture !");
			con.replaceDataWhere("apiculteur", "LEVEL", p.getUniqueId().toString(), String.valueOf(level), "UUID");
		}
	}
	
	public boolean hasDiscover(Player p)
	{
		String result = con.selectWhere2("apiculteur","UUID",p.getUniqueId().toString(), "XP", "LEVEL");
		
		if(result != null && result != "nodata")
			return true;
		else
			return false;
	}
	
	private void storeXp(Player p, float xp)
	{
		UUID uuid = p.getUniqueId();
		
		if(!hasDiscover(p)) {
			int z = 0;
			con.insert3("INSERT INTO apiculteur(UUID, XP, LEVEL) VALUES(?,?,?)", p.getUniqueId().toString(), String.valueOf(xp), String.valueOf(z));
			Bukkit.broadcastMessage(ChatColor.WHITE+p.getName()+" a réalisé le progrès "+ChatColor.GREEN+"§l[Apiculteur du dimanche!]");
		}
		else
		{
			con.replaceDataWhere("apiculteur", "XP", p.getUniqueId().toString(), String.valueOf(getCurrentXP(uuid)+xp), "UUID");
			p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("+"+xp+"xp gagnée pour le métier d'Apiculteur     "+getCurrentXP(uuid)+"/"+getNextTotalXpToLevel(uuid)+"xp"));
		}
	}
	
	public void addExperience(Player p, float xp)
	{
		this.storeXp(p, xp);
		
		if(xp >= getCurrentLeftingXp(p.getUniqueId())) {
			addLevel(p);
		}
	}
	
	// niveau du joueur
	public int getCurrentLevel(UUID uuid)
	{
		String result = con.selectWhere("apiculteur","UUID",uuid.toString(), "LEVEL");
		
		if(result != null && result != "nodata")
			return Integer.parseInt(result);
		else
		    return 0;
	}

	// xp du joueur
	public float getCurrentXP(UUID uuid)
	{
		String result = con.selectWhere("apiculteur","UUID",uuid.toString(), "XP");
		
		if(result != null && result != "nodata")
			return Float.parseFloat(result);
		else
			return 0;
	}
	
	// return l'xp total pour passer le niveau
	public int getNextTotalXpToLevel(UUID uuid) {
		return ((getCurrentLevel(uuid)+1)*60);
		
	}
	
	// return l'xp necessaire pour passer le niveau
	public float getCurrentLeftingXp(UUID uuid) {
		return (getNextTotalXpToLevel(uuid)-getCurrentXP(uuid));
	}
}
