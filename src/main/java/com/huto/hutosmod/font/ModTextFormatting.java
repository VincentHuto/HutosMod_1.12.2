package com.huto.hutosmod.font;

import com.huto.hutosmod.recipies.EnumEssecenceType;

import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.annotation.varargs;

@SideOnly(Side.CLIENT)
public class ModTextFormatting {
	@SideOnly(Side.CLIENT)
	private static FontRenderer aklo_font;

	@SideOnly(Side.CLIENT)
	public static void setAkloFont(FontRenderer font) {
		if (aklo_font == null && Loader.instance().getLoaderState() == LoaderState.INITIALIZATION)
			aklo_font = font;
	}

	@SideOnly(Side.CLIENT)
	public static FontRenderer getAkloFont() {
		return aklo_font;
	}

	@SideOnly(Side.CLIENT)
	public static String stringToGolden(String parString, int parShineLocation, boolean parReturnToBlack) {
		int stringLength = parString.length();
		if (stringLength < 1) {
			return "";
		}
		String outputString = "";
		for (int i = 0; i < stringLength; i++) {
			if ((i + parShineLocation + Minecraft.getSystemTime() / 20) % 88 == 0) {
				outputString = outputString + TextFormatting.WHITE + parString.substring(i, i + 1);
			} else if ((i + parShineLocation + Minecraft.getSystemTime() / 20) % 88 == 1) {
				outputString = outputString + TextFormatting.GOLD + parString.substring(i, i + 1);
			} else if ((i + parShineLocation + Minecraft.getSystemTime() / 20) % 88 == 87) {
				outputString = outputString + TextFormatting.WHITE + parString.substring(i, i + 1);
			} else {
				outputString = outputString + TextFormatting.BLACK + parString.substring(i, i + 1);
			}
		}
		// return color to a common one after (most chat is white, but for other GUI
		// might want black)
		if (parReturnToBlack) {
			return outputString + TextFormatting.BLACK;
		}
		return outputString + TextFormatting.WHITE;
	}

	@SideOnly(Side.CLIENT)
	public static String stringToRedObf(String parString, int parShineLocation, boolean parReturnToBlack) {
		int stringLength = parString.length();
		if (stringLength < 1) {
			return "";
		}
		String outputString = "";
		for (int i = 0; i < stringLength; i++) {
			if ((i + parShineLocation + Minecraft.getSystemTime() / 20) % 88 == 0) {
				outputString = outputString + TextFormatting.OBFUSCATED + parString.substring(i, i + 1);
			} else if ((i + parShineLocation + Minecraft.getSystemTime() / 20) % 88 == 1) {
				outputString = outputString + TextFormatting.RED + parString.substring(i, i + 1);
			} else if ((i + parShineLocation + Minecraft.getSystemTime() / 20) % 88 == 87) {
				outputString = outputString + TextFormatting.OBFUSCATED + parString.substring(i, i + 1);
			} else {
				outputString = outputString + TextFormatting.BLACK + parString.substring(i, i + 1);
			}
		}
		// return color to a common one after (most chat is white, but for other GUI
		// might want black)
		if (parReturnToBlack) {
			return outputString + TextFormatting.BLACK;
		}
		return outputString + TextFormatting.WHITE;
	}

	@SideOnly(Side.CLIENT)
	public static String stringToRainbow(String parString, boolean parReturnToBlack) {
		int stringLength = parString.length();
		if (stringLength < 1) {
			return "";
		}
		String outputString = "";
		TextFormatting[] colorChar = { TextFormatting.RED, TextFormatting.GOLD, TextFormatting.YELLOW,
				TextFormatting.GREEN, TextFormatting.AQUA, TextFormatting.BLUE, TextFormatting.LIGHT_PURPLE,
				TextFormatting.DARK_PURPLE };
		for (int i = 0; i < stringLength; i++) {
			outputString = outputString + colorChar[i % 8] + parString.substring(i, i + 1);
		}
		// return color to a common one after (most chat is white, but for other GUI
		// might want black)
		if (parReturnToBlack) {
			return outputString + TextFormatting.BLACK;
		}
		return outputString + TextFormatting.WHITE;
	}

	@SideOnly(Side.CLIENT)
	public static String stringToResonant(String parString) {

		float stringValue = Float.parseFloat(parString);
		int stringLength = parString.length();
		if (stringLength < 1) {
			return "";
		}

		
		System.out.println(stringValue);
		String outputString = "";
		TextFormatting[] karmicColors = { TextFormatting.RED, TextFormatting.DARK_RED, TextFormatting.BLUE,
				TextFormatting.AQUA };
		TextFormatting[] ManaColors = { TextFormatting.BLUE, TextFormatting.AQUA };
		if (stringValue > 0) {

			for (int i = 0; i < stringLength; i++) {
				outputString = TextFormatting.ITALIC + outputString + ManaColors[i % 2] + parString.substring(i, i + 1);
			}
		} else if (stringValue < 0) {
			for (int i = 0; i < stringLength; i++) {
				outputString = TextFormatting.ITALIC + outputString + karmicColors[i % 2]
						+ parString.substring(i, i + 1);
			}

		} else if (stringValue == 0.0) {
			for (int i = 0; i < stringLength; i++) {
				outputString = TextFormatting.WHITE + parString;
			}
		}
		return outputString;
	}

}
