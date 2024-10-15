package me.sirniklas.valorofthevalley.Game.Commands.SubCommands.AdminCommands.UtilityCommands;

import me.sirniklas.valorofthevalley.Data.VotvConfig;
import me.sirniklas.valorofthevalley.Data.VotvConfigLoader;
import me.sirniklas.valorofthevalley.Data.VotvRegenerableBlocksLoader;
import me.sirniklas.valorofthevalley.Data.VotvShopItemsLoader;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads the configuration file";
    }

    @Override
    public String getSyntax() {
        return "/votv reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        // Clear current lists
        VotvRegenerableBlocksLoader.getInstance().MinableBlocks.clear();
        VotvRegenerableBlocksLoader.getInstance().RegenerableItemsList.clear();

        VotvShopItemsLoader.getInstance().ShopItems.clear();
        VotvShopItemsLoader.getInstance().shopItemsList.clear();


        // Reload the config and grab new variables.
        VotvConfig.getInstance().reloadConfig();

        VotvConfigLoader.getInstance().getConfig();
        VotvRegenerableBlocksLoader.getInstance().getRegenerableBlocks();
        VotvShopItemsLoader.getInstance().getShopItems();

        // Tell player configs have been reloaded.
        player.sendMessage(MiniMessage.miniMessage().deserialize("<b><dark_red>VOTV</dark_red> <yellow>DEBUG</yellow><dark_gray>:</dark_gray> </b><u><i><green><hover:show_text:'Configuration file has been successfully reloaded!'>Reload successful!</hover></green></i></u>"));
        player.sendMessage(VotvConfigLoader.getInstance().SpawnRegionName);
    }
}
