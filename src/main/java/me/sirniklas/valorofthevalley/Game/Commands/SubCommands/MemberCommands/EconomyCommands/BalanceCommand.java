package me.sirniklas.valorofthevalley.Game.Commands.SubCommands.MemberCommands.EconomyCommands;

import me.sirniklas.valorofthevalley.Data.VotvConfigLoader;
import me.sirniklas.valorofthevalley.Economy.VotvEconomy;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BalanceCommand extends SubCommand {

    VotvEconomy votvEconomy;

    public BalanceCommand(VotvEconomy votvEconomy) {
        this.votvEconomy = votvEconomy;
    }

    @Override
    public String getName() {
        return "balance";
    }

    @Override
    public String getDescription() {
        return "Get player's balance";
    }

    @Override
    public String getSyntax() {
        return "/votv balance <player>";
    }

    @Override
    public void perform(Player player, String[] args) {
        int medals = 0;

        if (args.length == 1) {
            medals = votvEconomy.getPlayerMedals(player);
            player.sendMessage(MiniMessage.miniMessage().deserialize(VotvConfigLoader.getInstance().getVotvEconomyChatPrefix() + VotvConfigLoader.getInstance().getVotvEconomyBalanceSelfMessage().replace("%medals%", String.valueOf(medals).replace("%newline%", "\n"))));
        } else {
            // Get Player
            String playerName = args[1];
            Player target = Bukkit.getPlayer(playerName);

            if (target != null) {
                if (player.hasPermission("votv.admin")) {
                    medals = votvEconomy.getPlayerMedals(target);
                    player.sendMessage(MiniMessage.miniMessage().deserialize(VotvConfigLoader.getInstance().getVotvEconomyChatPrefix() + VotvConfigLoader.getInstance().getVotvEconomyBalanceOtherMessage().replace("%player%", target.getName()).replace("%medals%", String.valueOf(medals).replace("%newline%", "\n"))));
                }
            } else {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<b><dark_red>VOTV</dark_red> <red>ERROR</red><dark_gray>:</dark_gray></b> <red>Player not found</red>"));
            }
        }
    }
}
