package me.sirniklas.valorofthevalley.Game.Listeners;

import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.SQLException;

public class PlayerKillListener implements Listener {

    private final ValorOfTheValley plugin;

    public PlayerKillListener(ValorOfTheValley plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) throws SQLException {
//        Player player = event.getEntity();

        if(event.getPlayer().getKiller() == null) return;

        Player killer = event.getPlayer().getKiller();

        int medals = plugin.getVotVDatabase().getPlayerData(killer, "medals");
        medals += (int) (Math.random() * 10) + 1;

        plugin.getVotVDatabase().updatePlayerData(killer, "medals", medals);

        killer.sendMessage(ChatColor.RED + "+" + medals + " medals");

        killer.sendMessage(ChatColor.GREEN + "-" + plugin.getVotVDatabase().getPlayerData(killer, "medals") + " medals");
    }
}
