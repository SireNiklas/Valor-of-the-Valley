package me.sirniklas.valorofthevalley.Game.Commands.SubCommands.MemberCommands.ArenaCommands;

import me.sirniklas.valorofthevalley.BananaLibrary.PlayerUtilities.PlayerExperience;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommand;
import me.sirniklas.valorofthevalley.ValorOfTheValley;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class LeaveCommand extends SubCommand {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Teleports and clears the player inventory.";
    }

    @Override
    public String getSyntax() {
        return "/votv leave";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("\n<b><dark_red>VO</dark_red><red>TV</red><dark_gray> <gold>MANAGER</gold>:</dark_gray></b>\n\n<color:#ff0000><u><i><click:run_command:'/votv leave confirm'><hover:show_text:'Clicking here confirms your choice to leave.'>CLICK TO EXIT</hover></click></i></u></color>\n"));
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("leave") && args[1].equalsIgnoreCase("confirm")) {
            player.teleport(new Location(Bukkit.getServer().getWorld("spawn"), -35.5, 65, 2.50));
            try {
                player.setExp(0);
                player.setLevel(0);
                player.getInventory().clear();
                PlayerExperience.changePlayerExp(player, ValorOfTheValley.getInstance().getVotVDatabase().getPlayerData(player, "exp"));
                if (ValorOfTheValley.getInstance().playerCounts.contains(player)) {
                    ValorOfTheValley.getInstance().playerCounts.remove(player);
                }            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
