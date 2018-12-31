package su.mellgrief.apanel.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import su.mellgrief.apanel.Main;

public class AReportQuestExecutor implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(isAdmin(p)) {
				if(args.length > 1) {
					if(Main.getInstance().are.reports.containsKey(args[0])) {
						String answer = arrayToString(args);
						if(Bukkit.getPlayer(args[0]).isOnline()) {
							Bukkit.getPlayer(args[0]).sendMessage(Main.getInstance().config.cfg.getString("apanel.areport-answer").replaceAll("%moder%", p.getName()).replaceAll("%answer%", answer).replaceAll("&", "§"));
							Main.are.reports.remove(args[0]);
						} else {
							p.sendMessage(Main.getInstance().config.cfg.getString("apanel.areport-offline").replaceAll("&", "§"));
						}
					} else { p.sendMessage(Main.getInstance().config.cfg.getString("apanel.areport-404").replaceAll("&", "§")); }
					return true;
				}
			}
		}
		return false;
	}

	public void sendToModerators(String message, Player p) {
		for(String key : Main.getInstance().config.cfg.getConfigurationSection("apanel.admins").getKeys(false)) {
			if(Bukkit.getPlayer(key).isOnline()) {
				Bukkit.getPlayer(key).sendMessage(Main.getInstance().config.cfg.getString("apanel.areport-notification").replaceAll("&", "§").replaceAll("%from%", p.getName()).replaceAll("%message%", message));
			}
		}
	}
	
	
	public boolean isAdmin(Player p) {
		if(Main.getInstance().config.cfg.getConfigurationSection("apanel.admins." + p.getName()) != null) {
			if(Main.getInstance().config.cfg.getInt("apanel.admins." + p.getName() + ".level") >= 4) {
				return true;
			}
		}
		return false;
	}
	
	public String arrayToString(String[] args) {
		   String res = "";
		   for (int i = 1; i < args.length; i++) {
		      res = res + " " + args[i];
		   }
		   return res;
		}
}
