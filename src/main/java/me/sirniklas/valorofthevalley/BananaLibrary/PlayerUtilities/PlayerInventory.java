package me.sirniklas.valorofthevalley.BananaLibrary.PlayerUtilities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerInventory {

    // Check if player inventory has any items, return true if empty.
    public static boolean isInventoryEmpty(Player p){
        for(ItemStack item : p.getInventory().getContents())
        {
            if(item != null)
                return false;
        }
        return true;
    }
}
