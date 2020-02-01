package red.oscar.prisonores.command;

import red.oscar.prisonores.PrisonOres;
import red.oscar.prisonores.configuration.Messages;
import red.oscar.prisonores.utility.Multiplier;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import red.oscar.prisonores.utility.NumberUtility;
import org.bukkit.command.CommandSender;
import rip.captain.frameworkfix.command.Command;
import rip.captain.frameworkfix.command.CommandInterface;

import java.text.DecimalFormat;

@Command(commandName = "multiplier", commandPermission = "prisonores.multiplier", commandDescription = "Set a players active multiplier.")
public class MultiplierCommand implements CommandInterface {

    private DecimalFormat removeDecimals = new DecimalFormat("#");
    private NumberUtility numberUtility = new NumberUtility();

    @Override
    public boolean executeCommand(CommandSender commandSender, String s, String[] strings) {

        if (strings.length < 3) {
            Messages.MULTIPLIER_COMMAND_USAGE.sendMessage(commandSender);
            return true;
        }

        String targetPlayerName = strings[0];

        if (Bukkit.getOfflinePlayer(targetPlayerName) == null) {
            return true;
        }

        OfflinePlayer targetPlayer = Bukkit.getPlayer(targetPlayerName);
        String percentageMultiplierString = strings[1];

        if (!numberUtility.isDouble(percentageMultiplierString)) {
            Messages.VALID_NUMBER_PERCENTAGE.sendMessage(commandSender);
            return true;
        }

        double percentageMultiplier = Double.valueOf(percentageMultiplierString);

        String secondsActiveString = strings[2];

        if (!numberUtility.isInteger(secondsActiveString)) {
            Messages.VALID_SECONDS.sendMessage(commandSender);
            return true;
        }

        int secondsActive = Integer.valueOf(secondsActiveString);

        Multiplier newMultiplier;

        if (secondsActive == -1) {
            newMultiplier = new Multiplier((percentageMultiplier / 100), -1);
        } else {
            newMultiplier = new Multiplier((percentageMultiplier / 100), System.currentTimeMillis() + (secondsActive * 1000));
        }

        PrisonOres.getPrisonOres().getMultiplierHandler().addMultiplier(targetPlayer, newMultiplier);
        PrisonOres.getPrisonOres().getSqlHandler().setMultiplier(targetPlayer, newMultiplier);

        Messages.MULTIPLIER_GIVEN.sendMessage(commandSender, targetPlayer.getName(), removeDecimals.format(percentageMultiplier));

        if (targetPlayer.isOnline()) {
            Messages.MULTIPLIER_RECEIVED.sendMessage(targetPlayer.getPlayer(), removeDecimals.format(percentageMultiplier));
        }
        return true;
    }
}