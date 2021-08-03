package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import ml.northwestwind.moreboots.events.LivingEvent;

public class IceBootsItem extends BootsItem {
    public IceBootsItem() {
        super(ItemInit.ModArmorMaterial.ICE, "ice_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        FrostWalkerEnchantment.freezeWater(entity, entity.world, new BlockPos(entity.getPos()), 2);
        int num = entity.getRandom().nextInt(100);
        if (num == 0)
            if (entity instanceof ServerPlayerEntity) boots.damage(1, entity.world.random, (ServerPlayerEntity) entity);
            else boots.damage(1, entity, entity1 -> entity1.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
    }
}
