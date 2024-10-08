package me.sirniklas.valorofthevalley.Utility.Commands;

import me.sirniklas.valorofthevalley.Data.VOTVConfigLoader;
import me.sirniklas.valorofthevalley.Economy.VOTVEconomy;
import me.sirniklas.valorofthevalley.PlayerUtilities.PlayerExperience;
import me.sirniklas.valorofthevalley.PlayerUtilities.PlayerInventory;
import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.*;

public class VOTVCommands implements CommandExecutor, TabExecutor {

    public final ValorOfTheValley valorOfTheValley;
    public final VOTVEconomy votvEconomy;

    public VOTVCommands(ValorOfTheValley valorOfTheValley, VOTVEconomy votvEconomy) {
        this.valorOfTheValley = valorOfTheValley;
        this.votvEconomy = votvEconomy;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("join")) {
            if (sender instanceof Player player) {
                if (PlayerInventory.isInventoryEmpty(player)) {
                    // Figure out how to cache the prefix.
                    try {
                        valorOfTheValley.getVotVDatabase().updatePlayerData(player, "exp", PlayerExperience.getPlayerExp(player));
                        player.setExp(0);
                        player.setLevel(0);
                        player.teleport(new Location(Bukkit.getServer().getWorld("VotV"), 0.500, 65.00, 0.500));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    player.sendMessage(VOTVConfigLoader.getInstance().ChatPrefix + ": " + ChatColor.RED + "Please empty your inventory to join Vallor of the Valley!");
                }
            }
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
            if (sender instanceof Player player) {
                player.teleport(new Location(Bukkit.getServer().getWorld("spawn"), -35.5, 65, 2.50));
                try {
                    player.setExp(0);
                    player.setLevel(0);
                    player.getInventory().clear();
                    PlayerExperience.changePlayerExp(player, valorOfTheValley.getVotVDatabase().getPlayerData(player, "exp"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (args[0].equalsIgnoreCase("shop")) {
            Player player = (Player) sender;
            Inventory shopInventory = Bukkit.createInventory(player, 9*6, "VOTV Medals Shop");

            ItemStack streamKey = new ItemStack(Material.OMINOUS_TRIAL_KEY);
            ItemMeta streamKeyItemMeta = streamKey.getItemMeta();
            streamKeyItemMeta.setDisplayName("Stream Crate Key");

            ItemStack voteKey = new ItemStack(Material.TRIAL_KEY);
            ItemMeta voteKeyItemMeta = streamKey.getItemMeta();
            voteKeyItemMeta.setDisplayName("Vote Key");

            ItemStack betaKey = new ItemStack(Material.OMINOUS_TRIAL_KEY);
            ItemMeta betaKeyItemMeta = streamKey.getItemMeta();
            betaKeyItemMeta.setDisplayName("Beta Key");

            shopInventory.setItem(11, streamKey);
            shopInventory.setItem(12, voteKey);
            shopInventory.setItem(13, betaKey);

            player.openInventory(shopInventory);
            player.setMetadata("OpenedMenu", new FixedMetadataValue(valorOfTheValley, "Shop Menu"));
            return true;
        }

        // Set Player Medals
        if (args[0].equalsIgnoreCase("set")) {
            String playerName = args[1];
            Player target = Bukkit.getPlayer(playerName);

            if (target != null) {
                int amount = Integer.parseInt(args[2]);

                votvEconomy.setPlayerMedals(target, amount);

                sender.sendMessage(ChatColor.GOLD + "You have set " + target.getName() + " medals to " + amount + "!");
                target.sendMessage(ChatColor.GOLD + "Your medals have been set to " + amount + "!");
            } else {
                sender.sendMessage(ChatColor.RED + "Player not found");
            }
        }

        // Give Player Medals
        if (args[0].equalsIgnoreCase("give")) {

            String playerName = args[1];
            Player target = Bukkit.getPlayer(playerName);

            int amount = Integer.parseInt(args[2]);

            if (target != null) {
                votvEconomy.giveMedals(target, amount);

                sender.sendMessage(ChatColor.GOLD + "You have given " + amount + " medals to " + target.getName() + "!");
                target.sendMessage(ChatColor.GOLD + "You have received " + amount + " medals from " + sender.getName() + "!");

            } else {
                sender.sendMessage(ChatColor.RED + "Player not found");
                return true;
            }
        }

        // Take Player Medals
        if (args[0].equalsIgnoreCase("take")) {
            // Get Player
            String playerName = args[1];
            Player target = Bukkit.getPlayer(playerName);

            int amount = Integer.parseInt(args[2]);

            if (target != null) {
                votvEconomy.takeMedals(target, amount);

                sender.sendMessage(ChatColor.GOLD + "You have given " + amount + " medals to " + target.getName() + "!");
                target.sendMessage(amount + " medals were taken from you by " + sender.getName() + "!");

            } else {
                sender.sendMessage(ChatColor.RED + "Player not found");
                return true;
            }
        }

        // Clear Player Medals
        if (args[0].equalsIgnoreCase("clear")) {
            if (sender instanceof Player p) {
                votvEconomy.setPlayerMedals(p, 0);
                sender.sendMessage(ChatColor.GOLD + "You have cleared your medals!");

                return true;
            }
            // Get Player
            String playerName = args[1];
            Player target = Bukkit.getPlayer(playerName);

            if (target != null) {
                votvEconomy.setPlayerMedals(target, 0);
                sender.sendMessage(ChatColor.GOLD + "You have cleared " + target.getName() + " medals!");
            } else {
                sender.sendMessage(ChatColor.RED + "Player not found");
                return true;
            }
        }

        // Player Medal Balance
        if (args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("bal")) {
            int medals = 0;
            if (sender instanceof Player p) {
                medals = votvEconomy.getPlayerMedals(p);
                sender.sendMessage(ChatColor.GOLD + "You have " + medals + " medals");

                return true;
            }

            // Get Player
            String playerName = args[1];
            Player target = Bukkit.getPlayer(playerName);
            if (target != null) {
                if (sender.hasPermission("votv.admin")) {
                    medals = votvEconomy.getPlayerMedals(target);
                    sender.sendMessage(target.getName() + " has " + medals + " medals");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Player not found");
                return true;
            }
        }

        // Pay Target Medals
        if (args[0].equalsIgnoreCase("pay")) {
            if (sender instanceof Player p) {
                // Get Player
                String playerName = args[1];
                Player target = Bukkit.getPlayer(playerName);

                if (target != null) {
                    int senderMedals = votvEconomy.getPlayerMedals(p);
                    int amount = Integer.parseInt(args[2]);

                    if (votvEconomy.checkIfPlayerHasEnough(target, amount)) {

                        senderMedals -= amount;
                        votvEconomy.setPlayerMedals(p, senderMedals);

                        int receiverMedals = votvEconomy.getPlayerMedals(target);
                        receiverMedals += amount;
                        votvEconomy.setPlayerMedals(target, receiverMedals);

                        sender.sendMessage(ChatColor.GOLD + "You have sent " + amount + " medals to " + target.getName() + "!");
                        target.sendMessage(ChatColor.GOLD + "You have received " + amount + " medals from " + sender.getName() + "!");
                    } else {
                        sender.sendMessage("You can't pay that much!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Player not found");
                    return true;
                }
            }
        }

        // Sell Target Medals
        if (args[0].equalsIgnoreCase("sell")) {
            // TODO MAYBE
        }
        if (args[0].equalsIgnoreCase("version")) {

            // Figure out how to cache the prefix.
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', VOTVConfigLoader.getInstance().ChatPrefix) + " " + "Version: 0.0-dev");

            return true;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("votv.admin")) {
            return Arrays.asList("version", "join", "leave", "balance", "pay", "set", "give", "take", "clear");
        } else {
            return Arrays.asList("join", "leave", "balance", "pay");
        }
    }
}
