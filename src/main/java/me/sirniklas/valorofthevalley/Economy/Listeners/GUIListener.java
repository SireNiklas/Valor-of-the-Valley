package me.sirniklas.valorofthevalley.Economy.Listeners;

import me.sirniklas.valorofthevalley.Economy.VOTVEconomy;
import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    private final VOTVEconomy votvEconomy;

    public GUIListener(VOTVEconomy votvEconomy) {
        this.votvEconomy = votvEconomy;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (p.hasMetadata("OpenedMenu")) {
            e.setCancelled(true);

            if(e.getSlot() == 11) {
                if (votvEconomy.checkIfPlayerHasEnough(p, 50)) {
                    votvEconomy.takeMedals(p, 50);
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ei give " + p.getName() + " StreamKey");
                    p.closeInventory();
                } else {
                    p.sendMessage("Not enough medals");
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();

        if (player.hasMetadata("OpenedMenu")) {
            player.removeMetadata("OpenedMenu", ValorOfTheValley.getInstance());
        }
    }

}
