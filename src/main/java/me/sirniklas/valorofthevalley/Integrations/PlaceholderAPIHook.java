package me.sirniklas.valorofthevalley.Integrations;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.sirniklas.valorofthevalley.Data.VotvConfigLoader;
import me.sirniklas.valorofthevalley.Data.VotvRegenerableBlocksLoader;
import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholderAPIHook extends PlaceholderExpansion {

    private final ValorOfTheValley plugin; // The instance is created in the constructor and won't be modified, so it can be final

    public PlaceholderAPIHook(ValorOfTheValley plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "votv";
    }

    @Override
    public @NotNull String getAuthor() {
        return "SirNiklas";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.0-Dev";
    }

    @Override
    public boolean persist() {
        return true; //


    }

//    @Override
//    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
//        return params;
//    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("playercount")) {
            return plugin.playerCounts.size() == 0 ? "0" : String.valueOf(plugin.playerCounts.size());
        }
        return null;
    }

    public static void registerHook() {
        new PlaceholderAPIHook(ValorOfTheValley.getInstance()).register();
    }
}
