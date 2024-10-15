package me.sirniklas.valorofthevalley.Game.Listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.sirniklas.valorofthevalley.Data.VotvConfigLoader;
import me.sirniklas.valorofthevalley.Data.VotvRegenerableBlocksLoader;
import me.sirniklas.valorofthevalley.BananaLibrary.PlayerUtilities.PlayerExperience;
import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class RegionListener implements Listener {

    public final ValorOfTheValley valorOfTheValley;

    public RegionListener(ValorOfTheValley valorOfTheValley) {
        this.valorOfTheValley = valorOfTheValley;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Inventory inventory = player.getInventory();
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer(); //Accessing all the region managers
        RegionManager wgRegions = container.get(BukkitAdapter.adapt(player.getWorld())); //Get the region manager which holds all the regions for the world the player is in
        if (wgRegions == null) { //Null check to ensure there are reigons in the world
            return;
        }

        ProtectedRegion wgPRegion  = wgRegions.getRegion(VotvConfigLoader.getInstance().ArenaRegionName); //Get the region with the name "Mine"
        if (wgPRegion == null) {  //Null check incase there is no region with the name Mine
            return;
        }

        if (wgPRegion.contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) { //Check if the region contains the players location

            // UNIVERSAL ORE
            if (VotvRegenerableBlocksLoader.getInstance().RegenerableItemsList.contains(block.getType())) {
                Material minedBlock = VotvRegenerableBlocksLoader.getInstance().MinableBlocks.get(block.getType()).getMinableBlock();
                int minedBlockRespawn = VotvRegenerableBlocksLoader.getInstance().MinableBlocks.get(block.getType()).getTimeTillRespawn();
                if (block.getType() == VotvRegenerableBlocksLoader.getInstance().MinableBlocks.get(block.getType()).getBlockType()) {
                    inventory.addItem(new ItemStack(VotvRegenerableBlocksLoader.getInstance().MinableBlocks.get(block.getType()).getInventoryItem()));
                    PlayerExperience.changePlayerExp(player, VotvRegenerableBlocksLoader.getInstance().MinableBlocks.get(block.getType()).getBlockExperience());
                    event.setCancelled(true);
                    block.setType(VotvRegenerableBlocksLoader.getInstance().MinableBlocks.get(block.getType()).getReplacementBlock());

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            block.setType(minedBlock);
                        }
                    }.runTaskLater(ValorOfTheValley.getInstance(), 20L * minedBlockRespawn);
                }
            }

            event.setCancelled(!player.hasPermission("votv.regions.build"));

        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer(); //Accessing all the region managers
        RegionManager wgRegions = container.get(BukkitAdapter.adapt(player.getWorld())); //Get the region manager which holds all the regions for the world the player is in
        if (wgRegions == null) { //Null check to ensure there are reigons in the world
            return;
        }

        ProtectedRegion wgPRegion  = wgRegions.getRegion(VotvConfigLoader.getInstance().ArenaRegionName); //Get the region with the name "Mine"
        if (wgPRegion == null) {  //Null check incase there is no region with the name Mine
            return;
        }

        if (wgPRegion.contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) { //Check if the region contains the players locatio
            if (player.hasPermission("votv.regions.build")) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) throws SQLException {
        Player player = event.getPlayer();
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer(); //Accessing all the region managers
        RegionManager wgRegions = container.get(BukkitAdapter.adapt(player.getWorld())); //Get the region manager which holds all the regions for the world the player is in
        if (wgRegions == null) { //Null check to ensure there are reigons in the world
            return;
        }

        ProtectedRegion wgPRegion  = wgRegions.getRegion(VotvConfigLoader.getInstance().ArenaRegionName); //Get the region with the name "Mine"
        if (wgPRegion == null) {  //Null check incase there is no region with the name Mine
            return;
        }

        if (wgPRegion.contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) {
            valorOfTheValley.getVotVDatabase().updatePlayerData(player, "combatlogged", 1);
            if (ValorOfTheValley.getInstance().playerCounts.contains(player)) {
                ValorOfTheValley.getInstance().playerCounts.remove(player);
            }
        }
    }
}
