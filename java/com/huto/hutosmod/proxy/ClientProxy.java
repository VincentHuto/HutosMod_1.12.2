package com.huto.hutosmod.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import com.huto.hutosmod.events.MaskOverlayHandler;
import com.huto.hutosmod.events.karmaViewHandler;
import com.huto.hutosmod.events.manaViewerHandler;
import com.huto.hutosmod.font.LovecraftFont;
import com.huto.hutosmod.font.ModTextFormatting;
import com.huto.hutosmod.gui.pages.GuiTomeTitle;
import com.huto.hutosmod.gui.pages.TomePageLib;
import com.huto.hutosmod.keybinds.KeyBindRegistry;
import com.huto.hutosmod.mindrunes.events.ClientEventHandler;
import com.huto.hutosmod.mindrunes.events.GuiEvents;
import com.huto.hutosmod.models.ClientTickHandler;
import com.huto.hutosmod.particles.FXLightning;
import com.huto.hutosmod.particles.ParticleTextureStitcher;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.renders.karmaViewHud;
import com.huto.hutosmod.renders.manaViewerHud;
import com.huto.hutosmod.renders.runicHealthRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerEventHandlers() {
		super.registerEventHandlers();
		MinecraftForge.EVENT_BUS.register(new GuiEvents());

	}
	
	@Override
	public RayTraceResult rayTrace(double blockReachDistance, float partialTicks, EntityPlayer player, World worldIn) {
		Vec3d vec3d = player.getPositionEyes(partialTicks);
		Vec3d vec3d1 = player.getLook(partialTicks);
		Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance,
				vec3d1.z * blockReachDistance);
		return worldIn.rayTraceBlocks(vec3d, vec3d2, false, false, true);
	}

	@Override
	public Vec3d getPositionEyes(float partialTicks, EntityPlayer player) {
		if (partialTicks == 1.0F) {
			return new Vec3d(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ);
		} else {
			double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) partialTicks;
			double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) partialTicks
					+ (double) player.getEyeHeight();
			double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) partialTicks;
			return new Vec3d(d0, d1, d2);
		}
	}

	@Override

	public void openTomeBook() {
		// Minecraft.getMinecraft().displayGuiScreen(new GuiTomeTitle(false));
	}

	@Override
	public void openElderBook() {
		// Minecraft.getMinecraft().displayGuiScreen(new GuiTomeTitle(true));
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

	public static runicHealthRenderer runicBarRendererIn;
	public static manaViewerHud manaViewerHudIn;
	public static karmaViewHud karmaViewerHudIn;

	@Override
	public void preInit() {
		KeyBindRegistry.register();
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
		MinecraftForge.EVENT_BUS.register(new ParticleTextureStitcher());
	}

	@Override
	public void init() {
		TomePageLib.registerPages();
		ModTextFormatting.setAkloFont(new LovecraftFont(Minecraft.getMinecraft().gameSettings,
				new ResourceLocation(Reference.MODID, "textures/font/aklo.png"), Minecraft.getMinecraft().renderEngine,
				true));
		if (Minecraft.getMinecraft().getResourceManager() instanceof IReloadableResourceManager) {
			((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager())
					.registerReloadListener(ModTextFormatting.getAkloFont());
		}
	}

	@Override
	public void postInit() {
		Minecraft mc = Minecraft.getMinecraft();
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