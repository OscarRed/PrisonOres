package red.oscar.prisonores.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import rip.captain.frameworkfix.configuration.Configuration;
import rip.captain.frameworkfix.configuration.ConfigurationInterface;
import rip.captain.frameworkfix.configuration.ConfigurationType;
import rip.captain.frameworkfix.itemstack.ItemStackBuilder;

import java.util.Arrays;

@AllArgsConstructor
@Configuration(filePath = "plugins/PrisonOres/config.yml", configurationType = ConfigurationType.YML)
public enum Config implements ConfigurationInterface {

    MYSQL_HOSTNAME("127.0.0.1"),
    MYSQL_PORT(3306),
    MYSQL_USE_SLL(false),
    MYSQL_USERNAME("root"),
    MYSQL_PASSWORD("password"),
    MYSQL_DATABASE("prisonores"),
    ORES(Arrays.asList(new OreValue(new ItemStackBuilder(Material.INK_SACK).data((short) 4).build(), 10), new OreValue(new ItemStackBuilder(Material.COAL_ORE).build(), 5.0), new OreValue(new ItemStackBuilder(Material.IRON_ORE).build(), 10.0)));

    @Getter @Setter private Object configurationValue;
}