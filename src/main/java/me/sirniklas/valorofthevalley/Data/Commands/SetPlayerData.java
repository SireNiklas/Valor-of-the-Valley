package me.sirniklas.valorofthevalley.Data.Commands;

import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class SetPlayerData implements CommandExecutor {

    public final ValorOfTheValley valorOfTheValley;

    public SetPlayerData(ValorOfTheValley valorOfTheValley) {
        this.valorOfTheValley = valorOfTheValley;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length != 4) {
            sender.sendMessage(ChatColor.RED + "Usage: /votv setplayerdata <player> <amount>");
            return true;
        }

        if (args[0].equalsIgnoreCase("set")) {
            if (args[1].equalsIgnoreCase("medals")) {
                // Get Player
                String playerName = args[2];
                Player target = Bukkit.getPlayer(playerName);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Player not found");
                    return true;
                }

                // Get Amount
                int amount = Integer.parseInt(args[3]);

                try {
                    valorOfTheValley.getVotVDatabase().updatePlayerData(target, "medals", amount);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                return true;
            }
        }

        return true;
    }
}
