package ml.northwestwind.moreboots.init.potion;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class SlipperinessEffect extends StatusEffect {
    public SlipperinessEffect() {
        super(StatusEffectCategory.NEUTRAL, 250876);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration > 0;
    }
}
