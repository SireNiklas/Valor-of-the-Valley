package me.sirniklas.valorofthevalley.listener;

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

public class RegionListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Regions regions = Regions.getInstance();

        Block block = event.getBlock();

        Region standingIn = regions.findRegion(event.getBlock().getLocation());

        Inventory inventory = player.getInventory();

        if (standingIn != null) {
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
            event.setCancelled(true);
        }
    }
}
