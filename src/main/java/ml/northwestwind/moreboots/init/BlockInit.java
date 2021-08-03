package ml.northwestwind.moreboots.init;

import ml.northwestwind.moreboots.MoreBoots;
import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.block.GlowstoneDustBlock;
import ml.northwestwind.moreboots.init.block.RainbowWoolBlock;
import ml.northwestwind.moreboots.init.block.RedstoneDustBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockInit {

    public static final Block RAINBOW_WOOL = new RainbowWoolBlock(AbstractBlock.Settings.of(Material.WOOL).strength(10.0f, 3600000f).sounds(BlockSoundGroup.WOOL));
    public static final Block GLOWSTONE_DUST = new GlowstoneDustBlock(AbstractBlock.Settings.of(Material.AIR).noCollision().breakInstantly().strength(0.5f).luminance(value -> 15));
    public static final Block REDSTONE_DUST = new RedstoneDustBlock(AbstractBlock.Settings.of(Material.AIR).noCollision().breakInstantly().strength(0.5f));
    public static final Block COBBLESTONE_8 = new Block(AbstractBlock.Settings.copy(Blocks.COBBLESTONE).strength(16.0f, 48.0f));
    public static final Block COBBLESTONE_64 = new Block(AbstractBlock.Settings.copy(Blocks.COBBLESTONE).strength(128.0f, 384.0f));
    public static final Block COBBLESTONE_512 = new Block(AbstractBlock.Settings.copy(Blocks.COBBLESTONE).strength(1024.0f, 3072.0f));

    public static void registerBlocks() {
        register("rainbow_wool", RAINBOW_WOOL, new Item.Settings().maxCount(64).group(MoreBoots.MoreBootsItemGroup.INSTANCE));
        register("glowstone_dust", GLOWSTONE_DUST, null);
        register("redstone_dust", REDSTONE_DUST, null);
        register("cobblestone_8", COBBLESTONE_8, new Item.Settings().maxCount(64).group(MoreBoots.MoreBootsItemGroup.INSTANCE));
        register("cobblestone_64", COBBLESTONE_64, new Item.Settings().maxCount(64).group(MoreBoots.MoreBootsItemGroup.INSTANCE));
        register("cobblestone_512", COBBLESTONE_512, new Item.Settings().maxCount(64).group(MoreBoots.MoreBootsItemGroup.INSTANCE));
    }

    private static void register(String id, Block block, Item.Settings settings) {
        Registry.register(Registry.BLOCK, new Identifier(Reference.MODID, id), block);
        if (settings != null) Registry.register(Registry.ITEM, new Identifier(Reference.MODID, id), new BlockItem(block, settings));
    }
}
