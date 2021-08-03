package ml.northwestwind.moreboots.mixins;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.BrewingStandScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingStandScreenHandler.PotionSlot.class)
public class MixinPotionSlot {
    @Inject(at = @At("HEAD"), method = "matches", cancellable = true)
    private static void matches(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem().equals(ItemInit.GLASS_BOOTS)) cir.setReturnValue(true);
    }
}
