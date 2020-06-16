package com.huto.hutosmod.blocks;

import javax.annotation.Nonnull;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.recipies.ModInventoryHelper;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.tileentity.TileEntityBellJar;
import com.huto.hutosmod.tileentity.TileEntityEssecenceEnhancer;
import com.huto.hutosmod.tileentity.TileEntityRuneStation;
import com.huto.hutosmod.tileentity.TileEntityWandMaker;
import com.huto.hutosmod.tileentity.TileManaSimpleInventory;

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

public class BlockEssenceEnhancer extends BlockBase implements IActivatable{
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final AxisAlignedBB ESSECENCE_ENHANCER = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	// Facing(kinda) more to do with facing of bounding boxes
	public static final AxisAlignedBB ESSECENCE_ENHANCER_WE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

	public BlockEssenceEnhancer(String name, Material material) {
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
		return new TileEntityEssecenceEnhancer();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float par7, float par8, float par9) {
		if (world.isRemote)
			return true;

		TileEntityEssecenceEnhancer te = (TileEntityEssecenceEnhancer) world.getTileEntity(pos);
		ItemStack stack = player.getHeldItem(hand);
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);

		if (player.isSneaking() && player.getHeldItemMainhand().getItem() != ItemRegistry.maker_activator) {
			if (true) {
				if(mana.getMana()>30) {
				te.addManaValue(30);
				mana.consume(30);
				}
				ModInventoryHelper.withdrawFromInventory(te, player);
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);
				return true;
			}
		}
		if (player.isSneaking()) {
			ModInventoryHelper.withdrawFromInventory(te, player);
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);
			return true;
		} else if (!stack.isEmpty()) {
			boolean result = te.addItem(player, stack, hand);
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);
			return result;
		}
		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(te);
		return false;
	}


	@Override
	public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		TileManaSimpleInventory inv = (TileManaSimpleInventory) world.getTileEntity(pos);
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
			return ESSECENCE_ENHANCER;
		case NORTH:
			return ESSECENCE_ENHANCER;
		case EAST:
			return ESSECENCE_ENHANCER_WE;
		case WEST:
			return ESSECENCE_ENHANCER_WE;
		}
	}

	@Override
	public boolean onUsedByActivator(EntityPlayer player, ItemStack stack, World world, BlockPos pos, EnumFacing side) {
		((TileEntityEssecenceEnhancer) world.getTileEntity(pos)).onWanded(player, stack);
		return true;
	}

}
