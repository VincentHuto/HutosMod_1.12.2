package com.huto.hutosmod.potions;

import com.huto.hutosmod.items.ItemRegistry;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PotionInit {
	public static final Potion runicProtectionEffect = new RunicHealthEffectPotion("runic_health", false, 16765229, 0,
			0);
	public static final Potion manaBurnEffect = new ManaBurnEffectPotion("mana_burn", true, 123, 1, 0);

	public static final PotionType runicProtection = new PotionType("runic_health",
			new PotionEffect[] { new PotionEffect(runicProtectionEffect, 1200) }).setRegistryName("Runic_Potion");
	public static final PotionType LONG_runicProtection = new PotionType("runic_health",
			new PotionEffect[] { new PotionEffect(runicProtectionEffect, 2400) }).setRegistryName("Runic_Elixer");
	public static final PotionType manaBurn = new PotionType("mana_burn",
			new PotionEffect[] { new PotionEffect(manaBurnEffect, 1200) }).setRegistryName("Mana_Burn_Potion");
	public static final PotionType LONG_manaBurn = new PotionType("mana_burn",
			new PotionEffect[] { new PotionEffect(manaBurnEffect, 2400) }).setRegistryName("Mana_Burn_ElixerFS");

	public static void registerPotions() {
		registerPotions(runicProtection, LONG_runicProtection, runicProtectionEffect);
		registerPotions(manaBurn, LONG_manaBurn, manaBurnEffect);

	}

	private static void registerPotions(PotionType defaultPotion, PotionType longPotion, Potion effect) {
		ForgeRegistries.POTIONS.register(effect);
		ForgeRegistries.POTION_TYPES.register(defaultPotion);
		ForgeRegistries.POTION_TYPES.register(longPotion);

		registerPotionMixes();
	}

	public static void registerPotionMixes() {
		// Extends the length
		PotionHelper.addMix(PotionTypes.AWKWARD, ItemRegistry.essence_drop, runicProtection);
		PotionHelper.addMix(runicProtection, Items.REDSTONE, LONG_runicProtection);

		PotionHelper.addMix(PotionTypes.AWKWARD, ItemRegistry.nullifying_powder, manaBurn);
		PotionHelper.addMix(manaBurn, Items.REDSTONE, LONG_manaBurn);

	}

}
