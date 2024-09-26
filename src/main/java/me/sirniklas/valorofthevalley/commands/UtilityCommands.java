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

        if(!(sender instanceof Player)) {
            if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
                List<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
                Audience audience = Audience.audience(playerList);
                audience.showTitle(Title.title(
                        Component.text("GAME STARTS IN:"), Component.text("5 Minutes"),
                        Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(5), Duration.ofSeconds(1))
                ));

//            sender.sendMessage("You must be a player to use this command!");
            return true;
        }

        Player player = (Player) sender;


        if(runningTasks.containsKey(player.getUniqueId())) {
            runningTasks.get(player.getUniqueId()).cancel();
            runningTasks.remove(player.getUniqueId());

            sender.sendMessage(ChatColor.RED + "You are already running!");

            return true;
        }
//
//        // Use Async not in Bukkit API
//
//
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                Player player = (Player) sender;
//                player.sendMessage(ChatColor.DARK_RED + "Commands EXECUTE late!");
//            }
//        }.runTaskLater(ValorOfTheValley.getInstance(), 20 * 60);
//
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                Player player = (Player) sender;
//                player.sendMessage(ChatColor.LIGHT_PURPLE + "Commands EXECUTE TIMER!");
//            }
//        }.runTaskTimer(ValorOfTheValley.getInstance(), 20, 20);

        if (args.length > 1) {

//              ValorOfTheValley.getInstance().scheduler.runTaskTimer(ValorOfTheValley.getInstance(), /* Lambda: */ task -> {
//                  int count = 3;
//
//                  count--;
//
//                  task.cancel(); // The entity is no longer valid, there's no point in continuing to run this task
//              } /* End of the lambda */, 0, 20);
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("set")) {
                String name;

                try {
                    name = args[1];
                } catch (IllegalArgumentException ex) {
                    sender.sendMessage("Invalid name entered!");

                    return true;
                }

                VOTVConfig.getInstance().setVotvPrefix(name);
            }
            return false;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("version")) {

            // Figure out how to cache the prefix.
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',VOTVConfig.getInstance().getVotvPrefix() + " " + "Version: 0.0-dev"));

            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        System.out.println("Args size: " + args.length);

        if (args.length == 1) {
            return Arrays.asList("version", "set", "start");
        }

        return new ArrayList<>();
    }
}
