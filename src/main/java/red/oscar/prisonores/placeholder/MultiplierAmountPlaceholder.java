package red.oscar.prisonores.placeholder;

import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import red.oscar.prisonores.PrisonOres;

public class MultiplierAmountPlaceholder implements PlaceholderReplacer {

    @Override
    public String onPlaceholderReplace(PlaceholderReplaceEvent event) {
        return PrisonOres.getPrisonOres().getMultiplierHandler().getMultiplierPlaceholder(event.getOfflinePlayer());
    }
}