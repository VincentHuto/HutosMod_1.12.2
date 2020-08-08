package com.huto.hutosmod.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityDisapperingBlock extends TileEntity implements ITickable {

	static int count = 0;

	@Override
	public void update() {

		if (count % 40 == 0) {
			world.setBlockToAir(this.pos);
			count =0;
		}
		count++;

	}

}
