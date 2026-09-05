package net.brdle.collectorsreap.common.item.food;

import net.brdle.collectorsreap.Util;
import net.brdle.collectorsreap.compat.Modid;
import net.brdle.collectorsreap.data.CRItemTags;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import java.util.List;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GummyItem extends CompatConsumable {
	public GummyItem(Properties prop, Modid... modid) {
		super(prop, true, false, modid);
	}

	public GummyItem(Properties prop, boolean hasCustomTooltip, Modid... modid) {
		super(prop, true, hasCustomTooltip, modid);
	}

	@Override
	public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
		return 14;
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> comps, @NotNull TooltipFlag isAdvanced) {
		super.appendHoverText(stack, context, comps, isAdvanced);
	}

	protected List<MobEffectInstance> getEffects(@NotNull ItemStack stack, @Nullable LivingEntity consumer) {
		return Util.getFoodEffects(stack.getFoodProperties(consumer));
	}

	// Applies food effects when called, used for mob_feedable interaction
	public void addEffects(@NotNull ItemStack stack, @NotNull LivingEntity consumer) {
		if (this.enabled()) {
			Util.addEffects(consumer, this.getEffects(stack, consumer));
		}
	}
}