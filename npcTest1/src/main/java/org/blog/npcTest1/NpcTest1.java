package org.blog.npcTest1;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.bukkit.util.BlockIterator;

public class NpcTest1 extends JavaPlugin implements Listener {
    private boolean isTalkingToNPC = false;
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this); // 이벤트 리스너 등록
    }

    @Override
    public void onDisable() {
        // Nothing to do
    }

    // 플레이어의 커서 위치에 NPC를 생성하는 메서드
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();

        // 플레이어가 우클릭한 경우
        if (event.getAction().name().contains("RIGHT_CLICK")) {
            Location targetLocation = getTargetBlockLocation(player);

            // NPC가 이미 그 위치에 있는지 확인
            if (!isNPCAtLocation(targetLocation)) {
                spawnNPC(targetLocation);
            }
        }


    }

    // 플레이어가 바라보는 위치를 얻는 메서드
    private Location getTargetBlockLocation(Player player) {
        // 플레이어가 바라보는 방향에 있는 블록을 찾기 위한 아이템으로 BlockIterator 사용
        BlockIterator blockIterator = new BlockIterator(player, 100); // 100 블록 내의 위치 탐지

        // 블록이 있는 위치를 찾을 때까지 반복
        while (blockIterator.hasNext()) {
            Location location = blockIterator.next().getLocation();
            // 블록을 만났을 때 그 위치를 반환
            if (location.getBlock().getType().isSolid()) {
                return location.add(0, 1, 0); // 블록의 상단 위치를 반환 (npc가 부딪히지 않게)
            }
        }

        // 만약 100블록 내에서 발견되지 않으면 기본적으로 플레이어가 서 있는 위치 반환
        return player.getLocation().add(player.getEyeLocation().getDirection().multiply(2)); // 2블록 앞에 NPC 생성
    }

    // NPC를 생성하는 메서드
    private void spawnNPC(Location location) {
        Villager npc = (Villager) location.getWorld().spawnEntity(location, org.bukkit.entity.EntityType.VILLAGER); // Villager 엔티티 생성
        npc.setCustomName("§6퀘스트 NPC"); // NPC 이름 설정
        npc.setCustomNameVisible(true); // NPC 이름을 플레이어에게 보이도록 설정
    }

    // 지정된 위치에 NPC가 이미 존재하는지 확인하는 메서드
    private boolean isNPCAtLocation(Location location) {
        for (Entity entity : location.getWorld().getEntities()) {
            // 해당 위치와 가까운 엔티티가 Villager라면 NPC가 이미 존재하는 것
            if (entity instanceof Villager && entity.getLocation().distance(location) < 1.5) {
                return true; // 가까운 위치에 NPC가 이미 존재하면 true 반환
            }
        }
        return false; // NPC가 존재하지 않으면 false 반환
    }

    // NPC와 대화 시작
    @EventHandler
    public void onNPCInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity instanceof Villager) {
            Villager npc = (Villager) entity;

            String npcName = npc.getCustomName() != null ? npc.getCustomName() : "NPC";

            if ("§6퀘스트 NPC".equals(npcName)) {
                player.sendMessage(Component.text("🎉 NPC와 대화가 시작되었습니다!").color(NamedTextColor.GREEN));
                player.sendMessage(Component.text("NPC: 안녕하세요! 퀘스트를 시작하고 싶으세요?").color(NamedTextColor.YELLOW));
                isTalkingToNPC = true; // 대화 중임을 표시
            }
        }
    }

    // 플레이어가 대화 중에 메시지를 입력하면 처리
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (isTalkingToNPC) {
            String message = event.getMessage().toLowerCase();

            if (message.contains("네")) {
                player.sendMessage(Component.text("NPC: 퀘스트를 시작합니다!").color(NamedTextColor.YELLOW));
                isTalkingToNPC = false; // 대화 종료
            } else if (message.contains("아니오")) {
                player.sendMessage(Component.text("NPC: 퀘스트를 취소하였습니다. 나중에 다시 오세요.").color(NamedTextColor.RED));
                isTalkingToNPC = false; // 대화 종료
            } else {
                player.sendMessage(Component.text("NPC: 답변을 '네' 또는 '아니오'로 해주세요.").color(NamedTextColor.RED));
            }

            event.setCancelled(true); // 메시지를 서버에 보내지 않도록 차단
        }
    }
}





