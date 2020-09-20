package me.bluemond.enchantedarrows.arrows;

import me.bluemond.enchantedarrows.utils.DebugPrinter;
import me.bluemond.enchantedarrows.EnchantedArrows;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ArrowManager {

    EnchantedArrows plugin;
    Map<UUID, AbstractArrow> activeArrows;
    List<Class<? extends AbstractArrow>> arrowClasses;

    public ArrowManager(EnchantedArrows plugin){
        this.plugin = plugin;
        activeArrows = new HashMap<>();
        arrowClasses = new ArrayList<>();
    }

    public void registerArrowClass(Class<? extends AbstractArrow> abstractArrowClass){
        arrowClasses.add(abstractArrowClass);
    }

    // return whether or not the shot arrow was an enchanted arrow
    public void registerShotArrow(UUID uuid, List<String> lore, EntityShootBowEvent event) throws
            NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        AbstractArrow abstractArrow = null;

        // assign abstract arrow to correct instance type (if one exists)
        for(Class<? extends AbstractArrow> arrowClass : arrowClasses){
            Constructor ctr = arrowClass.getConstructor(UUID.class);
            abstractArrow = (AbstractArrow) ctr.newInstance(uuid);
            String loreIdentifier = (String) arrowClass.getMethod("getLoreIdentifier").invoke(abstractArrow);
            DebugPrinter.print("Comparing lore: " + lore.get(0) + " to identifier: " + loreIdentifier);
            if(!lore.get(0).contains(loreIdentifier)){
                abstractArrow = null;
            }
        }

        // if an actual instance was found, register it
        if(abstractArrow != null){
            activeArrows.put(uuid, abstractArrow);
            abstractArrow.performArrowShoot(event);
        }
    }

    // returns whether or not the projectile hit was of an enchanted arrow
    public void activateHitEffect(UUID uuid, ProjectileHitEvent event) {
        DebugPrinter.print("Trying to find mapping for " + uuid);
        AbstractArrow abstractArrow = activeArrows.get(uuid);
        if(abstractArrow != null && !abstractArrow.hasHit()){
            DebugPrinter.print("activating hit effect for " + uuid);
            abstractArrow.performArrowHit(event);
            abstractArrow.setHasHit(true);
        }
    }

    // returns whether or not the arrow retrieval was an enchanted arrow
    public boolean attemptArrowRetrieval(UUID uuid, PlayerPickupArrowEvent event){
        if(event.getItem().getItemStack().getType() != Material.ARROW) return false;

        AbstractArrow abstractArrow = activeArrows.get(uuid);

        // not a registered abstract arrow
        if(abstractArrow == null) return false;

        // change retrieved item based on if retrievable arrow
        Item item = event.getItem();
        ItemStack arrowStack;
        if(abstractArrow.isRetrievable()){
            arrowStack = createAbstractArrowStack(abstractArrow, 1);
        }else{
            arrowStack = new ItemStack(Material.AIR);
        }
        item.setItemStack(arrowStack);

        // remove arrow from registry (lifecycle is complete)
        activeArrows.remove(uuid);

        return true;
    }

    public ItemStack createAbstractArrowStack(AbstractArrow abstractArrow, int amount){
        ItemStack arrowStack = new ItemStack(Material.ARROW);

        arrowStack.setAmount(amount);
        ItemMeta itemMeta = arrowStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(abstractArrow.getLoreIdentifier());
        itemMeta.setLore(lore);
        arrowStack.setItemMeta(itemMeta);

        return arrowStack;
    }
}
