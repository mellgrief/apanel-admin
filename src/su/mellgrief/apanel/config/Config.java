package su.mellgrief.apanel.config;

import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import su.mellgrief.apanel.Main;


public class Config {

	  private Main pl;
	  private File pluginFile;
	  public FileConfiguration cfg;
	  
	  public Config(Main plug, String file)
	  {
	    this.pl = plug;
	    this.pluginFile = new File(pl.getDataFolder(), file);
	  }
	  
	  public void checkOnStart() {
		  if(!pluginFile.exists()) {
			    pl.getConfig().options().copyDefaults(true);
				FileConfiguration conf = new YamlConfiguration();
				conf.set("apanel.achat", "[&7%level%] &6%player% : &3%msg%");
				conf.set("apanel.hchat", "[&7%level%] &6%player% : &3%msg%");
				for(int i = 1; i <= 9; i++) {
					List<String> tmp = Arrays.asList(new String[]{"tut.pex.admina"});
					conf.set("apanel.apex." + i + ".permissions", tmp);
				}
				
				
				conf.set("apanel.areport-404", "Репорт был не найден");
				conf.set("apanel.areport-offline", "Игрок оффлайн");
				conf.set("apanel.areport-answer", "%moder% %answer%");
				conf.set("apanel.areport-notification", "%from% %message%");
				conf.set("apanel.areport-have-report", "&aУ вас уже есть репорт жди-те ответа");
				conf.set("apanel.areport-cooldown", "&aВам осталось %time% секунд до нового репорта");
				conf.set("apanel.areport-success", "&aРепорт был успешно отправлен");

				conf.set("apanel.aask-404", "Вопрос был не найден");
				conf.set("apanel.aask-offline", "Игрок оффлайн");
				conf.set("apanel.aask-answer", "%moder% %answer%");
				conf.set("apanel.aask-notification", "%from% %message%");
				conf.set("apanel.aask-have-report", "&aУ вас уже есть вопрос жди-те ответа");
				conf.set("apanel.aask-cooldown", "&aВам осталось %time% секунд до нового вопроса");
				conf.set("apanel.aask-success", "&aВопрос был успешно отправлен");
				
				conf.set("apanel.admins.NickName.level", 1);
				conf.set("apanel.admins.NickName.password", "");
				try {
					conf.save(pluginFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
		  }
	  }

	
	  public void saveConfig() {
		  try {
			cfg.save(pluginFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	  
	  public void loadConfiguration() {
		  cfg = YamlConfiguration.loadConfiguration(pluginFile);
	  }
	
}
