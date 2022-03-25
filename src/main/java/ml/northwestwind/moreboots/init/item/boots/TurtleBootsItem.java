package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.events.LivingFallEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class TurtleBootsItem extends BootsItem {
    public TurtleBootsItem() {
        super(ItemInit.ModArmorMaterial.TURTLE, "turtle_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!entity.isInSwimmingPose() || !entity.isTouchingWater()) return;
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        Vec3d motion = entity.getVelocity();
        Vec3d direction = entity.getRotationVector().multiply(0.05);
        entity.setVelocity(motion.add(direction));
        boots.damage(1, entity, entity1 -> entity1.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
    }

    @Override
    public void onLivingFall(LivingFallEvent event) {
        event.setMultiplier(event.getMultiplier() * 0.9f);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        super.appendTooltip(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableText("credit.moreboots." + registryName));
    }
}