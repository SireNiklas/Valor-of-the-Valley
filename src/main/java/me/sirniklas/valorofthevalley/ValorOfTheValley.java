package me.sirniklas.valorofthevalley;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.sirniklas.valorofthevalley.Utility.VOTVConfig;
import me.sirniklas.valorofthevalley.commands.UtilityCommands;
import me.sirniklas.valorofthevalley.listener.ExtractionListener;
import me.sirniklas.valorofthevalley.listener.RegionListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class ValorOfTheValley extends JavaPlugin {

    public BukkitScheduler scheduler = this.getServer().getScheduler();
    public WorldGuardPlugin wg;

    @Override
    public void onEnable() {
        // Plugin startup logic

        getLogger().info("Valor of the Valley is enabled!");
        getLogger().info("BEFORE LOAD");
        Regions.getInstance().load();
        getLogger().info("AFTER LOAD");

        getServer().getPluginManager().registerEvents(new RegionListener(), this);
        getServer().getPluginManager().registerEvents(new ExtractionListener(), this);

        getCommand("valorofthevalley").setExecutor(new UtilityCommands());
//        getCommand("region").setExecutor(new RegionCommands());

        VOTVConfig.getInstance().load();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("Valor of the Valley is disabled!");
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
