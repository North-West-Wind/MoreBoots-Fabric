package ml.northwestwind.moreboots.events;

import ml.northwestwind.moreboots.events.annotations.Cancellable;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

import java.util.Collection;

@Cancellable
public class LivingDropsEvent extends LivingEvent
{
    private final DamageSource source;
    private final Collection<ItemEntity> drops;
    private final int lootingLevel;
    private final boolean recentlyHit;

    public LivingDropsEvent(LivingEntity entity, DamageSource source, Collection<ItemEntity> drops, int lootingLevel, boolean recentlyHit)
    {
        super(entity);
        this.source = source;
        this.drops = drops;
        this.lootingLevel = lootingLevel;
        this.recentlyHit = recentlyHit;
    }

    public DamageSource getSource()
    {
        return source;
    }

    public Collection<ItemEntity> getDrops()
    {
        return drops;
    }

    public int getLootingLevel()
    {
        return lootingLevel;
    }

    public boolean isRecentlyHit()
    {
        return recentlyHit;
    }
}
