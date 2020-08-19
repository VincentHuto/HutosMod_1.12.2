package com.huto.hutosmod.particles;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import com.huto.hutosmod.damage.ParticleEntityDamageSource;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class SphereParticle extends Particle {
	private final ResourceLocation smoothBubbleRL = new ResourceLocation(Reference.MODID + ":particle/SmoothBuble1");

	/**
	 * Construct a new FlameParticle at the given [x,y,z] position with the given
	 * initial velocity.
	 */

	public SphereParticle(World world, double x, double y, double z, double velocityX, double velocityY,
			double velocityZ, float r, float g, float b) {
		super(world, x, y, z, velocityX, velocityY, velocityZ);
		this.canCollide = false;
		this.particleRed = r;
		this.particleGreen = g;
		this.particleBlue = b;
		particleMaxAge = 0; // not used since we have overridden onUpdate
		this.particleScale = 1;
		final float ALPHA_VALUE = 0.79F;
		this.particleAlpha = ALPHA_VALUE;
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks()
				.getAtlasSprite(smoothBubbleRL.toString());
		setParticleTexture(sprite); // initialise the icon to our custom texture
	}

	public SphereParticle(World world, double x, double y, double z, double velocityX, double velocityY,
			double velocityZ, float r, float g, float b, int age, float scale) {

		super(world, x, y, z, velocityX, velocityY, velocityZ);
		this.canCollide = false;

		this.particleRed = r;
		this.particleGreen = g;
		this.particleBlue = b;
		particleMaxAge = age; // not used since we have overridden onUpdate
		this.particleScale = scale;
		final float ALPHA_VALUE = 0.79F;
		this.particleAlpha = ALPHA_VALUE;
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks()
				.getAtlasSprite(smoothBubbleRL.toString());
		setParticleTexture(sprite); // initialise the icon to our custom texture
	}

	/**
	 * Used to control what texture and lighting is used for the EntityFX. Returns
	 * 1, which means "use a texture from the blocks + items texture sheet" The
	 * vanilla layers are: normal particles: ignores world brightness lighting map
	 * Layer 0 - uses the particles texture sheet (textures\particle\particles.png)
	 * Layer 1 - uses the blocks + items texture sheet lit particles: changes
	 * brightness depending on world lighting i.e. block light + sky light Layer 3 -
	 * uses the blocks + items texture sheet (I think)
	 *
	 * @return
	 */
	@Override
	public int getFXLayer() {
		return 1;
	}

	// can be used to change the brightness of the rendered Particle.
	@Override
	public int getBrightnessForRender(float partialTick) {
		final int FULL_BRIGHTNESS_VALUE = 0xf000f0;
		return FULL_BRIGHTNESS_VALUE;
	}

	@Override
	public boolean shouldDisableDepth() {
		return false;
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		move(motionX, motionY, motionZ);

		if (onGround) {
			this.setExpired();

		}
		if (prevPosY == posY && motionY > 0) {
			this.setExpired();

		}

		if (this.particleMaxAge-- <= 0) {
			this.setExpired();
		}
	}

	@Override
	public void renderParticle(BufferBuilder bufferBuilder, Entity entity, float partialTick, float edgeLRdirectionX,
			float edgeUDdirectionY, float edgeLRdirectionZ, float edgeUDdirectionX, float edgeUDdirectionZ) {
		double minU = this.particleTexture.getMinU();
		double maxU = this.particleTexture.getMaxU();
		double minV = this.particleTexture.getMinV();
		double maxV = this.particleTexture.getMaxV();
		Random rand = new Random();
		double scale = 0.5 * (this.particleScale); // vanilla scaling factor
		final double scaleLR = scale;
		final double scaleUD = scale;
		double x = this.prevPosX + (this.posX - this.prevPosX) * partialTick - interpPosX;
		double y = this.prevPosY + (this.posY - this.prevPosY) * partialTick - interpPosY;
		double z = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTick - interpPosZ;

		// "lightmap" changes the brightness of the particle depending on the local
		// illumination (block light, sky light)
		// in this example, it's held constant, but we still need to add it to each
		// vertex anyway.
		int combinedBrightness = this.getBrightnessForRender(partialTick);
		int skyLightTimes16 = combinedBrightness >> 16 & 65535;
		int blockLightTimes16 = combinedBrightness & 65535;

		// the caller has already initiated rendering, using:
//    worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

		bufferBuilder
				.pos(x - edgeLRdirectionX * scaleLR - edgeUDdirectionX * scaleUD, y - edgeUDdirectionY * scaleUD,
						z - edgeLRdirectionZ * scaleLR - edgeUDdirectionZ * scaleUD)
				.tex(maxU, maxV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
				.lightmap(skyLightTimes16, blockLightTimes16).endVertex();
		bufferBuilder
				.pos(x - edgeLRdirectionX * scaleLR + edgeUDdirectionX * scaleUD, y + edgeUDdirectionY * scaleUD,
						z - edgeLRdirectionZ * scaleLR + edgeUDdirectionZ * scaleUD)
				.tex(maxU, minV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
				.lightmap(skyLightTimes16, blockLightTimes16).endVertex();
		bufferBuilder
				.pos(x + edgeLRdirectionX * scaleLR + edgeUDdirectionX * scaleUD, y + edgeUDdirectionY * scaleUD,
						z + edgeLRdirectionZ * scaleLR + edgeUDdirectionZ * scaleUD)
				.tex(minU, minV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
				.lightmap(skyLightTimes16, blockLightTimes16).endVertex();
		bufferBuilder
				.pos(x + edgeLRdirectionX * scaleLR - edgeUDdirectionX * scaleUD, y - edgeUDdirectionY * scaleUD,
						z + edgeLRdirectionZ * scaleLR - edgeUDdirectionZ * scaleUD)
				.tex(minU, maxV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
				.lightmap(skyLightTimes16, blockLightTimes16).endVertex();

	}

}
