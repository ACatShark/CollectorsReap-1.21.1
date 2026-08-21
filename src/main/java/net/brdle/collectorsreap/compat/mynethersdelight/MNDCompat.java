package net.brdle.collectorsreap.compat.mynethersdelight;

import net.brdle.collectorsreap.compat.Modid;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public class MNDCompat {
	public static Holder<MobEffect> getBeneficialPungent() {
		MobEffect effect = Modid.MND.effect("g_pungent", MobEffects.DAMAGE_RESISTANCE.value());
		return effect != null ? BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect) : MobEffects.DAMAGE_RESISTANCE;
	}
}
