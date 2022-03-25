package ml.northwestwind.moreboots.init.item;

import ml.northwestwind.moreboots.MoreBoots;
import ml.northwestwind.moreboots.Reference;
import net.minecraft.util.Identifier;

public interface IHaveRegistryName {
    MoreBoots.Holder<String> registryName = MoreBoots.Holder.init(null);

    default void setRegistryName(String registryName) {
        this.registryName.set(registryName);
    }

    default Identifier getRegistryName() {
        return new Identifier(Reference.MODID, registryName.get());
    }
}
