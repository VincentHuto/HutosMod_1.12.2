/*package com.huto.hutosmod.dimension.alagadda;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.IRenderHandler;

public class AlagadaSkyRendererOld extends IRenderHandler {
	private boolean vboEnabled;
	private int starGLCallList;
	private VertexBuffer starVBO;
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		// [VanillaCopy] Excerpt from RenderGlobal.loadRenderers as we don't get a callback
		boolean flag = this.vboEnabled;
		this.vboEnabled = OpenGlHelper.useVbo();
		if (flag != this.vboEnabled) {
			generateStars();
		}

		RenderGlobal rg = mc.renderGlobal;
		int pass = GameRenderer.anaglyphField ? GameRenderer.anaglyphField : 2;

		GlStateManager.disableTexture2D();
		Vec3d vec3d = world.getSkyColor(mc.getRenderViewEntity(), partialTicks);
		float f = (float) vec3d.x;
		float f1 = (float) vec3d.y;
		float f2 = (float) vec3d.z;

		if (pass != 2) {
			float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
			float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
			float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
			f = f3;
			f1 = f4;
			f2 = f5;
		}

		GlStateManager.color(f, f1, f2);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.depthMask(false);
		GlStateManager.enableFog();
		GlStateManager.color(f, f1, f2);

		if (this.vboEnabled) {
			rg.skyVBO.bindBuffer();
			GlStateManager.glEnableClientState(32884);
			GlStateManager.glVertexPointer(3, 5126, 12, 0);
			rg.skyVBO.drawArrays(7);
			rg.skyVBO.unbindBuffer();
			GlStateManager.glDisableClientState(32884);
		} else {
			GlStateManager.callList(rg.glSkyList);
		}

		GlStateManager.disableFog();
		GlStateManager.disableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		RenderHelper.disableStandardItemLighting();
		 TF - snip out sunrise/sunset since that doesn't happen here
         * float[] afloat = ...
         * if (afloat != null) ...
         

		GlStateManager.enableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.pushMatrix();
		float f16 = 1.0F - world.getRainStrength(partialTicks);
		GlStateManager.color(1.0F, 1.0F, 1.0F, f16);
		GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
         TF - snip out sun/moon
         * float f17 = 30.0F;
         * ...
          tessellator.draw();
         
		GlStateManager.disableTexture2D();
		float f15 = 1.0F; // TF - stars are always bright

		if (f15 > 0.0F) {
			GlStateManager.color(f15, f15, f15, f15);

			if (this.vboEnabled) {
				this.starVBO.bindBuffer();
				GlStateManager.glEnableClientState(32884);
				GlStateManager.glVertexPointer(3, 5126, 12, 0);
				this.starVBO.drawArrays(7);
				this.starVBO.unbindBuffer();
				GlStateManager.glDisableClientState(32884);
			} else {
				GlStateManager.callList(this.starGLCallList);
			}
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableFog();
		GlStateManager.popMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.color(0.0F, 0.0F, 0.0F);
		double d0 = mc.player.getPositionEyes(partialTicks).y - world.getHorizon();

		if (d0 < 0.0D) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, 12.0F, 0.0F);

			if (this.vboEnabled) {
				rg.sky2VBO.bindBuffer();
				GlStateManager.glEnableClientState(32884);
				GlStateManager.glVertexPointer(3, 5126, 12, 0);
				rg.sky2VBO.drawArrays(7);
				rg.sky2VBO.unbindBuffer();
				GlStateManager.glDisableClientState(32884);
			} else {
				GlStateManager.callList(rg.glSkyList2);
			}

			GlStateManager.popMatrix();
			float f18 = 1.0F;
			float f19 = -((float) (d0 + 65.0D));
			float f20 = -1.0F;
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
			bufferbuilder.pos(-1.0D, (double) f19, 1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(1.0D, (double) f19, 1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(1.0D, (double) f19, -1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(-1.0D, (double) f19, -1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(1.0D, (double) f19, 1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(1.0D, (double) f19, -1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(-1.0D, (double) f19, -1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(-1.0D, (double) f19, 1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
			bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
			tessellator.draw();
		}

		if (world.provider.isSkyColored()) {
			GlStateManager.color(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
		} else {
			GlStateManager.color(f, f1, f2);
		}

		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0F, -((float) (d0 - 16.0D)), 0.0F);
		GlStateManager.callList(rg.glSkyList2);
		GlStateManager.popMatrix();
		GlStateManager.enableTexture2D();
		GlStateManager.depthMask(true);
	}

		
		generateStars();

	}
	// [VanillaCopy] RenderGlobal.generateStars
		private void generateStars() {
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();

			if (this.starVBO != null) {
				this.starVBO.deleteGlBuffers();
			}

			if (this.starGLCallList >= 0) {
				GLAllocation.deleteDisplayLists(this.starGLCallList);
				this.starGLCallList = -1;
			}

			if (this.vboEnabled) {
				// TF - inlined RenderGlobal field that's only used once here
				this.starVBO = new VertexBuffer(DefaultVertexFormats.POSITION);
				this.renderStars(bufferbuilder);
				bufferbuilder.finishDrawing();
				bufferbuilder.reset();
				this.starVBO.bufferData(bufferbuilder.getByteBuffer());

			} else {
				this.starGLCallList = GLAllocation.generateDisplayLists(1);
				GlStateManager.pushMatrix();
				GlStateManager.glNewList(this.starGLCallList, 4864);
				this.renderStars(bufferbuilder);
				tessellator.draw();
				GlStateManager.glEndList();
				GlStateManager.popMatrix();
			}
		}
	
	
	 public void renderSky(float partialTicks, int pass)
	    {
	        net.minecraftforge.client.IRenderHandler renderer = this.world.provider.getSkyRenderer();
	        if (renderer != null)
	        {
	            renderer.render(partialTicks, world, mc);
	            return;
	        }

	        else if (this.mc.world.provider.isSurfaceWorld())
	        {
	            GlStateManager.disableTexture2D();
	            Vec3d vec3d = this.world.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
	            float f = (float)vec3d.x;
	            float f1 = (float)vec3d.y;
	            float f2 = (float)vec3d.z;

	            if (pass != 2)
	            {
	                float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
	                float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
	                float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
	                f = f3;
	                f1 = f4;
	                f2 = f5;
	            }

	            GlStateManager.color(f, f1, f2);
	            Tessellator tessellator = Tessellator.getInstance();
	            BufferBuilder bufferbuilder = tessellator.getBuffer();
	            GlStateManager.depthMask(false);
	            GlStateManager.enableFog();
	            GlStateManager.color(f, f1, f2);

	            if (this.vboEnabled)
	            {
	                this.skyVBO.bindBuffer();
	                GlStateManager.glEnableClientState(32884);
	                GlStateManager.glVertexPointer(3, 5126, 12, 0);
	                this.skyVBO.drawArrays(7);
	                this.skyVBO.unbindBuffer();
	                GlStateManager.glDisableClientState(32884);
	            }
	            else
	            {
	                GlStateManager.callList(this.glSkyList);
	            }

	            GlStateManager.disableFog();
	            GlStateManager.disableAlpha();
	            GlStateManager.enableBlend();
	            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	            RenderHelper.disableStandardItemLighting();
	            float[] afloat = this.world.provider.calcSunriseSunsetColors(this.world.getCelestialAngle(partialTicks), partialTicks);

	            if (afloat != null)
	            {
	                GlStateManager.disableTexture2D();
	                GlStateManager.shadeModel(7425);
	                GlStateManager.pushMatrix();
	                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
	                GlStateManager.rotate(MathHelper.sin(this.world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
	                GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
	                float f6 = afloat[0];
	                float f7 = afloat[1];
	                float f8 = afloat[2];

	                if (pass != 2)
	                {
	                    float f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
	                    float f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
	                    float f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
	                    f6 = f9;
	                    f7 = f10;
	                    f8 = f11;
	                }

	                bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
	                bufferbuilder.pos(0.0D, 100.0D, 0.0D).color(f6, f7, f8, afloat[3]).endVertex();
	                int l1 = 16;

	                for (int j2 = 0; j2 <= 16; ++j2)
	                {
	                    float f21 = (float)j2 * ((float)Math.PI * 2F) / 16.0F;
	                    float f12 = MathHelper.sin(f21);
	                    float f13 = MathHelper.cos(f21);
	                    bufferbuilder.pos((double)(f12 * 120.0F), (double)(f13 * 120.0F), (double)(-f13 * 40.0F * afloat[3])).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
	                }

	                tessellator.draw();
	                GlStateManager.popMatrix();
	                GlStateManager.shadeModel(7424);
	            }

	            GlStateManager.enableTexture2D();
	            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	            GlStateManager.pushMatrix();
	            float f16 = 1.0F - this.world.getRainStrength(partialTicks);
	            GlStateManager.color(1.0F, 1.0F, 1.0F, f16);
	            GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
	            GlStateManager.rotate(this.world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
	            float f17 = 30.0F;
	            this.renderEngine.bindTexture(SUN_TEXTURES);
	            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
	            bufferbuilder.pos((double)(-f17), 100.0D, (double)(-f17)).tex(0.0D, 0.0D).endVertex();
	            bufferbuilder.pos((double)f17, 100.0D, (double)(-f17)).tex(1.0D, 0.0D).endVertex();
	            bufferbuilder.pos((double)f17, 100.0D, (double)f17).tex(1.0D, 1.0D).endVertex();
	            bufferbuilder.pos((double)(-f17), 100.0D, (double)f17).tex(0.0D, 1.0D).endVertex();
	            tessellator.draw();
	            f17 = 20.0F;
	            this.renderEngine.bindTexture(MOON_PHASES_TEXTURES);
	            int k1 = this.world.getMoonPhase();
	            int i2 = k1 % 4;
	            int k2 = k1 / 4 % 2;
	            float f22 = (float)(i2 + 0) / 4.0F;
	            float f23 = (float)(k2 + 0) / 2.0F;
	            float f24 = (float)(i2 + 1) / 4.0F;
	            float f14 = (float)(k2 + 1) / 2.0F;
	            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
	            bufferbuilder.pos((double)(-f17), -100.0D, (double)f17).tex((double)f24, (double)f14).endVertex();
	            bufferbuilder.pos((double)f17, -100.0D, (double)f17).tex((double)f22, (double)f14).endVertex();
	            bufferbuilder.pos((double)f17, -100.0D, (double)(-f17)).tex((double)f22, (double)f23).endVertex();
	            bufferbuilder.pos((double)(-f17), -100.0D, (double)(-f17)).tex((double)f24, (double)f23).endVertex();
	            tessellator.draw();
	            GlStateManager.disableTexture2D();
	            float f15 = this.world.getStarBrightness(partialTicks) * f16;

	            if (f15 > 0.0F)
	            {
	                GlStateManager.color(f15, f15, f15, f15);

	                if (this.vboEnabled)
	                {
	                    this.starVBO.bindBuffer();
	                    GlStateManager.glEnableClientState(32884);
	                    GlStateManager.glVertexPointer(3, 5126, 12, 0);
	                    this.starVBO.drawArrays(7);
	                    this.starVBO.unbindBuffer();
	                    GlStateManager.glDisableClientState(32884);
	                }
	                else
	                {
	                    GlStateManager.callList(this.starGLCallList);
	                }
	            }

	            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	            GlStateManager.disableBlend();
	            GlStateManager.enableAlpha();
	            GlStateManager.enableFog();
	            GlStateManager.popMatrix();
	            GlStateManager.disableTexture2D();
	            GlStateManager.color(0.0F, 0.0F, 0.0F);
	            double d3 = this.mc.player.getPositionEyes(partialTicks).y - this.world.getHorizon();

	            if (d3 < 0.0D)
	            {
	                GlStateManager.pushMatrix();
	                GlStateManager.translate(0.0F, 12.0F, 0.0F);

	                if (this.vboEnabled)
	                {
	                    this.sky2VBO.bindBuffer();
	                    GlStateManager.glEnableClientState(32884);
	                    GlStateManager.glVertexPointer(3, 5126, 12, 0);
	                    this.sky2VBO.drawArrays(7);
	                    this.sky2VBO.unbindBuffer();
	                    GlStateManager.glDisableClientState(32884);
	                }
	                else
	                {
	                    GlStateManager.callList(this.glSkyList2);
	                }

	                GlStateManager.popMatrix();
	                float f18 = 1.0F;
	                float f19 = -((float)(d3 + 65.0D));
	                float f20 = -1.0F;
	                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
	                bufferbuilder.pos(-1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(-1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(-1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(-1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
	                bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
	                tessellator.draw();
	            }

	            if (this.world.provider.isSkyColored())
	            {
	                GlStateManager.color(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
	            }
	            else
	            {
	                GlStateManager.color(f, f1, f2);
	            }

	            GlStateManager.pushMatrix();
	            GlStateManager.translate(0.0F, -((float)(d3 - 16.0D)), 0.0F);
	            GlStateManager.callList(this.glSkyList2);
	            GlStateManager.popMatrix();
	            GlStateManager.enableTexture2D();
	            GlStateManager.depthMask(true);
	        }
	    }
	
	
	// [VanillaCopy] of RenderGlobal.renderStars but with double the number of them
		@SuppressWarnings("unused")
		private void renderStars(BufferBuilder bufferBuilder) {
			Random random = new Random(10842L);
			bufferBuilder.begin(7, DefaultVertexFormats.POSITION);

			// TF - 1500 -> 3000
			for (int i = 0; i < 3000; ++i) {
				double d0 = (double) (random.nextFloat() * 2.0F - 1.0F);
				double d1 = (double) (random.nextFloat() * 2.0F - 1.0F);
				double d2 = (double) (random.nextFloat() * 2.0F - 1.0F);
				double d3 = (double) (0.15F + random.nextFloat() * 0.1F);
				double d4 = d0 * d0 + d1 * d1 + d2 * d2;

				if (d4 < 1.0D && d4 > 0.01D) {
					d4 = 1.0D / Math.sqrt(d4);
					d0 = d0 * d4;
					d1 = d1 * d4;
					d2 = d2 * d4;
					double d5 = d0 * 100.0D;
					double d6 = d1 * 100.0D;
					double d7 = d2 * 100.0D;
					double d8 = Math.atan2(d0, d2);
					double d9 = Math.sin(d8);
					double d10 = Math.cos(d8);
					double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
					double d12 = Math.sin(d11);
					double d13 = Math.cos(d11);
					double d14 = random.nextDouble() * Math.PI * 2.0D;
					double d15 = Math.sin(d14);
					double d16 = Math.cos(d14);

					for (int j = 0; j < 4; ++j) {
						double d17 = 0.0D;
						double d18 = (double) ((j & 2) - 1) * d3;
						double d19 = (double) ((j + 1 & 2) - 1) * d3;
						double d20 = 0.0D;
						double d21 = d18 * d16 - d19 * d15;
						double d22 = d19 * d16 + d18 * d15;
						double d23 = d21 * d12 + 0.0D * d13;
						double d24 = 0.0D * d12 - d21 * d13;
						double d25 = d24 * d9 - d22 * d10;
						double d26 = d22 * d9 + d24 * d10;
						bufferBuilder.pos(d5 + d25, d6 + d23, d7 + d26).endVertex();
					}
				}
			}
		}

}
*/