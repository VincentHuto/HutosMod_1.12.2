package com.huto.hutosmod.blocks;

import javax.annotation.Nonnull;

import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.recipies.ModInventoryHelper;
import com.huto.hutosmod.tileentity.TileEntityManaFuser;
import com.huto.hutosmod.tileentity.TileEntityManaResonator;
import com.huto.hutosmod.tileentity.TileEntityWandMaker;
import com.huto.hutosmod.tileentity.TileManaSimpleInventory;

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

public class BlockManaResonator extends BlockBase implements IActivatable {
	public static final AxisAlignedBB MANA_FUSER = new AxisAlignedBB(0, 0, 0, 1, 0.9375, 1);
	// Facing(kinda) more to do with facing of bounding boxes
	public static final AxisAlignedBB MANA_FUSER_WE = new AxisAlignedBB(0, 0, 0, 1, 0.9375, 1);
	// Facing
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	{
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public BlockManaResonator(String name, Material material) {
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
		return new TileEntityManaResonator();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float par7, float par8, float par9) {
		if (world.isRemote)
			return true;

		TileEntityManaResonator te = (TileEntityManaResonator) world.getTileEntity(pos);
		ItemStack stack = player.getHeldItem(hand);
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);
		if (player.isSneaking()) {
			if (mana.getMana() > 30) {
				te.addManaValue(30);
				mana.consume(30);
			}
			ModInventoryHelper.withdrawFromInventory(te, player);
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);
			return true;

		} else if (player.getHeldItemMainhand().getItem() == ItemRegistry.maker_activator
				|| player.getHeldItemMainhand().getItem() == ItemRegistry.mana_debugtool ) {
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);

		}

		if (!player.isSneaking() && !stack.isEmpty()
				&& player.getHeldItemMainhand().getItem() != ItemRegistry.mana_debugtool || player.getHeldItemMainhand().getItem() != ItemRegistry.mana_extractor) {
			boolean result = te.addItem(player, stack, hand);
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);
			return result;
		}

		// If player NOT is sneaking and has an extractor
		if (!player.isSneaking() && stack.getItem() == ItemRegistry.mana_extractor) {
			if (te.getManaValue() > 30 && mana.getMana() <= mana.manaLimit() - 30) {
				mana.fill(30);
				te.setManaValue(te.getManaValue() - 30);
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);

			}
		}

		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);

		return false;
	}

	@Override
	public boolean onUsedByActivator(EntityPlayer player, ItemStack stack, World world, BlockPos pos, EnumFacing side) {
		((TileEntityManaResonator) world.getTileEntity(pos)).onWanded(player, stack);
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
					new ItemStack(BlockRegistry.mana_fuser));
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
			return MANA_FUSER;
		case NORTH:
			return MANA_FUSER;
		case EAST:
			return MANA_FUSER_WE;
		case WEST:
			return MANA_FUSER_WE;
		}
	}

	@Override
	public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		TileManaSimpleInventory inv = (TileManaSimpleInventory) world.getTileEntity(pos);
		ModInventoryHelper.dropInventory(inv, world, state, pos);
		super.breakBlock(world, pos, state);
	}
}
