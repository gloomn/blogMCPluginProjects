package org.blog.pluginExample;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginExample extends JavaPlugin implements Listener {

@Override
public void onEnable() {
    // Plugin startup logic
    getLogger().info("플러그인이 활성화되었습니다.");
}

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("플러그인이 비활성화되었습니다.");
    }


}