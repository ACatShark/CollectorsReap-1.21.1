package net.brdle.collectorsreap;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.brdle.collectorsreap.common.config.CRConfig;
import net.brdle.collectorsreap.common.item.CRItems;
import net.brdle.collectorsreap.common.item.IConfigured;
import net.brdle.collectorsreap.compat.Modid;
import net.brdle.collectorsreap.compat.Mods;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.EffectCure;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import java.util.*;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Util {
	public static final String EMPTY_STR = "";
	public static final Modid[] EMPTY = new Modid[]{};
	public static final String MC = "minecraft";
	public static final String LOADER = "neoforge";
	public static final UUID BLOCK_REACH = UUID.fromString("6ba3d68d-2e14-4b88-92c8-5a6796650af3");

	public static ResourceLocation rl(@NotNull String modid, @NotNull String path) {
		return ResourceLocation.fromNamespaceAndPath(modid, path);
	}

	public static ResourceLocation rl(@NotNull Modid modid, @NotNull String path) {
		return rl(modid.get(), path);
	}

	public static ResourceLocation rl(@NotNull String separated) {
		return ResourceLocation.parse(separated);
	}

	public static ResourceLocation rl(ItemLike itemLike) {
		if (itemLike instanceof Item item) {
			ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
			if (key != null) return key;
		} else if (itemLike instanceof Block block) {
			ResourceLocation key = BuiltInRegistries.BLOCK.getKey(block);
			if (key != null) return key;
		}
		return rl(EMPTY_STR, EMPTY_STR);
	}

	public static ResourceLocation cr(String path) {
		return rl(CollectorsReap.MODID, path);
	}

	public static TagKey<Item> it(String id, String path) {
		return ItemTags.create(rl(id, path));
	}

	public static TagKey<EntityType<?>> et(Modid modid, String path) {
		return TagKey.create(Registries.ENTITY_TYPE, rl(modid, path));
	}

	public static TagKey<EntityType<?>> et(String id, String path) {
		return TagKey.create(Registries.ENTITY_TYPE, rl(id, path));
	}

	// Returns true if tag is empty or null
	public static boolean tagEmpty(@Nullable TagKey<Item> tag) {
		if (tag == null) {
			return true;
		}
		return BuiltInRegistries.ITEM.getTag(tag).map(holderSet -> holderSet.size()).orElse(0) == 0;
	}

	// Returns true if tag has an entry or is null
	public static boolean tagPopulated(@Nullable TagKey<Item> tag) {
		if (tag == null) {
			return true;
		}
		return BuiltInRegistries.ITEM.getTag(tag).map(holderSet -> holderSet.size()).orElse(0) > 0;
	}

	public static String tagName(TagKey<?> tagKey) {
		return tagKey.location().toString();
	}

	public static MutableComponent tagComponent(TagKey<?> tagKey) {
		return Component.literal(tagName(tagKey));
	}

	public static ObjectArrayList<ItemStack> with(ObjectArrayList<ItemStack> before, ItemStack addition) {
		before.add(addition);
		return before.clone();
	}

	public static ObjectArrayList<ItemStack> with(ObjectArrayList<ItemStack> before, Item addition, int count) {
		return (count < 1) ? before : with(before, new ItemStack(addition, count));
	}

	public static ObjectArrayList<ItemStack> with(ObjectArrayList<ItemStack> before, Item addition, RandomSource rand, int min, int max) {
		if (max < min) {
			return before;
		}
		return with(before, addition, (max == min) ? min : rand.nextIntBetweenInclusive(min, max));
	}

	public static ObjectArrayList<ItemStack> with(ObjectArrayList<ItemStack> before, Item addition) {
		return with(before, addition, 1);
	}

	public static boolean itemExists(ResourceLocation location) {
		return Mods.stringLoaded(location.getNamespace()) && BuiltInRegistries.ITEM.containsKey(location);
	}

	public static boolean blockExists(ResourceLocation location) {
		return Mods.stringLoaded(location.getNamespace()) && BuiltInRegistries.BLOCK.containsKey(location);
	}

	@Nullable
	public static Item item(ResourceLocation rl) {
		return BuiltInRegistries.ITEM.get(rl);
	}

	@Nullable
	public static Item item(String id, String path) {
		return item(rl(id, path));
	}

	@NotNull
	public static Item item(ResourceLocation location, @NotNull Item backup) {
		if (itemExists(location)) {
			Item item = item(location);
			if (item != null) {
				return item;
			}
		}
		return backup;
	}

	@NotNull
	public static Item item(ResourceLocation location, @NotNull Supplier<Item> backup) {
		return item(location, backup.get());
	}

	@NotNull
	public static ItemStack itemStack(ResourceLocation location, @NotNull ItemStack backup) {
		if (itemExists(location)) {
			Item returnItem = item(location);
			if (returnItem != null) {
				return new ItemStack(returnItem);
			}
		}
		return backup;
	}

	public static boolean itemStackIs(ItemStack stack, ResourceLocation location) {
		return itemExists(location) && stack.is(item(location));
	}

	@Nullable
	public static Block block(Modid modid, String path) {
		return block(rl(modid, path));
	}

	@Nullable
	public static Block block(String id, String path) {
		return block(rl(id, path));
	}

	@Nullable
	public static Block block(ResourceLocation rl) {
		return BuiltInRegistries.BLOCK.get(rl);
	}

	public static boolean effectExists(ResourceLocation effect) {
		return Mods.stringLoaded(effect.getNamespace()) && BuiltInRegistries.MOB_EFFECT.containsKey(effect);
	}

	@Nullable
	private static MobEffect getBackup(@Nullable MobEffect[] backup) {
		return (
			(backup != null && backup.length > 0) ?
			backup[0] :
			null
		);
	}

	@Nullable
	public static MobEffect effect(ResourceLocation effLocation, MobEffect... backup) {
		return (
			effectExists(effLocation) ?
			BuiltInRegistries.MOB_EFFECT.get(effLocation) :
			getBackup(backup)
		);
	}

	@Nullable
	public static MobEffect effect(String id, String name, MobEffect... backup) {
		return effect(Util.rl(id, name), backup);
	}

	@Nullable
	public static MobEffect effect(Modid modid, String name, MobEffect... backup) {
		return (
			modid.loaded() ?
				effect(Util.rl(modid, name), backup) :
				getBackup(backup)
		);
	}

	@NotNull
	public static Holder<MobEffect> holder(@NotNull MobEffect effect) {
		return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
	}

	// Returns True if effect is removed
	public static boolean removeEffect(@NotNull LivingEntity entity, @NotNull MobEffect effect) {
		Holder<MobEffect> holder = Util.holder(effect);
		if (entity.hasEffect(holder)) {
			MobEffectEvent.Remove event = NeoForge.EVENT_BUS.post(new MobEffectEvent.Remove(entity, holder, EffectCure.get("milk")));
			if (!event.isCanceled()) {
				entity.removeEffect(holder);
				return true;
			}
		}
		return false;
	}

	public static void removeEffects(@NotNull LivingEntity entity, @NotNull Collection<MobEffect> effects) {
		for (MobEffect effect :  effects) removeEffect(entity, effect);
	}

	// Will not add if effect is null
	public static void addEffect(LivingEntity entity, @Nullable MobEffect effect, int duration, int amp) {
		if (effect != null) entity.addEffect(new MobEffectInstance(Util.holder(effect), duration, amp));
	}

	public static void addEffect(LivingEntity entity, String modid, String name, int duration, int amp, MobEffect... backup) {
		MobEffect me = effect(modid, name, backup);
		if (me != null) addEffect(entity, me, duration, amp);
	}

	public static void addEffects(@NotNull final LivingEntity entity, @NotNull List<MobEffectInstance> effects) {
		for (MobEffectInstance effect : effects) entity.addEffect(new MobEffectInstance(effect));
	}

	public static @NotNull List<MobEffectInstance> getFoodEffects(@Nullable final FoodProperties food) {
		return (
			food == null ?
			Collections.emptyList() :
			food.effects().stream().map(FoodProperties.PossibleEffect::effect)
				.map(effect -> new MobEffectInstance(effect.getEffect(), 100, 0)).toList()
		);
	}

	public static void addFoodEffects(@NotNull final LivingEntity entity, @NotNull final FoodProperties food) {
		addEffects(entity, getFoodEffects(food));
	}

	public static List<MobEffect> getCurableEffects(@NotNull final LivingEntity entity) {
		final Collection<MobEffectInstance> active = entity.getActiveEffects();
		return active.stream()
			.filter(instance ->
				instance.getEffect().value().getCategory().equals(MobEffectCategory.HARMFUL)
			)
			.map(instance -> instance.getEffect().value())
			.toList();
	}

	public static void removeCurableEffects(@NotNull final LivingEntity entity) {
		removeEffects(entity, getCurableEffects(entity));
	}

	public static List<MobEffect> getBeneficialEffects(@NotNull final LivingEntity entity) {
		final Collection<MobEffectInstance> active = entity.getActiveEffects();
		return active.stream()
			.map(instance -> instance.getEffect().value())
			.filter(effect -> effect.getCategory().equals(MobEffectCategory.BENEFICIAL))
			.toList();
	}

	public static ItemStack getStack(@Nullable Supplier<? extends ItemLike> r, int... count) { // Only considers first vararg entry
		if (r == null || r.get() == null) return ItemStack.EMPTY;
		return new ItemStack(Objects.requireNonNull(r.get()), count.length > 0 ? count[0] : 1);
	}

	public static String nameSpace(ItemLike itemLike) {
		return rl(itemLike).getNamespace();
	}

	public static String name(ItemLike itemLike) {
		return rl(itemLike).getPath();
	}

	public static String name(DeferredHolder<?, ?> reg) {
		return reg.getId().getPath();
	}

	public static String nameSpace(ItemStack stack) {
		return nameSpace(stack.getItem());
	}

	public static String name(ItemStack stack) {
		return name(stack.getItem());
	}

	// modid:item
	public static String id(ItemLike item) {
		return rl(item).toString();
	}

	public static Ingredient ing(Supplier<? extends ItemLike> i) {
		return Ingredient.of(i.get());
	}

	public static ItemStack enchant(ItemStack stack, Holder<Enchantment> enchantment, int level) {
		ItemStack enchanted = stack.copy();
		enchanted.enchant(enchantment, level);
		return enchanted;
	}

	public static void drop(Level level, ItemStack stack, BlockPos pos, Direction dir) {
		RandomSource random = level.getRandom();
		ItemEntity dropItem = new ItemEntity(
			level,
			pos.getX() + 0.5D + dir.getStepX() * 0.65D,
			pos.getY() + 0.1D,
			pos.getZ() + 0.5D + dir.getStepZ() * 0.65D,
			stack
		);
		dropItem.setDeltaMovement(
			0.05D * dir.getStepX() + random.nextDouble() * 0.02D,
			0.05D,
			0.05D * dir.getStepZ() + random.nextDouble() * 0.02D
		);
		level.addFreshEntity(dropItem);
	}

	public static boolean hasTagString(ItemStack stack, String key, String value) {
		if (stack.has(DataComponents.CUSTOM_DATA)) {
			CompoundTag tag = stack.get(DataComponents.CUSTOM_DATA).getUnsafe();
			return tag != null && tag.contains(key) && tag.getString(key).equals(value);
		}
		return false;
	}

	public static boolean configEnabled(String feature) {
		return CRConfig.verify(feature);
	}

	public static boolean configEnabled(@NotNull ItemLike item) {
		return configEnabled(Util.name(item));
	}

	public static boolean enabled(@NotNull ItemLike item) {
		return (item instanceof IConfigured conf) ? conf.enabled() : configEnabled(item);
	}

	public static boolean enabled(Supplier<? extends ItemLike> item) {
		return enabled(item.get());
	}

	// Only for items, not other config variables
	public static boolean enabled(String item) {
		return (
			CRItems.HELPER.getDeferredRegister().getEntries().stream()
				.filter(reg -> reg.getId().getPath().equals(item))
				.map(Util::enabled)
				.findAny()
				.orElse(configEnabled(item))
		);
	}

	public static MutableComponent translation(String modid, String key) {
		return Component.translatable(modid + "." + key);
	}

	public static MutableComponent translation(String modid, String prefix, String key) {
		return Component.translatable(prefix + "." + modid + "." + key);
	}

	public static MutableComponent description(String key) {
		return Component.translatable("desc." + CollectorsReap.MODID + "." + key);
	}

	public static MutableComponent tooltip(String key) {
		return Component.translatable("tooltip." + key);
	}

	public static MutableComponent crTooltip(String key) {
		return tooltip(CollectorsReap.MODID + "." + key);
	}

	public static float roundToHalf(float x) {
		return (float) Math.round(x * 0.5F) / 0.5F;
	}
}
