package net.brdle.collectorsreap.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.item.Item;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Mature flower whose right-click harvests the flower itself and resets the
 * plant back to its crop form at age 0 so it can regrow, mirroring the harvest
 * behaviour of the tall flowers. Left-clicking (loot table) still drops the
 * flower itself.
 */
public class HarvestableFlowerBlock extends FlowerBlock {
	private final Supplier<? extends Item> flower;
	private final Supplier<? extends Block> crop;

	public HarvestableFlowerBlock(Holder<MobEffect> effect, int effectDuration, Supplier<? extends Item> flower, Supplier<? extends Block> crop, Properties properties) {
		super(effect, effectDuration, properties);
		this.flower = flower;
		this.crop = crop;
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
		// Only reset the plant to the crop (age 0); the actual flower drop is
		// handled in onRemove so that right-click harvest mods (which replace
		// the block directly) also yield the flower.
		level.setBlock(pos, this.crop.get().defaultBlockState(), 2);
		return InteractionResult.sidedSuccess(level.isClientSide());
	}

	// When the flower is replaced by its crop (either by our own right-click
	// harvest or by an external harvest mod), drop the flower itself. Left-click
	// breaking (newState is air) is handled by the loot table instead, so we
	// only drop here when the block actually reverts to the crop.
	@SuppressWarnings("deprecation")
	@Override
	protected void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean moved) {
		if (!state.is(newState.getBlock()) && state.is(this)) {
			popResource(level, pos, new ItemStack(this.flower.get(), 1));
			level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1F, 0.8F + level.getRandom().nextFloat() * 0.4F);
		}
		super.onRemove(state, level, pos, newState, moved);
	}
}
