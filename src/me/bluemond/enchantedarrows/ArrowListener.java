package me.bluemond.enchantedarrows;

import me.bluemond.enchantedarrows.utils.DebugPrinter;
import me.bluemond.enchantedarrows.utils.InventoryUtil;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ArrowListener implements Listener {

    EnchantedArrows plugin;

    public ArrowListener(EnchantedArrows plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onArrowShoot(EntityShootBowEvent event) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Entity entity = event.getEntity();
        if(entity instanceof Player){
            Player player = (Player) entity;
            PlayerInventory playerInv = player.getInventory();
            ItemMeta arrowMeta = InventoryUtil.findFirstInInventory(playerInv, Material.ARROW).getItemMeta();
            List<String> lore = arrowMeta.getLore();
            DebugPrinter.print("Processing arrowmeta");

            if(lore != null){
                DebugPrinter.print("Arrow has lore");
                plugin.getArrowManager().registerShotArrow(event.getProjectile().getUniqueId(), lore, event);
            }

        }
    }



    @EventHandler
    public void onArrowHit(ProjectileHitEvent event){
        Projectile projectile = event.getEntity();
        DebugPrinter.print("Projectile hit event: " + projectile.getType());
        if(projectile.getType() == EntityType.ARROW){
            DebugPrinter.print("Projectile was arrow");
            plugin.getArrowManager().activateHitEffect(projectile.getUniqueId(), event);
        }
    }

    @EventHandler
    public void onPickupArrow(PlayerPickupArrowEvent event){
        plugin.getArrowManager().attemptArrowRetrieval(event.getArrow().getUniqueId(), event);
    }
}
