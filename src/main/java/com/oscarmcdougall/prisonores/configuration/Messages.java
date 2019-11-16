package com.oscarmcdougall.prisonores.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.captain.frameworkfix.configuration.Configuration;
import rip.captain.frameworkfix.configuration.ConfigurationInterface;
import rip.captain.frameworkfix.configuration.ConfigurationType;

@AllArgsConstructor
@Configuration(filePath = "plugins/PrisonOres/messages.yml", configurationType = ConfigurationType.YML)
public enum Messages implements ConfigurationInterface {

    MULTIPLIER_GIVEN("&eYou have given [0] a &l[1]% &eore value multiplier."),
    MULTIPLIER_RECEIVED("&eYou have received a &l[0]% &eore value multiplier."),
    MULTIPLIER_COMMAND_USAGE("&4Usage: &c/multiplier <player> <percentage> <seconds>"),
    VALID_NUMBER_PERCENTAGE("&4Error: &cPlease enter a valid percentage for the multiplier."),
    VALID_SECONDS("&4Error: &cPlease enter a valid number of seconds for the multiplier length."),
    PLACEHOLDERAPI_REQUIRED("This plugin requires MVdWPlaceholderAPI if you want to take advantage of placeholders."),
    PLACEHOLDERAPI_LOADED("Successfully established a link with MVdWPlaceholderAPI."),
    VAULT_REQUIRED("This plugin requires Vault as a dependency to run."),
    VAULT_LOADED("Successfully established a link with Vault.");

    @Getter @Setter private Object configurationValue;

    public String getMessage(String... strings) {
        String stringToReturn = (String) this.configurationValue;
        for (int i = 0; i < strings.length; i++) {
            String iteratedString = strings[i];
            stringToReturn = stringToReturn.replace("[" + i + "]", iteratedString);
        }
        return stringToReturn;
    }

    public void sendMessage(CommandSender commandSender, String... strings) {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', getMessage(strings)));
    }

    public void sendMessage(CommandSender commandSender) {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', (String) this.configurationValue));
    }

    public void sendMessage(Player player, String... strings) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', getMessage(strings)));
    }
}