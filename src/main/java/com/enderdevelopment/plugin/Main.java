package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Main extends JavaPlugin {
    private final Map<UUID, TextDisplay> healthDisplays = new HashMap<>();
    private BukkitTask healthUpdateTask;

    @Override
    public void onEnable() {
        healthUpdateTask = Bukkit.getScheduler().runTaskTimer(
                this,
                this::updateAllPlayerHealthDisplays,
                1L,
                2L
        );
    }

    @Override
    public void onDisable() {
        if (healthUpdateTask != null) {
            healthUpdateTask.cancel();
        }
        removeAllHealthDisplays();
    }

    private void updateAllPlayerHealthDisplays() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updatePlayerHealthDisplay(player);
        }
        healthDisplays.entrySet().removeIf(entry -> {
            if (Bukkit.getPlayer(entry.getKey()) == null) {
                entry.getValue().remove();
                return true;
            }
            return false;
        });
    }

    private void updatePlayerHealthDisplay(Player player) {
        TextDisplay display = healthDisplays.get(player.getUniqueId());
        if (display == null || !display.isValid()) {
            display = createHealthDisplay(player);
            healthDisplays.put(player.getUniqueId(), display);
        }

        AttributeInstance maxHealth = player.getAttribute(Attribute.MAX_HEALTH);
        double maximum = maxHealth == null ? 20.0D : maxHealth.getValue();
        double current = Math.max(0.0D, Math.min(player.getHealth(), maximum));
        NamedTextColor color = healthColor(current, maximum);

        Component text = Component.text("❤ ", NamedTextColor.RED)
                .append(Component.text(formatHealth(current) + "/" + formatHealth(maximum), color));
        display.text(text);
        display.teleport(displayLocation(player));
    }

    private TextDisplay createHealthDisplay(Player player) {
        TextDisplay display = (TextDisplay) player.getWorld().spawnEntity(
                displayLocation(player),
                EntityType.TEXT_DISPLAY
        );
        display.setBillboard(Display.Billboard.CENTER);
        display.setSeeThrough(true);
        display.setShadowed(false);
        display.setDefaultBackground(false);
        display.setPersistent(false);
        return display;
    }

    private Location displayLocation(Player player) {
        return player.getLocation().add(0.0D, 2.35D, 0.0D);
    }

    private void removeAllHealthDisplays() {
        for (TextDisplay display : healthDisplays.values()) {
            display.remove();
        }
        healthDisplays.clear();
    }

    private NamedTextColor healthColor(double current, double maximum) {
        double ratio = maximum <= 0.0D ? 0.0D : current / maximum;
        if (ratio <= 0.25D) {
            return NamedTextColor.DARK_RED;
        }
        if (ratio <= 0.5D) {
            return NamedTextColor.GOLD;
        }
        return NamedTextColor.GREEN;
    }

    private String formatHealth(double value) {
        if (value == Math.rint(value)) {
            return Integer.toString((int) value);
        }
        return String.format(java.util.Locale.ROOT, "%.1f", value);
    }
}
