package me.sirniklas.valorofthevalley;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.sirniklas.valorofthevalley.Data.Commands.GetPlayerData;
import me.sirniklas.valorofthevalley.Data.Commands.SetPlayerData;
import me.sirniklas.valorofthevalley.Data.Listeners.PlayerJoinListener;
import me.sirniklas.valorofthevalley.Data.VOTVConfig;
import me.sirniklas.valorofthevalley.Data.VOTVConfigLoader;
import me.sirniklas.valorofthevalley.Data.VotVDatabase;
import me.sirniklas.valorofthevalley.Economy.VOTVEconomy;
import me.sirniklas.valorofthevalley.Game.Listeners.ExtractionListener;
import me.sirniklas.valorofthevalley.Game.Listeners.PlayerKillListener;
import me.sirniklas.valorofthevalley.Game.Listeners.RegionListener;
import me.sirniklas.valorofthevalley.Utility.Commands.VOTVCommands;
import me.sirniklas.valorofthevalley.Utility.PlaceholderAPIHook;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class ValorOfTheValley extends JavaPlugin {


    // Dependencies
    private VotVDatabase votVDatabase;
    private me.sirniklas.valorofthevalley.Economy.VOTVEconomy VOTVEconomy;

    @Override
    public void onEnable() {
        // Plugin startup logic

        getLogger().info("Valor of the Valley is enabled!");

        VOTVEconomy = new VOTVEconomy(this);


        // Listener Registry
        getServer().getPluginManager().registerEvents(new RegionListener(this), this);
        getServer().getPluginManager().registerEvents(new ExtractionListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerKillListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        // Command Registry
        getCommand("valorofthevalley").setExecutor(new VOTVCommands(this, VOTVEconomy));
        getCommand("valorofthevalleyeconomyset").setExecutor(new SetPlayerData(this));
        getCommand("valorofthevalleyeconomy").setExecutor(new GetPlayerData(this));

        // Getting PlaceHolderAPI as a dependency
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            // Registering PlaceholderAPI - See PlaceholderAPIHook.java
            PlaceholderAPIHook.registerHook();
        }

        // Getting and loading the config.
        VOTVConfig.getInstance().load();
        VOTVConfigLoader.getInstance().getRegenerableBlocks();

        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
            votVDatabase = new VotVDatabase(getDataFolder().getAbsolutePath() + "/votv.db");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Failed to connect to the database!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("Valor of the Valley is disabled!");

        // Failed to initiate and close the connection.
        try {
            votVDatabase.CloseConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Remove and rely on dependency injection
    public VotVDatabase getVotVDatabase() {
        return votVDatabase;
    }

    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }

        return (WorldGuardPlugin) plugin;
    }

    public static ValorOfTheValley getInstance() {
        return getPlugin(ValorOfTheValley.class);
    };
}

/* TODO
Exchanged from shop command - Sudo runs as user.

Eco Commands
- Clear,
- Add,
- Subtract
- Set
 */
