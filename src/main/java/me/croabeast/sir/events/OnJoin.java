package me.croabeast.sir.events;

import me.croabeast.sir.SIR;
import me.croabeast.sir.utils.EventUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    private final SIR main;
    private final EventUtils eventUtils;

    public OnJoin(SIR main) {
        this.main = main;
        this.eventUtils = main.getEventUtils();
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        if (main.hasLogin && main.afterLogin) return;

        Player player = event.getPlayer();
        ConfigurationSection section = eventUtils.joinSection(player);
        if (section == null) return;

        eventUtils.addPerms(section);
        eventUtils.checkSections(section, player, true);
    }
}
