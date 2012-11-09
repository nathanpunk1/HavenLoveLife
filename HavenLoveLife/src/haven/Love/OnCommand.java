package haven.Love;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OnCommand implements Listener{
	private LoveLife plugin;
	public OnCommand(LoveLife instance) { this.plugin = instance; }
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
	{
		Player player = event.getPlayer();
		String pname = player.getName();
		String message = event.getMessage();
		
		if(!plugin.people.contains(pname))
		{
			plugin.people.add(pname);
		}
		if(message.equals("/love"))
		{
			player.sendMessage(ChatColor.GOLD + "========{ LoveLife }=========");
			player.sendMessage(ChatColor.DARK_AQUA + "/love list" + ChatColor.GRAY + " - See All Married Players");
			player.sendMessage(ChatColor.DARK_AQUA + "/love <name>" + ChatColor.GRAY + " - Send a Marry Request To Someone");
			player.sendMessage(ChatColor.DARK_AQUA + "/love accept <sender>" + ChatColor.GRAY +  " - Accept a Marry Request");
			player.sendMessage(ChatColor.DARK_AQUA + "/love chat" + ChatColor.GRAY + " - Enter/Leave Love Chat ");
			player.sendMessage(ChatColor.DARK_AQUA + "/love divorce" + ChatColor.GRAY +  " - Divorce You Partner");
			player.sendMessage(ChatColor.DARK_AQUA + "/love cost" + ChatColor.GRAY +  " - The Cost Of Marriage/Divorce");
			event.setCancelled(true);
			
		}
	}
	
}
