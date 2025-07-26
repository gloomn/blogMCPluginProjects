package org.blog.weather;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Weather extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("플러그인이 활성화됐습니다.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("플러그인이 비활성화됐습니다.");
    }

    public boolean onCommand(CommandSender cs, Command c, String s, String[] a) {
        Player player = (Player) cs;
        if (c.getName().equalsIgnoreCase("w")) {
            if (a.length > 0) {
                World world = player.getWorld();
                if (a[0].equalsIgnoreCase("sun")) {
                    world.setStorm(false);
                    world.setThundering(false);
                } else if (a[0].equalsIgnoreCase("rain")) {
                    world.setStorm(true);
                    world.setThundering(false);
                } else if (a[0].equalsIgnoreCase("storm")) {
                    world.setStorm(true);
                    world.setThundering(true);
                } else {
                    // 잘못된 입력에 대한 처리
                    player.sendMessage(Component.text("올바른 날씨를 입력하시오! (sun, rain, storm)").color(NamedTextColor.RED));
                    return false;  // 명령어 처리 실패
                }
            } else {
                player.sendMessage(Component.text("날씨를 입력하시오!").color(NamedTextColor.RED));
                return false;  // 인자가 없을 때 처리
            }
        } else {
            return false;  // 다른 명령어 처리
        }
        return true;  // 정상적으로 명령어가 처리된 경우
    }

}
