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

	    public static KeyBinding hello;
	 
		public static KeyBinding KEY_RUNES;


	    
	    public static void register()
	    {
	        hello = new KeyBinding("key.hello", Keyboard.KEY_V, "key.categories.tutorial");
	        KEY_RUNES = new KeyBinding("keybind.runes", Keyboard.KEY_B,"key.categories.inventory");
	        ClientRegistry.registerKeyBinding(hello);
	        ClientRegistry.registerKeyBinding(KEY_RUNES);

	    }
	
}
