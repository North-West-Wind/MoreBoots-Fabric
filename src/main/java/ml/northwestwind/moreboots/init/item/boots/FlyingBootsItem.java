package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingEquipmentChangeEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

import java.util.List;

public class FlyingBootsItem extends BootsItem {
    public FlyingBootsItem() {
        super(ItemInit.ModArmorMaterial.FLYING, "flying_boots");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        super.appendTooltip(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableText("credit.moreboots."+registryName));
    }

    @Override
    public void onLivingEquipmentChange(final LivingEquipmentChangeEvent event) {
        if (!event.getSlot().equals(EquipmentSlot.FEET)) return;
        if (!(event.getEntityLiving() instanceof PlayerEntity player)) return;
        ItemStack from = event.getFrom();
        ItemStack to = event.getTo();
        boolean oldFlying = player.getAbilities().allowFlying;
        if (!from.getItem().equals(ItemInit.FLYING_BOOTS) && to.getItem().equals(ItemInit.FLYING_BOOTS)) player.getAbilities().allowFlying = true;
        else if (from.getItem().equals(ItemInit.FLYING_BOOTS) && !to.getItem().equals(ItemInit.FLYING_BOOTS)) player.getAbilities().allowFlying = false;
        if (oldFlying != player.getAbilities().allowFlying) player.sendAbilitiesUpdate();
    }
}
