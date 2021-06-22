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
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Map;

public class BlockBroken implements Listener {
    private final Map<String, Integer> hoppers;
    private final Map<String, Integer> observers;
    private final int hoppersLimit;
    private final int observersLimit;

    public BlockBroken() {
        Main instance = Managers.getInstance();
        this.hoppers = instance.getHopperCount();
        this.observers = instance.getObserversCount();
        this.hoppersLimit = ConfigBuilder.getInt("hoppers.limit");
        this.observersLimit = ConfigBuilder.getInt("observers.limit");
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Material type = block.getType();
        String chunk = block.getChunk().toString();

        if (hoppers.containsKey(chunk) || observers.containsKey(chunk)) {

            if (type.equals(Material.HOPPER)) {
                int count = hoppers.get(chunk);
                if(count >= 1) {
                    count--;
                    hoppers.replace(chunk, count);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ConfigBuilder.getString("hoppers.name") + " : " + (count) + "/" + hoppersLimit));
                }
            }
            else if(type.equals(Material.OBSERVER)) {
                int count = observers.get(chunk);
                if(count >= 1) {
                    count--;
                    observers.replace(chunk, count);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ConfigBuilder.getString("observers.name") + " : " + (count) + "/" + observersLimit));
                }
            }
        }
    }
}
