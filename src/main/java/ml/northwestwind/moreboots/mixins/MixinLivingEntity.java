package ml.northwestwind.moreboots.mixins;

import com.google.common.collect.Lists;
import ml.northwestwind.moreboots.events.*;
import ml.northwestwind.moreboots.handler.MoreBootsHandler;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.boots.SlipperyBoots;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Collection;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends MixinEntity {
    @Shadow protected abstract int computeFallDamage(float fallDistance, float damageMultiplier);

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow protected int playerHitTimer;

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;computeFallDamage(FF)I"), method = "handleFallDamage")
    public int computeFallDamage(LivingEntity entity, float fallDistance, float damageMultiplier) {
        LivingFallEvent event = new LivingFallEvent((LivingEntity) (Object) this, fallDistance, damageMultiplier);
        MoreBootsHandler.onLivingFall(event);
        if (event.isCancelled()) return 0;
        return this.computeFallDamage(event.getDistance(), event.getMultiplier());
    }

    @Inject(at = @At("RETURN"), method = "jump")
    public void jump(CallbackInfo ci) {
        MoreBootsHandler.onLivingJump(new LivingEvent.LivingJumpEvent((LivingEntity) (Object) this));
    }

    @ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"), method = "damage")
    public void applyDamage(Args args) {
        DamageSource source = args.get(0);
        float amount = args.get(1);
        LivingHurtEvent event = new LivingHurtEvent((LivingEntity) (Object) this, source, amount);
        MoreBootsHandler.onLivingHurt(event);
        args.set(1, event.isCancelled() ? 0 : event.getAmount());
    }


    @Inject(method = "getEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;areEqual(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z"), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void getEquipment(CallbackInfoReturnable<Map<EquipmentSlot, ItemStack>> cir, Map<EquipmentSlot, ItemStack> map, EquipmentSlot[] var2, int var3, int var4, EquipmentSlot equipmentSlot, ItemStack itemStack3, ItemStack itemStack4) {
        MoreBootsHandler.onLivingEquipmentChange(new LivingEquipmentChangeEvent((LivingEntity) (Object) this, equipmentSlot, itemStack3, itemStack4));
    }

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    public void tick(CallbackInfo ci) {
        LivingEvent.LivingUpdateEvent event = new LivingEvent.LivingUpdateEvent((LivingEntity) (Object) this);
        MoreBootsHandler.onLivingUpdate(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(at = @At("RETURN"), method = "drop")
    public void drop(DamageSource source, CallbackInfo ci) {
        Entity killer = source.getAttacker();
        int looting = 0;
        if (killer instanceof LivingEntity)
            looting = EnchantmentHelper.getLooting((LivingEntity)killer);
        Collection<ItemEntity> items = Lists.newArrayList();
        LivingDropsEvent event = new LivingDropsEvent((LivingEntity) (Object) this, source, items, looting, playerHitTimer > 0);
        MoreBootsHandler.onLivingDrop(event);
        items.forEach(item -> this.world.spawnEntity(item));
    }

    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getSlipperiness()F"))
    public float getSlipperiness(Block block) {
        LivingEntity entity = (LivingEntity) (Object) this;
        return entity.getEquippedStack(EquipmentSlot.FEET).getItem().equals(ItemInit.SLIPPERY_BOOTS) ? SlipperyBoots.SLIPPERINESS : block.getSlipperiness();
    }
}
