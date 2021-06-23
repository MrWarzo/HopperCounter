package fr.mrwarzo.hoppercounter.commands;

import fr.mrwarzo.hoppercounter.Main;
import fr.mrwarzo.hoppercounter.managers.Managers;
import fr.mrwarzo.hoppercounter.tools.ConfigBuilder;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils.stripAccents;

public class CommandHopperCounter implements CommandExecutor, TabCompleter {
    private final Map<String, Integer> hoppers;
    private final Map<String, Integer> observers;
    List<String> commands = cmdListMaker();

    public CommandHopperCounter() {
        Main instance = Managers.getInstance();
        this.hoppers = instance.getHopperCount();
        this.observers = instance.getObserversCount();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§6------------------------");
            sender.sendMessage("§6Nom:      HopperCounter");
            sender.sendMessage("§6Auteur:   MrWarzo");
            sender.sendMessage("§6Version:  1.0.1");
            sender.sendMessage("§6------------------------");
        } else if (args.length == 1) {
            switch (args[0]) {
                // reloadcfg ne marche pas encore
                case "reloadcfg":
                    if (sender.hasPermission(ConfigBuilder.getString("permissions.reloadcfg"))) {
                        String reloadmsg = ConfigBuilder.getString("other.reloadcfg");

                        if (sender instanceof ConsoleCommandSender) {
                            reloadmsg = stripAccents(reloadmsg);
                        }

                        ConfigBuilder.reload();
                        sender.sendMessage(reloadmsg);
                    } else {
                        sender.sendMessage(ConfigBuilder.getString("other.noperm"));
                    }
                    break;
                case "reloadchunk":
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        if (player.hasPermission(ConfigBuilder.getString("permissions.reloadchunk"))) {
                            Chunk chunk = player.getLocation().getChunk();
                            Integer hoppersCount = 0;
                            Integer observersCount = 0;

                            for (int x = 0; x < 16; x++) {
                                for (int y = 0; y < 128; y++) {
                                    for (int z = 0; z < 16; z++) {
                                        if (chunk.getBlock(x, y, z).getType().equals(Material.HOPPER)) {
                                            hoppersCount++;
                                        } else if (chunk.getBlock(x, y, z).getType().equals(Material.OBSERVER)) {
                                            observersCount++;
                                        }
                                    }
                                }
                            }

                            if (hoppers.containsKey(chunk.toString())) {
                                hoppers.replace(chunk.toString(), hoppersCount);
                            } else {
                                hoppers.put(chunk.toString(), hoppersCount);
                            }

                            if (observers.containsKey(chunk.toString())) {
                                observers.replace(chunk.toString(), observersCount);
                            } else {
                                observers.put(chunk.toString(), observersCount);
                            }



                            player.sendMessage(ConfigBuilder.getString("other.reloadchunk"));
                        } else {
                            player.sendMessage(ConfigBuilder.getString("other.noperm"));
                        }
                    } else {
                        sender.sendMessage(ConfigBuilder.getString("other.noplayer"));
                    }
                    break;
            }
        } else {
            sender.sendMessage(ConfigBuilder.getString("other.usage"));
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> result = new ArrayList<>();

        if (args.length == 1) {
            for (String str : commands) {
                if (str.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(str);
                }
            }
            return result;
        }
        return null;
    }

    public static List<String> cmdListMaker() {
        List<String> commands = new ArrayList<>();

        commands.add("reloadcfg");
        commands.add("reloadchunk");

        return commands;
    }
}
