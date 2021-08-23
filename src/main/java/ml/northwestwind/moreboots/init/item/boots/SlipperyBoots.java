package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

import java.util.List;

public class SlipperyBoots extends BootsItem {
    public static final float SLIPPERINESS = 1.1f;

    public SlipperyBoots() {
        super(ItemInit.ModArmorMaterial.SLIPPERY, "slippery_boots");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        super.appendTooltip(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableText("credit.moreboots."+registryName));
    }
}
