package net.brdle.collectorsreap.compat.letfishlove;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

// Let Fish Love integration is currently disabled until a 1.21.1 NeoForge version is available.
// These methods return placeholder instances; the items/blocks will be hidden when the mod is absent.
public class LetFishLoveCompat {
	public static Block platinumBassRoeBlock() {
		return new Block(BlockBehaviour.Properties.of());
	}

	public static Item platinumBassRoeItem() {
		return new Item(new Item.Properties());
	}

	public static Block tigerPrawnRoeBlock() {
		return new Block(BlockBehaviour.Properties.of());
	}

	public static Item tigerPrawnRoeItem() {
		return new Item(new Item.Properties());
	}
}
