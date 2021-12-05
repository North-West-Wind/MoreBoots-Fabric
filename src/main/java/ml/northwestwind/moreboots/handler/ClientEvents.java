package ml.northwestwind.moreboots.handler;

import ml.northwestwind.moreboots.container.screen.StorageBootsScreen;
import ml.northwestwind.moreboots.init.ContainerInit;
import ml.northwestwind.moreboots.init.KeybindInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class ClientEvents {
    public static void clientSetup() {
        KeybindInit.register();
        ScreenRegistry.register(ContainerInit.STORAGE_BOOTS, StorageBootsScreen::new);
    }
}
