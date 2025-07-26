package org.blog.voteSystem;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class VoteSystem extends JavaPlugin implements Listener, CommandExecutor {

    private final Map<UUID, String> votes = new HashMap<>();
    private final List<String> options = Arrays.asList("블레이즈", "좀비", "거미");

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("vote")).setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("VotePlugin 활성화됨!");
    }

    @Override
    public void onDisable() {
        getLogger().info("VotePlugin 비활성화됨!");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player)) return true;
        openVoteGUI(player);
        return true;
    }

    public void openVoteGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, Component.text("오늘 밤의 몬스터는?").color(NamedTextColor.DARK_GREEN));

        gui.setItem(2, createVoteItem(Material.BLAZE_ROD, "블레이즈"));
        gui.setItem(4, createVoteItem(Material.ROTTEN_FLESH, "좀비"));
        gui.setItem(6, createVoteItem(Material.STRING, "거미"));

        player.openInventory(gui);
    }

    private ItemStack createVoteItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text(name).color(NamedTextColor.YELLOW));
            item.setItemMeta(meta);
        }
        return item;
    }

    @EventHandler
    public void onVoteClick(InventoryClickEvent event) {
        if (event.getView().title().equals(Component.text("오늘 밤의 몬스터는?").color(NamedTextColor.DARK_GREEN))) {
            event.setCancelled(true); // 아이템 이동 방지

            if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) return;

            Player player = (Player) event.getWhoClicked();

            String choice = PlainTextComponentSerializer.plainText().serialize(
                    Objects.requireNonNull(event.getCurrentItem().getItemMeta().displayName())
            );


            if (votes.containsKey(player.getUniqueId())) {
                player.sendMessage(Component.text("이미 투표했어요! 중복 투표는 안돼요!").color(NamedTextColor.RED));
                return;
            }

            votes.put(player.getUniqueId(), choice);
            player.closeInventory();
            player.sendMessage(
                    Component.text("투표 완료: ", NamedTextColor.DARK_GREEN)
                            .append(Component.text(choice, NamedTextColor.YELLOW))
            );

            broadcastVoteStatus();
        }
    }

    public void broadcastVoteStatus() {
        Map<String, Integer> resultCount = new HashMap<>();

        for (String option : options) {
            resultCount.put(option, 0);
        }

        for (String vote : votes.values()) {
            resultCount.put(vote, resultCount.getOrDefault(vote, 0) + 1);
        }

        Bukkit.broadcast(Component.text("\"\uD83D\uDCCA 현재 투표 현황:\"").color(NamedTextColor.AQUA));
        for (String option : options) {
            Bukkit.broadcast(Component.text(" - " + option + ": " + resultCount.get(option) + "표"));
        }
    }
}

