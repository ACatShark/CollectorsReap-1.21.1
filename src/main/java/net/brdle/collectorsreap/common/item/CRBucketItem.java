package net.brdle.collectorsreap.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

public class CRBucketItem extends MobBucketItem {
	private final DeferredHolder<EntityType<?>, ? extends EntityType<?>> fish;

	public CRBucketItem(DeferredHolder<EntityType<?>, ? extends EntityType<?>> fish, Fluid content, SoundEvent emptySound, Item.Properties properties) {
		super(EntityType.TADPOLE, content, emptySound, properties);
		this.fish = fish;
	}

	@Override
	public void checkExtraContent(@Nullable Player player, Level level, ItemStack containerStack, BlockPos pos) {
		if (level instanceof ServerLevel serverLevel) {
			this.fish.get().spawn(serverLevel, containerStack, player, pos, MobSpawnType.BUCKET, true, false);
		}
	}
}