package com.huto.hutosmod.particles;

import org.lwjgl.opengl.GL11;

import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * User: The Grey Ghost Date: 24/12/2014 Custom Particle to illustrate how to
 * add a Particle with your own texture and movement/animation behaviour
 */
public class TestParticle extends Particle {
	public float rotationPointX;
	public float rotationPointY;
	public float rotationPointZ;

	double gravity, friction, r_e, g_e, b_e, opacity_e;
	private final ResourceLocation flameRL = new ResourceLocation(Reference.MODID + ":particle/smoothbuble1");

	/**
   * Construct a new FlameParticle at the given [x,y,z] position with the given initial velocity.
   */
  public TestParticle(World world, double x, double y, double z,
                       double velocityX, double velocityY, double velocityZ,int siz, int lengt,
           			double gravit, double Ra, double Ga, double Ba, double opacita, double frictio)
  {
	  super(world, x, y, z, velocityX, velocityY, velocityZ);
		this.motionX = velocityX;
		this.motionY = velocityY;
		this.motionZ = velocityZ;
		this.friction = frictio;
		this.particleMaxAge = lengt;
		this.particleScale = siz / 10;
		this.gravity = gravit * 0.001;
		this.r_e = Ra;
		this.g_e = Ga;
		this.b_e = Ba;
		this.opacity_e = opacita;
    // overriden onUpdate()

    //the vanilla Particle constructor added random variation to our starting velocity.  Undo it!
    motionX = velocityX;
    motionY = velocityY;
    motionZ = velocityZ;

    // set the texture to the flame texture, which we have previously added using TextureStitchEvent
    //   (see TextureStitcherBreathFX)
    TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(flameRL.toString());
    setParticleTexture(sprite);  // initialise the icon to our custom texture
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
	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		move(motionX, motionY, motionZ); // simple linear motion. You can change speed by changing motionX, motionY,
		// motionZ every tick. For example - you can make the particle accelerate
		// downwards due to gravity by
		// final double GRAVITY_ACCELERATION_PER_TICK = -0.02;
		// motionY += GRAVITY_ACCELERATION_PER_TICK;

		// collision with a block makes the ball disappear. But does not collide with
		// entities
		if (onGround) { // onGround is only true if the particle collides while it is moving
						// downwards...
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

		double scale = 0.1F * this.particleScale; // vanilla scaling factor
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
		bufferBuilder.color((float)r_e,(float) g_e,(float) b_e,(float) opacity_e);
		bufferBuilder
				.pos(x - edgeLRdirectionX * scaleLR - edgeUDdirectionX * scaleUD, y - edgeUDdirectionY * scaleUD,
						z - edgeLRdirectionZ * scaleLR - edgeUDdirectionZ * scaleUD)
				.tex(maxU, maxV).color((float) r_e, (float) g_e, (float) b_e, (float) opacity_e)
				.lightmap(skyLightTimes16, blockLightTimes16).endVertex();
		bufferBuilder
				.pos(x - edgeLRdirectionX * scaleLR + edgeUDdirectionX * scaleUD, y + edgeUDdirectionY * scaleUD,
						z - edgeLRdirectionZ * scaleLR + edgeUDdirectionZ * scaleUD)
				.tex(maxU, minV).color((float) r_e, (float) g_e, (float) b_e, (float) opacity_e)
				.lightmap(skyLightTimes16, blockLightTimes16).endVertex();
		bufferBuilder
				.pos(x + edgeLRdirectionX * scaleLR + edgeUDdirectionX * scaleUD, y + edgeUDdirectionY * scaleUD,
						z + edgeLRdirectionZ * scaleLR + edgeUDdirectionZ * scaleUD)
				.tex(minU, minV).color((float) r_e, (float) g_e, (float) b_e, (float) opacity_e)
				.lightmap(skyLightTimes16, blockLightTimes16).endVertex();
		bufferBuilder
				.pos(x + edgeLRdirectionX * scaleLR - edgeUDdirectionX * scaleUD, y - edgeUDdirectionY * scaleUD,
						z + edgeLRdirectionZ * scaleLR - edgeUDdirectionZ * scaleUD)
				.tex(minU, maxV).color((float) r_e, (float) g_e, (float) b_e, (float) opacity_e)
				.lightmap(skyLightTimes16, blockLightTimes16).endVertex();
		

		

	}

}
