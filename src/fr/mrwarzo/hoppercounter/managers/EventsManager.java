package fr.mrwarzo.hoppercounter.managers;

import fr.mrwarzo.hoppercounter.listeners.BlockBroken;
import fr.mrwarzo.hoppercounter.listeners.BlockPlaced;
import fr.mrwarzo.hoppercounter.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventsManager {
    public static void register(Main instance) {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new BlockPlaced(), instance);
        pm.registerEvents(new BlockBroken(), instance);
    }
}
