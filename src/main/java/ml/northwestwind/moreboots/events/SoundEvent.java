package ml.northwestwind.moreboots.events;

import net.minecraft.client.sound.SoundSystem;

public class SoundEvent extends Event {
    private final SoundSystem manager;
    public SoundEvent(SoundSystem manager)
    {
        this.manager = manager;
    }

    public SoundSystem getManager()
    {
        return manager;
    }
}
