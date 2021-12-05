package ml.northwestwind.moreboots.handler;

import ml.northwestwind.moreboots.events.*;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;

import java.util.Random;

public class MoreBootsHandler {
    private static final Random rng = new Random();

    public static void onLivingFall(final LivingFallEvent event) {
        ItemStack boots = event.getEntityLiving().getEquippedStack(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onLivingFall(event);
    }

    public static void onLivingJump(final LivingEvent.LivingJumpEvent event) {
        ItemStack boots = event.getEntityLiving().getEquippedStack(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onLivingJump(event);
    }

    public static void onLivingHurt(final LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Entity attacker = event.getSource().getAttacker();
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);
        ItemStack atBoots;
        LogManager.getLogger().info("Firing LivingDamageEvent to entity: {} and boots: {}", entity.getClass().getName(), boots.getItem().getClass().getName());
        if (attacker instanceof LivingEntity && (atBoots = ((LivingEntity) attacker).getEquippedStack(EquipmentSlot.FEET)).getItem() instanceof BootsItem) ((BootsItem) atBoots.getItem()).onLivingAttack(event);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onLivingHurt(event);
    }

    public static void onPlayerXpChange(final PlayerXpEvent.XpChange event) {
        ItemStack boots = event.getPlayer().getEquippedStack(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onPlayerXpChange(event);
    }

    public static void onLivingEquipmentChange(final LivingEquipmentChangeEvent event) {
        ItemStack to = event.getTo();
        ItemStack from = event.getFrom();
        if (from.getItem().equals(ItemInit.LOKI_BOOTS) && !from.getItem().equals(to.getItem())) event.getEntityLiving().setInvisible(false);
        if (from.getItem() instanceof BootsItem) ((BootsItem) from.getItem()).onLivingEquipmentChange(event);
        if (to.getItem() instanceof BootsItem) ((BootsItem) to.getItem()).onLivingEquipmentChange(event);
    }

    public static void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        ItemStack boots = event.getEntityLiving().getEquippedStack(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onLivingUpdate(event);
    }

    public static void onLivingDrop(final LivingDropsEvent event) {
        int looting = event.getLootingLevel();
        int shouldDrop = rng.nextInt((1 + looting) * 2) + looting;
        if (shouldDrop < 1) return;
        LivingEntity entity = event.getEntityLiving();
        if (entity.getType().equals(EntityType.BAT)) {
            ItemStack stack = new ItemStack(ItemInit.BAT_HIDE, shouldDrop);
            ItemEntity item = new ItemEntity(entity.world, entity.getX(), entity.getY(), entity.getZ(), stack);
            event.getDrops().add(item);
        } else if (entity.getType().equals(EntityType.STRIDER)) {
            ItemStack stack = new ItemStack(ItemInit.STRIDER_FOOT, shouldDrop);
            ItemEntity item = new ItemEntity(entity.world, entity.getX(), entity.getY(), entity.getZ(), stack);
            event.getDrops().add(item);
        }
    }
}
