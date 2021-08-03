package ml.northwestwind.moreboots.mixins;

import ml.northwestwind.moreboots.events.PlaySoundEvent;
import ml.northwestwind.moreboots.handler.MoreBootsClientHandler;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundSystem.class)
public abstract class MixinSoundSystem {
    @Shadow public abstract void play(SoundInstance sound);

    @Unique private boolean isReCall;

    @Inject(at = @At("HEAD"), method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", cancellable = true)
    public void play(SoundInstance sound, CallbackInfo ci) {
        if (isReCall) {
            isReCall = false;
            return;
        }
        PlaySoundEvent event = new PlaySoundEvent((SoundSystem) (Object) this, sound);
        MoreBootsClientHandler.onPlaySound(event);
        isReCall = true;
        this.play(event.getResultSound());
        ci.cancel();
    }
}
