package com.huto.hutosmod.blocks;

import java.util.Random;

import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.particles.EntityBaseFX;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEnchantedOre extends BlockBase {

	public BlockEnchantedOre(String name, Material material) {
		super(name, material);
		setSoundType(SoundType.STONE);
		setHarvestLevel("pickaxe", 2);
		setLightLevel(0.8F);
		setLightOpacity(1);
	}
	
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ItemRegistry.mana_powder;
    }
	
    
    /**
     * Get the quantity dropped based on the given fortune level
     */
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return this.quantityDropped(random) + random.nextInt(fortune + 1);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random)
    {
        return 2 + random.nextInt(2);
    }

}
