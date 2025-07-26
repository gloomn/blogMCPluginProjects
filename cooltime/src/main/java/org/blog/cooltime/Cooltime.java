package org.blog.cooltime;

import org.bukkit.plugin.java.JavaPlugin;

public final class Cooltime extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("플러그인이 활성화되었습니다.");
        getCommand("gui").setExecutor(new GUICommand());
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("플러그인이 비활성화되었습니다.");
    }
}
