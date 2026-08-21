package net.brdle.collectorsreap.common.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.brdle.collectorsreap.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

public class LootItemBlockIsTagCondition implements LootItemCondition {
	public static final MapCodec<LootItemBlockIsTagCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
		instance.group(
			ResourceLocation.CODEC.fieldOf("tag").forGetter(c -> c.tag.location())
		).apply(instance, rl -> new LootItemBlockIsTagCondition(TagKey.create(Registries.BLOCK, rl))));

	final TagKey<Block> tag;

	LootItemBlockIsTagCondition(TagKey<Block> tag) {
		this.tag = tag;
	}

	public static LootItemCondition.Builder isTag(TagKey<Block> tag) {
		return () -> new LootItemBlockIsTagCondition(tag);
	}

	@Override
	public @NotNull LootItemConditionType getType() {
		return CRLootItemConditions.IS_TAG.get();
	}

	/**
	 * Evaluates this predicate on the given argument.
	 *
	 * @param lootContext the input argument
	 * @return {@code true} if the input argument matches the predicate,
	 * otherwise {@code false}
	 */
	@Override
	public boolean test(LootContext lootContext) {
		BlockState state = lootContext.getParamOrNull(LootContextParams.BLOCK_STATE);
		return state != null && state.is(this.tag);
	}
}
