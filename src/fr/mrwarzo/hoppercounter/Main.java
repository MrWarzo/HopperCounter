package fr.mrwarzo.hoppercounter;

import fr.mrwarzo.hoppercounter.managers.Managers;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static Map<String, Integer> hoppersCount;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static Map<String, Integer> observersCount;

    private File dataf;
    private FileConfiguration data;

    Managers managers = new Managers();

    @Override
    public void onEnable() {
        hoppersCount = new HashMap<>();
        observersCount = new HashMap<>();

        managers.load(this);
    }

    @Override
    public void onDisable() {
        managers.unload();
    }

    public Map<String, Integer> getHopperCount() {
        return hoppersCount;
    }

    public Map<String, Integer> getObserversCount() {
        return observersCount;
    }

    public void saveData() throws IOException {
        for (Map.Entry<String, Integer> entry : hoppersCount.entrySet()) {
            this.data.set("hc." + entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Integer> entry : observersCount.entrySet()) {
            this.data.set("oc." + entry.getKey(), entry.getValue());
        }

        this.data.save(dataf);
    }

    public void restoreData() {
        this.data.getConfigurationSection("hc").getKeys(false).forEach(key -> {
            Integer content = (Integer) this.data.get("hc." + key);
            hoppersCount.put(key, content);
        });

        this.data.getConfigurationSection("oc").getKeys(false).forEach(key -> {
            Integer content = (Integer) this.data.get("oc." + key);
            observersCount.put(key, content);
        });
    }

    public FileConfiguration getData() {
        return data;
    }

    public void createFiles() {
        dataf = new File(getDataFolder(), "data.yml");

        if(!dataf.exists()) {
            dataf.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }
        data = new YamlConfiguration();

        try {
            data.load(dataf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
