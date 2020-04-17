package com.huto.hutosmod.potions;

import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class RunicHealthEffectPotion extends Potion {

	public RunicHealthEffectPotion(String name,boolean isBadEffectIn, int color, int iconIndexX,int iconIndexY) {
		super(isBadEffectIn, color);
		setPotionName("effect" + name);
		setRegistryName(new ResourceLocation(Reference.MODID + ":" + name));
		setIconIndex(iconIndexX, iconIndexY);
		
	}

	@Override
	public boolean hasStatusIcon() {
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID + ":textures/gui/potion_effects.png"));;
		return true;
	}
	
}
