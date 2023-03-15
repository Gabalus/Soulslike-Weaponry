package net.soulsweaponry.client.registry;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.soulsweaponry.SoulsWeaponry;
import net.soulsweaponry.registry.PacketRegistry;

public class KeyBindRegistry {

    private static KeyBinding returnFreyrSword;
    private static KeyBinding stationaryFreyrSword;
    private static KeyBinding collectSummons;
    private static KeyBinding switchWeapon;

    public static void initClient() {
        returnFreyrSword = registerKeyboard("return_freyr_sword", GLFW.GLFW_KEY_R);
        stationaryFreyrSword = registerKeyboard("freyr_sword_stationary", GLFW.GLFW_KEY_Z);
        collectSummons = registerKeyboard("collect_summons_soul_reaper", GLFW.GLFW_KEY_V);
        switchWeapon = registerKeyboard("switch_weapon", GLFW.GLFW_KEY_B);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (returnFreyrSword.wasPressed()) {
                ClientPlayNetworking.send(PacketRegistry.RETURN_FREYR_SWORD, PacketByteBufs.empty());
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (stationaryFreyrSword.wasPressed()) {
                ClientPlayNetworking.send(PacketRegistry.STATIONARY_FREYR_SWORD, PacketByteBufs.empty());
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (collectSummons.wasPressed()) {
                ClientPlayNetworking.send(PacketRegistry.COLLECT_SUMMONS, PacketByteBufs.empty());
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (switchWeapon.wasPressed()) {
                ClientPlayNetworking.send(PacketRegistry.SWITCH_TRICK_WEAPON, PacketByteBufs.empty());
            }
        });
    }

    private static KeyBinding registerKeyboard(String name, int keycode) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + SoulsWeaponry.ModId + "." + name, InputUtil.Type.KEYSYM, keycode, "category." + SoulsWeaponry.ModId + ".main"));
    }
}
