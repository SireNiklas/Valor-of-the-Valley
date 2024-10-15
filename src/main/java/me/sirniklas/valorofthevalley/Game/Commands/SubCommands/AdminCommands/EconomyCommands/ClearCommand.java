package me.sirniklas.valorofthevalley.Game.Commands.SubCommands.AdminCommands.EconomyCommands;

import me.sirniklas.valorofthevalley.Economy.VotvEconomy;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClearCommand extends SubCommand {

    VotvEconomy votvEconomy;

    public ClearCommand(VotvEconomy votvEconomy) {
        this.votvEconomy = votvEconomy;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Clears palyers medal balance";
    }

    @Override
    public String getSyntax() {
        return "/votv clear <player>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("votv.admin")) {
            votvEconomy.setPlayerMedals(player, 0);
            player.sendMessage(ChatColor.GOLD + "You have cleared your medals!");

            // Get Player
            String playerName = args[1];
            Player target = Bukkit.getPlayer(playerName);

            if (target != null) {
                votvEconomy.setPlayerMedals(target, 0);
                player.sendMessage(ChatColor.GOLD + "You have cleared " + target.getName() + " medals!");
            } else {
                player.sendMessage(ChatColor.RED + "Player not found");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        }
    }
}
