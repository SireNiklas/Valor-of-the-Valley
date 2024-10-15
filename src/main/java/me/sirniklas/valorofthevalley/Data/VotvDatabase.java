package me.sirniklas.valorofthevalley.Data;

import org.bukkit.entity.Player;

import java.sql.*;

public class VotvDatabase {

    private final Connection connection;

    interface IDataType {
        void dataType();
    }

    interface IDataValue {
        void dataValue();
    }

    public VotvDatabase(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);

        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE TABLE IF NOT EXISTS players (
                uuid TEXT PRIMARY KEY,
                username TEXT NOT NULL,
                medals INTEGER NOT NULL DEFAULT 0,
                honor FLOAT NOT NULL DEFAULT 0,
                exp INT NOT NULL DEFAULT 0,
                kills INTEGER NOT NULL DEFAULT 0, 
                deaths INTEGER NOT NULL DEFAULT 0,
                combatlogged INT NOT NULL DEFAULT 0)
            """);
        }
    }

    public void CloseConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void addPlayer(Player player) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players (uuid, username) VALUES (?, ?)")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            preparedStatement.setString(2, player.getName());
            preparedStatement.executeUpdate();
        }
    }

    public boolean checkPlayer(Player player) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    public void updatePlayerData(Player player, String data, int value) throws SQLException {
        if (!checkPlayer(player)) {
            addPlayer(player);
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET " + data + " = ? WHERE uuid = ?")) {
            preparedStatement.setInt(1, value);
            preparedStatement.setString(2, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
        }

    }

    public int getPlayerData(Player player, String data) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + data + " FROM players WHERE uuid = ?")) {
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(data);
            } else {
                return 0;
            }
        }
    }
}
