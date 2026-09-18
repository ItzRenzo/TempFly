package com.moneybags.tempfly.aesthetic.title;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import com.moneybags.tempfly.event.TitleSendEvent;
import com.moneybags.tempfly.util.U;

public class ModernTitle implements Title {

	@Override
    public void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        if (!player.isOnline()) return;
        TitleSendEvent event = new TitleSendEvent(player, title, subtitle);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        player.sendTitle(event.getTitle() == null ? null : U.cc(event.getTitle()).replace("%player%", player.getDisplayName()),
                event.getSubtitle() == null ? null : U.cc(event.getSubtitle()).replace("%player%", player.getDisplayName()),
                fadeIn, stay, fadeOut);
    }

	@Override
    public void clearTitle(Player player) {
        player.sendTitle("", "", 0, 0, 0);
    }
}
