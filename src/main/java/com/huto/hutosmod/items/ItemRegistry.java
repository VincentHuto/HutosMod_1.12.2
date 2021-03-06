package com.huto.hutosmod.items;

import java.util.ArrayList;
import java.util.List;

import com.huto.hutosmod.items.armor.ArmorManaViewer;
import com.huto.hutosmod.items.armor.ArmorMysteriousMask;
import com.huto.hutosmod.items.armor.ArmourBase;
import com.huto.hutosmod.items.armor.ElderBoots;
import com.huto.hutosmod.items.armor.ElderChest;
import com.huto.hutosmod.items.armor.ElderHelemt;
import com.huto.hutosmod.items.armor.ElderLeggs;
import com.huto.hutosmod.items.armor.ItemFloatBoots;
import com.huto.hutosmod.items.runes.ItemContractRuneBeast;
import com.huto.hutosmod.items.runes.ItemContractRuneCorrupt;
import com.huto.hutosmod.items.runes.ItemContractRuneHunter;
import com.huto.hutosmod.items.runes.ItemContractRuneImpure;
import com.huto.hutosmod.items.runes.ItemContractRuneMilkweed;
import com.huto.hutosmod.items.runes.ItemContractRuneRadiance;
import com.huto.hutosmod.items.runes.ItemRune;
import com.huto.hutosmod.items.runes.ItemRuneBeast;
import com.huto.hutosmod.items.runes.ItemRuneClaw;
import com.huto.hutosmod.items.runes.ItemRuneCommunion;
import com.huto.hutosmod.items.runes.ItemRuneEye;
import com.huto.hutosmod.items.runes.ItemRuneGuidance;
import com.huto.hutosmod.items.runes.ItemRuneHeir;
import com.huto.hutosmod.items.runes.ItemRuneLake;
import com.huto.hutosmod.items.runes.ItemRuneMoon;
import com.huto.hutosmod.items.runes.ItemRuneMorph;
import com.huto.hutosmod.items.runes.ItemRuneMorphCW;
import com.huto.hutosmod.items.runes.ItemRuneOedon;
import com.huto.hutosmod.items.runes.ItemRuneRapture;
import com.huto.hutosmod.items.tools.ItemKnapper;
import com.huto.hutosmod.items.tools.ToolAxe;
import com.huto.hutosmod.items.tools.ToolHoe;
import com.huto.hutosmod.items.tools.ToolNullSword;
import com.huto.hutosmod.items.tools.ToolPickaxe;
import com.huto.hutosmod.items.tools.ToolShovel;
import com.huto.hutosmod.items.tools.ToolTreecapitator;
import com.huto.hutosmod.items.tools.ToolVeinPickaxe;
import com.huto.hutosmod.items.wands.ItemAbsorbWand;
import com.huto.hutosmod.items.wands.ItemConsumeWand;
import com.huto.hutosmod.items.wands.ItemFireballWand;
import com.huto.hutosmod.items.wands.ItemGreatFireballWand;
import com.huto.hutosmod.items.wands.ItemGreatStormWand;
import com.huto.hutosmod.items.wands.ItemHealOthersWand;
import com.huto.hutosmod.items.wands.ItemHealSelfWand;
import com.huto.hutosmod.items.wands.ItemLightningWand;
import com.huto.hutosmod.items.wands.ItemRendingWand;
import com.huto.hutosmod.items.wands.ItemSacrificeWand;
import com.huto.hutosmod.reference.Reference;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.EnumHelper;

public class ItemRegistry {

	// Lists
	public static final List<Item> ITEMS = new ArrayList<Item>();
	// Damage Source
	public static final DamageSource NullSwordDamageSource = new DamageSource("null").setDamageBypassesArmor();
	// Items
	public static final Item grey_ingot = new ItemBase("grey_ingot");
	public static final Item grey_powder = new ItemBase("grey_powder");
	public static final Item grey_crystal = new ItemBase("grey_crystal");
	public static final Item null_ingot = new ItemBase("null_ingot");
	public static final Item nullifying_powder = new ItemBase("nullifying_powder");
	public static final Item null_crystal = new ItemBase("null_crystal");
	public static final Item null_rod = new ItemBase("null_rod");
	public static final Item channeling_ingot = new ItemBase("channeling_ingot");
	public static final Item mana_powder = new ItemBase("mana_powder");
	public static final Item mana_crystal = new ItemBase("mana_crystal");
	public static final Item channeling_rod = new ItemBase("channeling_rod");
	public static final Item essence_drop = new ItemBase("essence_drop");
	public static final Item karmic_drop = new ItemBase("karmic_drop");
	public static final Item anti_tear = new ItemBase("anti_tear");
	public static final Item karmic_bar = new ItemBase("karmic_bar");
	public static final Item readied_pane = new ItemBase("readied_pane");
	public static final Item phantasmal_pane = new ItemBase("phantasmal_pane");
	public static final Item mind_spike = new ItemBase("mind_spike");
	public static final Item magatamabead = new ItemBase("magatamabead");
	public static final Item enhancedmagatama = new ItemBase("enhancedmagatama");
	public static final Item energy_focus = new ItemBase("energy_focus");
	public static final Item purging_stone = new ItemPurgingStone("purging_stone");
	public static final Item grand_purging_stone = new ItemGrandPurgingStone("grand_purging_stone");
	public static final Item resonant_fuel = new ItemResonantFuel("resonant_fuel");
	public static final Item enhance_overlay = new ItemEnchancedOverlay("enhance_overlay");
	public static final Item GUI_PORTA_BENCH = new ItemPortaBench("porta_bench");
	public static final Item drying_agent = new ItemDryingAgent("drying_agent");
	public static final Item soaking_agent = new ItemSoakingAgent("soaking_agent");
	public static final Item storming_agent = new ItemStormingAgent("storming_agent");
	public static final Item repulsion_charm = new ItemRepulsionCharm("repulsion_charm");
	public static final Item attraction_charm = new ItemAttractionCharm("attraction_charm");


