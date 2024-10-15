package me.sirniklas.valorofthevalley.Game.Commands.SubCommands.MemberCommands.ArenaCommands;

import me.sirniklas.valorofthevalley.Data.VotvConfigLoader;
import me.sirniklas.valorofthevalley.BananaLibrary.PlayerUtilities.PlayerExperience;
import me.sirniklas.valorofthevalley.BananaLibrary.PlayerUtilities.PlayerInventory;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommand;
import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class JoinCommand extends SubCommand {

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Teleports and clears the players inventory for Valor of the Valley.";
    }

    @Override
    public String getSyntax() {
        return "/votv join";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("join")) {
            if (PlayerInventory.isInventoryEmpty(player)) {
                // Figure out how to cache the prefix.
                try {
                    ValorOfTheValley.getInstance().getVotVDatabase().updatePlayerData(player, "exp", PlayerExperience.getPlayerExp(player));
                    player.setExp(0);
                    player.setLevel(0);
                    player.teleport(new Location(Bukkit.getServer().getWorld("VotV"), 0.500, 65.00, 0.500));

                    if (!ValorOfTheValley.getInstance().playerCounts.contains(player)) {
                        ValorOfTheValley.getInstance().playerCounts.add(player);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                player.sendMessage(VotvConfigLoader.getInstance().getVotvManagerChatPrefix() + ": " + ChatColor.RED + "Please empty your inventory to join Vallor of the Valley!");
            }
        }
    }
}
