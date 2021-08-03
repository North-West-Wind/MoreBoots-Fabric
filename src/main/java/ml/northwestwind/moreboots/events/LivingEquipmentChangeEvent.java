package ml.northwestwind.moreboots.events;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class LivingEquipmentChangeEvent extends LivingEvent
{
    private final EquipmentSlot slot;
    private final ItemStack from;
    private final ItemStack to;

    public LivingEquipmentChangeEvent(LivingEntity entity, EquipmentSlot slot, ItemStack from, ItemStack to)
    {
        super(entity);
        this.slot = slot;
        this.from = from;
        this.to = to;
    }

    public EquipmentSlot getSlot() { return this.slot; }
    public ItemStack getFrom() { return this.from; }
    public ItemStack getTo() { return this.to; }
}
