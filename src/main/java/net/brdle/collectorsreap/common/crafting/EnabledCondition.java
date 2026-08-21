package net.brdle.collectorsreap.common.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.brdle.collectorsreap.CollectorsReap;
import net.brdle.collectorsreap.Util;
import net.brdle.collectorsreap.common.config.CRConfig;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class EnabledCondition implements ICondition {
	public static final ResourceLocation ID = Util.cr("enabled");
	public static final MapCodec<EnabledCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
		instance.group(
			Codec.STRING.fieldOf("value").forGetter(c -> c.value)
		).apply(instance, EnabledCondition::new));

	private static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS = DeferredRegister.create(NeoForgeRegistries.CONDITION_SERIALIZERS, CollectorsReap.MODID);
	public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<EnabledCondition>> ENABLED = CONDITIONS.register("enabled", () -> CODEC);

	private final String value;

	public EnabledCondition(String value) {
		this.value = value;
	}

	@Override
	public boolean test(IContext context) {
		return CRConfig.verify(this.value);
	}

	@Override
	public MapCodec<? extends ICondition> codec() {
		return CODEC;
	}

	public static void create(IEventBus bus) {
		CONDITIONS.register(bus);
	}

	@Override
	public String toString() {
		return "enabled(\"" + this.value + "\")";
	}
}
