package me.bluemond.enchantedarrows.utils;

import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Iterator;

public class InventoryUtil {
    public static ItemStack findFirstInInventory(PlayerInventory inventory, Material material){
        Iterator<ItemStack> invIterator = inventory.iterator();

        // first check offhand slot
        ItemStack itemStack = inventory.getItem(EquipmentSlot.OFF_HAND);
        if(itemStack != null && itemStack.getType() == material){
            return itemStack;
        }

        // iterate through rest of slots
        while(invIterator.hasNext()){
            itemStack = invIterator.next();
            if(itemStack != null && itemStack.getType() == material){
                return itemStack;
            }
        }

        return null;
    }
}
