package me.sirniklas.valorofthevalley.Economy.Listeners;

import me.sirniklas.valorofthevalley.Data.VotvShopItemsLoader;
import me.sirniklas.valorofthevalley.Economy.VotvEconomy;
import me.sirniklas.valorofthevalley.ValorOfTheValley;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GUIListener implements Listener {

    private final VotvEconomy votvEconomy;

    public GUIListener(VotvEconomy votvEconomy) {
        this.votvEconomy = votvEconomy;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (p.hasMetadata("OpenedMenu")) {
            e.setCancelled(true);
            if(VotvShopItemsLoader.getInstance().shopItemsList.contains(e.getSlot())) {
                if (votvEconomy.checkIfPlayerHasEnough(p, VotvShopItemsLoader.getInstance().ShopItems.get(e.getSlot()).getItemPrice())) {
                    votvEconomy.takeMedals(p, VotvShopItemsLoader.getInstance().ShopItems.get(e.getSlot()).getItemPrice());
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), VotvShopItemsLoader.getInstance().getLang(p, VotvShopItemsLoader.getInstance().ShopItems.get(e.getSlot()).getItemCommand()));
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
