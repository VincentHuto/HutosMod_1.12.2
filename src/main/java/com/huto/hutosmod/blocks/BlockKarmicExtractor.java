package com.huto.hutosmod.blocks;

import javax.annotation.Nonnull;

import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.karma.IKarma;
import com.huto.hutosmod.karma.KarmaProvider;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.tileentity.TileEntityKarmicExtractor;

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

public class BlockKarmicExtractor extends BlockBase implements IActivatable {
	public static final AxisAlignedBB EXTRACTOR = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0625D, 1.0D);
	// Facing(kinda) more to do with facing of bounding boxes
	public static final AxisAlignedBB EXTRACTOR_WE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0625D, 1.0D);

	// Facing
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	{
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public BlockKarmicExtractor(String name, Material material) {
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(5.0F);
		setResistance(15.0F);
		setHarvestLevel("pickaxe", 2);
		// setLightLevel(1.0F);
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
		return new TileEntityKarmicExtractor();
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
		if (world.isRemote)
			return true;
		ItemStack stack = player.getHeldItem(hand);
		IKarma karma = player.getCapability(KarmaProvider.KARMA_CAPABILITY, null);
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);

		TileEntityKarmicExtractor drum = (TileEntityKarmicExtractor) world.getTileEntity(pos);

		// If player NOT is sneaking and has an extractor
		if (!player.isSneaking() && stack.getItem() == ItemRegistry.mana_extractor) {
			if (drum.getManaValue() > 30 && mana.getMana() <= mana.manaLimit() - 30) {
				mana.fill(30);
				drum.setManaValue(drum.getManaValue() - 30);
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(drum);

			}
		}
		return false;
	}

	// Facing(kinda) more to do with facing of bounding boxes
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (((EnumFacing) state.getValue(FACING))) {
		case SOUTH:
		default:
			return EXTRACTOR;
		case NORTH:
			return EXTRACTOR;
		case EAST:
			return EXTRACTOR_WE;
		case WEST:
			return EXTRACTOR_WE;
		}
	}

	@Override
	public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean onUsedByActivator(EntityPlayer player, ItemStack stack, World world, BlockPos pos, EnumFacing side) {
		((TileEntityKarmicExtractor) world.getTileEntity(pos)).onWanded(player, stack);
		return false;
	}
}
