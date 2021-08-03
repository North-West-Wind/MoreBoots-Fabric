package ml.northwestwind.moreboots.events;

import net.minecraft.entity.player.PlayerEntity;

public class PlayerEvent extends LivingEvent {
    private final PlayerEntity player;

    public PlayerEvent(PlayerEntity player) {
        super(player);
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
    }
}
