package com.huto.hutosmod.renders;

import javax.annotation.Nonnull;

import com.huto.hutosmod.models.ClientTickHandler;
import com.huto.hutosmod.models.ModelMagatama;
import com.huto.hutosmod.tileentity.TileEntityRuneStation;
import com.huto.hutosmod.tileentity.TileEntityWandMaker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class RenderTileRuneStation extends TileEntitySpecialRenderer<TileEntityRuneStation> {

	final ModelMagatama magatamas = new ModelMagatama();
	@Override
	public void render(@Nonnull TileEntityRuneStation altar, double x, double y, double z, float partticks, int digProgress, float unused) {

		}

}
