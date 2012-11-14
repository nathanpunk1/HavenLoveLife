package haven.Love;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import haven.Love.OnCommand;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.economy.Economy;

public class LoveLife extends JavaPlugin{

	public static Economy economy = null;
	public Logger log = Logger.getLogger("Minecraft");
	private List<String> part = new ArrayList<String>();
	public List<String> people = new ArrayList<String>();
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	public String name = "LoveLife";
	public String version = "1.0";
	public boolean used = false;

	
	public void onEnable(){
	
		PluginManager pm = getServer().getPluginManager();
		final FileConfiguration config = this.getConfig();
		
		pm.registerEvents(new OnCommand(this), this);
		
		getCommand("love").setExecutor(new LoveLifeCMD(this));
		
		config.options().header("Configuration file for Lovelife");
		config.addDefault("Divorce_Message", "%player_1% has divorced with %player_2%");
		config.addDefault("Marriage_Message", "%player_1% has married with %player_2%");
		config.addDefault("marriage.cost", 0);
		config.addDefault("divorce.cost", 0);
		getCustomConfig().addDefault("partners", part);
		getCustomConfig().options().copyDefaults(true);
		config.options().copyDefaults(true);
		saveConfig();
		saveCustomConfig();
		
		log.info("LoveLife Enabled");
		
		if(setupEconomy().booleanValue())
		{
			System.out.println("[LoveLife]: " + ChatColor.GREEN + "Vault has successfully liked with" + economy.getName());
		}
		else
		{
			System.out.println("[LoveLife]:" +  ChatColor.DARK_RED + "Vault could not find an economy system");
		}
		
}	

	public void reloadCustomConfig()
	{
		if (customConfigFile == null)
		{
			customConfigFile = new File(getDataFolder(), "Data.yml");
		}
		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
		java.io.InputStream defConfigStream = this.getResource("Data.yml");
		if(defConfigStream != null)
		{
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			customConfig.setDefaults(defConfig);
		}
		
	}		
		public FileConfiguration getCustomConfig()
		{
			if(customConfig == null)
			{
				this.reloadCustomConfig();
			}
			return customConfig;
		}

		public void saveCustomConfig()
		{
			if(customConfig ==null || customConfigFile ==null)
			{
				return;
			}
			try
			{
				getCustomConfig().save(customConfigFile);
			}
			catch(IOException ex)
			{
				this.getLogger().log(Level.SEVERE, "could not save" + customConfigFile, ex);
			}
		}

	// This checks what the balance is of the player for Divorce
	public boolean hasMoneyDivorce(Player player, double price)
	{
		if(economy.getBalance(player.getName()) <= price)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	// This withdraws the money from the player and tells if they don't have enough
		public boolean buyDivorce(Player player, double price)
		{
			if(hasMoneyDivorce(player, price))
			{
				economy.withdrawPlayer(player.getName(), price);
				return true;
			}
			else
			{
				player.sendMessage(ChatColor.DARK_RED + "You do not have enough money");
				return false;
			}
		}
		
	// This checks what the balance is of the player for Marriage
		public boolean hasMoneyMarriage(Player player, double price)
		{
			if(economy.getBalance(player.getName()) <= price)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	
		// This withdraws the money from the player and tells if they don't have enough
		public boolean buyMarriage(Player player, double price)
		{
			if(hasMoneyMarriage(player, price))
			{
				economy.withdrawPlayer(player.getName(), price);
				return true;
			}
			else
			{
				player.sendMessage(ChatColor.DARK_RED + "You do not have enough money");
				return false;
			}
		}

	// This sets up the economy via vault
	private Boolean setupEconomy()
	{
		Plugin vault = getServer().getPluginManager().getPlugin("Vault");
		if(vault == null)
		{
			return Boolean.valueOf(false);
		}
		@SuppressWarnings("rawtypes")
		RegisteredServiceProvider economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
		if(economyProvider != null)
		{
			economy = (Economy)economyProvider.getProvider();
		}
		return Boolean.valueOf(economy != null);
	}
	
	public String fixColors(String message)
	{
		message = message.replaceAll("&0", ChatColor.BLACK + "");
		message = message.replaceAll("&1", ChatColor.DARK_BLUE + "");
		message = message.replaceAll("&2", ChatColor.DARK_GREEN + "");
		message = message.replaceAll("&3", ChatColor.DARK_AQUA + "");
		message = message.replaceAll("&4", ChatColor.DARK_RED + "");
		message = message.replaceAll("&5", ChatColor.DARK_PURPLE + "");
		message = message.replaceAll("&6", ChatColor.GOLD + "");
		message = message.replaceAll("&7", ChatColor.GRAY + "");
		message = message.replaceAll("&8", ChatColor.DARK_GRAY + "");
		message = message.replaceAll("&9", ChatColor.BLUE + "");
		message = message.replaceAll("&a", ChatColor.GREEN + "");
		message = message.replaceAll("&b", ChatColor.AQUA + "");
		message = message.replaceAll("&c", ChatColor.RED + "");
		message = message.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
		message = message.replaceAll("&e", ChatColor.YELLOW + "");
		message = message.replaceAll("&0", ChatColor.WHITE + "");
		return message;
	}
	
}
