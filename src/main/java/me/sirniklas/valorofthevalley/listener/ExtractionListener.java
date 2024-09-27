package me.sirniklas.valorofthevalley.listener;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import de.netzkronehd.wgregionevents.events.RegionEnteredEvent;
import de.netzkronehd.wgregionevents.events.RegionLeftEvent;
import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class ExtractionListener implements Listener {

    HashMap<UUID, BukkitTask> taskID = new HashMap<UUID, BukkitTask>();


    public void startExtractionCountdown(Player player){
        BukkitTask task = new BukkitRunnable() {
            int counter = 5;
            public void run() {
                counter--;
                if (counter != 0) {
                    player.sendMessage("Extraction starting in " + counter);
                } else {
                    player.teleport(new Location(Bukkit.getWorld("world"), 0, 0, 0));
                    cancel();
                    //arena.start();
                }
            }

        }.runTaskTimer(ValorOfTheValley.getInstance(), 20, 20);

        taskID.put(player.getUniqueId(), task);
    }

    @EventHandler
    public void onPlayerEnter(RegionEnteredEvent event) {
        Player player = event.getPlayer();

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer(); //Accessing all the region managers
        RegionManager wgRegions = container.get(BukkitAdapter.adapt(player.getWorld())); //Get the region manager which holds all the regions for the world the player is in
        if (wgRegions == null) { //Null check to ensure there are reigons in the world
            return;
        }

        ProtectedRegion wgPRegion  = wgRegions.getRegion("extraction1"); //Get the region with the name "Mine"
        if (wgPRegion == null) {  //Null check incase there is no region with the name Mine
            return;
        }

        if (wgPRegion.contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) { //Check if the region contains the players location
            startExtractionCountdown(player);
//            new BukkitRunnable() {
//                int counter = 5;
//                public void run() {
//                    counter--;
//                    if(counter  != 0) {
//                        player.sendMessage("Starting in " + counter);
//                    } else {
//                        player.teleport(new Location(Bukkit.getWorld("world"), 0, 0, 0));
//                        cancel();
//                        //arena.start();
//                    }
//
//
//                }
//            }.runTaskTimer(ValorOfTheValley.getInstance(), 20, 20);

        }
    }

    @EventHandler
    public void onPlayerEnter(RegionLeftEvent event) {
        Player player = event.getPlayer();

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer(); //Accessing all the region managers
        RegionManager wgRegions = container.get(BukkitAdapter.adapt(player.getWorld())); //Get the region manager which holds all the regions for the world the player is in
        if (wgRegions == null) { //Null check to ensure there are reigons in the world
            return;
        }

        ProtectedRegion wgPRegion  = wgRegions.getRegion("extraction1"); //Get the region with the name "Mine"
        if (wgPRegion == null) {  //Null check incase there is no region with the name Mine
            return;
        }

        if (!wgPRegion.contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) { //Check if the region does not contain the players location
            taskID.get(player.getUniqueId()).cancel();

            player.sendMessage("Canceling Extraction!");
        }
    }
}
