package me.sirniklas.valorofthevalley.Utility.Commands;

import me.sirniklas.valorofthevalley.Data.VOTVConfigLoader;
import me.sirniklas.valorofthevalley.Economy.VOTVEconomy;
import me.sirniklas.valorofthevalley.PlayerUtilities.PlayerExperience;
import me.sirniklas.valorofthevalley.PlayerUtilities.PlayerInventory;
import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.*;

public class VOTVCommands implements CommandExecutor, TabExecutor {

    public final ValorOfTheValley valorOfTheValley;
    public final VOTVEconomy votEconomy;

    public VOTVCommands(ValorOfTheValley valorOfTheValley, VOTVEconomy votEconomy) {
        this.valorOfTheValley = valorOfTheValley;
        this.votEconomy = votEconomy;
    }

    private Map<UUID, BukkitTask> runningTasks = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // PLAYER ONLY COMMANDS
        if ((sender instanceof Player player)) {
//            // Connsole usable command Command, START THE GAME
//            if (args.length == 1 && args[0].equalsIgnoreCase("pre-startprompt")) {
//                player.sendMessage("PreSTART");
//                List<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
//                Audience audience = Audience.audience(playerList);
//                audience.showTitle(Title.title(
//                        Component.text("GAME STARTS IN:"), Component.text("5 Minutes"),
//                        Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(5), Duration.ofSeconds(1))
//                ));
//                return true;
//            }
//            if (args.length == 1 && args[0].equalsIgnoreCase("startprompt")) {
//                List<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
//                Audience audience = Audience.audience(playerList);
//
//                Title.Times times = Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(8), Duration.ofSeconds(1));
//
//                audience.sendTitlePart(TitlePart.TITLE, Component.text("GAME STARTS IN:"));
//                audience.sendTitlePart(TitlePart.TIMES, times);
//
//                new BukkitRunnable() {
//                    int counter = 10;
//
//                    public void run() {
//                        counter--;
//                        if (counter != 0) {
//                            audience.sendTitlePart(TitlePart.SUBTITLE, Component.text(counter));
//                        } else {
//                            audience.clearTitle();
//                            cancel();
//                            //arena.start();
//                        }
//                    }
//
//                }.runTaskTimer(ValorOfTheValley.getInstance(), 20, 20);
//            }

            if (args.length == 1 && args[0].equalsIgnoreCase("join")) {


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
                    player.sendMessage(ChatColor.RED + "Please empty your inventory to join Vallor of the Valley!");
                }
                return true;
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
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

            // Set Player Medals
            if (args.length == 1 && args[0].equalsIgnoreCase("set")) {
                String playerName = args[1];
                Player target = Bukkit.getPlayer(playerName);

                int amount = Integer.parseInt(args[2]);

                votEconomy.setPlayerMedals(target, amount);

                sender.sendMessage(ChatColor.GOLD + "You have set " + target.getName() + " medals to " + amount + "!");
                target.sendMessage(ChatColor.GOLD + "Your medals have been set to " + amount + "!");
            }

            // Give Player Medals
            if (args.length == 1 && args[0].equalsIgnoreCase("give")) {

                String playerName = args[1];
                Player target = Bukkit.getPlayer(playerName);

                if (target != null) {
                    int amount = Integer.parseInt(args[2]);
                    int receiverMedals = votEconomy.getPlayerMedals(target);

                    receiverMedals += amount;
                    votEconomy.setPlayerMedals(target, receiverMedals);

                    sender.sendMessage(ChatColor.GOLD + "You have given " + amount + " medals to " + target.getName() + "!");
                    //sender.sendMessage(ChatColor.GOLD + "You have received " + amount + " medals from " + sender.getName() + "!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Player not found");
                    return true;
                }
            }

            // Take Player Medals
            if (args.length == 1 && args[0].equalsIgnoreCase("take")) {
                if (args.length == 1 && sender instanceof Player p) {
                    // Get Player
                    String playerName = args[1];
                    Player target = Bukkit.getPlayer(playerName);

                    if (target != null) {
                        int amount = Integer.parseInt(args[2]);
                        int receiverMedals = votEconomy.getPlayerMedals(target);

                        receiverMedals -= amount;
                        votEconomy.setPlayerMedals(target, receiverMedals);

                        sender.sendMessage(ChatColor.GOLD + "You have given " + amount + " medals to " + target.getName() + "!");
                        target.sendMessage(amount + " medals were taken from you by " + sender.getName() + "!");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Player not found");
                        return true;
                    }
                }
            }

            // Clear Player Medals
            if (args.length == 1 && args[0].equalsIgnoreCase("clear")) {
                if (sender instanceof Player p) {
                    votEconomy.setPlayerMedals(p, 0);
                    sender.sendMessage(ChatColor.GOLD + "You have cleared your medals!");

                    return true;
                }
                // Get Player
                String playerName = args[1];
                Player target = Bukkit.getPlayer(playerName);

                if (target != null) {
                    votEconomy.setPlayerMedals(target, 0);
                    sender.sendMessage(ChatColor.GOLD + "You have cleared " + target.getName() + " medals!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Player not found");
                    return true;
                }
            }

            // Player Medal Balance
            if (args.length == 1 && args[0].equalsIgnoreCase("balance") || args[0].equalsIgnoreCase("bal")) {
                int medals = 0;
                if (sender instanceof Player p) {
                    medals = votEconomy.getPlayerMedals(p);
                    sender.sendMessage(ChatColor.GOLD + "You have " + medals + " medals");

                    return true;
                }

                // Get Player
                String playerName = args[1];
                Player target = Bukkit.getPlayer(playerName);
                if (target != null) {
                    if (sender.hasPermission("votv.admin")) {
                        medals = votEconomy.getPlayerMedals(target);
                        sender.sendMessage(target.getName() + " has " + medals + " medals");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Player not found");
                    return true;
                }
            }

            // Pay Target Medals
            if (args.length == 1 && args[0].equalsIgnoreCase("pay")) {
                if (sender instanceof Player p) {
                    // Get Player
                    String playerName = args[1];
                    Player target = Bukkit.getPlayer(playerName);

                    if (target != null) {
                        int senderMedals = votEconomy.getPlayerMedals(p);
                        int amount = Integer.parseInt(args[2]);

                        if (amount <= senderMedals) {

                            senderMedals -= amount;
                            votEconomy.setPlayerMedals(p, senderMedals);

                            int receiverMedals = votEconomy.getPlayerMedals(target);
                            receiverMedals += amount;
                            votEconomy.setPlayerMedals(target, receiverMedals);

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
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', VOTVConfigLoader.getInstance().ChatPrefix) + " " + "Version: 0.0-dev");

                return true;
            }
            return true;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
//        if (args.length == 1 && sender.hasPermission("votv.admin"))
//            return Arrays.asList("version");

        return Arrays.asList("join", "leave", "balance", "pay");

    }
}
