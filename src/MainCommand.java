package dev.stan.mc;

import java.util.Map;
import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
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
        
        if (player.hasPermission("itemshow.show")) {
        	
        	if (player.hasPermission("itemshow.sound")) {
            	
    			for (Player p: Bukkit.getOnlinePlayers()) {
    				
    				System.out.println(player.getLocation());
    				p.getWorld().playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 3.0F, 0.5F);
    			}
            }
            if (!player.hasPermission("itemshow.bypass")) {
            	
        		int cooldownTime = 300;
                if(cooldowns.containsKey(sender.getName())) {
                    long secondsLeft = ((cooldowns.get(sender.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
                    if(secondsLeft>0) {
                    
                        sender.sendMessage(ChatColor.RED + "You cant use that command for another "+ ChatColor.YELLOW + secondsLeft + ChatColor.RED + " seconds!");
                        return true;
                    }
                }
                cooldowns.put(sender.getName(), System.currentTimeMillis());
                itemCommand(player);
		    
            	} else {
            		itemCommand(player);
            	}
        }
		return false;
	}
	
	public static void itemCommand(Player player) {
		
		ItemStack inHand = player.getInventory().getItemInMainHand();
		
		if (inHand.getItemMeta().hasDisplayName() && inHand.getItemMeta().hasEnchants()) {
				
    		Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.WHITE + " is selling an item!");
    		Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage(ChatColor.WHITE + "Item: " + ChatColor.RESET + StringUtils.capitalize(inHand.getItemMeta().getDisplayName()));
			Bukkit.broadcastMessage(ChatColor.WHITE + "Enchantments: ");
			
			Map<Enchantment, Integer> enchantments = inHand.getItemMeta().getEnchants();
			for (Enchantment enchantment : enchantments.keySet()) {
					
				String nameAndLevel = ChatColor.WHITE + "- " + ChatColor.YELLOW + enchantment.getKey().getKey().replace("_", " ") + " " +  enchantments.get(enchantment);
				Bukkit.broadcastMessage(nameAndLevel);
				}						
		}
		else if (inHand.getItemMeta().hasEnchants() && !inHand.getItemMeta().hasDisplayName()) {
				
    		Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.WHITE + " is selling an item!");
    		Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage(ChatColor.WHITE + "Item: " + ChatColor.YELLOW + StringUtils.capitalize(inHand.getType().name().toLowerCase()).replace("_", " "));
			Bukkit.broadcastMessage(ChatColor.WHITE + "Enchantments: ");
				
			Map<Enchantment, Integer> enchantments = inHand.getItemMeta().getEnchants();
			for (Enchantment enchantment : enchantments.keySet()) {
					
				String nameAndLevel = ChatColor.WHITE + "- " + ChatColor.YELLOW + enchantment.getKey().getKey().replace("_", " ") + " " +  enchantments.get(enchantment);
				Bukkit.broadcastMessage(nameAndLevel);
				}
		}
		else if (!inHand.getItemMeta().hasDisplayName()) {
				
    		Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.WHITE + " is selling an item!");
    		Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage(ChatColor.WHITE + "Item: " + ChatColor.YELLOW + StringUtils.capitalize(inHand.getType().name().toLowerCase()).replace("_", " "));
			Bukkit.broadcastMessage(ChatColor.WHITE + "Enchantments: " + ChatColor.RED + "none");
        	    			
		}	            
	}
}
