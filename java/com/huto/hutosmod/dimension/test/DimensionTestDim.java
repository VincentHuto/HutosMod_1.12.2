package com.huto.hutosmod.dimension.test;

import com.huto.hutosmod.dimension.DimensionRegistry;
import com.huto.hutosmod.dimension.dreamscape.SkyRenderDreamScape;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
public class DimensionTestDim extends WorldProviderSurface {


	private static volatile boolean skylightEnabled = true;
	private static SkyRenderDreamScape rendererSky;

	@Override
	protected void init() {
		this.biomeProvider = new BiomeProviderTest(this.world.getSeed());
	}

	@Override
	public boolean canDoLightning(Chunk chunk) {
		return true;
	}
	
	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		return false;
	}

	@Override
	public DimensionType getDimensionType() {
		return DimensionRegistry.TEST_DIM;
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new TestDimGen(this.world, this.world.getSeed());
	}

	@Override
	public boolean isSurfaceWorld() {
		return false;
	}

	@Override
	public boolean canRespawnHere() {
		return false;
	}

	// Toggles fog
	@Override
	public boolean doesXZShowFog(int x, int z) {
		return false;
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {

		return 1f;

	}

	@Override
	public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
		return new Vec3d(0 / 255d, 55 / 255d, 0 / 255d);
	}

	@Override
	public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
		return new Vec3d(110 / 255d, 0d / 255d, 255d / 255d);
	}

	@Override
	public float getCloudHeight() {
		return 120.0f;
	}

	@Override
	public boolean shouldMapSpin(String entity, double x, double z, double rotation) {
		return true;
	}

	@Override
	public double getMovementFactor() {
		return 32.0d;
	}

	@Override
	protected void generateLightBrightnessTable() {
		float f = this.hasSkyLight ? 0.0F : 0.1F;
		for (int i = 0; i <= 15; ++i) {
			float f1 = 1.0F - (float) i / 15.0F;
			this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
		}
	}

	@Override
	public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight,
			float[] colors) {
//		EntityPlayer player = this.world.getPlayerEntityByName();

		final float r = 20 / 255f, g =0 / 255f, b = 0 / 255f;
		if (!hasSkyLight) {
			colors[0] = r + blockLight * (1.0f - r);
			colors[1] = g + blockLight * (1.0f - g);
			colors[2] = b + blockLight * (1.4f - b);

		}
	}

}
