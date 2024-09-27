package me.sirniklas.valorofthevalley.listener;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.sirniklas.valorofthevalley.Region;
import me.sirniklas.valorofthevalley.Regions;
import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class RegionListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Regions regions = Regions.getInstance();

        Block block = event.getBlock();

        Region standingIn = regions.findRegion(event.getBlock().getLocation());

        Inventory inventory = player.getInventory();

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer(); //Accessing all the region managers
        RegionManager wgRegions = container.get(BukkitAdapter.adapt(player.getWorld())); //Get the region manager which holds all the regions for the world the player is in
        if (wgRegions == null) { //Null check to ensure there are reigons in the world
            return;
        }

        ProtectedRegion wgPRegion  = wgRegions.getRegion("ValorArena"); //Get the region with the name "Mine"
        if (wgPRegion == null) {  //Null check incase there is no region with the name Mine
            return;
        }

        if (wgPRegion.contains(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ())) { //Check if the region contains the players location
            // COAL ORE
            if (Material.COAL_ORE == block.getType()) {
                inventory.addItem(new ItemStack(Material.COAL));
                event.setCancelled(true);
                block.setType(Material.COBBLESTONE);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.COAL_ORE);
                    }
                }.runTaskLater(ValorOfTheValley.getInstance(), 20 * 30);

            }
            // COPPER ORE
            else if (Material.COPPER_ORE == block.getType()) {
                inventory.addItem(new ItemStack(Material.COPPER_INGOT));
                event.setCancelled(true);
                block.setType(Material.COBBLESTONE);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.COPPER_ORE);
                    }
                }.runTaskLater(ValorOfTheValley.getInstance(), 20 * 60);

            }
            // IRON ORE
            else if (Material.IRON_ORE == block.getType()) {
                inventory.addItem(new ItemStack(Material.IRON_INGOT));
                event.setCancelled(true);
                block.setType(Material.COBBLESTONE);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.IRON_ORE);
                    }
                }.runTaskLater(ValorOfTheValley.getInstance(), 20 * 120);

            }
            // GOLD ORE
            else if (Material.GOLD_ORE == block.getType()) {
                inventory.addItem(new ItemStack(Material.GOLD_INGOT));
                event.setCancelled(true);
                block.setType(Material.COBBLESTONE);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.GOLD_ORE);
                    }
                }.runTaskLater(ValorOfTheValley.getInstance(), 20 * 180);

            }
            // DIAMOND ORE
            else if (Material.DIAMOND_ORE == block.getType()) {
                inventory.addItem(new ItemStack(Material.DIAMOND));
                event.setCancelled(true);
                block.setType(Material.COBBLESTONE);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.DIAMOND_ORE);
                    }
                }.runTaskLater(ValorOfTheValley.getInstance(), 20 * 240);

            }
            // EMERALD ORE
            else if (Material.EMERALD_ORE == block.getType()) {
                inventory.addItem(new ItemStack(Material.EMERALD));
                event.setCancelled(true);
                block.setType(Material.COBBLESTONE);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.EMERALD_ORE);
                    }
                }.runTaskLater(ValorOfTheValley.getInstance(), 20 * 240);

            }
            // NETHERITE
            else if (Material.ANCIENT_DEBRIS == block.getType()) {
                inventory.addItem(new ItemStack(Material.NETHERITE_SCRAP));
                event.setCancelled(true);
                block.setType(Material.MAGMA_BLOCK);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.ANCIENT_DEBRIS);
                    }
                }.runTaskLater(ValorOfTheValley.getInstance(), 20 * 600);

            }
            // QUATERZ
            else if (Material.NETHER_QUARTZ_ORE == block.getType()) {
                inventory.addItem(new ItemStack(Material.QUARTZ));
                event.setCancelled(true);
                block.setType(Material.NETHERRACK);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.NETHER_QUARTZ_ORE);
                    }
                }.runTaskLater(ValorOfTheValley.getInstance(), 20 * 240);

            }
            // AMETHYST
            else if (Material.AMETHYST_BLOCK == block.getType()) {
                inventory.addItem(new ItemStack(Material.AMETHYST_SHARD));
                event.setCancelled(true);
                block.setType(Material.SMOOTH_BASALT);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.AMETHYST_BLOCK);
                    }
                }.runTaskLater(ValorOfTheValley.getInstance(), 20 * 120);
            }
            // Melons
            else if (Material.MELON == block.getType()) {
                Random random = new Random();
                int randomItemCount = random.nextInt((5 - 1) + 1) + 1;
                for (int i = 0; i < randomItemCount; i++) {
                    inventory.addItem(new ItemStack(Material.MELON_SLICE));
                }
                event.setCancelled(true);
                block.setType(Material.MANGROVE_ROOTS);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.MELON);
                    }
                }.runTaskLater(ValorOfTheValley.getInstance(), 20 * 15);
            }
//            // AMETHYST
//            else if (Material.AMETHYST_BLOCK == block.getType()) {
//                inventory.addItem(new ItemStack(Material.AMETHYST_SHARD));
//                event.setCancelled(true);
//                block.setType(Material.SMOOTH_BASALT);
//
//                new BukkitRunnable() {
//                    @Override
//                    public void run() {
//                        block.setType(Material.AMETHYST_BLOCK);
//                    }
//                }.runTaskLater(ValorOfTheValley.getInstance(), 20 * 120);
//            }
            // Melons
            else if (Material.OAK_WOOD == block.getType()) {
                for (int i = 0; i <= 4; i++) {
                    inventory.addItem(new ItemStack(Material.OAK_PLANKS));
                }
                event.setCancelled(true);
                block.setType(Material.STRIPPED_OAK_WOOD);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.OAK_WOOD);
                    }
                }.runTaskLater(ValorOfTheValley.getInstance(), 20 * 15);
            }

            if (player.hasPermission("votv.regions.build")) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }

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
}
