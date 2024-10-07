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

public class GetPlayerData implements CommandExecutor {

    public final ValorOfTheValley valorOfTheValley;

    public GetPlayerData(ValorOfTheValley valorOfTheValley) {
        this.valorOfTheValley = valorOfTheValley;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        try {
            if (args.length == 0 && sender instanceof Player player) {
                int medals = valorOfTheValley.getVotVDatabase().getPlayerData(player, "medals");
                sender.sendMessage(ChatColor.GOLD + "You have " + medals + " medals");

                return true;
            }

            // Get Player
            String playerName = args[0];
            Player player = Bukkit.getPlayer(playerName);
            if (player == null) {
                sender.sendMessage(ChatColor.RED + "Player not found");
                return true;
            }

            // Get Amount
            int amount = this.valorOfTheValley.getVotVDatabase().getPlayerData(player, "medals");
            int cl = this.valorOfTheValley.getVotVDatabase().getPlayerData(player, "combatlogged");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + player.getName() + " has &e" + amount + "&a points!"));
            sender.sendMessage(ChatColor.GOLD + "You have combat logged? " + cl);

        } catch (
        SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
