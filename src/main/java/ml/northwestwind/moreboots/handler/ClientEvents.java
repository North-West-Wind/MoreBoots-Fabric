package ml.northwestwind.moreboots.handler;

import ml.northwestwind.moreboots.container.screen.StorageBootsScreen;
import ml.northwestwind.moreboots.init.ContainerInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.KeybindInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;

@Environment(EnvType.CLIENT)
public class ClientEvents {
    public static void clientSetup() {
        KeybindInit.register();
        ScreenRegistry.register(ContainerInit.STORAGE_BOOTS, StorageBootsScreen::new);
    }
}
