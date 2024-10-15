package me.sirniklas.valorofthevalley.Game.Listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import de.netzkronehd.wgregionevents.events.RegionEnterEvent;
import de.netzkronehd.wgregionevents.events.RegionEnteredEvent;
import de.netzkronehd.wgregionevents.events.RegionLeaveEvent;
import de.netzkronehd.wgregionevents.events.RegionLeftEvent;
import me.sirniklas.valorofthevalley.Data.VotvConfigLoader;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ValleyListener implements Listener {

    Permission permission;

    public ValleyListener(Permission permission) {
        this.permission = permission;
    }

    @EventHandler
    public void onPlayerEnterValleySpawn(RegionEnteredEvent event) {

        Player player = event.getPlayer();

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer(); //Accessing all the region managers
        RegionManager wgRegions = container.get(BukkitAdapter.adapt(player.getWorld())); //Get the region manager which holds all the regions for the world the player is in
        if (wgRegions == null) { //Null check to ensure there are reigons in the world
            return;
        }

        ProtectedRegion wgPRegion = wgRegions.getRegion(VotvConfigLoader.getInstance().SpawnRegionName); //Get the region with the name "Mine"
        if (wgPRegion == null) {  //Null check incase there is no region with the name Mine
            return;
        }

        if (wgPRegion.contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) { //Check if the region contains the players location
            // When you join VOTV
            if (!permission.playerInGroup(player, "in-valley")) {
                permission.playerAddGroup(player, "in-valley");
            }

            // THIS GIVES PERMS ON SPAWN RE-ENTER
            if (permission.playerInGroup(player, "in-combat")) {
                // When you enter spawn from valley
                permission.playerRemoveGroup(player, "in-combat");
                permission.playerAddGroup(player, "in-valley");
            }
        }
    }

    @EventHandler
    public void onPlayerLeaveValleySpawn(RegionLeftEvent event) {

        Player player = event.getPlayer();

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer(); //Accessing all the region managers
        RegionManager wgRegions = container.get(BukkitAdapter.adapt(player.getWorld())); //Get the region manager which holds all the regions for the world the player is in
        if (wgRegions == null) { //Null check to ensure there are reigons in the world
            return;
        }

        ProtectedRegion wgPRegion = wgRegions.getRegion(VotvConfigLoader.getInstance().SpawnRegionName); //Get the region with the name "Mine"
        if (wgPRegion == null) {  //Null check incase there is no region with the name Mine
            return;
        }

        if (!wgPRegion.contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) { //Check if the region contains the players location
            if (permission.playerInGroup(player, "in-valley")) {
                permission.playerRemoveGroup(player, "in-valley");
                permission.playerAddGroup(player, "in-combat");
            }
        }
    }
}
