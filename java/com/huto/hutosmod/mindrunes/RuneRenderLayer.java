
package com.huto.hutosmod.mindrunes;

import javax.annotation.Nonnull;

import org.lwjgl.opengl.GL11;

import com.huto.hutosmod.mindrunes.IRenderBauble.RenderType;
import com.huto.hutosmod.mindrunes.events.IRunesItemHandler;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class RuneRenderLayer implements LayerRenderer<EntityPlayer> {

	@Override
	public void doRenderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		
		if( player.getActivePotionEffect(MobEffects.INVISIBILITY) != null)
			return;

		IRunesItemHandler inv = RuneApi.getRuneHandler(player);

		dispatchRenders(inv, player, RenderType.BODY, partialTicks);

		float yaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * partialTicks;
		float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
		float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;

		GlStateManager.pushMatrix();
		GlStateManager.rotate(yawOffset, 0, -1, 0);
		GlStateManager.rotate(yaw - 270, 0, 1, 0);
		GlStateManager.rotate(pitch, 0, 0, 1);
		dispatchRenders(inv, player, RenderType.HEAD, partialTicks);
		GlStateManager.popMatrix();
	}

	private void dispatchRenders(IRunesItemHandler inv, EntityPlayer player, RenderType type, float partialTicks) {
		for(int i = 0; i < inv.getSlots(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if(stack != null && !stack.isEmpty()) {
				Item item = stack.getItem();
				if(item instanceof IRenderBauble) {
					GlStateManager.pushMatrix();
					GL11.glColor3ub((byte) 255, (byte) 255, (byte) 255); 
					GlStateManager.color(1F, 1F, 1F, 1F);
					((IRenderBauble) stack.getItem()).onPlayerBaubleRender(stack, player, type, partialTicks);
					GlStateManager.popMatrix();
				}
			}
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}