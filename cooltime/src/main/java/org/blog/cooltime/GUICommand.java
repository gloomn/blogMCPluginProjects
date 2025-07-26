package org.blog.cooltime;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUICommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        Inventory gui = Bukkit.createInventory(null, 9, Component.text("스킬 GUI").color(NamedTextColor.GREEN));

        ItemStack skillItem = new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta meta = skillItem.getItemMeta();
        meta.displayName(Component.text("파워 스킬 사용").color(NamedTextColor.AQUA));
        skillItem.setItemMeta(meta);

        gui.setItem(4, skillItem);
        player.openInventory(gui);
        return true;
    }
}
