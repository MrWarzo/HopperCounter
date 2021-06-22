package fr.mrwarzo.hoppercounter.managers;

import fr.mrwarzo.hoppercounter.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Managers {
    private static Main instance;
    private static Managers managers;

    public void load(Main instance) {
        Managers.instance = instance;
        Managers.managers = this;

        try {
            CommandsManager.register(instance);
            EventsManager.register(instance);

            instance.createFiles();
            instance.saveDefaultConfig();
            if (instance.getData().contains("hc") || instance.getData().contains("oc")) {
                instance.restoreData();
            }

            // Envoie d'un message de validation à la console au démarrage
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[HopperCounter] Activation reussie");
        } catch (Exception e) {

            // Envoie d'un message d'erreur à la console
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[HopperCounter] Activation interrompue");
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + e.toString());
        }
    }

    public void unload() {
        try {
            instance.saveData();
            // Envoie d'un message de validation à la console à la fermeture
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HopperCounter] Desactivation reussie");
        } catch (Exception e) {
            // Envoie d'un message d'erreur à la console
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[HopperCounter] Desactivation interrompue");
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + e.toString());
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public static Managers getManagers() {
        return managers;
    }
}
