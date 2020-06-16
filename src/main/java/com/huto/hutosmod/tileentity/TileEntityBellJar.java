package com.huto.hutosmod.tileentity;

import com.huto.hutosmod.blocks.BlockManaBelljar;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityBellJar extends TileEntity {
	
    @SideOnly(Side.CLIENT)
    public boolean shouldRenderFace(EnumFacing face)
    {
        return true;
    }
}
