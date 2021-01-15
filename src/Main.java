package dev.stan.mc;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;


public class Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		System.out.println("ShowItem plugin enabled [+]");
		
		getCommand("item").setExecutor(new MainCommand());
	}
	
}
