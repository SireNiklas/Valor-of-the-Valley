package me.sirniklas.valorofthevalley.Utility;

import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class VOTVConfig {

    private final static VOTVConfig instance = new VOTVConfig();

    private File file;
    private YamlConfiguration config;

    private String votvPrefix;

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

        votvPrefix = config.getString("Utility.VOTV_Chat_Prefix");
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

    public String getVotvPrefix() {
        return votvPrefix;
    }

    public void setVotvPrefix(String votvPrefix) {
        this.votvPrefix = votvPrefix;

        set("Utility.VOTV_Chat_Prefix", votvPrefix);
    }

    public static VOTVConfig getInstance() {
        return instance;
    }
}

