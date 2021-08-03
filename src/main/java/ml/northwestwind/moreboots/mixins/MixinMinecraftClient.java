package ml.northwestwind.moreboots.mixins;

import ml.northwestwind.moreboots.handler.MoreBootsClientHandler;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Shadow @Final private static Logger LOGGER;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;resetLastAttackedTicks()V"), method = "doAttack")
    public void fireEmptyLeftClick(CallbackInfo ci) {
        LOGGER.info("Firing empty click");
        MoreBootsClientHandler.onPlayerLeftClick();
    }
}
