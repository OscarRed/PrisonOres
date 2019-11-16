package com.oscarmcdougall.prisonores.placeholder;

import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import com.oscarmcdougall.prisonores.PrisonOres;
import org.bukkit.OfflinePlayer;

public class TimeExpiresPlaceholder implements PlaceholderReplacer {

    @Override
    public String onPlaceholderReplace(PlaceholderReplaceEvent event) {

        OfflinePlayer currentPlayer = event.getOfflinePlayer();

        return PrisonOres.getPrisonOres().getMultiplierHandler().getSecondsLeftPlaceholder(currentPlayer);
    }
}