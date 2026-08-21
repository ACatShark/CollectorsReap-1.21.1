package net.brdle.collectorsreap.compat;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class CompatEffects {
	private static Holder<MobEffect> holder(MobEffect effect, Holder<MobEffect> backup) {
		return effect != null ? BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect) : backup;
	}

	public static Holder<MobEffect> getCaffeinated() {
		return holder(Modid.FR.effect("caffeinated", MobEffects.CONFUSION.value()), MobEffects.CONFUSION);
	}

	public static Holder<MobEffect> getSpitting() {
		return holder(Modid.AT.effect("spitting", MobEffects.CONFUSION.value()), MobEffects.CONFUSION);
	}

	public static Holder<MobEffect> getPersistence() {
		return holder(Modid.AT.effect("persistence", MobEffects.CONFUSION.value()), MobEffects.CONFUSION);
	}

	public static Holder<MobEffect> getRelief() {
		return holder(Modid.AT.effect("relief", MobEffects.CONFUSION.value()), MobEffects.CONFUSION);
	}

	public static Holder<MobEffect> getStuffed() {
		return holder(Modid.SEAS.effect("stuffed", MobEffects.CONFUSION.value()), MobEffects.CONFUSION);
	}

	public static Holder<MobEffect> getThornResistance() {
		return holder(Modid.SEAS.effect("thorn_resistance", MobEffects.FIRE_RESISTANCE.value()), MobEffects.FIRE_RESISTANCE);
	}

	public static Holder<MobEffect> getRooted() {
		return holder(Modid.SEAS.effect("rooted", MobEffects.DAMAGE_RESISTANCE.value()), MobEffects.DAMAGE_RESISTANCE);
	}

	public static Holder<MobEffect> getVitality() {
		return holder(Modid.RF.effect("vitality", MobEffects.DIG_SPEED.value()), MobEffects.DIG_SPEED);
	}

	public static Holder<MobEffect> getTenacity() {
		return holder(Modid.RF.effect("tenacity", MobEffects.DAMAGE_RESISTANCE.value()), MobEffects.DAMAGE_RESISTANCE);
	}

	public static Holder<MobEffect> getMaturity() {
		return holder(Modid.RF.effect("maturity", getCaffeinated().value()), getCaffeinated());
	}

	public static Holder<MobEffect> getTipsy() {
		return holder(Modid.BC.effect("tipsy", getCaffeinated().value()), getCaffeinated());
	}

	public static Holder<MobEffect> getIntoxication() {
		return holder(Modid.BC.effect("intoxication", getCaffeinated().value()), getCaffeinated());
	}

	public static Holder<MobEffect> getRaging() {
		return holder(Modid.BC.effect("raging", getCaffeinated().value()), getCaffeinated());
	}

	public static Holder<MobEffect> getSweetHeart() {
		return holder(Modid.BC.effect("sweet_heart", getCaffeinated().value()), getCaffeinated());
	}

	public static Holder<MobEffect> getTracer() {
		return holder(Modid.COS.effect("tracer", MobEffects.GLOWING.value()), MobEffects.GLOWING);
	}

	public static Holder<MobEffect> getExuberant() {
		return holder(Modid.COS.effect("exuberant", ModEffects.COMFORT.value()), ModEffects.COMFORT);
	}

	public static Holder<MobEffect> getCarotene() {
		return holder(Modid.COS.effect("carotene", MobEffects.NIGHT_VISION.value()), MobEffects.NIGHT_VISION);
	}

	public static Holder<MobEffect> getSpite() {
		return holder(Modid.SOB.effect("spite", MobEffects.FIRE_RESISTANCE.value()), MobEffects.FIRE_RESISTANCE);
	}

	public static Holder<MobEffect> getToughness() {
		return holder(Modid.SOB.effect("toughness", MobEffects.DAMAGE_RESISTANCE.value()), MobEffects.DAMAGE_RESISTANCE);
	}

	public static Holder<MobEffect> getReach() {
		return holder(Modid.SOB.effect("reach", MobEffects.DAMAGE_BOOST.value()), MobEffects.DAMAGE_BOOST);
	}

	public static Holder<MobEffect> getPeace() {
		return holder(Modid.NIRV.effect("peace", MobEffects.GLOWING.value()), MobEffects.GLOWING);
	}

	public static Holder<MobEffect> getFrostResistance() {
		return holder(Modid.WS.effect("frost_resistance", MobEffects.FIRE_RESISTANCE.value()), MobEffects.FIRE_RESISTANCE);
	}
}
