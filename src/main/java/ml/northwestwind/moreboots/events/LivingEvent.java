package ml.northwestwind.moreboots.events;

import ml.northwestwind.moreboots.events.annotations.Cancellable;
import net.minecraft.entity.LivingEntity;

public class LivingEvent extends EntityEvent {
    public LivingEvent(LivingEntity entity) {
        super(entity);
    }

    public LivingEntity getEntityLiving() {
        return (LivingEntity) getEntity();
    }

    public static class LivingJumpEvent extends LivingEvent {
        public LivingJumpEvent(LivingEntity entity) {
            super(entity);
        }
    }

    @Cancellable
    public static class LivingUpdateEvent extends LivingEvent {
        public LivingUpdateEvent(LivingEntity entity) {
            super(entity);
        }
    }
}
