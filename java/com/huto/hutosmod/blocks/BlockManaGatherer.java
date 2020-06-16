package com.huto.hutosmod.blocks;

import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.items.ItemUpgrade;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.recipies.ModInventoryHelper;
import com.huto.hutosmod.reference.Reference;
import com.huto.hutosmod.tileentity.TileEntityBellJar;
import com.huto.hutosmod.tileentity.TileEntityManaCapacitor;
import com.huto.hutosmod.tileentity.TileEntityManaGatherer;
import com.huto.hutosmod.tileentity.TileEntityRuneStation;
import com.huto.hutosmod.tileentity.TileEntityStorageDrum;

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
import net.minecraft.item.Item;
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
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockManaGatherer extends BlockBase {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final AxisAlignedBB GATHERER = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, .8125D, 0.75D, .8125D);
	// Facing(kinda) more to do with facing of bounding boxes
	public static final AxisAlignedBB GATHERER_WE = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, .8125D, 0.75, .8125D);

	public BlockManaGatherer(String name, Material material) {
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

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float par7, float par8, float par9) {
		if (!world.isRemote) {
			TileEntityManaGatherer drum = (TileEntityManaGatherer) world.getTileEntity(pos);
			IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);
			System.out.println(mana.getMana());
			ItemStack stack = player.getHeldItem(hand);
			Item stackItem = stack.getItem();

			// If player NOT is sneaking and has an extractor
			if (!player.isSneaking() && stackItem == ItemRegistry.mana_extractor) {
				if (drum.getManaValue() > 30 && mana.getMana() <= mana.manaLimit() - 30) {
					mana.fill(30);
					drum.setManaValue(drum.getManaValue() - 30);
					drum.sendUpdates();
					VanillaPacketDispatcher.dispatchTEToNearbyPlayers(drum);
				}
			}
		}
		return true;
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
