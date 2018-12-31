package su.mellgrief.apanel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import su.mellgrief.apanel.commands.AAskExecutor;
import su.mellgrief.apanel.commands.AAskQuestExecutor;
import su.mellgrief.apanel.commands.APanelExecutor;
import su.mellgrief.apanel.commands.AReportExecutor;
import su.mellgrief.apanel.commands.AReportQuestExecutor;
import su.mellgrief.apanel.config.Config;
import su.mellgrief.apanel.listener.AChatListener;
import su.mellgrief.apanel.listener.APanelListener;

public class Main extends JavaPlugin {
	
	public static Logger log = Logger.getLogger("Minecraft");
	
	public static Main instance;
	
	public static Config config;
	
	public static List<Player> mustAuth = new ArrayList();
	
	public static APanelExecutor ape = new APanelExecutor();
	public static AReportExecutor are = new AReportExecutor();
	public static AReportQuestExecutor arqe = new AReportQuestExecutor();
	public static AAskExecutor aae = new AAskExecutor();
	public static AAskQuestExecutor aaqe = new AAskQuestExecutor();
	
	public static AChatListener acl = new AChatListener();
	
	@Override
	public void onEnable() {
		instance = this;
		config = new Config(instance, "config.yml");
		config.checkOnStart();
		config.loadConfiguration();
		getCommand("apanel").setExecutor(ape);
		getCommand("report").setExecutor(are);
		getCommand("reportquest").setExecutor(arqe);
		getCommand("ask").setExecutor(aae);
		getCommand("askquest").setExecutor(aaqe);

		this.getServer().getPluginManager().registerEvents(new APanelListener(), getInstance());
		this.getServer().getPluginManager().registerEvents(acl, getInstance());
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static Main getInstance() {
		return instance;
	}

}
