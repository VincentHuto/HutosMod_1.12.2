package com.huto.hutosmod.renders.layers;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.huto.hutosmod.models.ModelColin;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.renders.RenderColin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerPlayerAura implements LayerRenderer<EntityPlayer> {
	private final ModelPlayer  colinModel = new ModelPlayer(0, true);
	private static final ResourceLocation COLIN_ARMOR = new ResourceLocation(Reference.MODID + ":textures/entity/colin_armor2.png");
	private final RenderPlayer colinRenderer;
	public LayerPlayerAura(RenderPlayer colinRendererIn) {
		this.colinRenderer = colinRendererIn;
	}

	@Override
	public void doRenderLayer(EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		Random rand = new Random();
		 GlStateManager.color(1F, 0F, 1F, 1F);
			GlStateManager.translate(0, -1.3, 0.0F);

		GlStateManager.depthMask(!player.isInvisible());
		this.colinRenderer.bindTexture(COLIN_ARMOR);
		//GL11.glScaled(.25, .25, .25);
		GlStateManager.matrixMode(5890);
		GlStateManager.loadIdentity();
		// Calls Instance of the game to bind the texture, then sclaes it
		Minecraft mc = Minecraft.getMinecraft();
		mc.getTextureManager().getTexture(COLIN_ARMOR);
		GL11.glScalef(5.0F, 5.0F, 5.0F);

		// Gets the variables needs for translation
		float mX = (float) player.ticksExisted + partialTicks;
		float mY = MathHelper.sin(mX * (rand.nextFloat() * 0.00000000000000000000000001F) * 0.002F) * 2.0F;
		float mZ = mX * 0.0011F;
		// Gets the variables needed for rotation
		float rX = (float) player.ticksExisted + partialTicks;
		float rY = MathHelper.cos(mX * 0.002F) * (float) player.ticksExisted + partialTicks;
		// Makes the texture move
		GlStateManager.translate(mY, mZ, 0.0F);
		GlStateManager.rotate(rX, mY, mZ, rY);

		GlStateManager.matrixMode(5888);
		GlStateManager.enableBlend();

		// Overall Scale how far it floats off the model
		GlStateManager.scale(2.0F, 2.0F, 2.0F);
		// Maybe Changing Color?
		// for (int i = 0; i < ageInTicks; i++) {}
		GlStateManager.color(0.5F, 0.5F, 1.5F, 1.0F);
		// Adds lighting of some form?

		GlStateManager.disableLighting();
		// Also brightens it deals with transparency of some type
		GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

		// Makes the aura move with the model
		//this.colinModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, scale, headPitch,player);
		// Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
		this.colinModel.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,scale);

		Minecraft.getMinecraft().entityRenderer.setupFogColor(false);

		GlStateManager.matrixMode(5890);
		GlStateManager.loadIdentity();
		GlStateManager.matrixMode(5888);
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
	}
	
	@Override
	public boolean shouldCombineTextures() {
		
		return false;
	}
}