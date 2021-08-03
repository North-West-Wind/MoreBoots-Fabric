package ml.northwestwind.moreboots.container.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import ml.northwestwind.moreboots.container.StorageBootsContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class StorageBootsScreen extends HandledScreen<StorageBootsContainer>
{
    private static final Identifier GUI_TEXTURE = new Identifier("minecraft", "textures/gui/container/generic_54.png");
    private final int rows;

    public StorageBootsScreen(StorageBootsContainer container, PlayerInventory playerInventory, Text titleIn)
    {
        super(container, playerInventory, titleIn);
        this.passEvents = false;
        this.rows = container.getContainerRows();
        this.backgroundHeight = 114 + this.rows * 18;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks); //Super
        this.drawMouseoverTooltip(matrixStack, mouseX, mouseY); //Render hovered tooltips
    }

    //Render
    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.rows * 18 + 17);
        this.drawTexture(matrices, i, j + this.rows * 18 + 17, 0, 126, this.backgroundWidth, 96);
    }
}