package me.sirniklas.valorofthevalley.Economy;

import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.ChatColor;
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

    public void giveMedals(Player target, int amount) {
        int medalsToGive = getPlayerMedals(target);

        medalsToGive += amount;
        setPlayerMedals(target, medalsToGive);
    }

    public void takeMedals(Player target, int amount) {
        int medalsToTake = getPlayerMedals(target);

        if (checkIfPlayerHasEnough(target, amount)) {

            medalsToTake -= amount;
            setPlayerMedals(target, medalsToTake);
        } else {
            target.sendMessage("You can't pay that much!");
        }
    }

    public boolean checkIfPlayerHasEnough(Player target, int amount) {
        int medalsToCheck = getPlayerMedals(target);

        return amount <= medalsToCheck;
    }

}
