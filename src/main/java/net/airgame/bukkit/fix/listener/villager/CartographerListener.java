package net.airgame.bukkit.fix.listener.villager;

import net.airgame.bukkit.fix.FixPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerCareerChangeEvent;

public class CartographerListener implements Listener {
    private final FixPlugin plugin;

    public CartographerListener(FixPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onVillagerCareerChange(VillagerCareerChangeEvent event) {
        if (event.getProfession() != Villager.Profession.CARTOGRAPHER) {
            return;
        }
        Villager entity = event.getEntity();
        Bukkit.getScheduler().runTaskLater(
                plugin,
                () -> {
                    Location location = entity.getLocation();
                    for (int x = -2; x <= 2; x++) {
                        for (int y = -2; y <= 2; y++) {
                            for (int z = -2; z <= 2; z++) {
                                Location blockLocation = location.clone().add(x, y, z);
                                Block block = blockLocation.getBlock();
                                if (block.getType() == Material.CARTOGRAPHY_TABLE) {
                                    World world = block.getWorld();
                                    for (int i = 0; i < 100; i++) {
                                        world.spawnParticle(
                                                Particle.BLOCK_CRACK,
                                                block.getLocation().add(
                                                        Math.random(),
                                                        Math.random(),
                                                        Math.random()
                                                ),
                                                1,
                                                block.getBlockData()
                                        );
                                    }
                                    world.playSound(block.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_WOOD_BREAK, 1, 1);
                                    block.breakNaturally();
                                }
                            }
                        }
                    }
                },
                1
        );
        event.setCancelled(true);
    }

}
