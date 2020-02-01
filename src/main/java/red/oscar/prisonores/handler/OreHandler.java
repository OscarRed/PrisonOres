package red.oscar.prisonores.handler;

import red.oscar.prisonores.configuration.Config;
import red.oscar.prisonores.configuration.OreValue;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OreHandler {

    private Map<Material, OreValue> oreValueMap = new HashMap<>();

    public OreHandler() {
        for (OreValue oreValue : (List<OreValue>) Config.ORES.getConfigurationValue()) {
            oreValueMap.put(oreValue.getOreItemStack().getType(), oreValue);
        }
    }

    public double getValueFromBlock(Block targetBlock) {
        return oreValueMap.containsKey(targetBlock.getType()) ? oreValueMap.get(targetBlock.getType()).getOreBaseValue() : 0;
    }
}