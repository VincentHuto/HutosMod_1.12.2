package com.huto.hutosmod.blocks;

import java.util.List;

import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.items.ItemUpgrade;
import com.huto.hutosmod.mana.IMana;
import com.huto.hutosmod.mana.ManaProvider;
import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketHandler;
import com.huto.hutosmod.network.VanillaPacketDispatcher;
import com.huto.hutosmod.recipies.ModInventoryHelper;
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

public class BlockManaStorageDrum extends BlockBase {
	public static final AxisAlignedBB STORAGE_DRUM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.5, 1.0D);
	// Facing(kinda) more to do with facing of bounding boxes
	public static final AxisAlignedBB STORAGE_DRUM_WE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.5, 1.0D);

	// Facing
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	{
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public BlockManaStorageDrum(String name, Material material) {
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
		for (int i = 0; i < 30; i++) {
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

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float par7, float par8, float par9) {
		if (!world.isRemote) {

			TileEntityStorageDrum drum = (TileEntityStorageDrum) world.getTileEntity(pos);
			IMana mana = player.getCapability(ManaProvider.MANA_CAP, null);
			ItemStack stack = player.getHeldItem(hand);
			Item stackItem = stack.getItem();

			// If NOT sneaking and your hand IS empty
			if (!player.isSneaking() && stack.isEmpty()) {
				String message = String.format("Drum contains §9%d§r mana ", (int) drum.getManaValue());
				player.sendMessage(new TextComponentString(message));

			}

			// If player IS sneaking and isnt holding an extractor
			if (!player.isSneaking() && stackItem == ItemRegistry.upgrade_wrench) {
				ModInventoryHelper.withdrawFromInventory(drum, player);
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(drum);
			}

			// If there is something in your hand add it to the block if its not an
			// extractor
			if (!stack.isEmpty() && stackItem instanceof ItemUpgrade) {
				drum.addItem(player, stack, hand);
				VanillaPacketDispatcher.dispatchTEToNearbyPlayers(drum);
			}

			// If player is sneaking and hand is empty
			if (player.isSneaking() && stack.isEmpty()) {
				if (mana.getMana() > 30 && drum.getManaValue() <= drum.getTankSize() - 30) {
					drum.addManaValue(30);
					mana.consume(30);
					VanillaPacketDispatcher.dispatchTEToNearbyPlayers(drum);
				}
			}
			// If player NOT is sneaking and has an extractor
			if (!player.isSneaking() && stackItem == ItemRegistry.mana_extractor) {
				if (drum.getManaValue() > 30 && mana.getMana() <= mana.manaLimit() - 30) {
					mana.fill(30);
					drum.setManaValue(drum.getManaValue() - 30);
					VanillaPacketDispatcher.dispatchTEToNearbyPlayers(drum);
				}
			}
			// Upgrade clause
			if (stackItem == ItemRegistry.magatamabead
					&& player.getHeldItemOffhand().getItem() == ItemRegistry.blood_ingot && drum.getTankLevel() < 9
					|| stackItem == ItemRegistry.enhancedmagatama && drum.getTankLevel() < 9) {
				drum.addTankLevel(1);
				player.getHeldItemMainhand().shrink(1);
				player.getHeldItemOffhand().shrink(1);

			}
			// Says the tank is full
			if (drum.getManaValue() >= drum.getTankSize()) {
				String message = String.format("§4Drum is full §r");
				player.sendMessage(new TextComponentString(message));
			}
			VanillaPacketDispatcher.dispatchTEToNearbyPlayers(drum);
		}
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

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("§3Magical Storage! §r");
		super.addInformation(stack, player, tooltip, advanced);

	}
}
