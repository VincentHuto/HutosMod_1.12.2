package com.huto.hutosmod.blocks;

import javax.annotation.Nonnull;

import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.recipies.ModInventoryHelper;
import com.huto.hutosmod.tileentites.TileEntityBellJar;
import com.huto.hutosmod.tileentites.TileEntityStorageDrum;
import com.huto.hutosmod.tileentites.TileEntityWandMaker;
import com.huto.hutosmod.tileentites.TileSimpleInventory;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
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

public class mana_storagedrumBlock extends BlockBase {
	public static final AxisAlignedBB STORAGE_DRUM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.5, 1.0D);
	// Facing(kinda) more to do with facing of bounding boxes
	public static final AxisAlignedBB STORAGE_DRUM_WE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.5, 1.0D);
	
	// Facing
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	{
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public mana_storagedrumBlock(String name, Material material) {
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
		return new TileEntityStorageDrum();
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
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockDestroyedByPlayer(worldIn, pos, state);
		//inner ring
			for(int j=0; j<100; j++) {
			/*	worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX()+.5, pos.getY(), pos.getZ()+.5,Math.sin(j)/9, Math.sin(j)/3, Math.cos(j)/9);
				worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX()+.5, pos.getY(), pos.getZ()+.5,Math.cos(j)/9, Math.sin(j)/3, Math.sin(j)/9);
				worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX()+.5, pos.getY(), pos.getZ()+.5,Math.sin(-j)/9, Math.sin(j)/3, Math.cos(-j)/9);
				worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX()+.5, pos.getY(), pos.getZ()+.5,Math.cos(-j)/9, Math.sin(j)/3, Math.sin(-j)/9);*/
			}
			//outer ring
			for(int i=0; i<30; i++) {
		worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX()+.5, pos.getY(), pos.getZ()+.5,Math.sin(i)/3, Math.sin(i)/3, Math.cos(i)/3);
		worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX()+.5, pos.getY(), pos.getZ()+.5,Math.cos(i)/3, Math.sin(i)/3, Math.sin(i)/3);
		worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX()+.5, pos.getY(), pos.getZ()+.5,Math.sin(-i)/3, Math.sin(i)/3, Math.cos(-i)/3);
		worldIn.spawnParticle(EnumParticleTypes.DRAGON_BREATH, pos.getX()+.5, pos.getY(), pos.getZ()+.5,Math.cos(-i)/3, Math.sin(i)/3, Math.sin(-i)/3);
				
		}
	}
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float par7, float par8, float par9) {
		TileEntityStorageDrum drum = (TileEntityStorageDrum) world.getTileEntity(pos);
		IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);
		if (!player.isSneaking() && player.getHeldItemMainhand().isEmpty()) {
			String message = String.format("Drum contains §9%d§r mana ", (int) drum.getManaValue());
			player.sendMessage(new TextComponentString(message));

		}
		System.out.println(drum.getManaValue());

		if (player.isSneaking() && player.getHeldItemMainhand().isEmpty()) {
			if (mana.getMana() > 30 && drum.getManaValue() <= drum.getTankSize() - 30) {
				drum.addManaValue(30);
				mana.consume(30);
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(drum);
			}
		}

		if (!player.isSneaking() && player.getHeldItemMainhand().getItem() == ItemRegistry.mana_extractor) {
			if (drum.getManaValue() > 30 && mana.getMana() <= mana.manaLimit() - 30) {
				mana.fill(30);

				drum.setManaValue(drum.getManaValue() - 30);
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(drum);
			}
		}

		if (player.getHeldItemMainhand().getItem() == ItemRegistry.magatamabead
				&& player.getHeldItemOffhand().getItem() == ItemRegistry.blood_ingot && drum.getTankLevel() < 9) {
			drum.addTankLevel(1);
			player.getHeldItemMainhand().shrink(1);
			player.getHeldItemOffhand().shrink(1);

		}
		if (drum.getManaValue() >= drum.getTankSize()) {
			String message = String.format("§4Drum is full §r");
			player.sendMessage(new TextComponentString(message));
		}

		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(drum);

		return true;
	}

	// Facing(kinda) more to do with facing of bounding boxes
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (((EnumFacing) state.getValue(FACING))) {
		case SOUTH:
		default:
			return STORAGE_DRUM;
		case NORTH:
			return STORAGE_DRUM;
		case EAST:
			return STORAGE_DRUM_WE;
		case WEST:
			return STORAGE_DRUM_WE;
		}
	}

	
}
