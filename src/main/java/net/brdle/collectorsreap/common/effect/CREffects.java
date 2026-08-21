package net.brdle.collectorsreap.common.effect;

import net.brdle.collectorsreap.CollectorsReap;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CREffects {
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, CollectorsReap.MODID);

	public static final DeferredHolder<MobEffect, MobEffect> CORROSION = EFFECTS.register("corrosion",
		CorrosionEffect::new);
	public static final DeferredHolder<MobEffect, MobEffect> VOLATILITY = EFFECTS.register("volatility",
		VolatilityEffect::new);
	public static final DeferredHolder<MobEffect, MobEffect> SURGE = EFFECTS.register("surge",
		SurgeEffect::new);
	public static final DeferredHolder<MobEffect, MobEffect> REBOUND = EFFECTS.register("rebound",
		ReboundEffect::new);

	public static void create(IEventBus bus) {
		EFFECTS.register(bus);
	}
}
