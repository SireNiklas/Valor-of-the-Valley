package me.sirniklas.valorofthevalley;

import me.sirniklas.valorofthevalley.Utility.VOTVConfig;
import me.sirniklas.valorofthevalley.commands.RegionCommand;
import me.sirniklas.valorofthevalley.commands.UtilityCommands;
import me.sirniklas.valorofthevalley.listener.RegionListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class ValorOfTheValley extends JavaPlugin {

    public BukkitScheduler scheduler = this.getServer().getScheduler();

    @Override
    public void onEnable() {
        // Plugin startup logic

        getLogger().info("Valor of the Valley is enabled!");
        getLogger().info("BEFORE LOAD");
        Regions.getInstance().load();
        getLogger().info("AFTER LOAD");

        getServer().getPluginManager().registerEvents(new RegionListener(), this);

        getCommand("valorofthevalley").setExecutor(new UtilityCommands());
        getCommand("region").setExecutor(new RegionCommand());

        VOTVConfig.getInstance().load();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("Valor of the Valley is disabled!");
    }

    public static ValorOfTheValley getInstance() {
        return getPlugin(ValorOfTheValley.class);
    };
}
