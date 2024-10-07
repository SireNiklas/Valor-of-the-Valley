package me.sirniklas.valorofthevalley.Game.Listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.sirniklas.valorofthevalley.Data.VOTVConfigLoader;
import me.sirniklas.valorofthevalley.PlayerUtilities.PlayerExperience;
import me.sirniklas.valorofthevalley.SirLib.Regions;
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

        ProtectedRegion wgPRegion  = wgRegions.getRegion(VOTVConfigLoader.getInstance().WorldGuardRegionName); //Get the region with the name "Mine"
        if (wgPRegion == null) {  //Null check incase there is no region with the name Mine
            return;
        }

        if (wgPRegion.contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) { //Check if the region contains the players location

            // UNIVERSAL ORE
            if (VOTVConfigLoader.getInstance().RegenerableItemsList.contains(block.getType())) {
                Material minedBlock = VOTVConfigLoader.getInstance().MinableBlocks.get(block.getType()).getMinableBlock();
                int minedBlockRespawn = VOTVConfigLoader.getInstance().MinableBlocks.get(block.getType()).getTimeTillRespawn();
                if (block.getType() == VOTVConfigLoader.getInstance().MinableBlocks.get(block.getType()).getBlockType()) {
                    inventory.addItem(new ItemStack(VOTVConfigLoader.getInstance().MinableBlocks.get(block.getType()).getInventoryItem()));
                    PlayerExperience.changePlayerExp(player, VOTVConfigLoader.getInstance().MinableBlocks.get(block.getType()).getBlockExperience());
                    event.setCancelled(true);
                    block.setType(VOTVConfigLoader.getInstance().MinableBlocks.get(block.getType()).getReplacementBlock());

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
        Regions regions = Regions.getInstance();

        if (player.hasPermission("votv.regions.build")) {
            event.setCancelled(false);
        } else {
            player.sendMessage(regions.findRegion(event.getBlock().getLocation()).toString());
            event.setCancelled(true);
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

        ProtectedRegion wgPRegion  = wgRegions.getRegion(VOTVConfigLoader.getInstance().WorldGuardRegionName); //Get the region with the name "Mine"
        if (wgPRegion == null) {  //Null check incase there is no region with the name Mine
            return;
        }

        if (wgPRegion.contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) {
            valorOfTheValley.getVotVDatabase().updatePlayerData(player, "combatlogged", 1);
        }
    }
}
