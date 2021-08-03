package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.Vec3d;

public class MagmaBootsItem extends BootsItem {
    public MagmaBootsItem() {
        super(ItemInit.ModArmorMaterial.MAGMA, "magma_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Fluid fluid = entity.world.getFluidState(entity.getBlockPos()).getFluid();
        if (fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER) {
            if (entity.isSpectator() || (entity instanceof PlayerEntity && ((PlayerEntity) entity).getAbilities().flying))
                return;
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 20, 0, false, false));
            Vec3d vector3d = entity.getVelocity();
            entity.setVelocity(vector3d.x, Math.max(-0.6D, vector3d.y - 0.03D), vector3d.z);
            entity.fallDistance = 0.0F;
        }
    }
}
