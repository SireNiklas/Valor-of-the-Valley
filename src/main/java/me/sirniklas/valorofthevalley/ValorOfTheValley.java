package me.sirniklas.valorofthevalley;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.sirniklas.valorofthevalley.Game.Listeners.ValleyListener;
import net.milkbowl.vault.permission.Permission;
import me.sirniklas.valorofthevalley.Data.*;
import me.sirniklas.valorofthevalley.Data.Listeners.PlayerJoinListener;
import me.sirniklas.valorofthevalley.Economy.ItemCreator;
import me.sirniklas.valorofthevalley.Economy.VotvEconomy;
import me.sirniklas.valorofthevalley.Economy.Listeners.GUIListener;
import me.sirniklas.valorofthevalley.Game.Listeners.ExtractionListener;
import me.sirniklas.valorofthevalley.Game.Listeners.PlayerKillListener;
import me.sirniklas.valorofthevalley.Game.Listeners.RegionListener;
//import me.sirniklas.valorofthevalley.Game.Commands.CancelBackCommand;
import me.sirniklas.valorofthevalley.Game.Commands.CommandManager;
import me.sirniklas.valorofthevalley.Integrations.PlaceholderAPIHook;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ValorOfTheValley extends JavaPlugin {

    private static Permission permission = null;

    public List<Player> playerCounts = new ArrayList<>();

    // Dependencies
    private VotvDatabase votVDatabase;

    @Override
    public void onEnable() {
        // Plugin startup logic

        setupPermissions();

        VotvEconomy votvEconomy = new VotvEconomy();
        ItemCreator itemCreator = new ItemCreator();

        getLogger().info("Valor of the Valley is enabled!");

        // Listener Registry
        getServer().getPluginManager().registerEvents(new RegionListener(this), this);
        getServer().getPluginManager().registerEvents(new ExtractionListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerKillListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new GUIListener(VotvEconomy.getInstance()), this);
        getServer().getPluginManager().registerEvents(new ValleyListener(permission), this);

        // Command Manager
        getCommand("valorofthevalley").setExecutor(new CommandManager(votvEconomy, itemCreator, permission));

        // Getting PlaceHolderAPI as a dependency
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            // Registering PlaceholderAPI - See PlaceholderAPIHook.java
            PlaceholderAPIHook.registerHook();
        }

        // Getting and loading the config.
        VotvConfig.getInstance().load();
        VotvConfigLoader.getInstance().getConfig();
        VotvRegenerableBlocksLoader.getInstance().getRegenerableBlocks();
        VotvShopItemsLoader.getInstance().getShopItems();

        getConfig().options().copyDefaults(true);
        saveConfig();

        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
            votVDatabase = new VotvDatabase(getDataFolder().getAbsolutePath() + "/votv.db");
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
    public VotvDatabase getVotVDatabase() {
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

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        permission = rsp.getProvider();
        return permission != null;
    }

    public static Permission getPermissions() {
        return permission;
    }

    public static @NotNull ValorOfTheValley getInstance() {
        return getPlugin(ValorOfTheValley.class);
    };
}
