package net.brdle.collectorsreap.data.gen;

import net.brdle.collectorsreap.CollectorsReap;
import net.brdle.collectorsreap.compat.Modid;
import net.brdle.collectorsreap.data.CRMobEffectTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;

public class CRMobEffectTagProvider extends IntrinsicHolderTagsProvider<MobEffect> {
	public CRMobEffectTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
		super(output, Registries.MOB_EFFECT, provider, (effect) -> BuiltInRegistries.MOB_EFFECT.getResourceKey(effect).get(), CollectorsReap.MODID, helper);
	}

	@Override
	protected void addTags(HolderLookup.@NotNull Provider provider) {
		this.tag(CRMobEffectTags.UNOBTAINABLE_FROM_PANETTONE)
			.addOptional(Modid.DUNG.rl("ravenous_rush"))
			.addOptional(Modid.COFH.rl("true_invisibility"));
	}
}