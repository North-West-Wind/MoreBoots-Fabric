package ml.northwestwind.moreboots.events;

import ml.northwestwind.moreboots.events.annotations.Cancellable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

@Cancellable
public class LivingHurtEvent extends LivingEvent{
    private final DamageSource source;
    private float amount;

    public LivingHurtEvent(LivingEntity entity, DamageSource source, float amount) {
        super(entity);
        this.source = source;
        this.amount = amount;
    }

    public DamageSource getSource() {
        return source;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
