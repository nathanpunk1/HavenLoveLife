package haven.Love;

import haven.Love.LoveLife;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OnCommand implements Listener {

	private LoveLife plugin;
	public OnCommand(LoveLife instance) { this.plugin = instance; }
	
	public void onPlayerCommandPreproccess(PlayerCommandPreprocessEvent event)
	{
		Player player = event.getPlayer();
		String pname = player.getName();
		String message = event.getMessage();
		
		if(!plugin.people.contains(pname))
		{
			plugin.people.add(pname);
		}
		if(message.equalsIgnoreCase("/love"))
		{
			player.sendMessage(ChatColor.RED + "========{ LoveLife }=========");
			player.sendMessage(ChatColor.RED + "/love list" + ChatColor.GREEN + " - See All Married Players");
			player.sendMessage(ChatColor.RED + "/love <name>" + ChatColor.GREEN + " - Send a Marry Request To Someone");
			player.sendMessage(ChatColor.RED + "/love accept <sender>" + ChatColor.GREEN +  " - Accept a Marry Request");
			player.sendMessage(ChatColor.RED + "/love chat" + ChatColor.GREEN + " - Enter/Leave Love Chat ");
			player.sendMessage(ChatColor.RED + "/love divorce" + ChatColor.GREEN +  " - Divorce You Partner");
			player.sendMessage(ChatColor.RED + "/love cost" + ChatColor.GREEN +  " - The Cost Of Marriage/Divorce");
			event.setCancelled(true);
			
		}
	}
	
}
