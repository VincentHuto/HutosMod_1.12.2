package com.huto.hutosmod.renders;

import java.text.DecimalFormat;

import org.lwjgl.opengl.GL11;

import com.huto.hutosmod.network.PacketGetMana;
import com.huto.hutosmod.network.PacketHandler;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class manaViewerHud extends Gui {

	/* These two variables describe the size of the bar */
	private final static int BAR_HEIGHT = 9;
	private final static int BAR_SPACING_ABOVE_EXP_BAR = 3; // pixels between the BAR and the Experience Bar below it
	public static float mana = 0;
	public static float sync = 0;

	private Minecraft mc;

	public manaViewerHud(Minecraft mc) {
		this.mc = mc;
	}

	/* This helper method will render the bar */
	public void renderStatusBar(int screenWidth, int screenHeight, World world, EntityPlayer player) {
		PacketHandler.INSTANCE.sendToServer(new PacketGetMana(mana, "com.huto.hutosmod.renders.manaViewerHud", "mana"));
		String m = String.valueOf(mana);
		FontRenderer fr = mc.fontRenderer;
		DecimalFormat d = new DecimalFormat("0.0");

		final int vanillaExpLeftX = screenWidth / 2 - 91; // leftmost edge of the experience bar
		final int vanillaExpTopY = screenHeight - 9; // top of the experience bar

		int newBarWidth = 0;
		int newBarWidth2 = 0;
		int newBarWidth3 = 0;
		int manaValue=0;
		if (mana <= 300) {
			 manaValue=(int) (mana-300);

			newBarWidth = (int) (mana) / 2;
		}	
		if (mana > 300 && mana <= 600) {
			 manaValue=(int) (mana-300);
			newBarWidth = 150;
			newBarWidth2 = (int)(manaValue)/2;
		}
		if (mana > 600 && mana <= 900) {
			 manaValue=(int) (mana-600);

			newBarWidth = 150;
			newBarWidth2 = 150;
			newBarWidth3 = (int) (manaValue) / 2;
		}
		if (mana >= 900) {
			newBarWidth = 150;
			newBarWidth2 = 150;
			newBarWidth3 = 150;
		}

		GL11.glPushMatrix();
		// Black
		GL11.glPushMatrix();
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
		GL11.glTranslatef(vanillaExpLeftX - 1 - 39, vanillaExpTopY + 3 - BAR_SPACING_ABOVE_EXP_BAR - BAR_HEIGHT + 8, 0);
		GL11.glScalef(0.75F, 0.75F, 0.75F);
		drawTexturedModalRect(-130, -5, 0, 0, newBarWidth + 2, BAR_HEIGHT + 2);
		GL11.glPopMatrix();
		
		// Blue Bar
		if (this.mana <= 300) {
			//Blue
			GL11.glColor4f(0.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef(vanillaExpLeftX - 39, vanillaExpTopY - BAR_SPACING_ABOVE_EXP_BAR - BAR_HEIGHT + 8, 0);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			drawTexturedModalRect(-130, 0, 0, 0, newBarWidth, BAR_HEIGHT);
			//Purple Bar
		} else if (this.mana > 300 && this.mana <= 600) {
			//Blue
			GL11.glColor4f(0.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef(vanillaExpLeftX - 39, vanillaExpTopY - BAR_SPACING_ABOVE_EXP_BAR - BAR_HEIGHT + 8, 0);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			drawTexturedModalRect(-130, 0, 0, 0, newBarWidth, BAR_HEIGHT);
			//Black
			GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
			drawTexturedModalRect(-131, -16, 0, 0, newBarWidth2 + 2, BAR_HEIGHT + 2);
			//Purple
			GL11.glColor4f(1.0F, 0.0F, 1.0F, 0.0F);
			drawTexturedModalRect(-130, -15, 0, 0, newBarWidth2, BAR_HEIGHT);
		} else {
			//Blue
			GL11.glTranslatef(vanillaExpLeftX - 39, vanillaExpTopY - BAR_SPACING_ABOVE_EXP_BAR - BAR_HEIGHT + 8, 0);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			//Blue
			GL11.glColor4f(0.0F, 1.0F, 1.0F, 1.0F);
			drawTexturedModalRect(-130, 0, 0, 0, newBarWidth, BAR_HEIGHT);
			//Black
			GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
			drawTexturedModalRect(-131, -16, 0, 0, newBarWidth2 + 2, BAR_HEIGHT + 2);
			//Purple
			GL11.glColor4f(1.0F, 0.0F, 1.0F, 0.0F);
			drawTexturedModalRect(-130, -15, 0, 0, newBarWidth2, BAR_HEIGHT);
			//Black
			GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
			drawTexturedModalRect(-131, -31, 0, 0, newBarWidth3 + 2, BAR_HEIGHT + 2);
			//Red
			GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.0F);
			drawTexturedModalRect(-130, -30, 0, 0, newBarWidth3, BAR_HEIGHT);
		}
		
		
		
		
		
		
		
		
		GL11.glPushMatrix();
		// "Mana Value"
	//	GL11.glScalef(1.0f, 1.20f, 1);
		GL11.glTranslatef(-53, 30, 0);
		fr.drawString("Mana Value", -fr.getStringWidth(d.format(mana)) - 49, -29, 0x000000);
		fr.drawString("Mana Value", -fr.getStringWidth(d.format(mana)) - 50, -29, 0x2F9AFF);
		GL11.glPopMatrix();
		// Digits
		GL11.glPushMatrix();
		GL11.glTranslatef(24, 21, 0);
		GL11.glScalef(1.0f, 1.0f, 1);
		// Shadow String
		fr.drawString(d.format(mana), -50, -20, 0x000000);
		// Blue String renders after so its on top
		fr.drawString(d.format(mana), -51, -20, 0x2F9AFF);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		//GL11.glPopAttrib();
	}

}
