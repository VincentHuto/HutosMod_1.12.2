package com.huto.hutosmod;

import com.huto.hutosmod.creativetabs.HutosTab;
import com.huto.hutosmod.proxy.CommonProxy;
import com.huto.hutosmod.proxy.IProxy;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.reference.RegistryHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class MainClass {
	public static IProxy iproxy;

	@Instance
	public static MainClass instance;
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	public static SimpleNetworkWrapper network;

	// Creative Tabs
	public static final CreativeTabs tabHutosMod = new HutosTab("tabHutosMod");

	static {
		FluidRegistry.enableUniversalBucket();
	}

	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		RegistryHandler.preInitRegistries(event);
		proxy.preInit();
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {
		RegistryHandler.initRegistries();
		proxy.init();
	}

	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {
		RegistryHandler.postInitRegistries();
		proxy.postInit();
	}

	@EventHandler
	public static void PostInit(FMLServerStartingEvent event) {
		RegistryHandler.serverRegistries(event);

	}
}