package com.huto.hutosmod.renders.layers;

import org.lwjgl.opengl.GL11;

import com.huto.hutosmod.entities.EntityColin;
import com.huto.hutosmod.models.ModelColin;
import com.huto.hutosmod.models.ModelKeybladeTest;
import com.huto.hutosmod.models.ModelKingdomKey;
import com.huto.hutosmod.models.ModelKingdomKeyD;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.renders.RenderColin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerColinKeys implements LayerRenderer<EntityColin> {
	private static final ResourceLocation COLIN_ARMOR = new ResourceLocation(
			Reference.MODID + ":textures/entity/modelelemental.png");
	private final RenderColin colinRenderer;
	private final ModelColin colinModel = new ModelColin();
	final ModelKeybladeTest cubes = new ModelKeybladeTest();
	final ModelKingdomKey KingdomKeys = new ModelKingdomKey();
	final ModelKingdomKeyD KingdomKeyDs = new ModelKingdomKeyD();

	public LayerColinKeys(RenderColin colinRendererIn) {
		this.colinRenderer = colinRendererIn;
	}

	public void doRenderLayer(EntityColin entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (entitylivingbaseIn.isVulnerable()) {
			GlStateManager.pushMatrix();
			GL11.glTranslatef(0F, -1.71F, 0F);
			GL11.glScalef(2.4F, 2.4F, 2.4F);
			float[] angles = new float[15];
			for (int i = 0; i < 3; i++) {
				float anglePer = 360F / i;
				float totalAngle = 0F;
				angles[i] = totalAngle += anglePer;
				for (i = 0; i < angles.length; i++)
					angles[i] = totalAngle += anglePer;
				// Shadow Length
				int repeat = 5;
				// Key number
				// this.cubes.renderSpinningCubes(2, repeat, repeat);
				this.KingdomKeys.renderSpinningKey(2, repeat, repeat);
				this.KingdomKeyDs.renderSpinningKey(1, repeat, repeat);

				GlStateManager.enableBlend();
				GlStateManager.matrixMode(5890);
				GlStateManager.loadIdentity();
				GlStateManager.matrixMode(5888);
				GlStateManager.popMatrix();

			}
		}
	}

	@Override
	public boolean shouldCombineTextures() {

		return false;
	}
}