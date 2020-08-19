package com.huto.hutosmod.proxy;

import com.google.common.util.concurrent.ListenableFuture;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface IProxy {

	 void registerEventHandlers();

	long getWorldElapsedTicks();

	public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule);
	public void registerItemRenderer(Item item, int meta, String id);

	public void registerRenderThings();
	public EntityPlayer getClientPlayer();

	public void postInit();
	public void preInit();

	public World getClientWorld();
	public void init();
	void lightningFX(Vector3 vectorStart, Vector3 vectorEnd, float ticksPerMeter, long seed, int colorOuter, int colorInner);

	default void lightningFX(Vector3 vectorStart, Vector3 vectorEnd, float ticksPerMeter, int colorOuter, int colorInner) {
		lightningFX(vectorStart, vectorEnd, ticksPerMeter, System.nanoTime(), colorOuter, colorInner);
	}

	public RayTraceResult rayTrace(double blockReachDistance, float partialTicks, EntityPlayer player, World worldIn);
	public Vec3d getPositionEyes(float partialTicks, EntityPlayer player);

	public void openTomeBook();
	public void openElderBook();

	public ModelBiped getArmorModel(int i);

	public void spawnEffect(Particle partilce);
	
}
