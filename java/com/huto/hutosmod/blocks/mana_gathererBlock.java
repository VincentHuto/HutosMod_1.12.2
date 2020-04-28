package com.huto.hutosmod.blocks;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.tileentity.TileEntityBellJar;
import com.huto.hutosmod.tileentity.TileEntityManaGatherer;
import com.huto.hutosmod.tileentity.TileEntityRuneStation;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class mana_gathererBlock extends BlockBase {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final AxisAlignedBB GATHERER = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, .8125D, 0.75D, .8125D);
	// Facing(kinda) more to do with facing of bounding boxes
	public static final AxisAlignedBB GATHERER_WE = new AxisAlignedBB(0.1875D, 0.0D,0.1875D, .8125D, 0.75, .8125D);
	public mana_gathererBlock(String name, Material material) {
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
		return new TileEntityManaGatherer();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return true;
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
			return GATHERER;
		case NORTH:
			return GATHERER;
		case EAST:
			return GATHERER_WE;
		case WEST:
			return GATHERER_WE;
		}
	}

}