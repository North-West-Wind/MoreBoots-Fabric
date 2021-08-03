package ml.northwestwind.moreboots.events;

import ml.northwestwind.moreboots.events.annotations.Cancellable;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public abstract class RenderLivingEvent<T extends LivingEntity, M extends EntityModel<T>> extends Event
{

    private final LivingEntity entity;
    private final LivingEntityRenderer<T, M> renderer;
    private final float partialRenderTick;
    private final MatrixStack matrixStack;
    private final VertexConsumerProvider buffers;
    private final int light;

    public RenderLivingEvent(LivingEntity entity, LivingEntityRenderer<T, M> renderer, float partialRenderTick, MatrixStack matrixStack, VertexConsumerProvider buffers, int light)
    {
        this.entity = entity;
        this.renderer = renderer;
        this.partialRenderTick = partialRenderTick;
        this.matrixStack = matrixStack;
        this.buffers = buffers;
        this.light = light;
    }

    public LivingEntity getEntity() { return entity; }
    public LivingEntityRenderer<T, M> getRenderer() { return renderer; }
    public float getPartialRenderTick() { return partialRenderTick; }
    public MatrixStack getMatrixStack() { return matrixStack; }
    public VertexConsumerProvider getBuffers() { return buffers; }
    public int getLight() { return light; }

    @Cancellable
    public static class Pre<T extends LivingEntity, M extends EntityModel<T>> extends RenderLivingEvent<T, M>
    {
        public Pre(LivingEntity entity, LivingEntityRenderer<T, M> renderer, float partialRenderTick, MatrixStack matrixStack, VertexConsumerProvider buffers, int light) {
            super(entity, renderer, partialRenderTick, matrixStack, buffers, light);
        }
    }

    public static class Post<T extends LivingEntity, M extends EntityModel<T>> extends RenderLivingEvent<T, M>
    {
        public Post(LivingEntity entity, LivingEntityRenderer<T, M> renderer, float partialRenderTick, MatrixStack matrixStack, VertexConsumerProvider buffers, int light) {
            super(entity, renderer, partialRenderTick, matrixStack, buffers, light);
        }
    }
}
