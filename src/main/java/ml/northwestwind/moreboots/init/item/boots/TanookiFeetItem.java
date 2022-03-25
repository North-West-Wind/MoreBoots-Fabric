package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

import java.util.List;

public class TanookiFeetItem extends GlidingBootsItem {
    public TanookiFeetItem() {
        super(ItemInit.ModArmorMaterial.TANOOKI, "tanooki_feet");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        super.appendTooltip(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableText("credit.moreboots."+registryName));
    }
}