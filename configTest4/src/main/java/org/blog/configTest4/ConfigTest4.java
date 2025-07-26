package org.blog.configTest4;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ConfigTest4 extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("플러그인이 활성화됐습니다.");
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Nothing to do
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String @NotNull [] args) {
        if (command.getName().equalsIgnoreCase("bannedlist")) {
            List<String> bannedWords = getConfig().getStringList("banned-words");

            if (bannedWords.isEmpty()) {
                sender.sendMessage("🚫 금지어가 설정되어 있지 않습니다.");
                return true;
            }

            sender.sendMessage("📛 금지어 목록:");
            for (String word : bannedWords) {
                sender.sendMessage("❌ " + word);
            }

            return true;
        }

        return false;
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        String rawMessage = PlainTextComponentSerializer.plainText().serialize(event.message()).toLowerCase();

        // 1. 모든 공백 제거
        String normalized = rawMessage.replaceAll("\\s+", "");

        // 2. 특수 문자, 기호 제거
        normalized = normalized.replaceAll("[^가-힣a-z0-9]", "");

        // 3. 반복 문자 제거 (선택)
        // normalized = normalized.replaceAll("(.)\\1{2,}", "$1");  // 예: ㅂㅂㅂ → ㅂ

        // 4. 금지어 검사
        List<String> bannedWords = getConfig().getStringList("banned-words");

        for (String word : bannedWords) {
            if (normalized.contains(word.toLowerCase())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(
                        Component.text("🚫 금지어가 포함되어 있어 채팅이 취소되었습니다.", NamedTextColor.RED)
                );
                return;
            }
        }
    }

}
