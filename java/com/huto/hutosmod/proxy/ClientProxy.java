package com.huto.hutosmod.proxy;

import org.lwjgl.input.Keyboard;

import com.google.common.util.concurrent.ListenableFuture;
import com.huto.hutosmod.events.MaskOverlayHandler;
import com.huto.hutosmod.events.karmaViewHandler;
import com.huto.hutosmod.events.manaViewerHandler;
import com.huto.hutosmod.font.LovecraftFont;
import com.huto.hutosmod.font.TextFormating;
import com.huto.hutosmod.keybinds.KeyBindRegistry;
import com.huto.hutosmod.mindrunes.GUI.GuiMindRunes;
import com.huto.hutosmod.mindrunes.events.ClientEventHandler;
import com.huto.hutosmod.mindrunes.events.GuiEvents;
import com.huto.hutosmod.models.ClientTickHandler;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.renders.karmaViewHud;
import com.huto.hutosmod.renders.manaViewerHud;
import com.huto.hutosmod.renders.runicHealthRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}

	@Override
	public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
		return Minecraft.getMinecraft().addScheduledTask(runnableToSchedule);
	}

	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().player;
	}

	public static runicHealthRenderer runicBarRendererIn;
	public static manaViewerHud manaViewerHudIn;
	public static karmaViewHud karmaViewerHudIn;

	@Override
	public void preInit() {
		KeyBindRegistry.register();
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

	}

	@Override
	public void init() {
		
		TextFormating.setAkloFont(new LovecraftFont(Minecraft.getMinecraft().gameSettings,
				new ResourceLocation(Reference.MODID, "textures/font/aklo.png"), Minecraft.getMinecraft().renderEngine,
				true));
		if (Minecraft.getMinecraft().getResourceManager() instanceof IReloadableResourceManager) {
			((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager())
					.registerReloadListener(TextFormating.getAkloFont());
		}
	}

	@Override
	public void postInit() {
		Minecraft mc = Minecraft.getMinecraft();
		MinecraftForge.EVENT_BUS.register(new GuiEvents());
		runicBarRendererIn = new runicHealthRenderer(mc);
		MinecraftForge.EVENT_BUS.register(new MaskOverlayHandler(runicBarRendererIn));
		manaViewerHudIn = new manaViewerHud(mc);
		MinecraftForge.EVENT_BUS.register(new manaViewerHandler(manaViewerHudIn));
		karmaViewerHudIn = new karmaViewHud(mc);
		MinecraftForge.EVENT_BUS.register(new karmaViewHandler(karmaViewerHudIn));
	}

	@Override
	public long getWorldElapsedTicks() {
		return ClientTickHandler.ticksInGame;
	}

}