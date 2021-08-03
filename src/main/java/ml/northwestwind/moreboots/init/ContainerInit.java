package ml.northwestwind.moreboots.init;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.container.StorageBootsContainer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ContainerInit {
    public static final ScreenHandlerType<StorageBootsContainer> STORAGE_BOOTS = ScreenHandlerRegistry.registerExtended(new Identifier(Reference.MODID, "storage_boots"), (syncId, inventory, buf) -> new StorageBootsContainer(syncId, inventory, buf.readVarInt()));

    public static void registerContainers() { }
}