	// Food
	public static final Item singeri_soup = new ItemSingeriSoup("singeri_soup", 3);
	public static final Item raw_morel_on_a_stick = new ItemBase("raw_morel_on_a_stick");
	public static final Item cooked_morel_on_a_stick = new ItemCookedMorelStick("cooked_morel_on_a_stick", 3, false);
	public static final Item mana_bottle = new ItemManaBottle("mana_bottle", 1);

	// Activator
	public static final Item maker_activator = new ItemMakerActivator("maker_activator");
	public static final Item mana_extractor = new ItemManaExtractor("mana_extractor");
	public static final Item mana_debugtool = new ItemDebugTool("mana_debugtool");
	public static final Item mystic_tome = new ItemMysticTome("mystic_tome");
	public static final Item elder_tome = new ItemElderTome("elder_tome");

	// Wands
	public static final Item wand_consume_mana = new ItemConsumeWand("wand_consume_mana");
	public static final Item wand_gain_mana = new ItemAbsorbWand("wand_gain_mana");
	public static final Item wand_fireball = new ItemFireballWand("wand_fireball");
	public static final Item wand_greatfireball = new ItemGreatFireballWand("wand_greatfireball");
	public static final Item wand_sacrifice = new ItemSacrificeWand("wand_sacrifice");
	public static final Item wand_rending = new ItemRendingWand("wand_rending");
	public static final Item wand_healself = new ItemHealSelfWand("wand_healself");
	public static final Item wand_healother = new ItemHealOthersWand("wand_healother");
	public static final Item wand_lightning = new ItemLightningWand("wand_lightning");
	public static final Item wand_greatstorm = new ItemGreatStormWand("wand_greatstorm");

	// Adding a toolMaterial
	public static final ToolMaterial MATERIAL_NULL = EnumHelper.addToolMaterial("material_null", 3, 2000, 12.0F, 0.3F,
			200);
	public static final ToolMaterial MATERIAL_OBSIDIAN_KNAPPER = EnumHelper.addToolMaterial("material_obsidian_knapper",
			1, 256, 0.5F, 0.3F, 5);
	// Tools
	public static final ItemSword null_sword = new ToolNullSword("null_sword", MATERIAL_NULL);
	public static final ItemPickaxe null_pickaxe = new ToolPickaxe("null_pickaxe", MATERIAL_NULL);
	public static final ItemAxe null_axe = new ToolAxe("null_axe", MATERIAL_NULL);
	public static final ItemSpade null_shovel = new ToolShovel("null_shovel", MATERIAL_NULL);
	public static final ItemHoe null_hoe = new ToolHoe("null_hoe", MATERIAL_NULL);
	public static final ItemPickaxe duality_pick = new ToolVeinPickaxe("duality_pick", MATERIAL_NULL);
	public static final ItemAxe duality_axe = new ToolTreecapitator("duality_axe", MATERIAL_NULL);

	public static final ItemTool iron_knapper = new ItemKnapper("iron_knapper", 25f, MATERIAL_OBSIDIAN_KNAPPER);
	public static final ItemTool obsidian_knapper = new ItemKnapper("obsidian_knapper", 50f, MATERIAL_OBSIDIAN_KNAPPER);

