package me.bluemond.enchantedarrows.arrows;

import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.UUID;

public abstract class AbstractArrow {

    private UUID entityID;
    private String loreIdentifier;
    private boolean isRetrievable;
    private boolean hasHit;

    public AbstractArrow(UUID uuid, String loreIdentifier, boolean isRetrievable){
        entityID = uuid;
        this.loreIdentifier = loreIdentifier;
        this.isRetrievable = isRetrievable;
        hasHit = false;
    }

    public abstract void performArrowHit(ProjectileHitEvent event);

    public abstract void performArrowShoot(EntityShootBowEvent event);

    public UUID getEntityID() {
        return entityID;
    }

    public boolean hasHit(){
        return hasHit;
    }

    public void setHasHit(boolean hasHit){
        this.hasHit = hasHit;
    }

    public String getLoreIdentifier(){
        return loreIdentifier;
    }

    public boolean isRetrievable(){
        return isRetrievable;
    }

}
