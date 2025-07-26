package org.blog.itemBuff;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class ItemBuff extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("플러그인 활성화됨!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("플러그인 비활성화됨!");
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(this, () -> {
            Material heldItem = player.getInventory().getItemInMainHand().getType();

            // 기존 효과 제거
            player.removePotionEffect(PotionEffectType.SPEED);
            player.removePotionEffect(PotionEffectType.BLINDNESS);

            // 조건에 따라 효과 부여
            if (heldItem == Material.GOLDEN_SWORD) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true, false));
                player.sendMessage("§e황금검을 들고 있어 신속 효과가 적용되었어요!");
            } else if (heldItem == Material.DIAMOND_SWORD) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 1, true, false));
                player.sendMessage("§e다이아몬드검을 들고 있어 실명 효과가 적용되었어요!");
            }
        }, 1L);
    }

}
