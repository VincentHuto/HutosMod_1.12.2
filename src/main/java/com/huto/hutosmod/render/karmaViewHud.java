package com.huto.hutosmod.render;

import java.text.DecimalFormat;

import org.lwjgl.opengl.GL11;

import com.huto.hutosmod.network.PacketGetKarma;
import com.huto.hutosmod.network.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class karmaViewHud extends Gui {

	
	public static float karma = 0;
	private Minecraft mc;

	public karmaViewHud(Minecraft mc) {
		this.mc = mc;
	}

	/* This helper method will render the bar */
	public void renderStatusBar(int screenWidth, int screenHeight, World world, EntityPlayer player) {
		PacketHandler.INSTANCE.sendToServer(new PacketGetKarma(karma, "com.huto.hutosmod.render.karmaViewHud", "karma"));
		String m = String.valueOf(karma);
		
		FontRenderer fr = mc.fontRenderer;
		DecimalFormat d = new DecimalFormat("#,#");
		final int vanillaExpLeftX = screenWidth / 2 - 91; // leftmost edge of the experience bar
		final int vanillaExpTopY = screenHeight - 9; // top of the experience bar

		GL11.glPushMatrix();
		GL11.glTranslatef(vanillaExpLeftX + 320,vanillaExpTopY- 8, 0);
		
		
		GL11.glPushMatrix();
		// "karma Value"
		GL11.glTranslatef(-53, 30, 0);
		fr.drawString("Karma Value: ", -fr.getStringWidth(m) - 50, -30, 0x000000);
		fr.drawString("Karma Value: ", -fr.getStringWidth(m) - 51, -30, 0xFFFFFF);
		GL11.glPopMatrix();
		
		// Digits
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 21, 0);
		// Shadow String
		fr.drawString(m, -50, -20, 0x000000);
		// Blue String renders after so its on top
		fr.drawString(m, -51, -21, 0xFFFFFF);
		GL11.glPopMatrix();
		
		
		GL11.glPopMatrix();
	}
}
