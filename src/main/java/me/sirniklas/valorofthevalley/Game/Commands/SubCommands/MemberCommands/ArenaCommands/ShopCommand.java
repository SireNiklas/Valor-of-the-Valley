package me.sirniklas.valorofthevalley.Game.Commands.SubCommands.MemberCommands.ArenaCommands;

import me.sirniklas.valorofthevalley.Data.VotvShopItemsLoader;
import me.sirniklas.valorofthevalley.Economy.ItemCreator;
import me.sirniklas.valorofthevalley.Game.Commands.SubCommand;
import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;

public class ShopCommand extends SubCommand {

    ItemCreator itemCreator;

    public ShopCommand(ItemCreator itemCreator) {
         this.itemCreator = itemCreator;
    }

    @Override
    public String getName() {
        return "shop";
    }

    @Override
    public String getDescription() {
        return "Opens the shop for the executing player.";
    }

    @Override
    public String getSyntax() {
        return "/votv shop";
    }

    public Inventory shopInventory;

    @Override
    public void perform(Player player, String[] args) {
        if (args[0].equalsIgnoreCase("shop")) {
            shopInventory = Bukkit.createInventory(player, 9*5, "VOTV Medals Shop");

            for (int i = 0; i < VotvShopItemsLoader.getInstance().shopItemsList.size(); i++) {
                shopInventory.setItem(i, itemCreator.shopItem(i));
            }

            player.openInventory(shopInventory);
            player.setMetadata("OpenedMenu", new FixedMetadataValue(ValorOfTheValley.getInstance(), "Shop Menu"));
        }
    }
}
