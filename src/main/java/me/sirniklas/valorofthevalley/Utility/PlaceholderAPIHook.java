package me.sirniklas.valorofthevalley.Utility;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.sirniklas.valorofthevalley.Data.VOTVConfigLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholderAPIHook extends PlaceholderExpansion {

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
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer(); //Accessing all the region managers
        RegionManager wgRegions = container.get(BukkitAdapter.adapt(player.getWorld())); //Get the region manager which holds all the regions for the world the player is in
        if (wgRegions == null) { //Null check to ensure there are reigons in the world
            return null;
        }

        ProtectedRegion wgPRegion  = wgRegions.getRegion(VOTVConfigLoader.getInstance().WorldGuardRegionName); //Get the region with the name "Mine"
        if (wgPRegion == null) {  //Null check incase there is no region with the name Mine
            return null;
        }

        int playerCount = 0;

        if (params.equalsIgnoreCase("playercount")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if(wgPRegion.contains(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()))
                    playerCount++;
            }
            return playerCount == 0 ? "0" : String.valueOf(playerCount);
        }
        return null;
    }

    public static void registerHook() {
        new PlaceholderAPIHook().register();
    }
}
