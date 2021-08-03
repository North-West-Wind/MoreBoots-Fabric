package ml.northwestwind.moreboots;

import ml.northwestwind.moreboots.handler.ClientEvents;
import ml.northwestwind.moreboots.handler.MoreBootsClientHandler;
import ml.northwestwind.moreboots.init.ItemInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;

public class MoreBootsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((stack, layer) -> {
            if (layer == 0) return -1;
            Potion potion = PotionUtil.getPotion(stack);
            return PotionUtil.getColor(potion);
        }, ItemInit.GLASS_BOOTS);
        ClientEvents.clientSetup();
        ClientTickEvents.END_CLIENT_TICK.register(MoreBootsClientHandler::onKeyInput);
    }
}
