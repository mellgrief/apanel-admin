package su.mellgrief.apanel.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import su.mellgrief.apanel.Main;

public class APanelExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length == 1) {
				try {
					if(Integer.parseInt(args[0]) >= 1 && Integer.parseInt(args[0]) <= 9) {
						int mode = Integer.parseInt(args[0]);
						handleInfoFromMode(p, mode);
					} else {
						p.sendMessage("§aЕблан число от 1 до 9");
					}
				} catch(NumberFormatException e) {
					p.sendMessage("§aЕблан это не число");
					return false;
				}
			} else if(args.length > 1) {
				if(args[0].equalsIgnoreCase("add") && Main.getInstance().config.cfg.getConfigurationSection("apanel.admins." + p.getName()) != null && p.isOp()) {
					try {
						if(Integer.parseInt(args[2]) >= 1 && Integer.parseInt(args[2]) <= 9) {
							String playerNickName = args[1];
							int mode = Integer.parseInt(args[2]);
							addAdmin(playerNickName, mode);
							p.sendMessage("§aАдминка была выдана");
						} else {
							p.sendMessage("§aЕблан число от 1 до 9");
						}
					} catch(NumberFormatException e) {
						p.sendMessage("§aЕблан это не число");
						return false;
					}
				} else if(args[0].equalsIgnoreCase("remove") && Main.getInstance().config.cfg.getConfigurationSection("apanel.admins." + p.getName()) != null) {
					String playerNickName = args[1];
					if(removeAdmin(playerNickName)) {
						p.sendMessage("§aВы сняли админку микрочелу");
					} else {
						p.sendMessage("§cТы чё еблан? У него не было админки");
					}
				} else if(args[0].equalsIgnoreCase("login")) {
					String playerPassword = args[1];
					if(Main.getInstance().mustAuth.contains(p)) {
						if(playerPassword.equalsIgnoreCase(getPasswordFromPlayer(p))) {
							Main.getInstance().mustAuth.remove(p);
							p.sendMessage("§aВы были авторизированы");
						}	
					} else {
						p.sendMessage("§4Вы уже были авторизированы");
					}
				} else if(args[0].equalsIgnoreCase("chat") && Main.getInstance().config.cfg.getConfigurationSection("apanel.admins." + p.getName()) != null){
					if(Main.getInstance().config.cfg.getInt("apanel.admins." + p.getName() + ".level") >= 4 && Main.getInstance().config.cfg.getInt("apanel.admins." + p.getName() + ".level") <= 9) {
						if(args[1].equalsIgnoreCase("on")) {
							if(!Main.acl.adminChat.contains(p) && !Main.acl.helperChat.contains(p)) {
								Main.acl.adminChat.add(p);
								p.sendMessage("§aВы включили чат админа");
							} else {
								p.sendMessage("§aВы уже состоите в каком-то из чатов");
							}
						} else if(args[1].equalsIgnoreCase("off")){
							if(Main.acl.adminChat.contains(p)) {
								Main.acl.adminChat.remove(p);
								p.sendMessage("§aВы выключили чат админа");
							}
						}
					}
				} else if(args[0].equalsIgnoreCase("hchat") && Main.getInstance().config.cfg.getConfigurationSection("apanel.admins." + p.getName()) != null){
					if(Main.getInstance().config.cfg.getInt("apanel.admins." + p.getName() + ".level") >= 1 && Main.getInstance().config.cfg.getInt("apanel.admins." + p.getName() + ".level") <= 9) {
						if(args[1].equalsIgnoreCase("on")) {
							if(!Main.acl.adminChat.contains(p) && !Main.acl.helperChat.contains(p)) {
								Main.acl.helperChat.add(p);
								p.sendMessage("§aВы включили чат хелпера");
							} else {
								p.sendMessage("§aВы уже состоите в каком-то из чатов");
							}
						} else if(args[1].equalsIgnoreCase("off")){
							if(Main.acl.helperChat.contains(p)) {
								Main.acl.helperChat.remove(p);
								p.sendMessage("§aВы выключили чат хелпера");
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	
	public String getPasswordFromPlayer(Player p) {
		if(Main.getInstance().config.cfg.getConfigurationSection("apanel.admins." + p.getName()) != null) {
			return Main.getInstance().config.cfg.getString("apanel.admins." + p.getName() + ".password");
		} else {
			return null;
		}
	}
	
	public void addAdmin(String playerNick, int mode) {
		PermissionUser pu = PermissionsEx.getUser(playerNick);
		List<String> permissionForPlayer = Main.getInstance().config.cfg.getStringList("apanel.apex." + mode + ".permissions");
		for(String perm : permissionForPlayer) {
			Main.getInstance().log.info(perm);
			pu.addPermission(perm);
		}
		Main.getInstance().config.cfg.set("apanel.admins." + playerNick + ".level", mode);
		Main.getInstance().config.cfg.set("apanel.admins." + playerNick + ".password", String.valueOf(getRandomPassword()));
		Main.getInstance().config.saveConfig();
	}
	
	public boolean removeAdmin(String playerNick) {
		if(Main.getInstance().config.cfg.getConfigurationSection("apanel.admins." + playerNick) != null) {
			PermissionUser pu = PermissionsEx.getUser(playerNick);
			List<String> permissionForPlayer = Main.getInstance().config.cfg.getStringList("apanel.apex." + Main.getInstance().config.cfg.getInt("apanel.admins." + playerNick + ".level") + ".permissions");
			for(String perm : permissionForPlayer) {
				pu.removePermission(perm);
			}
			Main.getInstance().config.cfg.set("apanel.admins." + playerNick, null);
			Main.getInstance().config.saveConfig();		
			return true;
		} else {
			return false;
		}
	}
	
	
	public void handleInfoFromMode(Player p, int mode) {
		if(Main.getInstance().config.cfg.getConfigurationSection("apanel.admins." + p.getName()) != null) {
			if(Main.getInstance().config.cfg.getInt("apanel.admins." + p.getName() + ".level") == mode) {
				List<String> permissionForPlayer = Main.getInstance().config.cfg.getStringList("apanel.apex." + mode + ".permissions");
				for(String perm : permissionForPlayer) {
					p.sendMessage("§cВам доступна комманда: " + perm);
				}
			} else { p.sendMessage("§cТы не админ такого уровня"); }
		} else {
			p.sendMessage("§cТы не админ иди нахуй");
		}
	}
	
	public static int getRandomPassword(){
        final int res = checkmath(111111, 99999999);
        return res;
	}
	
    public static int checkmath(int min, int max){
            max -= min;
            return (int) (Math.random() * ++max) + min;
    }

}
