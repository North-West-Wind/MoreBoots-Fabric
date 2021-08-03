package ml.northwestwind.moreboots.events;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;

public class PlaySoundEvent extends SoundEvent
{
    private final String name;
    private final SoundInstance sound;
    private SoundInstance result;

    public PlaySoundEvent(SoundSystem manager, SoundInstance sound)
    {
        super(manager);
        this.sound = sound;
        this.name = sound.getId().getPath();
        this.setResultSound(sound);
    }

    public String getName()
    {
        return name;
    }

    public SoundInstance getSound()
    {
        return sound;
    }

    public SoundInstance getResultSound()
    {
        return result;
    }

    public void setResultSound(SoundInstance result)
    {
        this.result = result;
    }
}
