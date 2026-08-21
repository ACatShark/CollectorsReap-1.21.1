package net.brdle.collectorsreap.client;

import com.teamabnormals.blueprint.core.api.BlueprintTrims;
import net.brdle.collectorsreap.CollectorsReap;
import net.brdle.collectorsreap.data.pack.CRTrimMaterials;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;
import java.util.concurrent.CompletableFuture;

public class CRSpriteSourceProvider extends SpriteSourceProvider {
	public CRSpriteSourceProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
		super(output, provider, CollectorsReap.MODID, helper);
	}

	@Override
	protected void gather() {
		this.atlas(BlueprintTrims.ARMOR_TRIMS_ATLAS).addSource(BlueprintTrims.materialPatternPermutations(CRTrimMaterials.PEARL));
		this.atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(BlueprintTrims.materialPermutationsForItemLayers(CRTrimMaterials.PEARL));
	}
}
