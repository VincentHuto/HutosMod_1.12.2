package com.huto.hutosmod.proxy;

import java.lang.reflect.Field;
import java.util.Map;

import com.google.common.util.concurrent.ListenableFuture;
import com.huto.hutosmod.events.KarmaViewHandler;
import com.huto.hutosmod.events.ManaViewerHandler;
import com.huto.hutosmod.events.MaskOverlayHandler;
import com.huto.hutosmod.font.LovecraftFont;
import com.huto.hutosmod.font.ModTextFormatting;
import com.huto.hutosmod.gui.pages.TomePageLib;
import com.huto.hutosmod.keybinds.KeyBindRegistry;
import com.huto.hutosmod.mindrunes.events.ClientEventHandler;
import com.huto.hutosmod.mindrunes.events.GuiEvents;
import com.huto.hutosmod.models.ClientTickHandler;
import com.huto.hutosmod.particles.FXLightning;
import com.huto.hutosmod.particles.ParticleTextureStitcher;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.render.karmaViewHud;
import com.huto.hutosmod.render.manaViewerHud;
import com.huto.hutosmod.render.runicHealthRenderer;
import com.huto.hutosmod.render.item.RenderCustomItemGlint;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderItemFrame;
import net.minecraft.client.renderer.entity.RenderPotion;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ClientProxy extends CommonProxy {

	public static runicHealthRenderer runicBarRendererIn;
	public static manaViewerHud manaViewerHudIn;
	public static karmaViewHud karmaViewerHudIn;

	@Override
	public void registerEventHandlers() {
		super.registerEventHandlers();
		MinecraftForge.EVENT_BUS.register(new GuiEvents());
	}

	@Override
	public void lightningFX(Vector3 vectorStart, Vector3 vectorEnd, float ticksPerMeter, long seed, int colorOuter,
			int colorInner) {
		Minecraft.getMinecraft().effectRenderer.addEffect(new FXLightning(Minecraft.getMinecraft().world, vectorStart,
				vectorEnd, ticksPerMeter, seed, colorOuter, colorInner));
	}

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

	@Override
	public long getWorldElapsedTicks() {
		return ClientTickHandler.ticksInGame;
	}

	@Override
	public void preInit() {
		KeyBindRegistry.register();
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		MinecraftForge.EVENT_BUS.register(new ParticleTextureStitcher());
	}

	@Override
	public void init() {
		//replaceRenderers();

		TomePageLib.registerPages();
		ModTextFormatting.setAkloFont(new LovecraftFont(Minecraft.getMinecraft().gameSettings,
				new ResourceLocation(Reference.MODID, "textures/font/aklo.png"), Minecraft.getMinecraft().renderEngine,
				true));
		if (Minecraft.getMinecraft().getResourceManager() instanceof IReloadableResourceManager) {
			((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager())
					.registerReloadListener(ModTextFormatting.getAkloFont());
		}
	}

	
	///CODE FOR CUSTOM ENCHANTMENT COLOR:green BEcause item rendering is now hardcoded, Fuck this
	
	/*public static Field renderItem = ObfuscationReflectionHelper.findField(Minecraft.class, "field_175621_X");
	public static RenderCustomItemGlint modRenderItem; // used to provide custom enchantment glint color
	public static Field modelManager = ObfuscationReflectionHelper.findField(Minecraft.class, "field_175617_aL");
	public static Field itemRenderer = ObfuscationReflectionHelper.findField(ItemRenderer.class, "field_178112_h");

	private void replaceRenderers() {
		Minecraft mc = Minecraft.getMinecraft();

		// Replace render item with custom version
		modelManager.setAccessible(true);
		renderItem.setAccessible(true);
		try {
			
			modRenderItem = new RenderCustomItemGlint((RenderItem) renderItem.get(mc),
					(ModelManager) modelManager.get(mc));
			renderItem.set(mc, modRenderItem);
			itemRenderer.set(mc.getItemRenderer(), modRenderItem);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		mc.getRenderManager().entityRenderMap.put(EntityItem.class,
				new RenderEntityItem(mc.getRenderManager(), modRenderItem));
		mc.getRenderManager().entityRenderMap.put(EntityItem.class,
				new RenderEntityItem(mc.getRenderManager(), modRenderItem));

		((IReloadableResourceManager) (mc.getResourceManager())).registerReloadListener(modRenderItem);
	}*/

	@Override
	public void postInit() {
		Minecraft mc = Minecraft.getMinecraft();
		runicBarRendererIn = new runicHealthRenderer(mc);
		MinecraftForge.EVENT_BUS.register(new MaskOverlayHandler(runicBarRendererIn));
		manaViewerHudIn = new manaViewerHud(mc);
		MinecraftForge.EVENT_BUS.register(new ManaViewerHandler(manaViewerHudIn));
		karmaViewerHudIn = new karmaViewHud(mc);
		MinecraftForge.EVENT_BUS.register(new KarmaViewHandler(karmaViewerHudIn));
	}

}