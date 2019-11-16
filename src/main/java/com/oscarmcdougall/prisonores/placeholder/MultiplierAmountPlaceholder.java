package com.oscarmcdougall.prisonores.placeholder;

import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import org.bukkit.OfflinePlayer;
import com.oscarmcdougall.prisonores.PrisonOres;

public class MultiplierAmountPlaceholder implements PlaceholderReplacer {

    @Override
    public String onPlaceholderReplace(PlaceholderReplaceEvent event) {

        OfflinePlayer currentPlayer = event.getOfflinePlayer();

        return PrisonOres.getPrisonOres().getMultiplierHandler().getMultiplierPlaceholder(currentPlayer);
    }
}