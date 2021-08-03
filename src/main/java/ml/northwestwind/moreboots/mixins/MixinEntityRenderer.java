package ml.northwestwind.moreboots.mixins;

import ml.northwestwind.moreboots.events.Event;
import ml.northwestwind.moreboots.events.RenderNameplateEvent;
import ml.northwestwind.moreboots.handler.MoreBootsClientHandler;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {
    @Shadow protected abstract <T extends Entity> boolean hasLabel(T entity);

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public <T extends Entity> void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        RenderNameplateEvent event = new RenderNameplateEvent(entity, entity.getDisplayName(), (EntityRenderer<T>) (Object) this, matrices, vertexConsumers, light, tickDelta);
        MoreBootsClientHandler.renderNameplate(event);
        if (event.getResult() == Event.Result.DENY || (event.getResult() != Event.Result.ALLOW && this.hasLabel(entity))) ci.cancel();
    }
}
