package net.brdle.collectorsreap.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * A {@link FlowerBlock} that can be right-clicked to pick its seeds, mirroring the
 * harvest behaviour of the crop it matures from. Left-clicking still drops the flower
 * itself (handled by the block loot table).
 */
public class PickableFlowerBlock extends FlowerBlock {
	private final Supplier<? extends Item> seeds;

	public PickableFlowerBlock(Holder<MobEffect> effect, int effectDuration, Supplier<? extends Item> seeds, Properties properties) {
		super(effect, effectDuration, properties);
		this.seeds = seeds;
	}

	// NeoForge 1.21.1 splits Block#use into useItemOn (item in hand) and
	// useWithoutItem (empty hand). The default useItemOn skips the empty-hand
	// path, so holding any item would prevent harvesting. Pass the interaction
	// through so the flower can be picked regardless of what is in hand.
	@SuppressWarnings("deprecation")
	@Override
	protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
		popResource(level, pos, new ItemStack(this.seeds.get(), 1));
		level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
		return InteractionResult.sidedSuccess(level.isClientSide());
	}
}
