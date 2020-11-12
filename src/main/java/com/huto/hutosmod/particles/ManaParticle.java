package com.huto.hutosmod.particles;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ManaParticle extends Particle {
	private final ResourceLocation smoothBubbleRL = new ResourceLocation(Reference.MODID + ":particle/SmoothBuble1");

	/**
	 * Construct a new FlameParticle at the given [x,y,z] position with the given
	 * initial velocity.
	 */

	public ManaParticle(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ,
			float r, float g, float b){
		super(world, x, y, z, velocityX, velocityY, velocityZ);
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

	public ManaParticle(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ,
			float r, float g, float b, int age, float scale)

	{

		super(world, x, y, z, velocityX, velocityY, velocityZ);
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

		// if you want the brightness to be the local illumination (from block light and
		// sky light) you can just use
		// Entity.getBrightnessForRender() base method, which contains:
		// BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
		// return this.worldObj.isBlockLoaded(blockpos) ?
		// this.worldObj.getCombinedLight(blockpos, 0) : 0;
	}

	// this function is used by ParticleManager.addEffect() to determine whether
	// depthmask writing should be on or not.
	// FlameBreathFX uses alphablending (i.e. the FX is partially transparent) but
	// we want depthmask writing on,
	// otherwise translucent objects (such as water) render over the top of our
	// breath, even if the particle is in front
	// of the water and not behind
	@Override
	public boolean shouldDisableDepth() {
		return false;
	}

	/**
	 * call once per tick to update the Particle position, calculate collisions,
	 * remove when max lifetime is reached, etc
	 */

	private EntityPlayer getNearestTargetableMob(World world, double xpos, double ypos, double zpos) {
		final double TARGETING_DISTANCE = 16;
		AxisAlignedBB targetRange = new AxisAlignedBB(xpos - TARGETING_DISTANCE, ypos, zpos - TARGETING_DISTANCE,
				xpos + TARGETING_DISTANCE, ypos + TARGETING_DISTANCE, zpos + TARGETING_DISTANCE);
		List<EntityPlayer> allNearbyMobs = world.getEntitiesWithinAABB(EntityPlayer.class, targetRange);
		EntityPlayer nearestMob = null;
		double closestDistance = Double.MAX_VALUE;
		for (EntityPlayer nextMob : allNearbyMobs) {
			double nextClosestDistance = nextMob.getDistanceSq(xpos, ypos, zpos);
			if (nextClosestDistance < closestDistance) {
				closestDistance = nextClosestDistance;
				nearestMob = nextMob;
			}
		}
		return nearestMob;
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		move(motionX, motionY, motionZ);
		EntityPlayer player = this.getNearestTargetableMob(world, posX, posY, posZ);
		if (player != null) {
			DecimalFormat df = new DecimalFormat("0");
			if (Double.parseDouble((df.format(posX))) == Double.parseDouble((df.format(player.posX)))
					&& Double.parseDouble((df.format(posY))) == Double.parseDouble((df.format(player.posY + 1)))
					&& Double.parseDouble((df.format(posZ))) == Double.parseDouble((df.format(player.posZ)))) {
				this.setExpired();
			} else if (Double.parseDouble((df.format(posX))) == Double.parseDouble((df.format(player.posX)))
					&& Double.parseDouble((df.format(posY))) == Double.parseDouble((df.format(player.posY + 2)))
					&& Double.parseDouble((df.format(posZ))) == Double.parseDouble((df.format(player.posZ)))) {
				this.setExpired();
			}
		}
		if (onGround) {
			this.setExpired();

		}

		if (prevPosY == posY && motionY > 0) { // detect a collision while moving upwards (can't move up at all)
			this.setExpired();
		}

		if (this.particleMaxAge-- <= 0) {
			this.setExpired();
		}
	}

	/**
	 * Render the Particle onto the screen. For more help with the tessellator see
	 * http://greyminecraftcoder.blogspot.co.at/2014/12/the-tessellator-and-worldrenderer-18.html
	 * <p/>
	 * You don't actually need to override this method, this is just a deobfuscated
	 * example of the vanilla, to show you how it works in case you want to do
	 * something a bit unusual.
	 * <p/>
	 * The Particle is rendered as a two-dimensional object (Quad) in the world
	 * (three-dimensional coordinates). The corners of the quad are chosen so that
	 * the Particle is drawn directly facing the viewer (or in other words, so that
	 * the quad is always directly face-on to the screen.) In order to manage this,
	 * it needs to know two direction vectors: 1) the 3D vector direction
	 * corresponding to left-right on the viewer's screen (edgeLRdirection) 2) the
	 * 3D vector direction corresponding to up-down on the viewer's screen
	 * (edgeUDdirection) These two vectors are calculated by the caller. For
	 * example, the top right corner of the quad on the viewer's screen is equal to:
	 * the centre point of the quad (x,y,z) plus the edgeLRdirection vector
	 * multiplied by half the quad's width plus the edgeUDdirection vector
	 * multiplied by half the quad's height. NB edgeLRdirectionY is not provided
	 * because it's always 0, i.e. the top of the viewer's screen is always directly
	 * up, so moving left-right on the viewer's screen doesn't affect the y
	 * coordinate position in the world
	 *
	 * @param bufferBuilder
	 * @param entity
	 * @param partialTick
	 * @param edgeLRdirectionX edgeLRdirection[XYZ] is the vector direction pointing
	 *                         left-right on the player's screen
	 * @param edgeUDdirectionY edgeUDdirection[XYZ] is the vector direction pointing
	 *                         up-down on the player's screen
	 * @param edgeLRdirectionZ edgeLRdirection[XYZ] is the vector direction pointing
	 *                         left-right on the player's screen
	 * @param edgeUDdirectionX edgeUDdirection[XYZ] is the vector direction pointing
	 *                         up-down on the player's screen
	 * @param edgeUDdirectionZ edgeUDdirection[XYZ] is the vector direction pointing
	 *                         up-down on the player's screen
	 */
	@Override
	public void renderParticle(BufferBuilder bufferBuilder, Entity entity, float partialTick, float edgeLRdirectionX,
			float edgeUDdirectionY, float edgeLRdirectionZ, float edgeUDdirectionX, float edgeUDdirectionZ) {
		double minU = this.particleTexture.getMinU();
		double maxU = this.particleTexture.getMaxU();
		double minV = this.particleTexture.getMinV();
		double maxV = this.particleTexture.getMaxV();
		Random rand = new Random();
		double scale = 0.5 * (this.particleScale) * this.particleMaxAge / 300; // vanilla scaling factor
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
