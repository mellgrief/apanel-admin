package su.mellgrief.apanel.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import su.mellgrief.apanel.Main;

public class AChatListener implements Listener {
	
	public List<Player> helperChat = new ArrayList();
	public List<Player> adminChat = new ArrayList();

	
	@EventHandler
	public void onPlayerConnect(PlayerLoginEvent e) {
		if(helperChat.contains(e.getPlayer())) {
			helperChat.remove(e.getPlayer());
		}
		if(adminChat.contains(e.getPlayer())) {
			adminChat.remove(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if(helperChat.contains(e.getPlayer())) {
			helperChat.remove(e.getPlayer());
		}
		if(adminChat.contains(e.getPlayer())) {
			adminChat.remove(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent e) {
		if(helperChat.contains(e.getPlayer())) {
			helperChat.remove(e.getPlayer());
		}
		if(adminChat.contains(e.getPlayer())) {
			adminChat.remove(e.getPlayer());
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onAsyncChat(AsyncPlayerChatEvent e) {
		if(helperChat.contains(e.getPlayer())) {
			sendToHelperChat(e.getMessage(), e.getPlayer());
			e.setCancelled(true);
		}
		if(adminChat.contains(e.getPlayer())) {
			sendToAdminChat(e.getMessage(), e.getPlayer());
			e.setCancelled(true);
		}
	}

	public void sendToAdminChat(String message, Player who) {
		for(Player p : this.adminChat) {
			p.sendMessage(formatAdminMessage(message, who));
		}
	}
	
	public void sendToHelperChat(String message, Player who) {
		for(Player p : this.helperChat) {
			p.sendMessage(formatHelperMessage(message, who));
		}
	}
	
	public String formatAdminMessage(String message, Player who) {
		String end = "";
		end = Main.getInstance().config.cfg.getString("apanel.achat");
		end = end.replaceAll("%player%", who.getName());
		end = end.replaceAll("%level%", getAdminLevel(who));
		end = end.replaceAll("%msg%", message);
		end = end.replaceAll("&", "§");
		return end;
	}
	
	public String formatHelperMessage(String message, Player who) {
		String end = "";
		end = Main.getInstance().config.cfg.getString("apanel.hchat");
		end = end.replaceAll("%player%", who.getName());
		end = end.replaceAll("%level%", getAdminLevel(who));
		end = end.replaceAll("%msg%", message);
		end = end.replaceAll("&", "§");
		return end;
	}

	public String getAdminLevel(Player p) {
		if(Main.getInstance().config.cfg.getConfigurationSection("apanel.admins." + p.getName()) != null) {
			return "" + Main.getInstance().config.cfg.getInt("apanel.admins." + p.getName() + ".level");
		} else {
			return "";
		}
	}

}
