package net.brdle.collectorsreap.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TallFlowerCropBlock extends TallBushCropBlock {
	public static final VoxelShape SMALL_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 13.0D, 13.0D);
	public static final int SHORT_THRESHOLD = 2;
	public static final int MAX_AGE = 4;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_4;

	private final Supplier<? extends Item> flower;

	public TallFlowerCropBlock(Supplier<? extends Item> flower, Properties properties) {
		super(properties);
		this.flower = flower;
	}

	@SuppressWarnings("deprecation")
	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return this.isTall(state) ? super.getShape(state, level, pos, context) : SMALL_SHAPE;
	}

	// NeoForge 1.21.1 将 Block#use 拆分为 useItemOn（手持物品）与 useWithoutItem（空手）。
	// 默认的 useItemOn 会跳过空手路径，导致手持物品时无法触发收获，因此需要透传给空手路径。
	@SuppressWarnings("deprecation")
	@Override
	protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
		if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
			state = level.getBlockState(pos.below());
			pos = pos.below();
		}
		if (state.is(this) && this.isMaxAge(state)) {
			// 收获时掉落对应的花，仅清零生长进度，不破坏植株、不收获种子。
			popResource(level, pos, new ItemStack(this.flower.get(), 1));
			level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1F, 0.8F + level.getRandom().nextFloat() * 0.4F);
			level.setBlock(pos, this.getStateForAge(0).setValue(HALF, DoubleBlockHalf.LOWER), 2);
			level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 2);
			return InteractionResult.sidedSuccess(level.isClientSide());
		}
		return InteractionResult.PASS;
	}

	@Override
	public final IntegerProperty getAgeProperty() {
		return AGE;
	}

	@Override
	public final int getMaxAge() {
		return MAX_AGE;
	}

	@Override
	public final int getShortThreshold() {
		return SHORT_THRESHOLD;
	}
}
