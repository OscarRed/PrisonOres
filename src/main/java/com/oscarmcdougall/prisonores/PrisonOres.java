package com.oscarmcdougall.prisonores;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import com.oscarmcdougall.prisonores.configuration.Config;
import com.oscarmcdougall.prisonores.configuration.Messages;
import com.oscarmcdougall.prisonores.configuration.OreValue;
import com.oscarmcdougall.prisonores.listener.OreListener;
import com.oscarmcdougall.prisonores.placeholder.MultiplierAmountPlaceholder;
import com.oscarmcdougall.prisonores.placeholder.TimeExpiresPlaceholder;
import com.oscarmcdougall.prisonores.storage.SQLHandler;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import rip.captain.frameworkfix.command.CommandHandler;
import rip.captain.frameworkfix.configuration.ConfigurationHandler;
import com.oscarmcdougall.prisonores.command.MultiplierCommand;
import com.oscarmcdougall.prisonores.handler.MultiplierHandler;
import com.oscarmcdougall.prisonores.handler.OreHandler;

import java.util.logging.Level;

public class PrisonOres extends JavaPlugin {

    @Getter private static PrisonOres prisonOres;
    private ConfigurationHandler configurationHandler;
    private CommandHandler commandHandler;
    @Getter private Economy vaultEconomy;
    @Getter private SQLHandler sqlHandler;
    @Getter private MultiplierHandler multiplierHandler;
    @Getter private OreHandler oreHandler;
    @Getter private OreListener oreListener;
    private MultiplierCommand multiplierCommand;

    /**
     * Called on the plugin enable.
     */

    @Override
    public void onEnable() {
        this.prisonOres = this;

        ConfigurationSerialization.registerClass(OreValue.class, "OreValue");

        this.configurationHandler = new ConfigurationHandler();
        this.configurationHandler.addConfigurationClass(Config.class);
        this.configurationHandler.addConfigurationClass(Messages.class);

        this.vaultEconomy = getVault();

        this.sqlHandler = new SQLHandler((String) Config.MYSQL_HOSTNAME.getConfigurationValue(), (int) Config.MYSQL_PORT.getConfigurationValue(), (String) Config.MYSQL_DATABASE.getConfigurationValue(), (String) Config.MYSQL_USERNAME.getConfigurationValue(), (String) Config.MYSQL_PASSWORD.getConfigurationValue(), (Boolean) Config.MYSQL_USE_SLL.getConfigurationValue());

        this.multiplierHandler = new MultiplierHandler();
        this.oreHandler = new OreHandler();
        this.oreListener = new OreListener();
        Bukkit.getPluginManager().registerEvents(this.oreListener, this);

        this.multiplierCommand = new MultiplierCommand();
        this.commandHandler = new CommandHandler(this);
        this.commandHandler.registerCommand(multiplierCommand);
        this.commandHandler.registerHelp();

        if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
            PlaceholderAPI.registerPlaceholder(this, "active_multiplier", new MultiplierAmountPlaceholder());
            PlaceholderAPI.registerPlaceholder(this, "time_expires_multiplier", new TimeExpiresPlaceholder());
            Bukkit.getLogger().log(Level.INFO, Messages.PLACEHOLDERAPI_LOADED.getMessage());
        } else {
            Bukkit.getLogger().log(Level.INFO, Messages.PLACEHOLDERAPI_REQUIRED.getMessage());
        }
    }

    /**
     * Called on the plugin disable.
     */

    @Override
    public void onDisable() {
        this.prisonOres = null;
    }

    private Economy getVault() {

        Plugin vaultPlugin = Bukkit.getPluginManager().getPlugin("Vault");

        if (vaultPlugin == null) {
            Bukkit.getLogger().log(Level.SEVERE, Messages.VAULT_REQUIRED.getMessage());
            return null;
        }

        RegisteredServiceProvider<Economy> registeredServiceProvider = Bukkit.getServicesManager().getRegistration(Economy.class);

        if (registeredServiceProvider == null) {
            Bukkit.getLogger().log(Level.SEVERE, Messages.VAULT_REQUIRED.getMessage());
            return null;
        }

        Bukkit.getLogger().log(Level.INFO, Messages.VAULT_LOADED.getMessage());
        return registeredServiceProvider.getProvider();
    }
}