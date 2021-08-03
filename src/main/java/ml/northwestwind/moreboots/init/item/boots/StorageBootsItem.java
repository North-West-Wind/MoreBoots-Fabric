package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.container.StorageBootsContainer;
import ml.northwestwind.moreboots.handler.packet.COpenStorageBootsPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import ml.northwestwind.moreboots.inventory.StorageBootsInventory;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.Nullable;

public class StorageBootsItem extends BootsItem {
    private static final int rows = 6;

    public StorageBootsItem() {
        super(ItemInit.ModArmorMaterial.STORAGE, "storage_boots");
    }

    public void showInventory(ServerPlayerEntity player) {
        ItemStack backpack = player.getEquippedStack(EquipmentSlot.FEET);
        if (!backpack.isEmpty()) {
            int rows = this.getRows();
            player.openHandledScreen(new ExtendedScreenHandlerFactory() {
                @Override
                public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
                    buf.writeVarInt(rows);
                }

                @Override
                public Text getDisplayName() {
                    return new TranslatableText("container.moreboots.storage_boots");
                }

                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                    return new StorageBootsContainer(syncId, player.getInventory(), new StorageBootsInventory(rows), rows);
                }
            });
        }
    }

    public int getRows() {
        return rows;
    }

    @Override
    public void activateBoots() {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        if (minecraft.player == null) return;
        ClientPlayNetworking.send(COpenStorageBootsPacket.ID, COpenStorageBootsPacket.createPacket());
    }
}
