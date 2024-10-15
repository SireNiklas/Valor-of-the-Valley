package me.sirniklas.valorofthevalley.Game.Commands.SubCommands.AdminCommands.EconomyCommands;

import me.sirniklas.valorofthevalley.Economy.VotvEconomy;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TakeCommand extends SubCommand {

    VotvEconomy votvEconomy;

    public TakeCommand(VotvEconomy votvEconomy) {
        this.votvEconomy = votvEconomy;
    }

    @Override
    public String getName() {
        return "take";
    }

    @Override
    public String getDescription() {
        return "Take medals from defined player or yourself.";
    }

    @Override
    public String getSyntax() {
        return "/take <player> <amount>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args[0].equalsIgnoreCase("take")) {
            // Get Player
            String playerName = args[1];
            Player target = Bukkit.getPlayer(playerName);

            int amount = Integer.parseInt(args[2]);

            if (target != null) {
                votvEconomy.takeMedals(target, amount);

                player.sendMessage(ChatColor.GOLD + "You have given " + amount + " medals to " + target.getName() + "!");
                target.sendMessage(amount + " medals were taken from you by " + player.getName() + "!");

            } else {
                player.sendMessage(ChatColor.RED + "Player not found");
            }
        }

    }
}
