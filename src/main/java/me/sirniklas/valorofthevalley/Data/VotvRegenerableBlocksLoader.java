package me.sirniklas.valorofthevalley.Data;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VotvRegenerableBlocksLoader {

    private final static VotvRegenerableBlocksLoader instance = new VotvRegenerableBlocksLoader();

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

    public HashMap<Material, RegenerableItems> MinableBlocks = new HashMap<>();
    public List<Material> RegenerableItemsList = new ArrayList<Material>();

    YamlConfiguration config = VotvConfig.getConfig();

    // Regenerating Blocks
    String blockType = "";
    String mineableBlock = "";
    String inventoryItem = "";
    String replacmentBlock = "";
    int itemAmount = 0;
    int blockExperience = 0;
    int timeTillRespawn = 0;

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
    }

    public VotvRegenerableBlocksLoader getRegenerableBlocksLoader() {
        return VotvRegenerableBlocksLoader.instance;
    }

    public static VotvRegenerableBlocksLoader getInstance() {
        return instance;
    }

}
