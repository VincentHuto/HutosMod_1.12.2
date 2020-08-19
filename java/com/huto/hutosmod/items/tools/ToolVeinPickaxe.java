package com.huto.hutosmod.items.tools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Comparators;
import com.google.common.collect.Lists;
import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;

public class ToolVeinPickaxe extends ItemPickaxe {

	public ToolVeinPickaxe(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);
		ItemRegistry.ITEMS.add(this);
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (world.isRemote) {
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}

		RayTraceResult mop = this.rayTrace(world, player, false);
		if (mop != null && mop.typeOfHit == RayTraceResult.Type.BLOCK) {
			if (isOre(world.getBlockState(mop.getBlockPos()))) {
				tryVeinMine(stack, player, mop);
			}
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);

	}

	public static boolean isOre(IBlockState state) {
		if (state.getBlock() == Blocks.LIT_REDSTONE_ORE) {
			return true;
		}
		if (Item.getItemFromBlock(state.getBlock()) == Items.AIR) {
			return false;
		}
		String oreDictName = getOreDictionaryName(stateToStack(state, 1));
		return oreDictName.startsWith("ore") || oreDictName.startsWith("denseore");
	}

	public static Iterable<BlockPos> getPositionsFromBox(AxisAlignedBB box) {
		return BlockPos.getAllInBox(new BlockPos(box.minX, box.minY, box.minZ),
				new BlockPos(box.maxX, box.maxY, box.maxZ));
	}

	public static String getOreDictionaryName(ItemStack stack) {
		if (stack.isEmpty()) {
			return "Unknown";
		}
		int[] oreIds = OreDictionary.getOreIDs(stack);

		if (oreIds.length == 0) {
			return "Unknown";
		}

		return OreDictionary.getOreName(oreIds[0]);
	}

	public static ItemStack stateToStack(IBlockState state, int stackSize) {
		return new ItemStack(state.getBlock(), stackSize, state.getBlock().getMetaFromState(state));
	}

	public static List<ItemStack> getBlockDrops(World world, EntityPlayer player, IBlockState state, ItemStack stack,
			BlockPos pos) {
		if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0
				&& state.getBlock().canSilkHarvest(world, pos, state, player)) {
			return Lists.newArrayList(new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)));
		}

		return state.getBlock().getDrops(world, pos, state,
				EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));
	}

	protected void tryVeinMine(ItemStack stack, EntityPlayer player, RayTraceResult mop) {
		if (player.getEntityWorld().isRemote) {
			return;
		}

		AxisAlignedBB aabb = getBroadDeepBox(mop.getBlockPos(), mop.sideHit, 5);
		IBlockState target = player.getEntityWorld().getBlockState(mop.getBlockPos());
		if (target.getBlockHardness(player.getEntityWorld(), mop.getBlockPos()) <= -1
				|| !(canHarvestBlock(target, stack)
						|| ForgeHooks.canToolHarvestBlock(player.getEntityWorld(), mop.getBlockPos(), stack))) {
			return;
		}

		List<ItemStack> drops = new ArrayList<>();

		for (BlockPos pos : BlockPos.getAllInBox(new BlockPos(aabb.minX, aabb.minY, aabb.minZ),
				new BlockPos(aabb.maxX, aabb.maxY, aabb.maxZ))) {
			IBlockState state = player.getEntityWorld().getBlockState(pos);
			if (isSameOre(target, state)) {
				int damage = harvestVein(player.getEntityWorld(), player, stack, pos, state, drops, 0);
				harvestVein(player.getEntityWorld(), player, stack, pos, state, drops, 0);
				player.getHeldItemMainhand().damageItem(damage, player);
			}
		}

		createLootDrop(drops, player.getEntityWorld(), mop.getBlockPos());
	}

	public static final Comparator<ItemStack> ITEMSTACK_ASCENDING = (o1, o2) -> {
		if ((o1.isEmpty() && o2.isEmpty())) {
			return 0;
		}
		if (o1.isEmpty()) {
			return 1;
		}
		if (o2.isEmpty()) {
			return -1;
		}
		if (areItemStacksEqualIgnoreNBT(o1, o2)) {
			// Same item id, same meta
			return o1.getCount() - o2.getCount();
		} else // Different id or different meta
		{
			// Different id
			if (o1.getItem() != o2.getItem()) {
				return Item.getIdFromItem(o1.getItem()) - Item.getIdFromItem(o2.getItem());
			} else {
				// Different meta
				return o1.getItemDamage() - o2.getItemDamage();
			}

		}
	};

	public static boolean areItemStacksEqualIgnoreNBT(ItemStack stack1, ItemStack stack2) {
		if (stack1.getItem() != stack2.getItem()) {
			return false;
		}

		if (stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE
				|| stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
			return true;
		}

		return stack1.getItemDamage() == stack2.getItemDamage();
	}

	public static void compactItemListNoStacksize(List<ItemStack> list) {
		for (int i = 0; i < list.size(); i++) {
			ItemStack s = list.get(i);
			if (!s.isEmpty()) {
				for (int j = i + 1; j < list.size(); j++) {
					ItemStack s1 = list.get(j);
					if (ItemHandlerHelper.canItemStacksStack(s, s1)) {
						s.grow(s1.getCount());
						list.set(j, ItemStack.EMPTY);
					}
				}
			}
		}

		list.removeIf(ItemStack::isEmpty);
		list.sort(ITEMSTACK_ASCENDING);
	}

	public static void createLootDrop(List<ItemStack> drops, World world, BlockPos pos) {
		createLootDrop(drops, world, pos.getX(), pos.getY(), pos.getZ());
	}

	public static void createLootDrop(List<ItemStack> drops, World world, double x, double y, double z) {
		compactItemListNoStacksize(drops);

		for (ItemStack drop : drops) {
			EntityItem ent = new EntityItem(world, x, y, z);
			ent.setItem(drop);
			ent.setEntityInvulnerable(true);
			world.spawnEntity(ent);
		}
	}

	private static boolean isSameOre(IBlockState target, IBlockState world) {
		if (target.getBlock() instanceof BlockRedstoneOre) {
			return world.getBlock() instanceof BlockRedstoneOre;
		}
		return target == world;
	}

	public int harvestVein(World world, EntityPlayer player, ItemStack stack, BlockPos pos, IBlockState target,
			List<ItemStack> currentDrops, int numMined) {
		if (numMined >= 250) {
			return numMined;
		}

		AxisAlignedBB b = new AxisAlignedBB(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1, pos.getX() + 1,
				pos.getY() + 1, pos.getZ() + 1);

		for (BlockPos currentPos : getPositionsFromBox(b)) {
			IBlockState currentState = world.getBlockState(currentPos);
			Block block = currentState.getBlock();
			if (currentState == target || (target == Blocks.LIT_REDSTONE_ORE && block == Blocks.REDSTONE_ORE)) {
				numMined++;
				currentDrops.addAll(getBlockDrops(world, player, currentState, stack, currentPos));
				world.setBlockToAir(currentPos);
				numMined = harvestVein(world, player, stack, currentPos, target, currentDrops, numMined);
				if (numMined >= 250) {
					break;
				}
			}
		}

		return numMined;
	}

	public static AxisAlignedBB getBroadDeepBox(BlockPos pos, EnumFacing direction, int offset) {
		switch (direction) {
		case EAST:
			return new AxisAlignedBB(pos.getX() - offset, pos.getY() - offset, pos.getZ() - offset, pos.getX(),
					pos.getY() + offset, pos.getZ() + offset);
		case WEST:
			return new AxisAlignedBB(pos.getX(), pos.getY() - offset, pos.getZ() - offset, pos.getX() + offset,
					pos.getY() + offset, pos.getZ() + offset);
		case UP:
			return new AxisAlignedBB(pos.getX() - offset, pos.getY() - offset, pos.getZ() - offset, pos.getX() + offset,
					pos.getY(), pos.getZ() + offset);
		case DOWN:
			return new AxisAlignedBB(pos.getX() - offset, pos.getY(), pos.getZ() - offset, pos.getX() + offset,
					pos.getY() + offset, pos.getZ() + offset);
		case SOUTH:
			return new AxisAlignedBB(pos.getX() - offset, pos.getY() - offset, pos.getZ() - offset, pos.getX() + offset,
					pos.getY() + offset, pos.getZ());
		case NORTH:
			return new AxisAlignedBB(pos.getX() - offset, pos.getY() - offset, pos.getZ(), pos.getX() + offset,
					pos.getY() + offset, pos.getZ() + offset);
		default:
			return new AxisAlignedBB(0, 0, 0, 0, 0, 0);
		}
	}

}
