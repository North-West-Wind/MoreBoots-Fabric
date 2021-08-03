package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.MoreBoots;
import ml.northwestwind.moreboots.events.Event;
import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.events.RenderLivingEvent;
import ml.northwestwind.moreboots.events.RenderNameplateEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;

public class LokiBootsItem extends BootsItem {
    public LokiBootsItem() {
        super(ItemInit.ModArmorMaterial.LOKI, "loki_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        NbtCompound tag = boots.getOrCreateNbt();
        int tickStill = tag.getInt("tickStill");
        Vec3d motion = entity.getVelocity();
        MoreBoots.LOGGER.info(motion);
        if (Math.abs(motion.x) > 0 || Math.abs(motion.y) > 0.8 || Math.abs(motion.z) > 0) {
            tickStill = 0;
            entity.setInvisible(false);
        } else if (tickStill < 200) tickStill++;
        else entity.setInvisible(true);
        tag.putInt("tickStill", tickStill);
        boots.setNbt(tag);
    }

    @Override
    public void preRenderLiving(RenderLivingEvent.Pre<?, ?> event) {
        NbtCompound tag = event.getEntity().getEquippedStack(EquipmentSlot.FEET).getOrCreateNbt();
        if (tag.getInt("tickStill") >= 200) event.setCancelled(true);
    }

    @Override
    public void renderNameplate(RenderNameplateEvent event) {
        event.setResult(Event.Result.DENY);
    }
}
