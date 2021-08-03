package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.Tag;

public class SpongeBootsItem extends BootsItem {
    private final Tag.Identified<Fluid> absorb;

    public SpongeBootsItem(ArmorMaterial material, String registryName, Tag.Identified<Fluid> absorb, boolean fireRes) {
        super(material, registryName, fireRes);
        this.absorb = absorb;
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        boolean absorbed = Utils.absorb(entity.world, entity.getBlockPos(), absorb);
        if (absorbed && entity.getRandom().nextInt(100) == 0)
            if (entity instanceof ServerPlayerEntity) boots.damage(1, entity.world.random, (ServerPlayerEntity) entity);
            else boots.damage(1, entity, entity1 -> entity1.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
    }
}
