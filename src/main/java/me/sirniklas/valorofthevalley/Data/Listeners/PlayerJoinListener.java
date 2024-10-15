package me.sirniklas.valorofthevalley.Data.Listeners;

import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class PlayerJoinListener implements Listener {
    public final ValorOfTheValley plugin;

    public PlayerJoinListener(ValorOfTheValley plugin) {this.plugin = plugin;}

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        Player player = event.getPlayer();

        // TODO Move database related content to a class then call it here.

        if (player != null) {
            if(!player.hasPlayedBefore()) {
                this.plugin.getVotVDatabase().addPlayer(player);
            } else {

                int combatloggedValue = this.plugin.getVotVDatabase().getPlayerData(player, "combatlogged");

                ValorOfTheValley plugin1 = this.plugin;

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try {
                            plugin1.getVotVDatabase().updatePlayerData(player, "combatlogged",0);
                            if (combatloggedValue == 1) {
                                player.getInventory().clear();

                                player.setExp(0);
                                player.setLevel(0);

                                player.teleport(new Location(Bukkit.getServer().getWorld("votv"), 0.500, 65.000, 0.500));
                                if (!ValorOfTheValley.getInstance().playerCounts.contains(player)) {
                                    ValorOfTheValley.getInstance().playerCounts.add(player);
                                }
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }.runTaskLater(this.plugin, 1);
            }
        }
    }
}
