package com.huto.hutosmod.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import com.huto.hutosmod.mindrunes.container.ContainerPlayerExpanded;
import com.huto.hutosmod.mindrunes.events.EventHandlerEntity;
import com.huto.hutosmod.mindrunes.events.EventHandlerItem;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy {

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


	
}
