package com.huto.hutosmod.reference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reference {

	public static final String MODID = "hutosmod";
	public static final String NAME = "Hutos Mod";
	public static final String VERSION = "1.0";
	public static final String ACCEPTED_VERSIONS = "[1.12.2]";
	public static final String CLIENT_PROXY_CLASS = "com.huto.hutosmod.proxy.ClientProxy";
	public static final String COMMON_PROXY_CLASS = "com.huto.hutosmod.proxy.CommonProxy";

	public static final int Entity_TestMob = 120;
	public static final int Entity_Colin = 42069;
	public static final int EntityMasked_Praetor = 42070;
	public static final int EntityElemental = 42071;

	public static final int GUI_MIND_RUNES = 0;
	public static final int Gui_Hud_Overlay_Runic = 3;
	public static final int GUI_Rune_Station = 6;
	public static final int Gui_Tome = 8;
	public static final int Gui_ElderTome = 9;

	public static final Logger log = LogManager.getLogger(MODID.toUpperCase());

	// Colors
	public static int white = 0xFFFFFF;
	public static int black = 0x000000;
	public static int red = 0xFF0000;
	public static int green = 0x00FF00;
	public static int blue = 0x0000FF;
	public static int yellow = 0xFFFF00;
	public static int teal = 0x00FFFF;
	public static int purple = 0xFF00FF;
	public static int orange = 0xFF7F00;
	public static int brown = 0x634000;
	public static int oxblood = 0x840063;

}
