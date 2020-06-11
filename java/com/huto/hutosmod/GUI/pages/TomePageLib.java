package com.huto.hutosmod.gui.pages;

import java.util.ArrayList;
import java.util.List;

import com.huto.hutosmod.blocks.BlockRegistry;
import com.huto.hutosmod.items.ItemRegistry;
import com.huto.hutosmod.reference.RegistryHandler;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class TomePageLib {

	// This is the page array for the book, needed because i dont know how to add
	// the pages to their own like sub class
	public static List<GuiTomePage> IntroPageList = new ArrayList<GuiTomePage>();
	public static List<GuiTomePage> KarmaPageList = new ArrayList<GuiTomePage>();
	public static List<GuiTomePage> WorldGenPageList = new ArrayList<GuiTomePage>();
	public static List<GuiTomePage> ArmorPageList = new ArrayList<GuiTomePage>();
	public static List<GuiTomePage> BlocksPageList = new ArrayList<GuiTomePage>();
	public static List<GuiTomePage> WandsPageList = new ArrayList<GuiTomePage>();
	public static List<GuiTomePage> RunesPageList = new ArrayList<GuiTomePage>();

	//Text Locations
	public static String INTRO_PAGE_1 = "mystictome.intro.page.1.text";
	public static String INTRO_PAGE_2 = "mystictome.intro.page.2.text";
	public static String INTRO_PAGE_3 = "mystictome.intro.page.3.text";
	public static String INTRO_PAGE_4 = "mystictome.intro.page.4.text";
	public static String INTRO_PAGE_5 = "mystictome.intro.page.5.text";
	
	//World
	public static String WORLD_PAGE_1 = "mystictome.world.page.1.text";
	
	//Equips
	public static String EQUIP_PAGE_1 = "mystictome.equip.page.1.text";
	//Machines
	public static String MACHINE_PAGE_1 = "mystictome.blocks.page.1.text";
	public static String MACHINE_PAGE_2 = "mystictome.blocks.page.2.text";
	public static String MACHINE_PAGE_3 = "mystictome.blocks.page.3.text";
	public static String MACHINE_PAGE_4 = "mystictome.blocks.page.4.text";
	public static String MACHINE_PAGE_5 = "mystictome.blocks.page.5.text";
	public static String MACHINE_PAGE_6 = "mystictome.blocks.page.6.text";
	public static String MACHINE_PAGE_7 = "mystictome.blocks.page.7.text";
	public static String MACHINE_PAGE_8 = "mystictome.blocks.page.8.text";
	//Wands
	public static String WANDS_PAGE_1 = "mystictome.wands.page.1.text";
	//Runes
	public static String RUNES_PAGE_1 = "mystictome.runes.page.1.text";

	
	
	public static void registerPages() {
		IntroPageList.clear();
		KarmaPageList.clear();
		WorldGenPageList.clear();
		ArmorPageList.clear();
		BlocksPageList.clear();
		WandsPageList.clear();
		RunesPageList.clear();
		// LEXICON PAGES
		// Intro
		IntroPageList.add(new GuiTomePage(0, EnumTomeCatagories.INTRO, "Page 1", "In the Begining",
				new ItemStack(ItemRegistry.channeling_ingot), I18n.format(INTRO_PAGE_1)));
		IntroPageList.add(new GuiTomePage(1, EnumTomeCatagories.INTRO, "Page 2", "A World of essence",
				new ItemStack(ItemRegistry.essence_drop), I18n.format(INTRO_PAGE_2)));
		IntroPageList.add(new GuiTomePage(2, EnumTomeCatagories.INTRO, "Page 3", "Channeling Basics",
				new ItemStack(ItemRegistry.channeling_rod), I18n.format(INTRO_PAGE_3)));
		IntroPageList.add(new GuiTomePage(3, EnumTomeCatagories.INTRO, "Page 4", "Nullification",
				new ItemStack(ItemRegistry.null_crystal), I18n.format(INTRO_PAGE_4)));
		IntroPageList.add(new GuiTomePage(4, EnumTomeCatagories.INTRO, "Page 5", "The power of vitals",
				new ItemStack(ItemRegistry.blood_ingot), I18n.format(INTRO_PAGE_5)));

		//World Gen
		WorldGenPageList.add(new GuiTomePage(0, EnumTomeCatagories.WORLDGEN, "Page 1", "Its all Natural!",
				new ItemStack(BlockRegistry.Mystic_Sapling), I18n.format(WORLD_PAGE_1)));
		// Weapons and Armor
		ArmorPageList.add(new GuiTomePage(0, EnumTomeCatagories.EQUIPS, "Page 1", "Form AND Function",
				new ItemStack(ItemRegistry.blood_chestplate), I18n.format(EQUIP_PAGE_1)));

		// Machines

		BlocksPageList.add(new GuiTomePage(0, EnumTomeCatagories.MACHINES, "Mana Gathering", "It comes from the air",
				new ItemStack(BlockRegistry.mana_gatherer), I18n.format(MACHINE_PAGE_1)));
		BlocksPageList.add(new GuiTomePage(1, EnumTomeCatagories.MACHINES, "Karmic Altar", "Mana, at what cost?",
				new ItemStack(BlockRegistry.karmic_altar), I18n.format(MACHINE_PAGE_2)));
		BlocksPageList.add(new GuiTomePage(2, EnumTomeCatagories.MACHINES, "Mana Belljar", "Clunky but works",
				new ItemStack(BlockRegistry.mana_capacitor), I18n.format(MACHINE_PAGE_3)));
		BlocksPageList.add(new GuiTomePage(3, EnumTomeCatagories.MACHINES, "Mana Storage", "Its like a cool battery",
				new ItemStack(BlockRegistry.mana_storagedrum), I18n.format(MACHINE_PAGE_4)));
		BlocksPageList.add(new GuiTomePage(4, EnumTomeCatagories.MACHINES, "Essence Enhancer", "Infusing and Upgrading",
				new ItemStack(BlockRegistry.essecence_enhancer), I18n.format(MACHINE_PAGE_5)));
		BlocksPageList.add(
				new GuiTomePage(5, EnumTomeCatagories.MACHINES, "Mana Collider", "Mix this and that...and maybe that",
						new ItemStack(BlockRegistry.mana_fuser), I18n.format(MACHINE_PAGE_6)));
		BlocksPageList.add(new GuiTomePage(6, EnumTomeCatagories.MACHINES, "Karmic Extractor", "Clean yourself up",
				new ItemStack(BlockRegistry.karmic_extractor), I18n.format(MACHINE_PAGE_7)));
		BlocksPageList.add(new GuiTomePage(7, EnumTomeCatagories.MACHINES, "Wand Fabricator", "Channeling finally",
				new ItemStack(BlockRegistry.wand_maker), I18n.format(MACHINE_PAGE_8)));

		// Wands & Magic Gloves
		WandsPageList.add(new GuiTomePage(0, EnumTomeCatagories.WANDS, "Wands", "Watch where your pointing it",
				new ItemStack(ItemRegistry.wand_consume_mana), I18n.format(WANDS_PAGE_1)));

		// Runes
		RunesPageList.add(new GuiTomePage(0, EnumTomeCatagories.RUNES, "Runes", "Etching your mind",
				new ItemStack(ItemRegistry.rune_blank), I18n.format(RUNES_PAGE_1)));
	}
}
