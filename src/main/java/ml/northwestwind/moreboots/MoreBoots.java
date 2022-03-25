package ml.northwestwind.moreboots;

import ml.northwestwind.moreboots.handler.ServerEvents;
import ml.northwestwind.moreboots.init.BlockInit;
import ml.northwestwind.moreboots.init.ContainerInit;
import ml.northwestwind.moreboots.init.EffectInit;
import ml.northwestwind.moreboots.init.ItemInit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoreBoots implements ModInitializer {
    public static MoreBoots INSTANCE;
    public static final Logger LOGGER = LogManager.getLogger();
    private static final Identifier END_CITY_TREASURE_LOOT_TABLE = new Identifier("chests/end_city_treasure");

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
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, table, setter) -> {
            if (END_CITY_TREASURE_LOOT_TABLE.equals(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(0.01f))
                        .with(ItemEntry.builder(ItemInit.FLOATING_CORE));
                table.pool(poolBuilder);
            }
        });
    }

    public static class MoreBootsItemGroup {
        public static final ItemGroup INSTANCE = FabricItemGroupBuilder.build(new Identifier(Reference.MODID, "morebootstab"), () -> new ItemStack(ItemInit.CACTUS_BOOTS));
    }

    public static class Holder<T> {
        private T obj;

        public static <T> Holder<T> init(T obj) {
            Holder<T> holder = new Holder<>();
            holder.set(obj);
            return holder;
        }

        public void set(T obj) {
            this.obj = obj;
        }

        public T get() {
            return obj;
        }
    }
}
