package ml.northwestwind.moreboots.init.item;

import ml.northwestwind.moreboots.MoreBoots;
import ml.northwestwind.moreboots.Reference;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class TooltipItem extends Item {
    private final String registryName;

    public TooltipItem(Settings properties, String registryName) {
        super(properties);
        this.registryName = registryName;
    }

    public TooltipItem(String registryName) {
        this(new Item.Settings().group(MoreBoots.MoreBootsItemGroup.INSTANCE), registryName);
    }

    public Identifier getRegistryName() {
        return new Identifier(Reference.MODID, registryName);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn)
    {
    }
}
