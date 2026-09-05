package net.brdle.collectorsreap.data.gen;

import net.brdle.collectorsreap.CollectorsReap;
import net.brdle.collectorsreap.common.block.CRBlocks;
import net.brdle.collectorsreap.common.item.CRItems;
import net.brdle.collectorsreap.common.loot.LootItemEnabledCondition;
import net.brdle.collectorsreap.common.loot.LootTableAddItemModifier;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderSet;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import java.util.concurrent.CompletableFuture;

public class CRLootModifierProvider extends GlobalLootModifierProvider {
	private final CompletableFuture<Provider> provider;

	public CRLootModifierProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, provider, CollectorsReap.MODID);
		this.provider = provider;
	}

	@Override
	protected void start() {
		this.add("abandoned_mineshaft", new LootTableAddItemModifier(
			new LootItemCondition[]{enabled(CRItems.LIME).build()},
			BuiltInLootTables.ABANDONED_MINESHAFT, CRItems.LIME.get(), 1, 2, 1F
		));
		this.add("simple_dungeon", new LootTableAddItemModifier(
			new LootItemCondition[]{enabled(CRItems.LIME_SEEDS).build()},
			BuiltInLootTables.SIMPLE_DUNGEON, CRItems.LIME_SEEDS.get(), 2, 4, 1F
		));
		this.add("bastion_hoglin_stable", new LootTableAddItemModifier(
			new LootItemCondition[]{enabled(CRItems.POMEGRANATE_SLICE).build()},
			BuiltInLootTables.BASTION_HOGLIN_STABLE, CRItems.POMEGRANATE_SLICE.get(), 4, 12, 1F
		));
		this.add("bastion_other", new LootTableAddItemModifier(
			new LootItemCondition[]{enabled(CRItems.POMEGRANATE_SLICE).build()},
			BuiltInLootTables.BASTION_OTHER, CRItems.POMEGRANATE_SLICE.get(), 6, 16, 1F
		));
		this.add("ancient_city_lucuma_sapling", new LootTableAddItemModifier(
			new LootItemCondition[]{enabled(CRBlocks.LUCUMA_SAPLING).build()},
			BuiltInLootTables.ANCIENT_CITY, CRBlocks.LUCUMA_SAPLING.get().asItem(), 1, 1, 1F
		));
		this.add("ancient_city_gilded_lucuma", new LootTableAddItemModifier(
			new LootItemCondition[]{enabled(CRItems.GILDED_LUCUMA).build()},
			BuiltInLootTables.ANCIENT_CITY, CRItems.GILDED_LUCUMA.get(), 1, 1, 1F
		));
		this.add("shipwreck_treasure", new LootTableAddItemModifier(
			new LootItemCondition[]{enabled(CRItems.LUNAR_PEARL).build()},
			BuiltInLootTables.SHIPWRECK_TREASURE, CRItems.LUNAR_PEARL.get(), 4, 8, 1F
		));
		this.add("buried_treasure", new LootTableAddItemModifier(
			new LootItemCondition[]{enabled(CRItems.LUNAR_PEARL).build()},
			BuiltInLootTables.BURIED_TREASURE, CRItems.LUNAR_PEARL.get(), 2, 7, 1F
		));
		// Fishing
		final LootItemCondition.Builder inRiver = LocationCheck.checkLocation(inBiome(Biomes.RIVER));
		final LootItemCondition.Builder inSwamp = LocationCheck.checkLocation(inBiome(Biomes.SWAMP));
		final LootItemCondition.Builder inMangroveSwamp = LocationCheck.checkLocation(inBiome(Biomes.MANGROVE_SWAMP));
		final LootItemCondition.Builder inOcean = LocationCheck.checkLocation(inBiome(Biomes.OCEAN));
		final LootItemCondition.Builder inWarmOcean = LocationCheck.checkLocation(inBiome(Biomes.WARM_OCEAN));
		final LootItemCondition.Builder inColdOcean = LocationCheck.checkLocation(inBiome(Biomes.COLD_OCEAN));
		this.add("fishing_platinum_bass", new LootTableAddItemModifier(
			new LootItemCondition[]{inRiver.build(), enabled(CRItems.PLATINUM_BASS).build()},
			BuiltInLootTables.FISHING_FISH, CRItems.PLATINUM_BASS.get(), 1, 1, 1F
		));
		this.add("fishing_tiger_prawn", new LootTableAddItemModifier(
			new LootItemCondition[]{inRiver.or(inSwamp).or(inMangroveSwamp).build(), enabled(CRItems.TIGER_PRAWN).build()},
			BuiltInLootTables.FISHING_FISH, CRItems.TIGER_PRAWN.get(), 1, 1, 1F
		));
		this.add("fishing_urchin", new LootTableAddItemModifier(
			new LootItemCondition[]{inOcean.or(inWarmOcean).or(inColdOcean).build(), enabled(CRItems.URCHIN).build()},
			BuiltInLootTables.FISHING_FISH, CRItems.URCHIN.get(), 1, 1, 1F
		));
		this.add("fishing_clam", new LootTableAddItemModifier(
			new LootItemCondition[]{inOcean.or(inWarmOcean).or(inColdOcean).build(), enabled(CRItems.CLAM).build()},
			BuiltInLootTables.FISHING_FISH, CRItems.CLAM.get(), 1, 1, 1F
		));
	}

	private LootItemCondition.Builder enabled(DeferredHolder<?, ? extends ItemLike> feature) {
		return LootItemEnabledCondition.enabled(feature);
	}

	private LocationPredicate.Builder inBiome(ResourceKey<Biome> biome) {
		return LocationPredicate.Builder.location().setBiomes(HolderSet.direct(this.provider.join().holderOrThrow(biome)));
	}
}
