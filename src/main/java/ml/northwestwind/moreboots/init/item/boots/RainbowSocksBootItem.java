package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.events.LivingEvent;
import ml.northwestwind.moreboots.events.LivingFallEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.Explosion;

public class RainbowSocksBootItem extends SocksBootsItem {
    public RainbowSocksBootItem() {
        super(ItemInit.ModArmorMaterial.RAINBOW_SOCKS, "rainbow_socks_boots");
    }

    @Override
    public void onLivingFall(final LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        float distance = event.getDistance();
        if (entity.world.isClient) return;
        event.setCancelled(true);
        if (distance > 10) entity.playSound(SoundEvents.ENTITY_PLAYER_BIG_FALL, 1, 1);
        else if (distance > 3) entity.playSound(SoundEvents.ENTITY_PLAYER_SMALL_FALL, 1, 1);
    }

    @Override
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isSneaking()) return;
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        Vec3d motion = entity.getVelocity();
        NbtCompound tag = boots.getOrCreateNbt();
        entity.setVelocity(motion.add(0, 0.01 * tag.getLong("tickSneak"), 0));
        tag.putLong("tickSneak", 0);
        boots.setNbt(tag);
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isSneaking() && entity.isOnGround()) {
            ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
            NbtCompound tag = boots.getOrCreateNbt();
            long tickSneak = tag.getLong("tickSneak");
            tag.putLong("tickSneak", tag.getLong("tickSneak") + 1);
            tickSneak += 1;
            if (entity instanceof PlayerEntity && !entity.world.isClient)
                ((PlayerEntity) entity).sendMessage(new TranslatableText("message.moreboots.building_speed", tickSneak), true);
            if (tickSneak >= 864000 && !entity.isSpectator()) {
                Vec3d pos = entity.getPos();
                tag.putLong("tickSneak", 0);
                boots.setDamage(boots.getMaxDamage());
                entity.world.createExplosion(entity, pos.x, entity.getBodyY(-0.0625D), pos.z, 10.0F, Explosion.DestructionType.BREAK);
                entity.setVelocity(entity.getVelocity().add(0, 0.01 * 864000, 0));
                if (entity instanceof PlayerEntity && !entity.world.isClient) {
                    MinecraftServer server = entity.world.getServer();
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) entity;
                    serverPlayerEntity.getAdvancementTracker().grantCriterion(server.getAdvancementLoader().get(new Identifier("moreboots", "moreboots/twelve_hours")), "twelve_hours");
                }
            }
            boots.setNbt(tag);
        }
    }
}
