package com.oscarmcdougall.prisonores.handler;

import com.oscarmcdougall.prisonores.PrisonOres;
import com.oscarmcdougall.prisonores.utility.Multiplier;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class MultiplierHandler {

    private Map<OfflinePlayer, Multiplier> activeMultiplierMap = new HashMap<>();

    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss") ;

    public MultiplierHandler() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

            Multiplier playerMultiplier = PrisonOres.getPrisonOres().getSqlHandler().getMultiplier(onlinePlayer);

            if (playerMultiplier != null) {
                activeMultiplierMap.put(onlinePlayer, playerMultiplier);
            }
        }
    }

    public void addMultiplier(OfflinePlayer targetPlayer, Multiplier activeMultiplier) {
        activeMultiplierMap.put(targetPlayer, activeMultiplier);
    }

    public boolean hasMultiplier(OfflinePlayer targetPlayer) {
        return activeMultiplierMap.containsKey(targetPlayer);
    }

    public String getMultiplierPlaceholder(OfflinePlayer targetPlayer) {
        return activeMultiplierMap.containsKey(targetPlayer) ? String.valueOf(activeMultiplierMap.get(targetPlayer).getMultiplierAmount() * 100) + "%" : "None Active";
    }

    public String getSecondsLeftPlaceholder(OfflinePlayer targetPlayer) {
        if (activeMultiplierMap.containsKey(targetPlayer)) {

            if (activeMultiplierMap.get(targetPlayer).getTimeExpires() == -1) {
                return "Infinite";
            }

            if (activeMultiplierMap.get(targetPlayer).getTimeExpires() < System.currentTimeMillis()) {
                activeMultiplierMap.remove(targetPlayer);
                PrisonOres.getPrisonOres().getSqlHandler().deleteMultiplier(targetPlayer);
                return null;
            }

            long timeLeft = (activeMultiplierMap.get(targetPlayer).getTimeExpires() - System.currentTimeMillis()) / 1000;

            return timeFormatter.format(LocalTime.MIN.plus(Duration.ofSeconds(timeLeft)));
        }
        return "00:00:00";
    }

    public double getMultiplier(OfflinePlayer targetPlayer) {

        if (!activeMultiplierMap.containsKey(targetPlayer)) {
            return 1;
        }

        Multiplier currentMultiplier = activeMultiplierMap.get(targetPlayer);
        long timeExpires = currentMultiplier.getTimeExpires();

        if (timeExpires < System.currentTimeMillis()) {
            activeMultiplierMap.remove(targetPlayer);
            PrisonOres.getPrisonOres().getSqlHandler().deleteMultiplier(targetPlayer);
            return 1;
        }

        return currentMultiplier.getMultiplierAmount();
    }
}