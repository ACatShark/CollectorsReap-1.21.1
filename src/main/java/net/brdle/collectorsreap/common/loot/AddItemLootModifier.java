package net.brdle.collectorsreap.common.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.brdle.collectorsreap.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class AddItemLootModifier extends LootModifier {
	public static final MapCodec<AddItemLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst -> codecStart(inst)
		.and(BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(g -> g.item))
		.and(Codec.INT.fieldOf("minAmount").forGetter(g -> g.minAmount))
		.and(Codec.INT.fieldOf("maxAmount").forGetter(g -> g.maxAmount))
		.and(Codec.FLOAT.fieldOf("chance").forGetter(g -> g.chance))
		.apply(inst, AddItemLootModifier::new)
	);
	protected final Item item;
	protected final int minAmount;
	protected final int maxAmount;
	protected final float chance;

	public AddItemLootModifier(LootItemCondition[] conditions, Item item, int minAmount, int maxAmount, float chance) {
		super(conditions);
		this.item = item;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.chance = chance;
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (context.getRandom().nextFloat() > this.chance || this.maxAmount < 1) {
			return generatedLoot;
		}
		int amount = this.minAmount == this.maxAmount ? this.minAmount : context.getRandom().nextInt(this.maxAmount + 1 - this.minAmount) + this.minAmount;
		return (amount >= 1) ? Util.with(generatedLoot, new ItemStack(this.item, amount)) : generatedLoot;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return CRLootModifiers.ADD_ITEM.get();
	}
}
