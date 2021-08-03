package ml.northwestwind.moreboots.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeybindInit {
    public static KeyBinding activate;

    public static void register() {
        activate = new KeyBinding("key.moreboots.activate", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "key.categories.moreboots");
        KeyBindingHelper.registerKeyBinding(activate);
    }
}
