package me.bluemond.enchantedarrows.arrows;

import me.bluemond.enchantedarrows.utils.DebugPrinter;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.UUID;

public class LightningArrow extends AbstractArrow{

    public LightningArrow(UUID uuid){
        super(uuid, "-Lightning Arrow-", true);
    }

    @Override
    public void performArrowHit(ProjectileHitEvent event) {
        DebugPrinter.print("performing lightning arrow hit");
        Entity entity = event.getEntity();
        entity.getWorld().strikeLightning(entity.getLocation());
    }

    @Override
    public void performArrowShoot(EntityShootBowEvent event) {
        // nothing
    }

}
