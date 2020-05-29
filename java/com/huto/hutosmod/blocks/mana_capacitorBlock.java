package com.huto.hutosmod.blocks;

import java.util.List;

import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.items.ItemUpgrade;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.recipies.ModInventoryHelper;
import com.huto.hutosmod.tileentity.TileEntityManaCapacitor;
import com.huto.hutosmod.tileentity.TileEntityStorageDrum;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class mana_capacitorBlock extends BlockBase {
	public static final AxisAlignedBB MANA_CAPACITOR = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, .8125D, 0.75D, .8125D);
	// Facing(kinda) more to do with facing of bounding boxes
	public static final AxisAlignedBB MANA_CAPACITOR_WE = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, .8125D, 0.75,
			.8125D);

	// Facing
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	{
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public mana_capacitorBlock(String name, Material material) {
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(5.0F);
		setResistance(15.0F);
		setHarvestLevel("pickaxe", 2);
		setLightOpacity(1);
	}

	// Facing
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.getFront(meta);

		if (facing.getAxis() == EnumFacing.Axis.Y) {
			facing = EnumFacing.NORTH;
		}
		return getDefaultState().withProperty(FACING, facing);
	}

	// Facing
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	// Facing
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	// Facing
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	private boolean canBlockStay(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos, EnumFacing.UP);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityManaCapacitor();
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float par7, float par8, float par9) {
		TileEntityManaCapacitor capac = (TileEntityManaCapacitor) world.getTileEntity(pos);
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);
		ItemStack stack = player.getHeldItem(hand);
		Item stackItem = stack.getItem();

		// If NOT sneaking and your hand IS empty
		if (!player.isSneaking() && stack.isEmpty()) {
			String message = String.format("Capacitor contains §9" + capac.getManaValue() + "§r mana ");
			player.sendMessage(new TextComponentString(message));

		}

		// If player IS sneaking and isnt holding an extractor
		if (!player.isSneaking() && stackItem == ItemRegistry.upgrade_wrench) {
			ModInventoryHelper.withdrawFromInventory(capac, player);
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(capac);
		}

		// If there is something in your hand add it to the block if its not an
		// extractor
		if (!stack.isEmpty() && stackItem instanceof ItemUpgrade) {
			capac.addItem(player, stack, hand);
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(capac);
		}

		// If player is sneaking and hand is empty
		if (player.isSneaking() && stack.isEmpty()) {
			if (mana.getMana() > 10 && capac.getManaValue() <= capac.getTankSize() - 10) {
				capac.addManaValue(10);
				mana.consume(10);
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(capac);
			}
		}
		// If player NOT is sneaking and has an extractor
		if (!player.isSneaking() && stackItem == ItemRegistry.mana_extractor) {
			if (capac.getManaValue() > 10 && mana.getMana() <= mana.manaLimit() - 10) {
				mana.fill(10);
				capac.setManaValue(capac.getManaValue() - 10);
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(capac);
			}
		}
		// Upgrade clause
		if (stackItem == ItemRegistry.magatamabead && player.getHeldItemOffhand().getItem() == ItemRegistry.blood_ingot
				&& capac.getTankLevel() < 3 || stackItem == ItemRegistry.enhancedmagatama && capac.getTankLevel() < 4) {
			capac.addTankLevel(1);
			player.getHeldItemMainhand().shrink(1);
			player.getHeldItemOffhand().shrink(1);

		}
		// Says the tank is full
		if (capac.getManaValue() >= capac.getTankSize()) {
			String message = String.format("§CCapacitor is full §r");
			player.sendMessage(new TextComponentString(message));
		}
		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(capac);
		return true;
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockDestroyedByPlayer(worldIn, pos, state);
		// inner ring
		for (int j = 0; j < 100; j++) {
			/*
			 * worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX()+.5,
			 * pos.getY(), pos.getZ()+.5,Math.sin(j)/9, Math.sin(j)/3, Math.cos(j)/9);
			 * worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX()+.5,
			 * pos.getY(), pos.getZ()+.5,Math.cos(j)/9, Math.sin(j)/3, Math.sin(j)/9);
			 * worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX()+.5,
			 * pos.getY(), pos.getZ()+.5,Math.sin(-j)/9, Math.sin(j)/3, Math.cos(-j)/9);
			 * worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX()+.5,
			 * pos.getY(), pos.getZ()+.5,Math.cos(-j)/9, Math.sin(j)/3, Math.sin(-j)/9);
			 */
		}
		// outer ring
		for (int i = 0; i < 10; i++) {
			worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX() + .5, pos.getY(), pos.getZ() + .5,
					Math.sin(i) / 3, Math.sin(i) / 3, Math.cos(i) / 3);
			worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX() + .5, pos.getY(), pos.getZ() + .5,
					Math.cos(i) / 3, Math.sin(i) / 3, Math.sin(i) / 3);
			worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX() + .5, pos.getY(), pos.getZ() + .5,
					Math.sin(-i) / 3, Math.sin(i) / 3, Math.cos(-i) / 3);
			worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX() + .5, pos.getY(), pos.getZ() + .5,
					Math.cos(-i) / 3, Math.sin(i) / 3, Math.sin(-i) / 3);

		}
	}

	// Facing(kinda) more to do with facing of bounding boxes
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (((EnumFacing) state.getValue(FACING))) {
		case SOUTH:
		default:
			return MANA_CAPACITOR;
		case NORTH:
			return MANA_CAPACITOR;
		case EAST:
			return MANA_CAPACITOR_WE;
		case WEST:
			return MANA_CAPACITOR_WE;
		}
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("§3Faster Storage! §r");
		super.addInformation(stack, player, tooltip, advanced);

	}

}
