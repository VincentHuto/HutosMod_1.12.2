package com.huto.hutosmod.dimension.alagadda;

import org.lwjgl.opengl.GL11;

import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.IRenderHandler;

public class AlagadaSkyRenderer extends IRenderHandler {
	public static final ResourceLocation SKY_TEXTURE = new ResourceLocation(Reference.MODID + ":textures/blocks/sky_texture.png");

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		this.renderSky(partialTicks, world, mc);

	}

	protected void renderSky(float partialTicks, WorldClient world, Minecraft mc) {
		Vec3d skyColor = world.getSkyColor(mc.getRenderViewEntity(), partialTicks);
		float skyR = (float)skyColor.x;
		float skyG = (float)skyColor.y;
		float skyB = (float)skyColor.z;

		float anaglyphR = 0.0F;
		float anaglyphG = 0.0F;
		float anaglyphB = 0.0F;

		if (mc.gameSettings.anaglyph) {
			anaglyphR = (skyR * 30.0F + skyG * 59.0F + skyB * 11.0F) / 100.0F;
			anaglyphG = (skyR * 30.0F + skyG * 70.0F) / 100.0F;
			anaglyphB = (skyR * 30.0F + skyB * 70.0F) / 100.0F;
			skyR = anaglyphR;
			skyG = anaglyphG;
			skyB = anaglyphB;
		}

		float invRainStrength = 1.0F - world.getRainStrength(partialTicks);

		GlStateManager.pushMatrix();
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);

		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();

	//	boolean useShaderSky = ShaderHelper.INSTANCE.isWorldShaderActive() && ShaderHelper.INSTANCE.getWorldShader() != null && ShaderHelper.INSTANCE.getWorldShader().getStarfieldTexture() >= 0;

		float starBrightness = (world.getStarBrightness(partialTicks) + 0.5F) * invRainStrength * invRainStrength * invRainStrength;
		float fade = 1.0F;


		GlStateManager.popMatrix();

		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();

/*		if (world.provider.isSkyColored()) {
			GlStateManager.color(skyR * 0.2F + 0.04F, skyG * 0.2F + 0.04F, skyB * 0.6F + 0.1F, starBrightness / (!useShaderSky ? 1.5F : 1.0F));
		} else {
			GlStateManager.color(skyR, skyG, skyB, starBrightness / (!useShaderSky ? 1.5F : 1.0F));
		}*/

		GlStateManager.enableTexture2D();

			if(Minecraft.getMinecraft().gameSettings.fancyGraphics) {
				//Render fancy non-shader sky dome
				mc.renderEngine.bindTexture(SKY_TEXTURE);
				GlStateManager.disableAlpha();
				GlStateManager.enableBlend();
				GlStateManager.enableTexture2D();
				RenderHelper.disableStandardItemLighting();
				GlStateManager.depthMask(false);
				GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
				GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
				GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
				GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
				GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
				GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
				GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
				GlStateManager.depthMask(true);
				GlStateManager.disableBlend();
				GlStateManager.enableAlpha();
	
	
			
		}
	}

}
