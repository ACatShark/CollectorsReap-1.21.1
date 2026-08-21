package net.brdle.collectorsreap.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.tag.CommonTags;
import java.util.Objects;
import java.util.function.Supplier;

public class EffectCakeBlock extends CakeBlock {

	private final Supplier<Item> slice;

	public EffectCakeBlock(Properties prop, Supplier<Item> slice) {
		super(prop);
		this.slice = slice;
	}

	public static void addEatEffect(ItemStack pFood, Level pLevel, Player p) {
		FoodProperties foodProperties = pFood.getFoodProperties(p);
		if (foodProperties != null) {
			for (FoodProperties.PossibleEffect possibleEffect : foodProperties.effects()) {
				if (!pLevel.isClientSide && possibleEffect.effect() != null && pLevel.random.nextFloat() < possibleEffect.probability()) {
					p.addEffect(possibleEffect.effect());
				}
			}
		}
	}

	public Item getSlice() {
		return slice.get();
	}

	@Override
	protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
		if (
			stack.is(ItemTags.CANDLES) &&
				state.getValue(BITES) == 0 &&
				Block.byItem(stack.getItem()) instanceof CandleBlock candle &&
				EffectCandleCakeBlock.exists(this, candle)
		) {
			if (!player.isCreative()) {
				stack.shrink(1);
			}
			level.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1F, 1F);
			level.setBlockAndUpdate(pos, EffectCandleCakeBlock.byCakeCandle(this, candle));
			level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
			player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
			return ItemInteractionResult.SUCCESS;
		}
		if (stack.is(CommonTags.Items.TOOLS_KNIFE)) {
			cutSlice(level, pos, state, player);
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		}
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult result) {
		InteractionResult eatResult = eatSlice(level, pos, state, player);
		if (eatResult.consumesAction()) {
			return eatResult;
		}
		if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && player.getItemInHand(InteractionHand.OFF_HAND).isEmpty()) {
			return InteractionResult.CONSUME;
		}
		return InteractionResult.PASS;
	}

	/**
	 * Eats a slice from the pie, feeding the player.
	 */
	public InteractionResult eatSlice(Level level, BlockPos pos, BlockState state, Player player) {
		if (!player.canEat(false)) {
			return InteractionResult.PASS;
		}
		player.awardStat(Stats.EAT_CAKE_SLICE);
		Item slice = this.getSlice();
		ItemStack sliceStack = slice.getDefaultInstance();
		FoodProperties food = sliceStack.getFoodProperties(player);
		if (food != null) {
			player.getFoodData().eat(food);
		}
		EffectCakeBlock.addEatEffect(sliceStack, level, player);
		level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
		int bites = state.getValue(BITES);
		if (bites < 6) {
			level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
		} else {
			level.removeBlock(pos, false);
			level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
		}
		return InteractionResult.SUCCESS;
	}

	/**
	 * Cuts off a bite and drops a slice item, without feeding the player.
	 */
	public InteractionResult cutSlice(Level level, BlockPos pos, BlockState state, Player player) {
		int bites = state.getValue(BITES);
		if (bites < 6) {
			level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
		} else {
			level.removeBlock(pos, false);
		}
		Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), this.getSlice().getDefaultInstance());
		level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
		return InteractionResult.SUCCESS;
	}
}
