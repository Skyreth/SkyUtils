package fr.skyreth.skyutils;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import fr.skyreth.skyutils.admin.AdminCommand;
import fr.skyreth.skyutils.admin.AdminListener;
import fr.skyreth.skyutils.admin.AdminMode;
import fr.skyreth.skyutils.commands.CraftCommand;
import fr.skyreth.skyutils.commands.DelhomeCommand;
import fr.skyreth.skyutils.commands.EcCommand;
import fr.skyreth.skyutils.commands.GiveCommand;
import fr.skyreth.skyutils.commands.Grade;
import fr.skyreth.skyutils.commands.HomeCommand;
import fr.skyreth.skyutils.commands.MinageCommand;
import fr.skyreth.skyutils.commands.SetHomeCommand;
import fr.skyreth.skyutils.commands.SpawnCommand;
import fr.skyreth.skyutils.commands.TpAccept;
import fr.skyreth.skyutils.commands.TpDeny;
import fr.skyreth.skyutils.commands.TpaCommand;
import fr.skyreth.skyutils.commands.TpaHereCommand;
import fr.skyreth.skyutils.commands.TpaHereRunnable;
import fr.skyreth.skyutils.commands.TpaRunnable;
import fr.skyreth.skyutils.enchant.EnchantManager;
import fr.skyreth.skyutils.jobs.Apiculteur;
import fr.skyreth.skyutils.jobs.JobListener;
import fr.skyreth.skyutils.misc.ArmorStandListener;
import fr.skyreth.skyutils.misc.CustomCraftingTable;
import fr.skyreth.skyutils.mobs.CustomMobRegistry;
import fr.skyreth.skyutils.mobs.MobSpawnCommand;
import fr.skyreth.skyutils.mobs.Mobs;
import fr.skyreth.skyutils.utils.PlayerDataManager;
import fr.skyreth.skyutils.utils.Utils;

public class Main extends JavaPlugin
{
	private static final Logger log = Bukkit.getLogger();
	private static Main instance;
	private Server serv = getServer();
	private Connexion connexion = new Connexion("Players.db");
	private PlayerDataManager pdm = new PlayerDataManager(this);
	private AdminMode admin = new AdminMode(this);
	private TpaRunnable tpa = new TpaRunnable(this);
	private TpaHereRunnable tpahere = new TpaHereRunnable(this);
	private Utils util;
	private CustomMobRegistry mobs;
	private Items items;
	private Apiculteur api;
	
	
	@Override
    public void onEnable() 
    {
		instance = this;
		api = new Apiculteur(this);
		util = new Utils();
		items = new Items();
        items.init(this);
        registerCommands();
        Recipes.registerCraft(serv, this);
        CustomCraftingTable table = new CustomCraftingTable(this);
        CustomMobRegistry reg = new CustomMobRegistry(this);
        mobs = reg;
        serv.getPluginManager().registerEvents(new Listener(this), this); 
        serv.getPluginManager().registerEvents(new EnchantManager(this), this); 
        serv.getPluginManager().registerEvents(new SPListener(this), this); 
        serv.getPluginManager().registerEvents(reg, this);
        serv.getPluginManager().registerEvents(table, this);
        serv.getPluginManager().registerEvents(new Mobs(this), this);
        serv.getPluginManager().registerEvents(new AdminListener(this), this);
        serv.getPluginManager().registerEvents(new ArmorStandListener(this), this);
        serv.getPluginManager().registerEvents(new JobListener(this), this);
        connexion.connect();
        connexion.createNewTable("`grade`", "`UUID` TEXT, `COLOR` TEXT, `GRADE` TEXT");
        connexion.createNewTable("`spawner`", "`UUID` TEXT, `SPAWNERS` TEXT");
        connexion.createNewTable("`homes`", "`UUID` TEXT, `LOCATION` TEXT, `Name` TEXT");
        connexion.createNewTable("`apiculteur`", "`UUID` TEXT, `LEVEL` TEXT, `XP` TEXT");
        table.init();
    }
	
	@Override
	public void onDisable() 
	{
		connexion.close(this.getLogger());
		log.info(this.getDescription()+" Disabled !");
	}
	
	public Apiculteur getApiculturInstance()
	{
		return api;
	}
	
	public Items getItems() {
		return items;
	}
	
	public Utils getUtil() {
		return util;
	}
	
	public CustomMobRegistry getCustomMobs() {
		return mobs;
	}
	
	public static Main getInstance() { 
		return instance; 
	}
	
	public Connexion getDataPlayer() {
		return connexion;
	}
	
	public AdminMode getAdminMode() {
		return admin;
	}
	
	public PlayerDataManager getPlayerData() {
		return pdm;
	}
	
	private void registerCommands() 
	{
		getCommand("ec").setExecutor(new EcCommand(this));
		getCommand("minage").setExecutor(new MinageCommand());
		getCommand("sethome").setExecutor(new SetHomeCommand(this));
		getCommand("admin").setExecutor(new AdminCommand(this));
		getCommand("grade").setExecutor(new Grade(this));
		getCommand("craft").setExecutor(new CraftCommand());
		getCommand("spawn").setExecutor(new SpawnCommand());
		getCommand("tpa").setExecutor(new TpaCommand(this));
		getCommand("tpaccept").setExecutor(new TpAccept(this));
		getCommand("tpdeny").setExecutor(new TpDeny(this));
		getCommand("delhome").setExecutor(new DelhomeCommand(this));
		getCommand("tpahere").setExecutor(new TpaHereCommand(this));
		PluginCommand give = getCommand("givec");
		give.setExecutor(new GiveCommand(this));
		give.setTabCompleter(new GiveCommand(this));
		PluginCommand mob = getCommand("mob");
		mob.setExecutor(new MobSpawnCommand(this));
		mob.setTabCompleter(new MobSpawnCommand(this));
		PluginCommand home = getCommand("home");
		home.setExecutor(new HomeCommand(this));
		home.setTabCompleter(new HomeCommand(this));
	}

	public TpaRunnable getTpaRunnable() {
		return tpa;
	}
	
	public TpaHereRunnable getTpaHereRunnable() {
		return tpahere;
	}
}
