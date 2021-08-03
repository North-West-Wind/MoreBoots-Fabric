package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingHurtEvent;
import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.init.EffectInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.TranslatableText;

public class SocksBootsItem extends BootsItem {
    public SocksBootsItem() {
        super(ItemInit.ModArmorMaterial.SOCKS, "socks_boots");
    }

    public SocksBootsItem(ArmorMaterial material, String registryName) {
        super(material, registryName);
    }

    @Override
    public void onLivingHurt(final LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        if (event.getSource().equals(DamageSource.IN_FIRE) || event.getSource().equals(DamageSource.LAVA))
            boots.setDamage(boots.getMaxDamage());
    }

    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        NbtCompound tag = boots.getOrCreateNbt();
        if (entity.isSubmergedInWater() && !entity.isSpectator()) {
            if (!tag.getBoolean("wet")) tag.putBoolean("wet", true);
            tag.putLong("wetTick", System.currentTimeMillis());
            boots.setNbt(tag);
            boots.setCustomName(new TranslatableText("item.moreboots.socks_boots_wet"));
        }
        if (tag.getBoolean("wet")) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 1));
            if (System.currentTimeMillis() - tag.getLong("wetTick") >= 300000) {
                tag.putBoolean("wet", false);
                tag.putLong("wetTick", 0);
                boots.setCustomName(new TranslatableText("item.moreboots.socks_boots"));
            }
        } else {
            entity.addStatusEffect(new StatusEffectInstance(EffectInit.WARMTH, 205));
        }
        boots.setNbt(tag);
    }
}
