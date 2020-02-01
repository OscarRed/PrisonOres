package red.oscar.prisonores.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@SerializableAs("OreValue")
public class OreValue implements ConfigurationSerializable {

    @Getter private ItemStack oreItemStack;
    @Getter private double oreBaseValue;

    public OreValue(Map<String, Object> serializeMap) {
        this.oreItemStack = (ItemStack) serializeMap.get("oreItemStack");
        this.oreBaseValue = (Double) serializeMap.get("oreBaseValue");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serializeMap = new HashMap<>();

        serializeMap.put("oreItemStack", oreItemStack);
        serializeMap.put("oreBaseValue", oreBaseValue);

        return serializeMap;
    }
}