package me.sirniklas.valorofthevalley.Data;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VotvShopItemsLoader {

    private final static VotvShopItemsLoader instance = new VotvShopItemsLoader();

    public class ShopItems {
        private final Material itemDisplay;
        private final String itemName;
        private final int itemPrice;
        private final int itemIndex;
        private final String itemCommand;

        public ShopItems(Material itemDisplay, String itemName, int itemPrice, int itemIndex, String itemCommand) {
            this.itemDisplay = itemDisplay;
            this.itemName = itemName;
            this.itemPrice = itemPrice;
            this.itemIndex = itemIndex;
            this.itemCommand = itemCommand;
        }

        public Material getItemDisplay() { return itemDisplay; }
        public String getItemName() { return itemName; }
        public int getItemPrice() { return itemPrice; }
        public int getItemIndex() { return itemIndex; }
        public String getItemCommand() { return itemCommand; }
    }

    void VotvShopItemsLoader() {

    }

    public HashMap<Integer, ShopItems> ShopItems = new HashMap<>();
    public List<Integer> shopItemsList = new ArrayList<>();

    YamlConfiguration config = VotvConfig.getConfig();

    // Shop Items
    String itemDisplay = "";
    String itemName = "";
    int itemPrice = 0;
    int itemIndex = 0;
    String itemCommand = "";

    public void getShopItems() {
        ConfigurationSection sec = config.getConfigurationSection("Shop-Items");
        for (String key : sec.getKeys(false)) {
            itemDisplay = config.getString("Shop-Items." + key + ".Item-Display");
            itemName = config.getString("Shop-Items." + key + ".Item-Name");
            itemPrice = config.getInt("Shop-Items." + key + ".Item-Price");
            itemIndex = config.getInt("Shop-Items." + key + ".Item-Index");
            itemCommand = config.getString("Shop-Items." + key + ".Item-Command");

            shopItemsList.add(itemIndex);

            ShopItems shopItem = new ShopItems(Material.getMaterial(itemDisplay), itemName, itemPrice, itemIndex, itemCommand);
            ShopItems.put(itemIndex, shopItem);
        }
    }

    public String getLang(Player player, String path) {
        String message = PlaceholderAPI.setPlaceholders(player, path);
        //Translate colors?
        return message;
    }

    public VotvShopItemsLoader getVotvShopItemsLoader() {
        return VotvShopItemsLoader.instance;
    }

    public static VotvShopItemsLoader getInstance() {
        return instance;
    }
}
