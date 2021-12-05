package ml.northwestwind.moreboots.mixins;

import ml.northwestwind.moreboots.events.PlayerXpEvent;
import ml.northwestwind.moreboots.handler.MoreBootsHandler;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends MixinLivingEntity {
    @Unique private boolean isReCallAddXp;

    @Shadow public abstract void addExperience(int experience);

    @Inject(at = @At("HEAD"), method = "addExperience", cancellable = true)
    public void addExperience(int experience, CallbackInfo ci) {
        if (isReCallAddXp) {
            isReCallAddXp = false;
            return;
        }
        PlayerXpEvent.XpChange event = new PlayerXpEvent.XpChange((PlayerEntity) (Object) this, experience);
        MoreBootsHandler.onPlayerXpChange(event);
        experience = event.isCancelled() ? 0 : event.getAmount();
        isReCallAddXp = true;
        this.addExperience(experience);
        ci.cancel();
    }
}
