package com.huto.hutosmod.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Helper {
	@SuppressWarnings("unused")
	@SideOnly(Side.CLIENT)
	public static void spawnEnitiyFX(Particle particleFX) {
			Minecraft mc = Minecraft.getMinecraft();
			if (mc != null && mc.getRenderViewEntity() != null && mc.effectRenderer != null) {
				int i = mc.gameSettings.particleSetting;
				double d6 = mc.getRenderViewEntity().posX - particleFX.interpPosX, d7 = mc.getRenderViewEntity().posY - particleFX.interpPosY,
						d8 = mc.getRenderViewEntity().posZ - particleFX.interpPosZ,
						d9 = Math.sqrt((float) mc.gameSettings.renderDistanceChunks) * 45;
				if (i > 1)
					;
				else {
					if (d6 + d7 + d8 > d9) {
						if ((5) == 0)
							Minecraft.getMinecraft().effectRenderer.addEffect(particleFX);
					} else
						Minecraft.getMinecraft().effectRenderer.addEffect(particleFX);
				}
			}
		}
	}
