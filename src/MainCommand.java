package dev.stan.mc;

import java.util.Map;
import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import net.md_5.bungee.api.ChatColor;

public class MainCommand implements CommandExecutor {
	
	public HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
        Player player = (Player) sender;
        
		int cooldownTime = 300;
        if(cooldowns.containsKey(sender.getName())) {
            long secondsLeft = ((cooldowns.get(sender.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
            if(secondsLeft>0) {
            	
                sender.sendMessage(ChatColor.RED + "You cant use that commands for another "+ ChatColor.GREEN + secondsLeft + ChatColor.RED + " seconds!");
                return true;
            }
        }
        cooldowns.put(sender.getName(), System.currentTimeMillis());     
		
		if (player.hasPermission("item.show")) {	
			
			ItemStack inHand = player.getInventory().getItemInMainHand();
			
			
			if (inHand.getItemMeta().hasDisplayName() && inHand.getItemMeta().hasEnchants()) {
				
				
				Bukkit.broadcastMessage(ChatColor.WHITE + "Item name: " + ChatColor.RESET + StringUtils.capitalize(inHand.getItemMeta().getDisplayName()));
				Bukkit.broadcastMessage(ChatColor.WHITE + "Enchantements: ");
        
				Map<Enchantment, Integer> enchantments = inHand.getItemMeta().getEnchants();
				
				
				for (Enchantment enchantment : enchantments.keySet()) {
					
					String nameAndLevel = ChatColor.WHITE + "- " + ChatColor.GREEN +  " " + enchantment.getKey().getKey() + enchantments.get(enchantment);
					Bukkit.broadcastMessage(nameAndLevel);
					
					}						
			}
			
			
			else if (inHand.getItemMeta().hasEnchants() && !inHand.getItemMeta().hasDisplayName()) {
				
				Bukkit.broadcastMessage(ChatColor.WHITE + "Item: " + ChatColor.GREEN + StringUtils.capitalize(inHand.getType().name().toLowerCase()));
				Bukkit.broadcastMessage(ChatColor.WHITE + "Enchantements: ");
				
				
				Map<Enchantment, Integer> enchantments = inHand.getItemMeta().getEnchants();
				
				for (Enchantment enchantment : enchantments.keySet()) {
					
					String nameAndLevel = ChatColor.WHITE + "- " + ChatColor.GREEN +  " " + enchantment.getKey().getKey() + enchantments.get(enchantment);
					Bukkit.broadcastMessage(nameAndLevel);
					
					}
			}
			
			
			else if (!inHand.getItemMeta().hasDisplayName()) {
				
				Bukkit.broadcastMessage(ChatColor.WHITE + "Item: " + ChatColor.GREEN + StringUtils.capitalize(inHand.getType().name().toLowerCase()));
				Bukkit.broadcastMessage(ChatColor.WHITE + "Enchantements: " + ChatColor.RED + "none");
				
			}
		} 
		
		else {
			
			Bukkit.broadcastMessage(ChatColor.RED + "You do not have permission to perform this command.");
	        return true;
		}
		return false;
	}
}
//else if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
//player.sendMessage(ChatColor.RED + "You do not have an item in your hand");
//}
