package me.sirniklas.valorofthevalley.Data;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.joml.Vector3d;

public class VotvConfigLoader {

    private final static VotvConfigLoader instance = new VotvConfigLoader();

    // VOTV - CHAT
    private String VotvPrefix;
    private String VotvManagerPrefix;
    private String VotvEconomyPrefix;
    private String VotvDebugPrefix;
    private String VotvErrorPrefix;

    private String Newline;

    private String VotvManagerChatPrefix;
    private String VotvEconomyChatPrefix;
    private String VotvDebugChatPrefix;
    private String VotvErrorChatPrefix;

    private String VotvEconomyBalanceSelfMessage;
    private String VotvEconomyBalanceOtherMessage;

    // VOTV - REGION
    public String ArenaRegionName;
    public String SpawnRegionName;
    public String ExtractionRegionName;

    // VOTV - EXIT
    public int ExtractionTime;
    public String ExtractionWorld;

    YamlConfiguration config = VotvConfig.getConfig();

    public void getConfig() {
        VotvPrefix = config.getString("Utility.VOTV-Chat-Prefix");
        VotvManagerPrefix = config.getString("Utility.Manager-Chat-Prefix");
        VotvEconomyPrefix = config.getString("Utility.Economy-Chat-Prefix");

        VotvDebugPrefix = config.getString("Utility.Debug-Chat-Prefix");
        VotvErrorPrefix = config.getString("Utility.Error-Chat-Prefix");

        Newline = config.getString("Utility.Newline");

        VotvEconomyBalanceSelfMessage = config.getString("Utility.Economy-Chat-Balance-Self-Message");
        VotvEconomyBalanceOtherMessage = config.getString("Utility.Economy-Chat-Balance-Other-Message");

        ArenaRegionName = config.getString("Utility.WorldGuard-Region-ArenaName");
        SpawnRegionName = config.getString("Utility.WorldGuard-Region-SpawnName");
        ExtractionRegionName = config.getString("Utility.WorldGuard-Region-ExtractionName");

        ExtractionTime = config.getInt("Utility.Extraction.ExtractionTime");
        ExtractionWorld = config.getString("Utility.Extraction.Extract-World");

//        ExtractionCoordinates.x = config.getInt("Utility.Extraction.Player-Teleport-Coordinates.X");
//        ExtractionCoordinates.y = config.getInt("Utility.Extraction.Player-Teleport-Coordinates.Y");
//        ExtractionCoordinates.z = config.getInt("Utility.Extraction.Player-Teleport-Coordinates.Z");

        //ExtractionLocation = new Location(Bukkit.getServer().getWorld(ExtractionWorld), ExtractionCoordinates.x, ExtractionCoordinates.y, ExtractionCoordinates.z);

    }

    public String getVotvManagerChatPrefix() {
        VotvManagerPrefix = VotvPrefix + " " + VotvManagerPrefix;
        return VotvManagerChatPrefix;
    }

    public String getVotvEconomyChatPrefix() {
        VotvEconomyChatPrefix = VotvPrefix + " " + VotvEconomyPrefix;
        return VotvEconomyChatPrefix;
    }

    public String getVotvEconomyBalanceSelfMessage() {
        return VotvEconomyBalanceSelfMessage;
    }

    public String getVotvEconomyBalanceOtherMessage() {
        return VotvEconomyBalanceOtherMessage;
    }

    public String getVotvDebugChatPrefix() {
        VotvDebugChatPrefix = VotvPrefix + " " + VotvDebugPrefix;
        return VotvDebugChatPrefix;
    }

    public String getVotvErrorChatPrefix() {
        VotvErrorChatPrefix = VotvPrefix + " " + VotvErrorPrefix;
        return VotvErrorChatPrefix;
    }

    public String getPlayerPlaceholder(Player playerPlaceholder, String path) {
        String message = PlaceholderAPI.setPlaceholders(playerPlaceholder, path);
        //Translate colors?
        return message;
    }

    public static VotvConfigLoader getInstance() {
        return instance;
    }


}
