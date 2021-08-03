package ml.northwestwind.moreboots.events;

import ml.northwestwind.moreboots.events.annotations.Cancellable;
import net.minecraft.entity.player.PlayerEntity;

public class PlayerXpEvent extends PlayerEvent {
    public PlayerXpEvent(PlayerEntity player) {
        super(player);
    }

    @Cancellable
    public static class XpChange extends PlayerXpEvent {
        private int amount;

        public XpChange(PlayerEntity player, int amount) {
            super(player);
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}
