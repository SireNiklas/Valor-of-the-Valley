package me.sirniklas.valorofthevalley.Economy;

import me.sirniklas.valorofthevalley.Data.VotvShopItemsLoader;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemCreator {

    public static ItemCreator instance;

    public ItemStack shopItem(int itemIndex) {
        ItemStack item = new ItemStack(VotvShopItemsLoader.getInstance().ShopItems.get(itemIndex).getItemDisplay());
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + VotvShopItemsLoader.getInstance().ShopItems.get(itemIndex).getItemName());

        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add("");
        itemLore.add(VotvShopItemsLoader.getInstance().ShopItems.get(itemIndex).getItemPrice() + " Medals: Left click to buy");

        itemMeta.setLore(itemLore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemCreator getInstance() {
        return instance;
    }
}
