package com.huto.hutosmod.blocks;

import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.tileentity.TileEntityCelestialActuator;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCelestialActuator extends BlockBase implements IActivatable {
	public static final AxisAlignedBB ACTUATOR = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, .8125D, 0.75, .8125D);
	// Facing(kinda) more to do with facing of bounding boxes
	public static final AxisAlignedBB ACTUATOR_WE = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, .8125D, 0.75, .8125D);
	// Facing
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	{
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public BlockCelestialActuator(String name, Material material) {
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
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
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
		return new TileEntityCelestialActuator();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float par7, float par8, float par9) {
		if (world.isRemote)
			return true;
		TileEntityCelestialActuator te = (TileEntityCelestialActuator) world.getTileEntity(pos);
		ItemStack stack = player.getHeldItem(hand);
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);
		if (player.isSneaking()) {
			if (mana.getMana() > 30 && te.getManaValue() <= te.getMaxMana() - 30) {
				te.addManaValue(30);
				mana.consume(30);
			}
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);
			return true;

		}
		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);

		return false;
	}

	@Override
	public boolean onUsedByActivator(EntityPlayer player, ItemStack stack, World world, BlockPos pos, EnumFacing side) {
		((TileEntityCelestialActuator) world.getTileEntity(pos)).onWanded(player, stack);
		return true;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.canBlockStay(worldIn, pos)) {
			worldIn.setBlockToAir(pos);
			InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(),
					new ItemStack(BlockRegistry.celestial_actuator));
		}
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

	// Facing(kinda) more to do with facing of bounding boxes
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (((EnumFacing) state.getValue(FACING))) {
		case SOUTH:
		default:
			return ACTUATOR;
		case NORTH:
			return ACTUATOR;
		case EAST:
			return ACTUATOR_WE;
		case WEST:
			return ACTUATOR_WE;
		}
	}
}
