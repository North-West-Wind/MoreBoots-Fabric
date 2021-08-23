package ml.northwestwind.moreboots.mixins;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.boots.SlipperyBoots;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BoatEntity.class)
public abstract class MixinBoatEntity extends MixinEntity {
    @Shadow @Nullable public abstract Entity getPrimaryPassenger();

    @Redirect(method = "method_7548", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getSlipperiness()F"))
    public float getSlipperiness(Block block) {
        Entity passenger = this.getPrimaryPassenger();
        if (!(passenger instanceof LivingEntity) || !((LivingEntity) passenger).getEquippedStack(EquipmentSlot.FEET).getItem().equals(ItemInit.SLIPPERY_BOOTS)) return block.getSlipperiness();
        return SlipperyBoots.SLIPPERINESS;
    }
}
