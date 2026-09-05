package net.brdle.collectorsreap.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * A {@link TallFlowerBlock} (two-high plant) that can be right-clicked to pick its seeds.
 * Removes both halves of the plant when harvested.
 */
public class PickableTallFlowerBlock extends TallFlowerBlock {
	private final Supplier<? extends Item> seeds;

	public PickableTallFlowerBlock(Supplier<? extends Item> seeds, Properties properties) {
		super(properties);
		this.seeds = seeds;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
		final BlockPos lowerPos = state.getValue(HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos;
		popResource(level, lowerPos, new ItemStack(this.seeds.get(), 1));
		level.setBlock(lowerPos, Blocks.AIR.defaultBlockState(), 2);
		level.setBlock(lowerPos.above(), Blocks.AIR.defaultBlockState(), 2);
		return InteractionResult.sidedSuccess(level.isClientSide());
	}
}
