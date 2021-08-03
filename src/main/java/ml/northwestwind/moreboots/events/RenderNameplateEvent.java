package ml.northwestwind.moreboots.events;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;

@Event.HasResult
public class RenderNameplateEvent extends EntityEvent {

    private Text nameplateContent;
    private final Text originalContent;
    private final EntityRenderer<?> entityRenderer;
    private final MatrixStack matrixStack;
    private final VertexConsumerProvider renderTypeBuffer;
    private final int packedLight;
    private final float partialTicks;

    public RenderNameplateEvent(Entity entity, Text content, EntityRenderer<?> entityRenderer, MatrixStack matrixStack, VertexConsumerProvider renderTypeBuffer, int packedLight, float partialTicks)
    {
        super(entity);
        this.originalContent = content;
        this.setContent(this.originalContent);
        this.entityRenderer = entityRenderer;
        this.matrixStack = matrixStack;
        this.renderTypeBuffer = renderTypeBuffer;
        this.packedLight = packedLight;
        this.partialTicks = partialTicks;
    }

    public void setContent(Text contents)
    {
        this.nameplateContent = contents;
    }
    public Text getContent()
    {
        return this.nameplateContent;
    }
    public Text getOriginalContent()
    {
        return this.originalContent;
    }
    public EntityRenderer<?> getEntityRenderer()
    {
        return this.entityRenderer;
    }
    public MatrixStack getMatrixStack()
    {
        return this.matrixStack;
    }
    public VertexConsumerProvider getRenderTypeBuffer()
    {
        return this.renderTypeBuffer;
    }
    public int getPackedLight()
    {
        return this.packedLight;
    }
    public float getPartialTicks()
    {
        return this.partialTicks;
    }
}
