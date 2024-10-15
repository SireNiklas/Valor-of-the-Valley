package me.sirniklas.valorofthevalley.Game.Commands.SubCommands.AdminCommands.UtilityCommands;

import me.sirniklas.valorofthevalley.Data.VotvConfigLoader;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class VersionCommand extends SubCommand {

    @Override
    public String getName() {
        return "version";
    }

    @Override
    public String getDescription() {
        return "Get the current version of Valor of the Valley Plugin.";
    }

    @Override
    public String getSyntax() {
        return "/votv version";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args[0].equalsIgnoreCase("version")) {

            // Figure out how to cache the prefix.
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', VotvConfigLoader.getInstance().getVotvDebugChatPrefix()) + " " + "Version: 0.0-dev");
        }
    }
}
