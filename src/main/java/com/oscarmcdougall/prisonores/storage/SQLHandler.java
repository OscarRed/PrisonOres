package com.oscarmcdougall.prisonores.storage;

import org.apache.commons.dbcp2.BasicDataSource;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import com.oscarmcdougall.prisonores.utility.Multiplier;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;

public class SQLHandler {

    private BasicDataSource basicDataSource = new BasicDataSource();

    public SQLHandler(String serverName, int portNumber, String databaseName, String databaseUsername, String databasePassword, boolean useSSL) {
        basicDataSource.setUrl("jdbc:mysql://" + serverName + ":" + portNumber + "/" + databaseName);
        basicDataSource.setUsername(databaseUsername);
        basicDataSource.setPassword(databasePassword);
        basicDataSource.setInitialSize(5);
        basicDataSource.setConnectionProperties("useSSL" + "=" + String.valueOf(useSSL) + ";rewriteBatchedStatements=true;");
        generateTables();
    }

    private void generateTables() {

        try {

            Statement generateStatement = getConnection().createStatement();
            generateStatement.executeUpdate("CREATE TABLE IF NOT EXISTS multipliers(uuid VARCHAR(36) NOT NULL, time_expires LONG NOT NULL, multiplier DECIMAL NOT NULL, PRIMARY KEY(uuid));");
            generateStatement.close();
            Bukkit.getLogger().log(Level.INFO, "Generating default MySQL tables if they do not exist.");
        } catch (SQLException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error whilst generating default MySQL tables!");
            e.printStackTrace();
        }
    }
    
    private Connection getConnection() {
        try {
            return basicDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Multiplier getMultiplier(OfflinePlayer targetPlayer) {

        String multiplierQuery = "SELECT time_expires, multiplier FROM multipliers WHERE uuid = ?";

        try {

            PreparedStatement preparedStatement = getConnection().prepareStatement(multiplierQuery);

            preparedStatement.setString(1, targetPlayer.getUniqueId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                double activeMultiplier = resultSet.getDouble("multiplier");
                long timeExpires = resultSet.getLong("time_expires");

                if (timeExpires == -1) {
                    return new Multiplier(activeMultiplier, timeExpires);
                }

                if (timeExpires < System.currentTimeMillis()) {
                    deleteMultiplier(targetPlayer);
                    return null;
                }

                return new Multiplier(activeMultiplier, timeExpires);
            }

        } catch (SQLException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error whilst grabbing the multiplier for '" + targetPlayer.getUniqueId().toString() + "'!");
            e.printStackTrace();
        }
        return null;
    }

    public void deleteMultiplier(OfflinePlayer targetPlayer) {

        String deleteUpdate = "DELETE FROM multipliers WHERE uuid = ?";

        try {

            PreparedStatement preparedStatement = getConnection().prepareStatement(deleteUpdate);

            preparedStatement.setString(1, targetPlayer.getUniqueId().toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error whilst clearing the multiplier for '" + targetPlayer + "'!");
            e.printStackTrace();
        }
    }

    public void setMultiplier(OfflinePlayer targetPlayer, Multiplier playerMultiplier) {

        String setMultiplierUpdate = "INSERT INTO multipliers(uuid, time_expires, multiplier) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE time_expires = ?, multiplier = ?";

        try {

            PreparedStatement preparedStatement = getConnection().prepareStatement(setMultiplierUpdate);

            preparedStatement.setString(1, targetPlayer.getUniqueId().toString());
            preparedStatement.setLong(2, playerMultiplier.getTimeExpires());
            preparedStatement.setDouble(3, playerMultiplier.getMultiplierAmount());
            preparedStatement.setLong(4, playerMultiplier.getTimeExpires());
            preparedStatement.setDouble(5, playerMultiplier.getMultiplierAmount());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Bukkit.getLogger().log(Level.INFO, "Error whilst setting the multiplier of '" + targetPlayer.getUniqueId().toString() + "' to 'x" + playerMultiplier.getMultiplierAmount() + "'!");
            e.printStackTrace();
        }
    }
}