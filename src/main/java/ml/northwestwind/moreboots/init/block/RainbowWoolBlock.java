package ml.northwestwind.moreboots.init.block;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class RainbowWoolBlock extends Block {
    private static final ArrayList<Item> dyes = Lists.newArrayList();

    static {
        dyes.add(Items.BLACK_DYE);
        dyes.add(Items.WHITE_DYE);
        dyes.add(Items.RED_DYE);
        dyes.add(Items.BROWN_DYE);
        dyes.add(Items.GREEN_DYE);
        dyes.add(Items.YELLOW_DYE);
        dyes.add(Items.BLUE_DYE);
        dyes.add(Items.CYAN_DYE);
        dyes.add(Items.LIME_DYE);
        dyes.add(Items.LIGHT_GRAY_DYE);
        dyes.add(Items.GRAY_DYE);
        dyes.add(Items.MAGENTA_DYE);
        dyes.add(Items.PINK_DYE);
        dyes.add(Items.LIGHT_BLUE_DYE);
        dyes.add(Items.ORANGE_DYE);
        dyes.add(Items.PURPLE_DYE);
    }

    public RainbowWoolBlock(Settings properties) {
        super(properties);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult result) {
        if(player.getStackInHand(hand).getItem() instanceof DyeItem) {
            if (!world.isClient) player.setStackInHand(hand, new ItemStack(dyes.get(new Random().nextInt(dyes.size()))));
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, result);
    }
}
