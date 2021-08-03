package ml.northwestwind.moreboots.init.item.boots;

import com.google.common.collect.Lists;
import ml.northwestwind.moreboots.events.PlaySoundEvent;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.List;

public class MusicBootsItem extends BootsItem {
    private static final List<SoundEvent> INSTRUMENTS = Lists.newArrayList(SoundEvents.BLOCK_NOTE_BLOCK_BANJO, SoundEvents.BLOCK_NOTE_BLOCK_BASS, SoundEvents.BLOCK_NOTE_BLOCK_BELL, SoundEvents.BLOCK_NOTE_BLOCK_BIT, SoundEvents.BLOCK_NOTE_BLOCK_CHIME, SoundEvents.BLOCK_NOTE_BLOCK_COW_BELL, SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO, SoundEvents.BLOCK_NOTE_BLOCK_FLUTE, SoundEvents.BLOCK_NOTE_BLOCK_GUITAR, SoundEvents.BLOCK_NOTE_BLOCK_HARP, SoundEvents.BLOCK_NOTE_BLOCK_XYLOPHONE, SoundEvents.BLOCK_NOTE_BLOCK_PLING, SoundEvents.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE);

    public MusicBootsItem() {
        super(ItemInit.ModArmorMaterial.MUSIC, "music_boots");
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onPlaySound(PlaySoundEvent event) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        SoundInstance sound = event.getSound();
        event.setResultSound(new PositionedSoundInstance(INSTRUMENTS.get(player.getRandom().nextInt(INSTRUMENTS.size())), SoundCategory.RECORDS, 1, (float) Math.pow(2.0D, (double) (player.getRandom().nextInt(24) - 12) / 12.0D), sound.getX(), sound.getY(), sound.getZ()));
    }
}
