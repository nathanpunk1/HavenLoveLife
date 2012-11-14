package haven.Love;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import haven.Love.LoveLife;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class LoveLifeCMD implements CommandExecutor{
	public List<String> reqs = new ArrayList<String>();
	public List<String> partners = new ArrayList<String>();
	private LoveLife plugin;
	public LoveLifeCMD(LoveLife instance) { this.plugin = instance; }
	private String NoPerm = "You dont have Permission!";
	private String Marry = "You Are Now Married Congratulations";
	private String po = "Your partner is offline!";
	
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args)
	{
		Player player = (Player)sender;
		
		File file = new File(plugin.getDataFolder(), "Data.yml");
		if(file.exists())
		{
			partners = plugin.getCustomConfig().getStringList("partners");
		}

	    player.hasPermission("love.*");
	    
	    if(args[0].equals("accept"))
	    {
	    	if(args.length == 2)
	    	{
	    		if(!player.hasPermission("love.*"))
	    		{
	    			player.sendMessage(ChatColor.DARK_RED + this.NoPerm);
	    			return true;
	    		}
	    		Player oPlayer = null;
	    		if(plugin.people.contains(args[1].toLowerCase()))
	    		{
	    			oPlayer = Bukkit.getServer().getPlayer(args[1].toLowerCase()); 
	    		}else
	    		{
	    			player.sendMessage(ChatColor.DARK_RED + "That player does not exist!");
	    			return true;
	    		}
	    		if(args[1] == player.getName())
	    		{
	    			player.sendMessage(ChatColor.DARK_RED + "You may not marry yourself!");
	    			return true;
	    		}else
	    		this.Accept(player, oPlayer);
	    	}else
	    	{
	    		player.sendMessage("Invalid useage: /love accept <sender>");
	    	}
	    }
	    
	    else if(args[0].equals("decline"))
	    {
	    	if(args.length == 2)
	    	{
	    		if(!player.hasPermission("love.*"))
	    		{
	    			player.sendMessage(ChatColor.DARK_RED + this.NoPerm);
	    			return true;
	    		}
	    		Player oPlayer = null;
	    		if(plugin.people.contains(args[1].toLowerCase()))
	    		{
	    			oPlayer = Bukkit.getServer().getPlayer(args[1].toLowerCase()); 
	    		}else
	    		{
	    			player.sendMessage(ChatColor.DARK_RED + "That player does not exist!");
	    			return true;
	    		}
	    		if(args[1] == player.getName())
	    		{
	    			player.sendMessage(ChatColor.DARK_RED + "You may not marry yourself!");
	    			return true;
	    		}else
	    		this.Decline(player, oPlayer);
	    	}else
	    	{
	    		player.sendMessage("Invalid useage: /love decline <sender>");
	    	}
	    }

	    else if(args[0].equals("cost"))
	    {
    		if(!player.hasPermission("love.*"))
    		{
    			player.sendMessage(ChatColor.DARK_RED + this.NoPerm);
    			return true;
    		}
	    	this.Cost(player);
	    }

	    
	    else if(args[0].equals("list"))
	    {
    		if(!player.hasPermission("love.*"))
    		{
    			player.sendMessage(ChatColor.DARK_RED + this.NoPerm);
    			return true;
    		}
	    	this.showList(player);
	    }

	    else if(args[0].equals("love"))
	    {
	    	if(!player.hasPermission("love.*"))
    		{
    			player.sendMessage(ChatColor.DARK_RED + this.NoPerm);
    			return true;
    		}
	    	if(plugin.people.contains(plugin.getCustomConfig().getString("Married." + player.getName())))
	    	{
	    		Bukkit.getServer().getPlayer(plugin.getCustomConfig().getString("Married." + player.getName()));
	    	}else
	    	{
	    		player.sendMessage(ChatColor.DARK_RED + this.po);
	    		return true;
	    	}
	    }
	    
	    else if(args[0].equals("divorce"))
	    {
    		String opname = plugin.getCustomConfig().getString("Married." + player.getName());
    		if(opname == "" || opname == null)
    		{
    			player.sendMessage(ChatColor.DARK_RED + "You are not married!");
    			return true;
    		}
    		this.divorce(player, opname);
	    }
	    
		else if(args.length == 1)
		{
			if(args[0].equalsIgnoreCase(sender.getName()))
			{
				sender.sendMessage(ChatColor.DARK_RED + "You may not marry yourself!");
				return true;
			}
    		if(!player.hasPermission("love.*"))
    		{
    			player.sendMessage(ChatColor.DARK_RED + this.NoPerm);
    			return true;
    		}
			Player oPlayer = null;
			if(plugin.people.contains(args[0].toLowerCase()))
			{
				oPlayer = Bukkit.getServer().getPlayer(args[0].toLowerCase());
			}else
			{
				player.sendMessage(ChatColor.DARK_RED + "That player does not exist!");
				return true;
			}
			this.SendRequest(player, oPlayer);
		}
		return true;
	}
	
	public void Decline(Player player, Player oPlayer){
		
	
	String pname = player.getName();
	String opname = oPlayer.getName();
	
	if(!reqs.contains(pname))
	{
		player.sendMessage(ChatColor.DARK_RED + "You dont have a marry request!");
		return;
	}
	if(!reqs.contains(opname))
	{
		player.sendMessage(ChatColor.DARK_RED + "You dont have a marry request from that player!");
		return;
	}
	if(reqs.contains(opname))
	{
		reqs.remove(opname);
	}
	if(reqs.contains(pname))
	{
		reqs.remove(pname);
	}
	player.sendMessage(ChatColor.RED + "Request denied" + " " + opname);
	oPlayer.sendMessage(ChatColor.RED + "Request denied" + " " + pname);
	}
	
	public void SendRequest(Player player, Player oPlayer)
	{
		String pname = player.getName();
		String opname = oPlayer.getName();

		if(plugin.getCustomConfig().getString("Married." + pname) != null && plugin.getCustomConfig().getString("Married." + pname) != "")
		{
			player.sendMessage(ChatColor.DARK_RED + "You are already Married!");
			return;
		}
		if(plugin.getCustomConfig().getString("Married." + opname) != null && plugin.getCustomConfig().getString("Married." + opname) != "")
		{
			player.sendMessage(ChatColor.DARK_RED + opname + " is already Married!");
			return;
		}
		if(plugin.getConfig().getInt("marriage.cost") != 0)
		{
			if(plugin.buyMarriage(player, plugin.getConfig().getInt("marriage.cost")))
			{
				player.sendMessage(ChatColor.GREEN + "$" + plugin.getConfig().getInt("marriage.cost") + " has been taken form your balance!");
			}else
			{
				return;
			}
		}
		player.sendMessage(ChatColor.GREEN + "Request has been sent!");
		oPlayer.sendMessage(ChatColor.GREEN + pname + " requested you to marry, type: " + ChatColor.LIGHT_PURPLE + "/love accept <sender>" + ChatColor.GREEN + " to accept");
		reqs.add(opname);
		reqs.add(pname);
	}

	public void Accept(Player player, Player oPlayer)
	{
	
		String pname = player.getName();
		String opname = oPlayer.getName();
		
		if(plugin.getCustomConfig().getString("Married." + pname) != null && plugin.getCustomConfig().getString("Married." + pname) != "")
		{
			player.sendMessage(ChatColor.DARK_RED + "You are already Married!");
			return;
		}
		if(plugin.getCustomConfig().getString("Married." + opname) != null && plugin.getCustomConfig().getString("Married." + opname) != "")
		{
			player.sendMessage(ChatColor.DARK_RED + opname + "is already Married!");
			return;
		}
		if(!reqs.contains(pname))
		{
			player.sendMessage(ChatColor.DARK_RED + "You dont have a marry request!");
			return;
		}
		if(!reqs.contains(opname))
		{
			player.sendMessage(ChatColor.DARK_RED + "You dont have a marry request from that player!");
			return;
		}
		plugin.getCustomConfig().set("Married." + pname, opname);
		plugin.getCustomConfig().set("Married." + opname, pname);
		plugin.getCustomConfig().set("partners", partners);
		plugin.saveCustomConfig();
		player.sendMessage(ChatColor.GREEN + this.Marry + " " + opname);
		oPlayer.sendMessage(ChatColor.GREEN + this.Marry + " " + pname);
		String message = plugin.getConfig().getString("Marriage_Message");
		message = message.replaceAll("%player_1%", pname);
		message = message.replaceAll("%player_2%", opname);
		message = plugin.fixColors(message);
		Bukkit.getServer().broadcastMessage(message);
	}

	public void Cost(Player player)
	{	
		player.sendMessage(ChatColor.GREEN + "Marriage: " + "$" + plugin.getConfig().getInt("marriage.cost"));
		player.sendMessage(ChatColor.GREEN + "Divorce: " + "$" + plugin.getConfig().getInt("divorce.cost"));
	}
	
	public void showList(Player player)
	{	
		player.sendMessage(ChatColor.GOLD + ".oOo.---------" + ChatColor.YELLOW + "Married Couples" + ChatColor.GOLD + "----------.oOo.");

		for(String partners : plugin.getCustomConfig().getStringList("partners"))
		{
			player.sendMessage(partners + " + " + plugin.getCustomConfig().getString("Married." + partners));
		}
	}

	public void divorce(Player player, String opname)
	{
		if(plugin.getConfig().getInt("divorce.cost") != 0)
		{
			if(plugin.buyDivorce(player, plugin.getConfig().getInt("divorce.cost")))
			{
				player.sendMessage(ChatColor.GREEN + "$" + plugin.getConfig().getInt("divorce.cost") + " has been taken form your balance!");
			}else
			{
				return;
			}
			plugin.getConfig().getInt("divorce.cost");
		}
		String pname = player.getName();
		plugin.getCustomConfig().set("Married." + pname, "");
		plugin.getCustomConfig().set("Married." + opname, "");
		plugin.saveCustomConfig();
		if(partners.contains(opname))
		{
			partners.remove(opname);
			plugin.getCustomConfig().set("partners", partners);
			plugin.saveCustomConfig();
		}
		if(partners.contains(pname))
		{
			partners.remove(pname);
			plugin.getCustomConfig().set("partners", partners);
			plugin.saveCustomConfig();
		}
		player.sendMessage(ChatColor.GREEN + "You have divorced your partner");
		String div = plugin.getConfig().getString("Divorce_Message");
		div = div.replaceAll("%player_1%", pname);
		div = div.replaceAll("%player_2%", opname);
		div = plugin.fixColors(div);
		Bukkit.getServer().broadcastMessage(div);
	}


}