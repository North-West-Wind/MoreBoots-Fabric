package ml.northwestwind.moreboots.init.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;

public class WarmthEffect extends StatusEffect {
    public WarmthEffect() {
        super(StatusEffectCategory.NEUTRAL, 16738816);
    }

    @Override
    public void applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
        Vec3d pos = livingEntity.getPos();
        Biome biome = livingEntity.world.getBiome(new BlockPos(pos));
        if (biome.isCold(new BlockPos(pos))) onApplied(livingEntity, livingEntity.getAttributes(), 0);
        else onRemoved(livingEntity, livingEntity.getAttributes(), 0);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration > 0;
    }
}
