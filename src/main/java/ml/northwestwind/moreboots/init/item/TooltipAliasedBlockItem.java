package ml.northwestwind.moreboots.init.item;

import ml.northwestwind.moreboots.MoreBoots;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class TooltipAliasedBlockItem extends AliasedBlockItem implements IHaveRegistryName {
    public TooltipAliasedBlockItem(Block block, Settings properties, String registryName) {
        super(block, properties);
        setRegistryName(registryName);
    }

    public TooltipAliasedBlockItem(Block block, String registryName) {
        this(block, new Item.Settings().group(MoreBoots.MoreBootsItemGroup.INSTANCE), registryName);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn)
    {
    }
}
