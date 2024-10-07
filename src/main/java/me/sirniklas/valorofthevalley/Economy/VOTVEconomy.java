package me.sirniklas.valorofthevalley.Economy;

import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class VOTVEconomy {

    public final ValorOfTheValley valorOfTheValley;

    public VOTVEconomy(ValorOfTheValley valorOfTheValley) {
        this.valorOfTheValley = valorOfTheValley;
    }

    public void setPlayerMedals(Player target, int value) {
        // Get Amount
        int setValue = value;

        try {
            valorOfTheValley.getVotVDatabase().updatePlayerData(target, "medals", setValue);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPlayerMedals(Player target) {
        int medals;
        try {
            medals = valorOfTheValley.getVotVDatabase().getPlayerData(target, "medals");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return medals;
    }

}
