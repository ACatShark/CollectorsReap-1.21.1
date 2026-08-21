package net.brdle.collectorsreap.common.item.food;

import net.minecraft.world.item.Rarity;

public class GildedLucumaItem extends LucumaItem {
	public GildedLucumaItem(Properties properties) {
		super(properties.rarity(Rarity.RARE));
	}
}