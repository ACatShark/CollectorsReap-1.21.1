package net.brdle.collectorsreap.data.pack;

import net.brdle.collectorsreap.Util;
import net.brdle.collectorsreap.common.item.CRItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.core.registries.BuiltInRegistries;
import java.util.Map;

public class CRTrimMaterials {
	public static final ResourceKey<TrimMaterial> PEARL = registerKey("pearl");

	public static void bootstrap(final BootstrapContext<TrimMaterial> context) {
		register(context, PEARL, CRItems.LUNAR_PEARL.get().asItem(), Style.EMPTY.withColor(15715308), Map.of());
	}

	private static ResourceKey<TrimMaterial> registerKey(final String name) {
		return ResourceKey.create(Registries.TRIM_MATERIAL, Util.cr(name));
	}

	private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> key, Item item, Style style, Map<Holder<ArmorMaterial>, String> overrides) {
		final ResourceLocation rl = key.location();
		context.register(key, new TrimMaterial(
			rl.getNamespace() + "_" + rl.getPath(),
			BuiltInRegistries.ITEM.wrapAsHolder(item),
			-1.0F,
			overrides,
			Component.translatable("trim_material." + rl.toLanguageKey()).withStyle(style)
		));
	}
}