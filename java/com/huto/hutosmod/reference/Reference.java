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
	public static final int EntityMasked_Praetor=42070;
	public static final int EntityElemental=42071;
	public static final int GUI_MIND_RUNES = 0;

	public static final int GUI_SINTERING_FURNACE = 1;

    public static final int Gui_Fusion_Furnace = 2;
    public static final int GUI_Rune_Station = 6;
    public static final int Gui_Hud_Overlay_Runic = 3;
	public static final Logger log = LogManager.getLogger(MODID.toUpperCase());

}
