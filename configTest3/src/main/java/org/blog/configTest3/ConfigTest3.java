package org.blog.configTest3;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class ConfigTest3 extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("플러그인이 활성화됐습니다.");
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String @NotNull [] args) {
        if (command.getName().equalsIgnoreCase("configtest")) {
            String pluginName = getConfig().getString("plugin-name");
            int maxPlayers = getConfig().getInt("max-players");
            boolean maintenance = getConfig().getBoolean("maintenance-mode");

            sender.sendMessage("📦 플러그인 이름: " + pluginName);
            sender.sendMessage("👥 최대 인원: " + maxPlayers);
            sender.sendMessage("🔧 점검 모드: " + (maintenance ? "켜짐" : "꺼짐"));
            return true;
        }

        return false;
    }
}
