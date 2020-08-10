package com.huto.hutosmod.blocks;

import javax.annotation.Nonnull;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.items.ItemUpgrade;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.recipies.ModInventoryHelper;
import com.huto.hutosmod.tileentity.TileEntityManaAbsorber;
import com.huto.hutosmod.tileentity.TileManaSimpleInventory;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockManaAbsorber extends BlockBase {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final AxisAlignedBB RUNE_STATION = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	// Facing(kinda) more to do with facing of bounding boxes
	public static final AxisAlignedBB RUNE_STATION_WE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

	public BlockManaAbsorber(String name, Material material) {
		super(name, material);
		setCreativeTab(MainClass.tabHutosMod);
		setHardness(8.0f);
		setHarvestLevel("pickaxe", -1);
		setResistance(18.0f);
		setSoundType(SoundType.STONE);

		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {

		EnumFacing enumfacing = EnumFacing.getFront(meta);
		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		EnumFacing facing = state.getValue(FACING);
		int meta = ((EnumFacing) state.getValue(FACING)).getIndex();
		return meta;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(FACING,
				placer.getHorizontalFacing());

	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityManaAbsorber();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {

			TileEntityManaAbsorber te = (TileEntityManaAbsorber) worldIn.getTileEntity(pos);
			ItemStack stack = playerIn.getHeldItem(hand);
			IMana mana = playerIn.getCapability(ManaProvider.MANA_CAP, null);
			Item stackItem = stack.getItem();

			if (playerIn.isSneaking()) {
				ModInventoryHelper.withdrawFromInventory(te, playerIn);
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);
				return true;
			}

			// If there is something in your hand add it to the block if its not an
			// extractor
			if (!stack.isEmpty() && stackItem != ItemRegistry.enhancedmagatama) {
				te.addItem(playerIn, stack, hand);
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);
				return true;

			}

			// Upgrade clause
			if (stackItem == ItemRegistry.enhancedmagatama && te.getRank() < 4) {
				te.addRankUp(1);
				playerIn.getHeldItemMainhand().shrink(1);
				playerIn.getHeldItemOffhand().shrink(1);
			}

			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);

			return false;
		}
		return false;

	}

	@Override
	public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		TileManaSimpleInventory inv = (TileManaSimpleInventory) world.getTileEntity(pos);
		ModInventoryHelper.dropInventory(inv, world, state, pos);
		super.breakBlock(world, pos, state);
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
			return RUNE_STATION;
		case NORTH:
			return RUNE_STATION;
		case EAST:
			return RUNE_STATION_WE;
		case WEST:
			return RUNE_STATION_WE;
		}
	}

}
