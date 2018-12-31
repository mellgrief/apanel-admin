package su.mellgrief.apanel.commands;

import java.util.Calendar;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import su.mellgrief.apanel.Main;

public class AAskExecutor implements CommandExecutor{

	HashMap<Player, Long> player_use = new HashMap();
	HashMap<String, String> reports = new HashMap();
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
		    Long now = Long.valueOf(Calendar.getInstance().getTimeInMillis() / 1000L);
		    Long was = Long.valueOf(0L);
		    if(player_use.containsKey(p)) {
		    	was = player_use.get(p);
		    }
		    if(reports.containsKey(p.getName())) {
		    	p.sendMessage(Main.getInstance().config.cfg.getString("apanel.aask-have-report").replaceAll("&", "§"));
			} else if(now.longValue() - was.longValue() < 60) {
		    	String mess = Main.getInstance().config.cfg.getString("apanel.aask-cooldown");
		    	mess = mess.replaceAll("%time%", "" + (60 - (now.longValue() - was.longValue())));
		    	mess = mess.replaceAll("&", "§");
		    	p.sendMessage(mess);
		    } else {
		    	p.sendMessage(Main.getInstance().config.cfg.getString("apanel.aask-success").replaceAll("&", "§"));
		    	reports.put(p.getName(), arrayToString(args));
		    	player_use.put(p, now);
		    	sendToModerators(reports.get(p.getName()), p);
		    }
		    return true;
		}
		return false;
	}
	
	public void sendToModerators(String message, Player p) {
		for(String key : Main.getInstance().config.cfg.getConfigurationSection("apanel.admins").getKeys(false)) {
			if(Bukkit.getPlayer(key).isOnline()) {
				Bukkit.getPlayer(key).sendMessage(Main.getInstance().config.cfg.getString("apanel.aask-notification").replaceAll("&", "§").replaceAll("%from%", p.getName()).replaceAll("%message%", message));
			}
		}
	}
	
	public String arrayToString(String[] args) {
	   String res = "";
	   for (int i = 0; i < args.length; i++) {
	      res = res + " " + args[i];
	   }
	   return res;
	}

}
