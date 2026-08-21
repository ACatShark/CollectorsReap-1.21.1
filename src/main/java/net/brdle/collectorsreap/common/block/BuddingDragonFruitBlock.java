package net.brdle.collectorsreap.common.block;

import net.brdle.collectorsreap.common.item.CRItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.BuddingBushBlock;

public class BuddingDragonFruitBlock extends BuddingBushBlock implements BonemealableBlock {
	public BuddingDragonFruitBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected @NotNull ItemLike getBaseSeedId() {
		return CRItems.DRAGON_FRUIT_SEEDS.get();
	}

	@Override
	public boolean canGrowPastMaxAge() {
		return true;
	}

	@Override
	public void growPastMaxAge(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		level.setBlockAndUpdate(pos, CRBlocks.PINK_DRAGON_FRUIT_CROP.get().defaultBlockState());
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return this.getAge(state) < this.getMaxAge();
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		int ageGrowth = Math.min(this.getAge(state) + random.nextInt(1, 4), this.getMaxAge());
		if (ageGrowth >= this.getMaxAge()) {
			level.setBlockAndUpdate(pos, CRBlocks.PINK_DRAGON_FRUIT_CROP.get().defaultBlockState());
		} else {
			level.setBlockAndUpdate(pos, state.setValue(AGE, ageGrowth));
		}
	}
}
