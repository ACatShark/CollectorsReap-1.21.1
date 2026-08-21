package net.brdle.collectorsreap.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

// Local replacement for Blueprint's WoodenCrateBlock (removed in Blueprint 8.x for 1.21.1)
public class WoodenCrateBlock extends Block {
	public static final MapCodec<WoodenCrateBlock> CODEC = simpleCodec(WoodenCrateBlock::new);

	public WoodenCrateBlock(MapColor mapColor) {
		this(BlockBehaviour.Properties.of().mapColor(mapColor).strength(2.0F, 3.0F).sound(SoundType.WOOD));
	}

	public WoodenCrateBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<? extends Block> codec() {
		return CODEC;
	}
}
