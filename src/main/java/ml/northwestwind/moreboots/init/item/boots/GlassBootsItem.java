package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.events.LivingFallEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class GlassBootsItem extends BootsItem {
    public GlassBootsItem() {
        super(ItemInit.ModArmorMaterial.GLASS, "glass_boots");
    }

    @Override
    public void onLivingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        float distance = event.getDistance();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        if (entity.world.isClient) return;
        if (distance < 3.0f) return;
        int damage = (int) (10 * distance);
        if (entity.getRandom().nextInt(2) == 0) damage = boots.getMaxDamage();
        boots.damage(damage, entity, livingEntity -> {
            livingEntity.world.playSound(null, livingEntity.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 1, 1);
            breakGlassBoots(boots, PotionUtil.getPotion(boots), livingEntity);
        });
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        Potion potion = PotionUtil.getPotion(boots);
        for (StatusEffectInstance instance : potion.getEffects()) {
            StatusEffectInstance newEffect = new StatusEffectInstance(instance.getEffectType(), 205, instance.getAmplifier());
            entity.addStatusEffect(newEffect);
        }
        if (potion.hasInstantEffect()) {
            ItemStack newBoots = new ItemStack(ItemInit.GLASS_BOOTS_EMPTY);
            newBoots.setDamage(boots.getDamage());
            entity.equipStack(EquipmentSlot.FEET, newBoots);
        } else {
            NbtCompound tag = boots.getOrCreateNbt();
            if (!tag.contains("Duration")) tag.putInt("Duration", 20 * 60 * 20);
            int ticksLeft = tag.getInt("Duration") - 1;
            tag.putInt("Duration", ticksLeft);
            boots.setNbt(tag);
            if (ticksLeft <= 0) {
                ItemStack newBoots = new ItemStack(ItemInit.GLASS_BOOTS_EMPTY);
                newBoots.setDamage(boots.getDamage());
                entity.equipStack(EquipmentSlot.FEET, newBoots);
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        super.appendTooltip(stack, worldIn, tooltip, flagIn);
        PotionUtil.buildTooltip(stack, tooltip, 0);
    }

    private static void breakGlassBoots(ItemStack stack, Potion potion, LivingEntity entity) {
        if (entity.world.isClient) return;
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(entity.world, entity.getX(), entity.getY(), entity.getZ());
        areaeffectcloudentity.setOwner(entity);

        areaeffectcloudentity.setRadius(3.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setRadiusGrowth(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());
        areaeffectcloudentity.setPotion(potion);

        for (StatusEffectInstance statusEffectInstance : PotionUtil.getCustomPotionEffects(stack)) {
            areaeffectcloudentity.addEffect(new StatusEffectInstance(statusEffectInstance));
        }

        NbtCompound compoundnbt = stack.getOrCreateNbt();
        if (compoundnbt != null && compoundnbt.contains("CustomPotionColor", 99)) {
            areaeffectcloudentity.setColor(compoundnbt.getInt("CustomPotionColor"));
        }

        entity.world.spawnEntity(areaeffectcloudentity);
    }
}
