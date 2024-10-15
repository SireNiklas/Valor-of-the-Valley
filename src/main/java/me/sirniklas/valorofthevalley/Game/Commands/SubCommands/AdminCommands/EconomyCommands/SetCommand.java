package me.sirniklas.valorofthevalley.Game.Commands.SubCommands.AdminCommands.EconomyCommands;

import me.sirniklas.valorofthevalley.Economy.VotvEconomy;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetCommand extends SubCommand {

    VotvEconomy votvEconomy;

    public SetCommand(VotvEconomy votvEconomy) {
        this.votvEconomy = votvEconomy;
    }

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getDescription() {
        return "Sets players medals to specified amount.";
    }

    @Override
    public String getSyntax() {
        return "/set <player> <amount>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args[0].equalsIgnoreCase("set")) {
            String playerName = args[1];
            Player target = Bukkit.getPlayer(playerName);

            if (target != null) {
                int amount = Integer.parseInt(args[2]);

                votvEconomy.setPlayerMedals(target, amount);

                player.sendMessage(ChatColor.GOLD + "You have set " + target.getName() + " medals to " + amount + "!");
                target.sendMessage(ChatColor.GOLD + "Your medals have been set to " + amount + "!");
            } else {
                player.sendMessage(ChatColor.RED + "Player not found");
            }
        }
    }
}
