package org.blog.cooltime;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class GUIListener implements Listener {

    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownTime = 10 * 1000;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        if(!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();

        if(event.getView().title().equals(Component.text("스킬 GUI").color(NamedTextColor.GREEN)))
        {
            event.setCancelled(true);

            ItemStack clicked = event.getCurrentItem();
            if(clicked == null || clicked.getType() != Material.DIAMOND_BLOCK) return;

            UUID uuid = player.getUniqueId();
            long now = System.currentTimeMillis();

            if(cooldowns.containsKey(uuid))
            {
                long lastUsed = cooldowns.get(uuid);
                long timeSince = now - lastUsed;
                if(timeSince < cooldownTime)
                {
                    long secondsLeft = (cooldownTime - timeSince) / 1000;
                    player.sendMessage(Component.text("쿨다운 중입니다!").color(NamedTextColor.RED));
                    return;
                }
            }

            player.sendMessage(Component.text("파워 스킬이 발동되었습니다!").color(NamedTextColor.GOLD));
            player.getWorld().strikeLightning(player.getLocation());

            cooldowns.put(uuid, now);
        }
    }
}