	// Adding an armor Material
	public static final ArmorMaterial armor_null = EnumHelper.addArmorMaterial("armor_null", Reference.MODID + ":null",
			33, new int[] { 3, 6, 8, 3 }, 30, SoundEvents.BLOCK_GLASS_BREAK, 3.0F);
	public static final ArmorMaterial armor_mysterious_mask = EnumHelper.addArmorMaterial("armor_mysterious_mask",
			Reference.MODID + ":mysterious_mask", 0, new int[] { 3, 6, 8, 3 }, 30, SoundEvents.BLOCK_ANVIL_BREAK, 3.0F);
	public static final ArmorMaterial armor_mana_viewer = EnumHelper.addArmorMaterial("armor_mana_viewer",
			Reference.MODID + ":mana_viewer", 0, new int[] { 3, 6, 8, 3 }, 30, SoundEvents.BLOCK_ANVIL_BREAK, 3.0F);
	public static final ArmorMaterial armor_elder = EnumHelper.addArmorMaterial("armor_elder",
			Reference.MODID + ":elder", 10, new int[] { 1, 3, 2, 1 }, 30, SoundEvents.AMBIENT_CAVE, 0.0F);

	// Armor
	public static final Item null_helmet = new ArmourBase("null_helmet", armor_null, 1, EntityEquipmentSlot.HEAD);
	public static final Item null_chestplate = new ArmourBase("null_chestplate", armor_null, 1,
			EntityEquipmentSlot.CHEST);
	public static final Item null_leggings = new ArmourBase("null_leggings", armor_null, 2, EntityEquipmentSlot.LEGS);
	public static final Item null_boots = new ArmourBase("null_boots", armor_null, 1, EntityEquipmentSlot.FEET);

	public static final Item floatboots = new ItemFloatBoots("floatboots", armor_null, 1, EntityEquipmentSlot.FEET);

	public static final Item mysterious_mask = new ArmorMysteriousMask("mysterious_mask", armor_mysterious_mask, 1,
			EntityEquipmentSlot.HEAD);
	public static final Item mana_viewer = new ArmorManaViewer("mana_viewer", armor_mana_viewer, 1,
			EntityEquipmentSlot.HEAD);

	public static final Item elder_helmet = new ElderHelemt(armor_elder, 1, EntityEquipmentSlot.HEAD);
	public static final Item elder_chestplate = new ElderChest(armor_elder, 1, EntityEquipmentSlot.CHEST);
	public static final Item elder_leggings = new ElderLeggs(armor_elder, 2, EntityEquipmentSlot.LEGS);
	public static final Item elder_boots = new ElderBoots(armor_elder, 1, EntityEquipmentSlot.FEET);

	// Drum Upgrades
	public static final Item upgrade_wrench = new ItemBase("upgrade_wrench").setMaxStackSize(1);
	public static final Item upgrade_blank = new ItemBase("upgrade_blank").setMaxStackSize(1);
	public static final Item upgrade_block = new ItemUpgrade("upgrade_block");
	public static final Item upgrade_player = new ItemUpgrade("upgrade_player");
	public static final Item upgrade_animal = new ItemUpgrade("upgrade_animal");
	public static final Item upgrade_mob = new ItemUpgrade("upgrade_mob");
	public static final Item upgrade_absorber = new ItemUpgrade("upgrade_absorber");
	public static final Item upgrade_import = new ItemUpgrade("upgrade_import");

	// Contract Runes
	public static final Item rune_beast_c = new ItemContractRuneBeast("rune_beast_c");
	public static final Item rune_corruption_c = new ItemContractRuneCorrupt("rune_corruption_c");
	public static final Item rune_impurity_c = new ItemContractRuneImpure("rune_impurity_c");
	public static final Item rune_milkweed_c = new ItemContractRuneMilkweed("rune_milkweed_c");
	public static final Item rune_radiance_c = new ItemContractRuneRadiance("rune_radiance_c");
	public static final Item rune_hunter_c = new ItemContractRuneHunter("rune_hunter_c");

	// Base Runes
	public static final Item rune_blank = new ItemRune("rune_blank").setMaxStackSize(64);
	public static final Item rune_metamorphosis = new ItemRuneMorph("rune_metamorphosis");
	public static final Item rune_metamorphosis_cw = new ItemRuneMorphCW("rune_metamorphosis_cw");
	public static final Item rune_lake = new ItemRuneLake("rune_lake");
	public static final Item rune_clawmark = new ItemRuneClaw("rune_clawmark");
	public static final Item rune_rapture = new ItemRuneRapture("rune_rapture");
	public static final Item rune_oedon = new ItemRuneOedon("rune_oedon");
	public static final Item rune_eye = new ItemRuneEye("rune_eye");
	public static final Item rune_moon = new ItemRuneMoon("rune_moon");
	public static final Item rune_beast = new ItemRuneBeast("rune_beast");
	public static final Item rune_heir = new ItemRuneHeir("rune_heir");
	public static final Item rune_guidance = new ItemRuneGuidance("rune_guidance");
	public static final Item rune_communion = new ItemRuneCommunion("rune_communion");

}
