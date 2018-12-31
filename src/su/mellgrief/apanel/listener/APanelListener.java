package su.mellgrief.apanel.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import su.mellgrief.apanel.Main;


public class APanelListener implements Listener{
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent e) {
		if(e.isCancelled() && (e.getPlayer() == null)) {
			return;
		}
		if(Main.mustAuth.contains(e.getPlayer())) {
			e.getPlayer().sendMessage("§c[§fMain§c] Даже не пытайся даун!");
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerConnect(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		String password = Main.getInstance().ape.getPasswordFromPlayer(p);
		if(password != null) {
			if(Main.mustAuth.contains(p)) {
				Main.log.info("Человек был удалён и кикнут");
				Main.mustAuth.remove(p);
				e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Перезайдите!");
			} else {
				Main.log.info("Человек был добавлен в список");
				Main.mustAuth.add(p);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent e) {
		if(Main.mustAuth.contains(e.getPlayer())) {
			Main.mustAuth.remove(e.getPlayer());
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerKick(PlayerKickEvent e) {
		if(Main.mustAuth.contains(e.getPlayer())) {
			Main.mustAuth.remove(e.getPlayer());
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onAsyncChat(AsyncPlayerChatEvent e) {
		if(Main.mustAuth.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
		if(Main.mustAuth.contains(e.getPlayer()) && !e.getMessage().contains("apanel")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerDropItemEvent(PlayerDropItemEvent e) {
		if(Main.mustAuth.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerEditBookEvent(PlayerEditBookEvent e) {
		if(Main.mustAuth.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
		if(Main.mustAuth.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		if(Main.mustAuth.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerPickupItemEvent(PlayerPickupItemEvent e) {
		if(Main.mustAuth.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onInventoryOpenEvent(InventoryOpenEvent e) {
		if(Main.mustAuth.contains((Player)e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onInventoryClickEvent(InventoryClickEvent e) {
		if(Main.mustAuth.contains((Player)e.getWhoClicked())) {
			e.setCancelled(true);
		}
	}
}
