package net.brdle.collectorsreap.data.gen;

import com.google.common.collect.ImmutableList;
import net.brdle.collectorsreap.common.loot.CRBlockLoot;
import net.brdle.collectorsreap.common.loot.CREntityLoot;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CRLootTableProvider extends LootTableProvider {
	public CRLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
		super(output, Collections.emptySet(),
			List.of(
				new LootTableProvider.SubProviderEntry(CRBlockLoot::new, LootContextParamSets.BLOCK),
				new LootTableProvider.SubProviderEntry(CREntityLoot::new, LootContextParamSets.ENTITY)
			), provider);
	}
}
