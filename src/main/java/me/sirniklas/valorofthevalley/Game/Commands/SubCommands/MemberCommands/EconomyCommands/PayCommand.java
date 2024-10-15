package me.sirniklas.valorofthevalley.Game.Commands.SubCommands.MemberCommands.EconomyCommands;

import me.sirniklas.valorofthevalley.Economy.VotvEconomy;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PayCommand extends SubCommand {

    VotvEconomy votvEconomy;

    public PayCommand(VotvEconomy votvEconomy) {
        this.votvEconomy = votvEconomy;
    }

    @Override
    public String getName() {
        return "pay";
    }

    @Override
    public String getDescription() {
        return "Pay medals to players";
    }

    @Override
    public String getSyntax() {
        return "/pay <player> <amount>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("votv.pay") || player.hasPermission("votv.*")) {
            // Get Player
            String playerName = args[1];
            Player target = Bukkit.getPlayer(playerName);

            if (target != null) {
                int senderMedals = votvEconomy.getPlayerMedals(player);
                int amount = Integer.parseInt(args[2]);

                if (votvEconomy.checkIfPlayerHasEnough(target, amount)) {

                    senderMedals -= amount;
                    votvEconomy.setPlayerMedals(player, senderMedals);

                    int receiverMedals = votvEconomy.getPlayerMedals(target);
                    receiverMedals += amount;
                    votvEconomy.setPlayerMedals(target, receiverMedals);

                    player.sendMessage(ChatColor.GOLD + "You have sent " + amount + " medals to " + target.getName() + "!");
                    target.sendMessage(ChatColor.GOLD + "You have received " + amount + " medals from " + player.getName() + "!");
                } else {
                    player.sendMessage("You can't pay that much!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Player not found");
            }
        }
    }
}
