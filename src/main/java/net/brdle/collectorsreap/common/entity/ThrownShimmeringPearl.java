package net.brdle.collectorsreap.common.entity;

import net.brdle.collectorsreap.common.event.CRSoundEvents;
import net.brdle.collectorsreap.common.item.CRItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class ThrownShimmeringPearl extends ThrowableItemProjectile {
	public ThrownShimmeringPearl(EntityType<? extends ThrownShimmeringPearl> type, Level level) {
		super(type, level);
	}

	public ThrownShimmeringPearl(Level level, LivingEntity shooter) {
		super(CREntities.SHIMMERING_PEARL.get(), shooter, level);
	}

	@Override
	public @NotNull Item getDefaultItem() {
		return CRItems.SHIMMERING_PEARL.get();
	}

	@Override
	public void onHitEntity(@NotNull EntityHitResult result) {
		super.onHitEntity(result);
		result.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 1F);
		this.level().playSound(null, this.getX(), this.getY(), this.getZ(), CRSoundEvents.SHIMMERING_PEARL_BREAK.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (this.level().getRandom().nextFloat() * 0.4F + 0.8F));
		this.discard();
	}

	private void teleport(Entity entity, double X, double Y, double Z) {
		entity.teleportTo(X, Y, Z);
		entity.resetFallDistance();
		if (entity instanceof LivingEntity living) {
			living.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 100, 2));
			if (entity.isInWater()) {
				living.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 60, 0));
			}
		}
	}

	@Override
	public void onHit(@NotNull HitResult result) {
		super.onHit(result);
		for (int i = 0; i < 32; ++i) {
			this.level().addParticle(ParticleTypes.BUBBLE_POP, this.getX(), this.getY() + this.random.nextDouble() * 2, this.getZ(), this.random.nextGaussian(), 0, this.random.nextGaussian());
		}
		if (!this.level().isClientSide() && !this.isRemoved()) {
			Entity entity = this.getOwner();
			if (entity instanceof ServerPlayer server) {
				if (server.connection.isAcceptingMessages() && server.level() == this.level() && !server.isSleeping()) {
					if (server.isPassenger()) {
						server.dismountTo(this.getX(), this.getY(), this.getZ());
					}
					this.teleport(entity, this.getX(), this.getY(), this.getZ());
				}
			} else if (entity != null) {
				this.teleport(entity, this.getX(), this.getY(), this.getZ());
			}
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), CRSoundEvents.SHIMMERING_PEARL_BREAK.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (this.level().getRandom().nextFloat() * 0.4F + 0.8F));
			this.discard();
		}
	}

	@Override
	public void tick() {
		Entity entity = this.getOwner();
		if (entity instanceof Player && !entity.isAlive()) {
			this.discard();
		} else {
			super.tick();
		}
	}

	@Override
	public boolean fireImmune() {
		return false;
	}

	@Override
	public boolean dismountsUnderwater() {
		return false;
	}
}
