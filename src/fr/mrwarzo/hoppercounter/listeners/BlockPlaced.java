package fr.mrwarzo.hoppercounter.listeners;

import fr.mrwarzo.hoppercounter.Main;
import fr.mrwarzo.hoppercounter.managers.Managers;
import fr.mrwarzo.hoppercounter.tools.ConfigBuilder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Map;

public class BlockPlaced implements Listener {
    private final Map<String, Integer> hoppers;
    private final Map<String, Integer> observers;
    private final int hoppersLimit;
    private final int observersLimit;

    public BlockPlaced() {
        Main instance = Managers.getInstance();
        this.hoppers = instance.getHopperCount();
        this.observers = instance.getObserversCount();
        this.hoppersLimit = ConfigBuilder.getInt("hoppers.limit");
        this.observersLimit = ConfigBuilder.getInt("observers.limit");
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        Player player = event.getPlayer();
        Material type = block.getType();
        String chunk = block.getChunk().toString();
        int count = 0;

        if (type.equals(Material.HOPPER)) {
            if (hoppers.containsKey(chunk)) {
                count = hoppers.get(chunk);
            } else {
                hoppers.put(chunk, count);
            }

            if (count < hoppersLimit) {
                count++;
                hoppers.replace(chunk, count);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ConfigBuilder.getString("hoppers.name") + " : " + count + "/" + hoppersLimit));
            } else {
                event.setCancelled(true);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ConfigBuilder.getString("hoppers.cantplace")));
            }
        } else if (type.equals(Material.OBSERVER)) {
            if (observers.containsKey(chunk)) {
                count = observers.get(chunk);
            } else {
                observers.put(chunk, count);
            }

            if (count < observersLimit) {
                count++;
                observers.replace(chunk, count);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ConfigBuilder.getString("observers.name") + " : " + count + "/" + observersLimit));
            } else {
                event.setCancelled(true);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ConfigBuilder.getString("observers.cantplace")));
            }
        }

    }
}
