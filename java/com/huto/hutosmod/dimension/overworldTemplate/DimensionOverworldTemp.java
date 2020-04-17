package com.huto.hutosmod.dimension.overworldTemplate;

import com.huto.hutosmod.dimension.DimensionRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class DimensionOverworldTemp extends WorldProvider{

	@Override
	public DimensionType getDimensionType() {
		return DimensionRegistry.OVERWORLD_TEMPLATE;
	}
	
	
	@Override
	protected void init() {

		this.biomeProvider = new BiomeProviderOverworld(this.world.getSeed());
	}
	@Override
	public IChunkGenerator  createChunkGenerator(){
		return new OverworldGenTemplate(this.world,this.world.getSeed());
	}
	
	@Override
	public boolean isSurfaceWorld() {
		return true;
	}
	
	@Override
	public boolean canRespawnHere() {
		return true;
	}
	
	@Override
	public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
		//This is an RGB Color value just get the RGB values and divide them by 255
		return new Vec3d(190D/255D, 204/255D, 78D/255D);
		
	}
		@Override
		protected void generateLightBrightnessTable() {
			float f = 0.5f;
			for(int i =0; i<= 15;i++) {
				float f1=1.0F *(float) i/15.0F;
				this.lightBrightnessTable[i]=(1.0F-f1)/(f1*3.0F+1.0F)*(1.0F-f)+f;
			}
			
		}
		
		@Override
		public float getCloudHeight() {
			// TODO Auto-generated method stub
			return 100F;
		}
	@Override
	public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
		// TODO Auto-generated method stub
		return new Vec3d(90D/255D, 0/255D, 90D/255D);
	}
}
