package com.huto.hutosmod.proxy;

import com.google.common.util.concurrent.ListenableFuture;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CommonProxy {

	public void registerEventHandlers() {
	}

	long getWorldElapsedTicks() {
		return 0;
	}

	public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
		throw new IllegalStateException("This should only be called from client side");
	}

	public void registerItemRenderer(Item item, int meta, String id) {

	}

	public void registerRenderThings() {
	}

	public EntityPlayer getClientPlayer() {
		throw new IllegalStateException("This should only be called from client side");
	}

	public void postInit() {

	}

	public void preInit() {

	}

	public World getClientWorld() {
		return null;
	}

	public void init() {

	}

	public void lightningFX(Vector3 vectorStart, Vector3 vectorEnd, float ticksPerMeter, long seed, int colorOuter,
			int colorInner) {

	}

	public RayTraceResult rayTrace(double blockReachDistance, float partialTicks, EntityPlayer player, World worldIn) {
		return null;
	}

	public Vec3d getPositionEyes(float partialTicks, EntityPlayer player) {
		return null;
	}

	public void openTomeBook() {
	}

	public void openElderBook() {

	}

	public ModelBiped getArmorModel(int i) {
		// TODO Auto-generated method stub
		return null;
	}

}
