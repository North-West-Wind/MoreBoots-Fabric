package ml.northwestwind.moreboots;

import ml.northwestwind.moreboots.handler.ServerEvents;
import ml.northwestwind.moreboots.init.BlockInit;
import ml.northwestwind.moreboots.init.ContainerInit;
import ml.northwestwind.moreboots.init.EffectInit;
import ml.northwestwind.moreboots.init.ItemInit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoreBoots implements ModInitializer {
    public static MoreBoots INSTANCE;
    public static final Logger LOGGER = LogManager.getLogger();

    public MoreBoots() {
        INSTANCE = this;
    }

    @Override
    public void onInitialize() {
        ItemInit.registerItems();
        ContainerInit.registerContainers();
        EffectInit.registerEffects();
        BlockInit.registerBlocks();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> ServerEvents.serverStarting());
    }

    public static class MoreBootsItemGroup {
        public static final ItemGroup INSTANCE = FabricItemGroupBuilder.build(new Identifier(Reference.MODID, "morebootstab"), () -> new ItemStack(ItemInit.CACTUS_BOOTS));
    }
}
