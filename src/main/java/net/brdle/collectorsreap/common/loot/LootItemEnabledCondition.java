package net.brdle.collectorsreap.common.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.brdle.collectorsreap.Util;
import net.brdle.collectorsreap.common.config.CRConfig;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public class LootItemEnabledCondition implements LootItemCondition {
	public static final MapCodec<LootItemEnabledCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
		instance.group(
			Codec.STRING.fieldOf("item").forGetter(c -> c.item)
		).apply(instance, LootItemEnabledCondition::new));

	final String item;

	LootItemEnabledCondition(String item) {
		this.item = item;
	}

	private static LootItemCondition.Builder enabled(String item) {
		return () -> new LootItemEnabledCondition(item);
	}

	public static LootItemCondition.Builder enabled(DeferredHolder<?, ? extends ItemLike> item) {
		return enabled(item.getId().getPath());
	}

	@Override
	public @NotNull LootItemConditionType getType() {
		return CRLootItemConditions.ENABLED.get();
	}

	/**
	 * Evaluates this predicate on the given argument.
	 *
	 * @param context the input argument
	 * @return {@code true} if the input argument matches the predicate,
	 * otherwise {@code false}
	 */
	@Override
	public boolean test(LootContext context) {
		return CRConfig.verify(this.item);
	}
}
