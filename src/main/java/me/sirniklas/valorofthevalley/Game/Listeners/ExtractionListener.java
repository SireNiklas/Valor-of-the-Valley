package me.sirniklas.valorofthevalley.Game.Listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import de.netzkronehd.wgregionevents.events.RegionEnteredEvent;
import de.netzkronehd.wgregionevents.events.RegionLeftEvent;
import me.sirniklas.valorofthevalley.Data.VOTVConfigLoader;
import me.sirniklas.valorofthevalley.PlayerUtilities.PlayerExperience;
import me.sirniklas.valorofthevalley.ValorOfTheValley;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.UUID;

public class ExtractionListener implements Listener {

    public final ValorOfTheValley valorOfTheValley;

    public ExtractionListener(ValorOfTheValley valorOfTheValley) {
        this.valorOfTheValley = valorOfTheValley;
    }

    HashMap<UUID, BukkitTask> taskID = new HashMap<UUID, BukkitTask>();

//    int ExtractionTitleTimer = VOTVConfigLoader.getInstance().ExtractionTime - 2;
//    int ExtractionTime = VOTVConfigLoader.getInstance().ExtractionTime;

    public void startExtractionCountdown(Player player){
        Audience audience = Audience.audience(player);
        audience.sendTitlePart(TitlePart.TITLE, Component.text("EXTRACTING IN:"));
        audience.sendTitlePart(TitlePart.TIMES, Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(28), Duration.ofSeconds(1)));

        BukkitTask task = new BukkitRunnable() {
            int counter = 30;
            public void run() {
                counter--;
                if (counter != 0) {
                    audience.sendTitlePart(TitlePart.SUBTITLE, Component.text(counter));
                } else {
                    player.teleport(new Location(Bukkit.getServer().getWorld("spawn"), -35.5, 65, 2.50));
                    try {
                        PlayerExperience.changePlayerExp(player, valorOfTheValley.getVotVDatabase().getPlayerData(player, "exp"));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    cancel();
                }
            }

        }.runTaskTimer(ValorOfTheValley.getInstance(), 20, 20);

        taskID.put(player.getUniqueId(), task);
    }

    @EventHandler
    public void onPlayerEnter(@NotNull RegionEnteredEvent event) {
        Player player = event.getPlayer();

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer(); //Accessing all the region managers
        RegionManager wgRegions = container.get(BukkitAdapter.adapt(player.getWorld())); //Get the region manager which holds all the regions for the world the player is in
        if (wgRegions == null) { //Null check to ensure there are reigons in the world
            return;
        }

        ProtectedRegion wgPRegion  = wgRegions.getRegion(VOTVConfigLoader.getInstance().ExtractionRegionName); //Get the region with the name "Mine"
        if (wgPRegion == null) {  //Null check incase there is no region with the name Mine
            return;
        }

        if (wgPRegion.contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) { //Check if the region contains the players location
            startExtractionCountdown(player);
        }
    }

    @EventHandler
    public void onPlayerLeave(@NotNull RegionLeftEvent event) {
        Player player = event.getPlayer();

        Audience audience = Audience.audience(player);

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer(); //Accessing all the region managers
        RegionManager wgRegions = container.get(BukkitAdapter.adapt(player.getWorld())); //Get the region manager which holds all the regions for the world the player is in
        if (wgRegions == null) { //Null check to ensure there are reigons in the world
            return;
        }

        ProtectedRegion wgPRegion  = wgRegions.getRegion(VOTVConfigLoader.getInstance().ExtractionRegionName); //Get the region with the name "Mine"
        if (wgPRegion == null) {  //Null check in case there is no region with the name.
            return;
        }

        if (!wgPRegion.contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) { //Check if the region does not contain the players location
            audience.clearTitle();
//          audience.showTitle(Title.title(Component.text("EXTRACTION CANCELLED!"), Component.empty(), Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(2), Duration.ofSeconds(1))));

            if(taskID.get(player.getUniqueId()) != null){
                taskID.get(player.getUniqueId()).cancel();
            }
        }
    }
}
