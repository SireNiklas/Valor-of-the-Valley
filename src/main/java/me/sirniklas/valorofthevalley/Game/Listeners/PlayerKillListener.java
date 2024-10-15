package me.sirniklas.valorofthevalley.Game.Listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.sirniklas.valorofthevalley.Data.VotvConfigLoader;
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

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer(); //Accessing all the region managers
        RegionManager wgRegions = container.get(BukkitAdapter.adapt(killer.getWorld())); //Get the region manager which holds all the regions for the world the player is in
        if (wgRegions == null) { //Null check to ensure there are reigons in the world
            return;
        }

        ProtectedRegion wgPRegion  = wgRegions.getRegion(VotvConfigLoader.getInstance().ArenaRegionName); //Get the region with the name "Mine"
        if (wgPRegion == null) {  //Null check incase there is no region with the name Mine
            return;
        }

        if (wgPRegion.contains(killer.getLocation().getBlockX(), killer.getLocation().getBlockY(), killer.getLocation().getBlockZ())) { //Check if the region contains the players location

        int medals = plugin.getVotVDatabase().getPlayerData(killer, "medals");
        medals += (int) (Math.random() * 10) + 1;

        plugin.getVotVDatabase().updatePlayerData(killer, "medals", medals);

        killer.sendMessage(ChatColor.RED + "+" + medals + " medals");

        killer.sendMessage(ChatColor.GREEN + "-" + plugin.getVotVDatabase().getPlayerData(killer, "medals") + " medals");
    }
        }
}
