package com.huto.hutosmod.blocks;

import javax.annotation.Nonnull;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.recipies.ModInventoryHelper;
import com.huto.hutosmod.tileentity.TileEntityDisplayPedestal;
import com.huto.hutosmod.tileentity.TileEntitySimpleInventory;
import com.huto.hutosmod.tileentity.TileManaSimpleInventory;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDisplayPedestal extends BlockBase {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final AxisAlignedBB DISPLAY_PEDESTAL = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0625D, 1.0D);
	// Facing(kinda) more to do with facing of bounding boxes
	public static final AxisAlignedBB DISPLAY_PEDESTAL_WE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0625D, 1.0D);

	public BlockDisplayPedestal(String name, Material material) {
		super(name, material);
		setCreativeTab(MainClass.tabHutosMod);
		setHardness(8.0f);
		setHarvestLevel("pickaxe", -1);
		setResistance(18.0f);
		setSoundType(SoundType.STONE);
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH));
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityDisplayPedestal();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float par7, float par8, float par9) {
		if (world.isRemote)
			return true;

		TileEntityDisplayPedestal te = (TileEntityDisplayPedestal) world.getTileEntity(pos);
		ItemStack stack = player.getHeldItem(hand);
		if (!player.isSneaking()) {

			if (!stack.isEmpty()) {
				boolean result = te.addItem(player, stack, hand);
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);
				return result;
			}
		}else if(player.isSneaking() && stack.isEmpty()) {
			ModInventoryHelper.withdrawFromInventory(te, player);
		}
		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);

		return false;
	}

	@Override
	public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		TileEntitySimpleInventory inv = (TileEntitySimpleInventory) world.getTileEntity(pos);
		ModInventoryHelper.dropInventory(inv, world, state, pos);
		super.breakBlock(world, pos, state);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();
		state = state.withProperty(FACING, EnumFacing.getFront(meta));
		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		IBlockState state = worldIn.getBlockState(pos.offset(facing.getOpposite()));
		if (state.getBlock() == this) {
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
			if (enumfacing == facing) {
				return this.getDefaultState().withProperty(FACING, facing);
			}
		}
		return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	// Facing(kinda) more to do with facing of bounding boxes
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (((EnumFacing) state.getValue(FACING))) {
		case SOUTH:
		default:
			return DISPLAY_PEDESTAL;
		case NORTH:
			return DISPLAY_PEDESTAL;
		case EAST:
			return DISPLAY_PEDESTAL_WE;
		case WEST:
			return DISPLAY_PEDESTAL_WE;
		}
	}

}
