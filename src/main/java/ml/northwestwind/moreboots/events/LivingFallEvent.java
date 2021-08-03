package ml.northwestwind.moreboots.events;

import ml.northwestwind.moreboots.events.annotations.Cancellable;
import net.minecraft.entity.LivingEntity;

@Cancellable
public class LivingFallEvent extends LivingEvent {
    private float distance;
    private float multiplier;
    private boolean cancelled;

    public LivingFallEvent(LivingEntity entity, float distance, float multiplier) {
        super(entity);
        this.distance = distance;
        this.multiplier = multiplier;
    }

    public float getDistance() {
        return distance;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
