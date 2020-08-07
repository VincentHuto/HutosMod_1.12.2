package com.huto.hutosmod.keybinds;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyBindRegistry {

	public static KeyBinding KEY_RUNES;
	public static KeyBinding KEY_JEI;

	public static void register() {
		KEY_RUNES = new KeyBinding("keybind.runes", Keyboard.KEY_M, "key.categories.inventory");
		ClientRegistry.registerKeyBinding(KEY_RUNES);

	}

}
