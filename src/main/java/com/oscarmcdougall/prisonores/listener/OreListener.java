package com.oscarmcdougall.prisonores.listener;

import com.oscarmcdougall.prisonores.PrisonOres;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OreListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        double moneyEarned = PrisonOres.getPrisonOres().getOreHandler().getValueFromBlock(event.getBlock());
        double activeMultiplier = PrisonOres.getPrisonOres().getMultiplierHandler().getMultiplier(event.getPlayer());

        if (moneyEarned > 0) {

            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);

            PrisonOres.getPrisonOres().getVaultEconomy().depositPlayer(event.getPlayer(), moneyEarned * activeMultiplier);
        }
    }
}