package com.moneybags.tempfly.aesthetic;

import com.moneybags.tempfly.aesthetic.actionbar.ActionBar;
import com.moneybags.tempfly.aesthetic.actionbar.ModernActionBar;
import org.bukkit.entity.Player;

import com.moneybags.tempfly.TempFly;

public class ActionBarAPI {
	
	private static ActionBar actionBar;
    
    public static void initialize(TempFly tempfly) {
        actionBar = new ModernActionBar(tempfly);
    }
    
    public static void sendActionBar(final Player player, final String message) {
    	actionBar.sendActionBar(player, message);
    }
    
    public static void sendActionBar(final Player player, final String message, int duration) {
    	actionBar.sendActionBar(player, message, duration);
    }
}
