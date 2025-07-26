package org.blog.configTest1;

import org.bukkit.plugin.java.JavaPlugin;

public final class ConfigTest1 extends JavaPlugin {

    @Override
    public void onEnable() {
        // config.yml이 존재하지 않으면 기본값 저장
        saveDefaultConfig();

        // 값 읽기
        String pluginName = getConfig().getString("plugin-name");
        int maxPoints = getConfig().getInt("max-points");

        getLogger().info("플러그인 이름: " + pluginName);
        getLogger().info("최대 포인트: " + maxPoints);
    }

    @Override
    public void onDisable() {
        // 플러그인 종료 시 처리
    }
}
