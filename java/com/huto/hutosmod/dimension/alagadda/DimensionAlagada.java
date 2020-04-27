package com.huto.hutosmod.dimension.alagadda;

import com.huto.hutosmod.dimension.DimensionRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DimensionAlagada extends WorldProviderSurface {

	private static final String SKYLIGHT_KEY = "HasSkylight";

	private static volatile boolean skylightEnabled = true;

	@Override
	protected void init() {
		this.biomeProvider = new BiomeProviderAlagada(this.world.getSeed());
	}

	@Override
	public DimensionType getDimensionType() {
		return DimensionRegistry.ALAGADA_DIM;
	}

	public static void setSkylightEnabled(boolean enabled) {
		skylightEnabled = enabled;
	}

	public static boolean isSkylightEnabled(NBTTagCompound data) {
		return data.hasKey(SKYLIGHT_KEY, Constants.NBT.TAG_BYTE) ? data.getBoolean(SKYLIGHT_KEY) : skylightEnabled;
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new AlagadaGen(this.world, this.world.getSeed());
	}

	@Override
	public boolean isSurfaceWorld() {
		return true;
	}

	@Override
	public boolean canRespawnHere() {
		return true;
	}

	// Toggles fog
	@Override
	public boolean doesXZShowFog(int x, int z) {
		return false;
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		// TODO Auto-generated method stub
//		return 01.0f;
		return 1f;

	}

	@Override
	public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
		return new Vec3d(55/255d, 55/255d, 55/255d);
	}

	@Override
	public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
		return new Vec3d(110 / 255d, 0d / 255d, 255d / 255d);
	}

	@Override
	public float getCloudHeight() {
		return 255.0f;
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
		
		final float r = 50f / 255f, g = 50f / 255f, b = 50f / 255f;
		if (!hasSkyLight) {
			colors[0] = r + blockLight * (102.0f - r);
			colors[1] = g + blockLight * (1.0f - g);
			colors[2] = b + blockLight * (111.4f - b);

		}
	}
	
/*3
 * */

}
