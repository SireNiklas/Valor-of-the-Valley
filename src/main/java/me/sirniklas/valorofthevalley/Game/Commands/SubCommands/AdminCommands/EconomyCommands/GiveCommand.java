package me.sirniklas.valorofthevalley.Game.Commands.SubCommands.AdminCommands.EconomyCommands;

import me.sirniklas.valorofthevalley.Economy.VotvEconomy;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GiveCommand extends SubCommand {

    public VotvEconomy votvEconomy;

    public GiveCommand(VotvEconomy votvEconomy) {
        this.votvEconomy = votvEconomy;
    }

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public String getDescription() {
        return "Give medals to player define or yourself.";
    }

    @Override
    public String getSyntax() {
        return "/give <player> <amount>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args[0].equalsIgnoreCase("give")) {

            String playerName = args[1];
            Player target = Bukkit.getPlayer(playerName);

            int amount = Integer.parseInt(args[2]);

            if (target != null) {
                votvEconomy.giveMedals(target, amount);

                player.sendMessage(ChatColor.GOLD + "You have given " + amount + " medals to " + target.getName() + "!");
                target.sendMessage(ChatColor.GOLD + "You have received " + amount + " medals from " + player.getName() + "!");

            } else {
                player.sendMessage(ChatColor.RED + "Player not found");
            }
        }
    }
}
