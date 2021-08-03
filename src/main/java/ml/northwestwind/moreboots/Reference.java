package ml.northwestwind.moreboots;

import net.minecraft.entity.damage.DamageSource;

public class Reference {
    public static final String MODID = "moreboots";

    public static final DamageSource STOMP = (new WidenedDamageSource("stomp"));

    public static class WidenedDamageSource extends DamageSource {
        public WidenedDamageSource(String name) {
            super(name);
        }
    }
}
