package fr.mrwarzo.hoppercounter.managers;

import fr.mrwarzo.hoppercounter.Main;
import fr.mrwarzo.hoppercounter.commands.*;

public class CommandsManager {
    public static void register(Main instance) {
        instance.getCommand("hoppercounter").setExecutor(new CommandHopperCounter());
        instance.getCommand("hoppercounter").setTabCompleter(new CommandHopperCounter());
    }
}
