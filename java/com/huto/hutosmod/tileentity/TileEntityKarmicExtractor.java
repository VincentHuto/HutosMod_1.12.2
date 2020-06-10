package com.huto.hutosmod.tileentity;

import com.huto.hutosmod.particles.ManaParticle;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityKarmicExtractor extends TileEntity implements ITickable {
	
    @SideOnly(Side.CLIENT)
    public boolean shouldRenderFace(EnumFacing face)
    {
        return true;
    }

	@Override
	public void update() {
		ManaParticle part1 = new ManaParticle(world, this.getPos().getX()+.5, this.getPos().getY()+.8, this.getPos().getZ()+.5, 0, 0.2, 0, 0, 0,0, 20, 3);
		Minecraft.getMinecraft().effectRenderer.addEffect(part1);
	}
}
