package com.huto.hutosmod.reference;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.biomes.BiomeRegistry;
import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.blocks.FluidInit;
import com.huto.hutosmod.commands.CommandClearKarma;
import com.huto.hutosmod.commands.CommandClearMana;
import com.huto.hutosmod.commands.CommandDimensionTeleport;
import com.huto.hutosmod.commands.CommandSetKarma;
import com.huto.hutosmod.commands.CommandSetMana;
import com.huto.hutosmod.dimension.DimensionRegistry;
import com.huto.hutosmod.entities.RegisterEntities;
import com.huto.hutosmod.events.ModEventHandler;
import com.huto.hutosmod.gui.GuiHandler;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.karma.IKarma;
import com.huto.hutosmod.karma.Karma;
import com.huto.hutosmod.karma.KarmaEventHandler;
import com.huto.hutosmod.karma.KarmaStorage;
import com.huto.hutosmod.keybinds.KeyInputEvents;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.Mana;
import com.huto.hutosmod.mana.ManaEventHandler;
import com.huto.hutosmod.mana.ManaStorage;
import com.huto.hutosmod.mindrunes.RuneItem;
import com.huto.hutosmod.mindrunes.RuneType;
import com.huto.hutosmod.mindrunes.cap.IRune;
import com.huto.hutosmod.mindrunes.cap.RuneCapabilities;
import com.huto.hutosmod.mindrunes.cap.RuneCapabilities.CapabilityRunes;
import com.huto.hutosmod.mindrunes.container.RunesContainer;
import com.huto.hutosmod.mindrunes.events.EventHandlerEntity;
import com.huto.hutosmod.mindrunes.events.EventHandlerItem;
import com.huto.hutosmod.mindrunes.events.IRunesItemHandler;
import com.huto.hutosmod.mindrunes.network.RunesPacketHandler;
import com.huto.hutosmod.network.PacketHandler;
import com.huto.hutosmod.potions.PotionEventHandler;
import com.huto.hutosmod.potions.PotionInit;
import com.huto.hutosmod.recipies.ModEnhancerRecipies;
import com.huto.hutosmod.recipies.ModFurnaceRecipies;
import com.huto.hutosmod.recipies.ModFuserRecipes;
import com.huto.hutosmod.recipies.ModWandRecipies;
import com.huto.hutosmod.render.RenderHandler;
import com.huto.hutosmod.render.tile.RenderTileBellJar;
import com.huto.hutosmod.render.tile.RenderTileCelestialActuator;
import com.huto.hutosmod.render.tile.RenderTileDisplayPedestal;
import com.huto.hutosmod.render.tile.RenderTileEssecenceEnhancer;
import com.huto.hutosmod.render.tile.RenderTileKarmicAltar;
import com.huto.hutosmod.render.tile.RenderTileKarmicExtractor;
import com.huto.hutosmod.render.tile.RenderTileManaCapacitor;
import com.huto.hutosmod.render.tile.RenderTileManaFuser;
import com.huto.hutosmod.render.tile.RenderTileManaGatherer;
import com.huto.hutosmod.render.tile.RenderTileStorageDrum;
import com.huto.hutosmod.render.tile.RenderTileWandMaker;
import com.huto.hutosmod.sound.SoundsHandler;
import com.huto.hutosmod.tileentity.TileEntityBellJar;
import com.huto.hutosmod.tileentity.TileEntityCelestialActuator;
import com.huto.hutosmod.tileentity.TileEntityDisplayPedestal;
import com.huto.hutosmod.tileentity.TileEntityEssecenceEnhancer;
import com.huto.hutosmod.tileentity.TileEntityHandler;
import com.huto.hutosmod.tileentity.TileEntityKarmicAltar;
import com.huto.hutosmod.tileentity.TileEntityKarmicExtractor;
import com.huto.hutosmod.tileentity.TileEntityManaCapacitor;
import com.huto.hutosmod.tileentity.TileEntityManaFuser;
import com.huto.hutosmod.tileentity.TileEntityManaGatherer;
import com.huto.hutosmod.tileentity.TileEntityStorageDrum;
import com.huto.hutosmod.tileentity.TileEntityWandMaker;
import com.huto.hutosmod.worldgen.ModWorldGen;
import com.huto.hutosmod.worldgen.WorldGenCustomMushroom;
import com.huto.hutosmod.worldgen.WorldGenCustomTrees;
import com.huto.hutosmod.worldgen.WorldGenHugeMorelMushroom;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class RegistryHandler {

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ItemRegistry.ITEMS.toArray(new Item[0]));
	}

	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(BlockRegistry.BLOCKS.toArray(new Block[0]));
		TileEntityHandler.registerTileEntities();

	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onModelRegister(ModelRegistryEvent event) {
		RenderHandler.registerEntityRenders();
		RenderHandler.registerCustomMeshesAndStates();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWandMaker.class, new RenderTileWandMaker());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBellJar.class, new RenderTileBellJar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStorageDrum.class, new RenderTileStorageDrum());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEssecenceEnhancer.class,
				new RenderTileEssecenceEnhancer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityManaGatherer.class, new RenderTileManaGatherer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityManaCapacitor.class, new RenderTileManaCapacitor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKarmicAltar.class, new RenderTileKarmicAltar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityManaFuser.class, new RenderTileManaFuser());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKarmicExtractor.class, new RenderTileKarmicExtractor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCelestialActuator.class,
				new RenderTileCelestialActuator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplayPedestal.class, new RenderTileDisplayPedestal());
		for (Item item : ItemRegistry.ITEMS) {
			MainClass.proxy.registerItemRenderer(item, 0, "inventory");
		}
		for (Block block : BlockRegistry.BLOCKS) {
			MainClass.proxy.registerItemRenderer(Item.getItemFromBlock(block), 0, "inventory");
		}
	}

	public static void preInitRegistries(FMLPreInitializationEvent event) {
		FluidInit.registerFluids();
		BiomeRegistry.registerBiomes();
		GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);
		GameRegistry.registerWorldGenerator(new WorldGenCustomTrees(), 0);
		GameRegistry.registerWorldGenerator(new WorldGenCustomMushroom(), 10);
		RegisterEntities.registerEntites();
		PotionInit.registerPotions();
		RunesPacketHandler.init();
		DimensionRegistry.registerDimension();
		PacketHandler.registerMessages(Reference.MODID);
	}

	public static void initRegistries() {
		NetworkRegistry.INSTANCE.registerGuiHandler(MainClass.instance, new GuiHandler());
		MinecraftForge.EVENT_BUS.register(new KeyInputEvents());
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		MinecraftForge.EVENT_BUS.register(new EventHandlerEntity());
		MinecraftForge.EVENT_BUS.register(new EventHandlerItem());
		MinecraftForge.EVENT_BUS.register(new ModEventHandler());
		MinecraftForge.EVENT_BUS.register(new ManaEventHandler());
		MinecraftForge.EVENT_BUS.register(new KarmaEventHandler());
		MinecraftForge.EVENT_BUS.register(new PotionEventHandler());
		MainClass.proxy.registerRenderThings();
		CapabilityManager.INSTANCE.register(IMana.class, new ManaStorage(), Mana.class);
		CapabilityManager.INSTANCE.register(IKarma.class, new KarmaStorage(), Karma.class);
		CapabilityManager.INSTANCE.register(IRunesItemHandler.class, new CapabilityRunes<IRunesItemHandler>(),
				RunesContainer.class);
		CapabilityManager.INSTANCE.register(IRune.class, new RuneCapabilities.CapabilityItemRuneStorage(),
				() -> new RuneItem(RuneType.OVERRIDE));
		ModFurnaceRecipies.init();
		ModWandRecipies.init();
		ModFuserRecipes.init();
		ModEnhancerRecipies.init();
		SoundsHandler.registerSounds();

	}

	public static void postInitRegistries() {

	}

	public static void serverRegistries(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandDimensionTeleport());
		event.registerServerCommand(new CommandClearMana());
		event.registerServerCommand(new CommandSetMana());
		event.registerServerCommand(new CommandClearKarma());
		event.registerServerCommand(new CommandSetKarma());

	}
}
