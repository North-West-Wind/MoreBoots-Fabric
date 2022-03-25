package ml.northwestwind.moreboots.mixins;

import ml.northwestwind.moreboots.events.RenderLivingEvent;
import ml.northwestwind.moreboots.handler.MoreBootsClientHandler;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class MixinLivingEntityRenderer {
    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", cancellable = true)
    public <T extends LivingEntity> void preRender(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        RenderLivingEvent.Pre event = new RenderLivingEvent.Pre(livingEntity, (LivingEntityRenderer) (Object) this, g, matrixStack, vertexConsumerProvider, i);
        MoreBootsClientHandler.preRenderLiving(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    public <T extends LivingEntity> void postRender(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        MoreBootsClientHandler.postRenderLiving(new RenderLivingEvent.Post(livingEntity, (LivingEntityRenderer) (Object) this, g, matrixStack, vertexConsumerProvider, i));
    }
}
