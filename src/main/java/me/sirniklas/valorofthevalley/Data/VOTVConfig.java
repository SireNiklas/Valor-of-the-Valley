package me.sirniklas.valorofthevalley.Data;

import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class VOTVConfig {

    private final static VOTVConfig instance = new VOTVConfig();

    private File file;
    private YamlConfiguration config;

    private VOTVConfig() {}

    public void load() {
        file = new File(ValorOfTheValley.getInstance().getDataFolder(), "config.yml");

        if (!file.exists())
            ValorOfTheValley.getInstance().saveResource("config.yml", false);

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save() {
        try {
            config.save(file);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }

    public static YamlConfiguration getConfig() {
        return instance.config;
    }

    public static VOTVConfig getInstance() {
        return instance;
    }
}

