package com.huto.hutosmod.items.tools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.huto.hutosmod.MainClass;
import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;

public class ToolTreecapitator extends ItemAxe {

	public ToolTreecapitator(String name, ToolMaterial material) {
		super(material, 6.0F, -3.2F);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(MainClass.tabHutosMod);

		ItemRegistry.ITEMS.add(this);
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);

		clearOdAOE(world, stack, player, "logWood", 0, hand);
		clearOdAOE(world, stack, player, "treeLeaves", 0, hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	protected void clearOdAOE(World world, ItemStack stack, EntityPlayer player, String odName, long emcCost,
			EnumHand hand) {

		if (world.isRemote) {
			return;
		}

		List<ItemStack> drops = new ArrayList<>();

		for (BlockPos pos : BlockPos.getAllInBox(new BlockPos(player).add(-8, -8, -8),
				new BlockPos(player).add(8, 8, 8))) {
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();

			if (block.isAir(state, world, pos) || Item.getItemFromBlock(block) == null) {
				continue;
			}

			ItemStack s = new ItemStack(block);
			int[] oreIds = OreDictionary.getOreIDs(s);

			String oreName;
			if (oreIds.length == 0) {
				if (block == Blocks.BROWN_MUSHROOM_BLOCK || block == Blocks.RED_MUSHROOM_BLOCK) {
					oreName = "logWood";
				} else {
					continue;
				}
			} else {
				oreName = OreDictionary.getOreName(oreIds[0]);
			}

			if (odName.equals(oreName)) {
				List<ItemStack> blockDrops = getBlockDrops(world, player, state, stack, pos);
				drops.addAll(blockDrops);
				world.setBlockToAir(pos);
				if (world.rand.nextInt(5) == 0) {
					((WorldServer) world).spawnParticle(EnumParticleTypes.PORTAL, pos.getX(), pos.getY(),
							pos.getZ(), 2, 0, 0, 0, 0, new int[0]);
				}

			}
		}

		createLootDrop(drops, world, player.posX, player.posY, player.posZ);
		if (player.getEntityWorld() instanceof WorldServer) {
			((WorldServer) player.getEntityWorld()).getEntityTracker().sendToTrackingAndSelf(player,
					new SPacketAnimation(player, hand == EnumHand.MAIN_HAND ? 0 : 3));
		}
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

}
