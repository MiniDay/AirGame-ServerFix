package net.airgame.bukkit.fix;

import net.airgame.bukkit.fix.listener.villager.ButcherListener;
import net.airgame.bukkit.fix.listener.villager.CartographerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class FixPlugin extends JavaPlugin {
    @Override
    public void onLoad() {
        saveDefaultConfig();
        reloadConfig();
    }

    @Override
    public void onEnable() {
        if (getConfig().getBoolean("butcherTradeReplace")) {
            Bukkit.getPluginManager().registerEvents(new ButcherListener(), this);
        }
        if (getConfig().getBoolean("cartographerKiller")) {
            Bukkit.getPluginManager().registerEvents(new CartographerListener(this), this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
