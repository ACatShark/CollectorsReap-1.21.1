package net.brdle.collectorsreap.common.block;

import com.teamabnormals.neapolitan.core.other.NeapolitanCauldronInteractions;
import net.brdle.collectorsreap.common.item.CRItems;
import net.minecraft.core.cauldron.CauldronInteraction;

public class CRCauldronInteractions {
	public static final CauldronInteraction.InteractionMap LIME_MILKSHAKE = CauldronInteraction.newInteractionMap("lime_milkshake");
	public static final CauldronInteraction.InteractionMap POMEGRANATE_MILKSHAKE = CauldronInteraction.newInteractionMap("pomegranate_milkshake");
	public static final CauldronInteraction.InteractionMap PINK_DRAGON_FRUIT_MILKSHAKE = CauldronInteraction.newInteractionMap("pink_dragon_fruit_milkshake");
	public static final CauldronInteraction.InteractionMap LUCUMA_MILKSHAKE = CauldronInteraction.newInteractionMap("lucuma_milkshake");

	public static void registerCauldronInteractions() {
		NeapolitanCauldronInteractions.addMilkshakeInteractions(CRItems.LIME_MILKSHAKE.get(), CRBlocks.LIME_MILKSHAKE_CAULDRON.get(), CRItems.LIME_ICE_CREAM.get(), LIME_MILKSHAKE.map());
		NeapolitanCauldronInteractions.addMilkshakeInteractions(CRItems.POMEGRANATE_MILKSHAKE.get(), CRBlocks.POMEGRANATE_MILKSHAKE_CAULDRON.get(), CRItems.POMEGRANATE_ICE_CREAM.get(), POMEGRANATE_MILKSHAKE.map());
		NeapolitanCauldronInteractions.addMilkshakeInteractions(CRItems.PINK_DRAGON_FRUIT_MILKSHAKE.get(), CRBlocks.PINK_DRAGON_FRUIT_MILKSHAKE_CAULDRON.get(), CRItems.PINK_DRAGON_FRUIT_ICE_CREAM.get(), PINK_DRAGON_FRUIT_MILKSHAKE.map());
		NeapolitanCauldronInteractions.addMilkshakeInteractions(CRItems.LUCUMA_MILKSHAKE.get(), CRBlocks.LUCUMA_MILKSHAKE_CAULDRON.get(), CRItems.LUCUMA_ICE_CREAM.get(), LUCUMA_MILKSHAKE.map());
	}
}
