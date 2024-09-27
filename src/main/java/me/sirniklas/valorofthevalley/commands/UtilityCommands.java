package me.sirniklas.valorofthevalley.commands;

import me.sirniklas.valorofthevalley.Utility.VOTVConfig;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.*;

public class UtilityCommands implements CommandExecutor, TabExecutor {

    private Map<UUID, BukkitTask> runningTasks = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // PLAYER ONLY COMMANDS
        if ((sender instanceof Player player)) {
            if (args.length > 1) {
                // Connsole usable command Command, START THE GAME
                if (args.length == 1 && args[0].equalsIgnoreCase("pre-startprompt")) {
                    List<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
                    Audience audience = Audience.audience(playerList);
                    audience.showTitle(Title.title(
                            Component.text("GAME STARTS IN:"), Component.text("5 Minutes"),
                            Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(5), Duration.ofSeconds(1))
                    ));
                    return true;
                }
                if (args.length == 1 && args[0].equalsIgnoreCase("set")) {
                    String name = "";

                    try {
                        name = args[1];
                    } catch (IllegalArgumentException ex) {
                        player.sendMessage("Invalid name entered!");

                    }
                    VOTVConfig.getInstance().setVotvPrefix(name);

                    return true;
                }

                if (args.length == 1 && args[0].equalsIgnoreCase("version")) {

                    // Figure out how to cache the prefix.
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', VOTVConfig.getInstance().getVotvPrefix() + " " + "Version: 0.0-dev"));
                    System.out.println("Version: 0.0-dev");

                    return true;
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            return Arrays.asList("version", "set", "start");
        }

        return new ArrayList<>();
    }
}
