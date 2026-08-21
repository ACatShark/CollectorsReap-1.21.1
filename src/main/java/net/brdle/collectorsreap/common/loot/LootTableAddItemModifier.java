package net.brdle.collectorsreap.common.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

// Adds an item to the loot of a specific loot table (replaces Blueprint's LootPoolEntriesModifier, removed in 8.x)
public class LootTableAddItemModifier extends LootModifier {
	public static final MapCodec<LootTableAddItemModifier> CODEC = RecordCodecBuilder.mapCodec(inst -> codecStart(inst)
		.and(ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("loot_table").forGetter(g -> g.lootTable))
		.and(BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(g -> g.item))
		.and(Codec.INT.fieldOf("minAmount").forGetter(g -> g.minAmount))
		.and(Codec.INT.fieldOf("maxAmount").forGetter(g -> g.maxAmount))
		.and(Codec.FLOAT.fieldOf("chance").forGetter(g -> g.chance))
		.apply(inst, LootTableAddItemModifier::new)
	);
	protected final ResourceKey<LootTable> lootTable;
	protected final Item item;
	protected final int minAmount;
	protected final int maxAmount;
	protected final float chance;

	public LootTableAddItemModifier(LootItemCondition[] conditions, ResourceKey<LootTable> lootTable, Item item, int minAmount, int maxAmount, float chance) {
		super(conditions);
		this.lootTable = lootTable;
		this.item = item;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.chance = chance;
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		if (!context.getQueriedLootTableId().equals(this.lootTable.location())) {
			return generatedLoot;
		}
		if (context.getRandom().nextFloat() > this.chance || this.maxAmount < 1) {
			return generatedLoot;
		}
		int amount = this.minAmount == this.maxAmount ? this.minAmount : context.getRandom().nextInt(this.maxAmount + 1 - this.minAmount) + this.minAmount;
		if (amount >= 1) {
			generatedLoot.add(new ItemStack(this.item, amount));
		}
		return generatedLoot;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return CRLootModifiers.ADD_ITEM_TO_TABLE.get();
	}
}
