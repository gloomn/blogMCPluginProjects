package org.blog.configTest2;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class ConfigTest2 extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig(); // config.yml이 없으면 복사
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, String label, String[] args) {
        // /broadcast 명령어
        if (command.getName().equalsIgnoreCase("broadcast")) {
            String message = getConfig().getString("broadcast-message");
            Bukkit.broadcast(Component.text("📢 " + message));
            return true;
        }

        // /reloadconfig 명령어
        if (command.getName().equalsIgnoreCase("reloadconfig")) {
            reloadConfig();
            sender.sendMessage("✅ 설정을 다시 불러왔습니다.");
            return true;
        }

        return false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
