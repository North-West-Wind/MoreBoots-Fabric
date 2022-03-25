package ml.northwestwind.moreboots.init;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.potion.SlipperinessEffect;
import ml.northwestwind.moreboots.init.potion.WarmthEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EffectInit {
    public static final StatusEffect WARMTH = new WarmthEffect().addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.2, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    public static final StatusEffect SLIPPERINESS = new SlipperinessEffect();

    public static void registerEffects() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(Reference.MODID, "warmth"), WARMTH);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(Reference.MODID, "slipperiness"), SLIPPERINESS);
    }
}
