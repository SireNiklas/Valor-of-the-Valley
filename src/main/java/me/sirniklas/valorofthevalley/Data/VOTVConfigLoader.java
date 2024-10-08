package me.sirniklas.valorofthevalley.Data;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VOTVConfigLoader {

    private final static VOTVConfigLoader instance = new VOTVConfigLoader();

    public class RegenerableItems {
        private final Material blockType;
        private final Material minableBlock;
        private final Material inventoryItem;
        private final Material replacementBlock;
        private final int blockAmount;
        private final int blockExperience;
        private final int timeTillRespawn;

        public RegenerableItems(Material blockType, Material minableBlock, Material inventoryItem, Material replacementBlock, int itemAmount, int blockExperience, int timeTillRespawn) {
            this.blockType = blockType;
            this.minableBlock = minableBlock;
            this.inventoryItem = inventoryItem;
            this.replacementBlock = replacementBlock;
            this.blockAmount = itemAmount;
            this.blockExperience = blockExperience;
            this.timeTillRespawn = timeTillRespawn;
        }

        public Material getBlockType() { return blockType; }
        public Material getMinableBlock() { return minableBlock; }
        public Material getInventoryItem() { return inventoryItem; }
        public Material getReplacementBlock() { return replacementBlock; }
        public int getBlockAmount() { return blockAmount; }
        public int getBlockExperience() { return blockExperience; }
        public int getTimeTillRespawn() { return timeTillRespawn; }
    }

    public class ShopItems {
        private final Material itemDisplay;
        private final int itemPrice;
        private final int itemIndex;
        private final String itemCommand;

        public ShopItems(Material itemDisplay, int itemPrice, int itemIndex, String itemCommand) {
            this.itemDisplay = itemDisplay;
            this.itemPrice = itemPrice;
            this.itemIndex = itemIndex;
            this.itemCommand = itemCommand;
        }

        public Material getItemDisplay() { return itemDisplay; }
        public int getItemPrice() { return itemPrice; }
        public int getItemIndex() { return itemIndex; }
        public String getItemCommand() { return itemCommand; }
    }

    void VOTVConfigLoader() {

    }

    public HashMap<Material, RegenerableItems> MinableBlocks = new HashMap<>();
    public List<Material> RegenerableItemsList = new ArrayList<Material>();

    // VOTV - CHAT
    public String ChatPrefix;

    // VOTV - REGION
    public String WorldGuardRegionName;
    public String ExtractionRegionName;

    // VOTV - EXIT
    public int ExtractionTime;
    public String ExtractionWorld;
    public Vector3d ExtractionCoordinates;

    public Location ExtractionLocation;

    YamlConfiguration config = VOTVConfig.getConfig();

    // Regenerating Blocks
    String blockType = "";
    String mineableBlock = "";
    String inventoryItem = "";
    String replacmentBlock = "";
    int itemAmount = 0;
    int blockExperience = 0;
    int timeTillRespawn = 0;

    String itemDisplay = "";
    int itemPrice = 0;
    int itemIndex = 0;
    String itemCommand = "";


    public void getRegenerableBlocks() {
        ConfigurationSection sec = config.getConfigurationSection("Regenerable-Blocks");
        for (String key : sec.getKeys(false)) {
            blockType = config.getString("Regenerable-Blocks." + key + ".Mineable-Block");
            RegenerableItemsList.add(Material.valueOf(blockType));

            mineableBlock = config.getString("Regenerable-Blocks." + key + ".Mineable-Block");
            inventoryItem = config.getString("Regenerable-Blocks." + key + ".Inventory-Item");
            replacmentBlock = config.getString("Regenerable-Blocks." + key + ".Replacement-Block");
            itemAmount = config.getInt("Regenerable-Blocks." + key + ".Item-Amount");
            blockExperience = config.getInt("Regenerable-Blocks." + key + ".Block-Experience");
            timeTillRespawn = config.getInt("Regenerable-Blocks." + key + ".Time-Till-Respawn");

            RegenerableItems RegenBlock = new RegenerableItems(Material.getMaterial(blockType), Material.getMaterial(mineableBlock), Material.getMaterial(inventoryItem), Material.getMaterial(replacmentBlock), itemAmount, blockExperience, timeTillRespawn);
            MinableBlocks.put(Material.valueOf(blockType), RegenBlock);
        }

//        sec = config.getConfigurationSection("Shop-Items");
//        for (String key : sec.getKeys(false)) {
//            itemDisplay = config.getString("Shop-Items." + key + ".Item-Displays");
//            RegenerableItemsList.add(Material.valueOf(blockType));
//
//            itemPrice = config.getInt("Shop-Items." + key + ".Item-Price");
//            itemIndex = config.getInt("Shop-Items." + key + ".Item-Index");
//            itemCommand = config.getString("Shop-Items." + key + ".Item-Command");
//
//            RegenerableItems RegenBlock = new RegenerableItems(Material.getMaterial(blockType), Material.getMaterial(mineableBlock), Material.getMaterial(inventoryItem), Material.getMaterial(replacmentBlock), itemAmount, blockExperience, timeTillRespawn);
//            MinableBlocks.put(Material.valueOf(blockType), RegenBlock);
//        }

        ChatPrefix = config.getString("Utility.ChatPrefix");

        WorldGuardRegionName = config.getString("Utility.WorldGuard-Region-ArenaName");
        ExtractionRegionName = config.getString("Utility.WorldGuard-Region-ExtractionName");

        ExtractionTime = config.getInt("Utility.Extraction.ExtractionTime");
        ExtractionWorld = config.getString("Utility.Extraction.Extract-World");

//        ExtractionCoordinates.x = config.getInt("Utility.Extraction.Player-Teleport-Coordinates.X");
//        ExtractionCoordinates.y = config.getInt("Utility.Extraction.Player-Teleport-Coordinates.Y");
//        ExtractionCoordinates.z = config.getInt("Utility.Extraction.Player-Teleport-Coordinates.Z");

        //ExtractionLocation = new Location(Bukkit.getServer().getWorld(ExtractionWorld), ExtractionCoordinates.x, ExtractionCoordinates.y, ExtractionCoordinates.z);
    }

    public VOTVConfigLoader getVOTVConfigLoader() {
        return VOTVConfigLoader.instance;
    }

    public static VOTVConfigLoader getInstance() {
        return instance;
    }

}
