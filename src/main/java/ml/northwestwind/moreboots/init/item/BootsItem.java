package ml.northwestwind.moreboots.init.item;

import ml.northwestwind.moreboots.MoreBoots;
import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.events.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class BootsItem extends ArmorItem {
    protected final String registryName;

    public BootsItem(ArmorMaterial material, String registryName, boolean isNetherite) {
        super(material, EquipmentSlot.FEET, isNetherite ? new Item.Settings().group(MoreBoots.MoreBootsItemGroup.INSTANCE).fireproof() : new Item.Settings().group(MoreBoots.MoreBootsItemGroup.INSTANCE));
        this.registryName = registryName;
    }

    public BootsItem(ArmorMaterial material, String registryName) {
        this(material, registryName, false);
    }

    public Identifier getRegistryName() {
        return new Identifier(Reference.MODID, registryName);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        if (!(stack.getItem() instanceof BootsItem)) return;
        tooltip.add(new TranslatableText("tooltip.moreboots." + registryName));
    }

    @Environment(EnvType.CLIENT)
    public void onPlaySound(PlaySoundEvent event) { }
    @Environment(EnvType.CLIENT)
    public void onPlayerLeftClick() { }
    public void onLivingFall(final LivingFallEvent event) { }
    public void onLivingJump(final LivingEvent.LivingJumpEvent event) { }
    public void onLivingHurt(final LivingHurtEvent event) { }
    public void onLivingAttack(final LivingHurtEvent event) { }
    public void onPlayerXpChange(final PlayerXpEvent.XpChange event) { }
    public void onLivingEquipmentChange(final LivingEquipmentChangeEvent event) { }
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) { }
    @Environment(EnvType.CLIENT)
    public void activateBoots() { }
    @Environment(EnvType.CLIENT)
    public void onShift() { }
    @Environment(EnvType.CLIENT)
    public void onJump() { }
    @Environment(EnvType.CLIENT)
    public void preRenderLiving(final RenderLivingEvent.Pre<?, ?> event) { }
    @Environment(EnvType.CLIENT)
    public void renderNameplate(final RenderNameplateEvent event) { }

    public void getCollisionShape(BlockView worldIn, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) { }
}
