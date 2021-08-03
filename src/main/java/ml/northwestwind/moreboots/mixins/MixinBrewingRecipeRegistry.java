package ml.northwestwind.moreboots.mixins;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(net.minecraft.recipe.BrewingRecipeRegistry.class)
public abstract class MixinBrewingRecipeRegistry {
    @Shadow @Final private static List<Ingredient> POTION_TYPES;

    /**
     * @author Fabric
     */
    @Overwrite
    private static void registerPotionType(Item item) {
        POTION_TYPES.add(Ingredient.ofItems(item));
    }

    @Inject(at = @At("HEAD"), method = "registerDefaults")
    private static void registerMore(CallbackInfo ci) {
        registerPotionType(ItemInit.GLASS_BOOTS);
    }
}
