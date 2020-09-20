package me.bluemond.enchantedarrows.arrows;

import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.UUID;

public class RidableArrow extends AbstractArrow{

    public RidableArrow(UUID uuid) {
        super(uuid, "-Ridable Arrow-", false);
    }

    @Override
    public void performArrowHit(ProjectileHitEvent event) {

    }

    @Override
    public void performArrowShoot(EntityShootBowEvent event) {
        event.getProjectile().addPassenger(event.getEntity());
    }

}
