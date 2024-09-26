package me.sirniklas.valorofthevalley;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public final class Regions {

    private static Regions instance = new Regions();

    private final File file;
    private final YamlConfiguration config;

    private final Set<Region> regions = new HashSet<>();

    private Regions() {
        file = new File(ValorOfTheValley.getInstance().getDataFolder(), "regions.yml");
        config = new YamlConfiguration();
    }

    public void load() {
        try {
            if (!file.exists())
                file.createNewFile();

            config.load(file);

        } catch (Throwable t) {
            t.printStackTrace();
        }

        regions.clear();

        // TODO Load regions
        if(config.isSet("regions")) {
            for (Map<?, ?> rawRegionMap : config.getMapList("regions"))
                regions.add(Region.deserialize((Map<String, Object>) rawRegionMap));

            System.out.println("Loaded Regions: " + getRegionsNames());
        }
    }

    // TODO save region
    public void save() {
        List<Map<String, Object>> serializedRegions = new ArrayList<>();

        for (Region region : regions)
            serializedRegions.add(region.serialize());

        config.set("regions", serializedRegions);

        try {
            config.save(file);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param location
     * @return
     */
    public Region findRegion(Location location) {
        for (Region region : regions)
            if (region.isWithin(location))
                return region;

        return null;
    }

    public Region findRegion(String name) {
        for (Region region : regions)
            if (region.getName().equalsIgnoreCase(name))
                return region;

        return null;
    }

    public Set<Region> getRegions() {
        return Collections.unmodifiableSet(regions);
    }

    public Set<String> getRegionsNames() {
        Set<String> names = new HashSet<>();

        for (Region region : regions)
            names.add(region.getName());

        return Collections.unmodifiableSet(names);
    }

    public void saveRegion(String name, Location primary, Location secondary) {
        regions.add(new Region(name, primary, secondary));

        save();
    }

//    public void set(String path, Object value) {
//        config.set(path, value);
//        save();
//    }

    public static Regions getInstance() {
        return instance;
    }
}
