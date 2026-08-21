package net.brdle.collectorsreap.common.event;

import net.brdle.collectorsreap.CollectorsReap;
import net.brdle.collectorsreap.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CRSoundEvents {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, CollectorsReap.MODID);

	public static final DeferredHolder<SoundEvent, SoundEvent> STRAW_BRUSH_COLLECT = SOUNDS.register("straw_brush_collect",
		() -> SoundEvent.createVariableRangeEvent(Util.cr("item.straw_brush.collect")));
	public static final DeferredHolder<SoundEvent, SoundEvent> STRAW_BRUSH_POLLINATE = SOUNDS.register("straw_brush_pollinate",
		() -> SoundEvent.createVariableRangeEvent(Util.cr("item.straw_brush.pollinate")));
	public static final DeferredHolder<SoundEvent, SoundEvent> PICK_LIME = SOUNDS.register("pick_lime",
		() -> SoundEvent.createVariableRangeEvent(Util.cr("block.lime_bush.pick_lime")));
	public static final DeferredHolder<SoundEvent, SoundEvent> PICK_POMEGRANATE = SOUNDS.register("pick_pomegranate",
		() -> SoundEvent.createVariableRangeEvent(Util.cr("block.pomegranate_bush.pick_pomegranate")));
	public static final DeferredHolder<SoundEvent, SoundEvent> PICK_DRAGON_FRUIT = SOUNDS.register("pick_dragon_fruit",
		() -> SoundEvent.createVariableRangeEvent(Util.cr("block.dragon_fruit_bush.pick_dragon_fruit")));
	public static final DeferredHolder<SoundEvent, SoundEvent> URCHIN_DART_THROW = SOUNDS.register("urchin_dart_throw",
		() -> SoundEvent.createVariableRangeEvent(Util.cr("entity.urchin_dart.throw")));
	public static final DeferredHolder<SoundEvent, SoundEvent> URCHIN_DART_HIT = SOUNDS.register("urchin_dart_hit",
		() -> SoundEvent.createVariableRangeEvent(Util.cr("entity.urchin_dart.hit")));
	public static final DeferredHolder<SoundEvent, SoundEvent> SHIMMERING_PEARL_THROW = SOUNDS.register("shimmering_pearl_throw",
		() -> SoundEvent.createVariableRangeEvent(Util.cr("entity.shimmering_pearl.throw")));
	public static final DeferredHolder<SoundEvent, SoundEvent> SHIMMERING_PEARL_BREAK = SOUNDS.register("shimmering_pearl_break",
		() -> SoundEvent.createVariableRangeEvent(Util.cr("entity.shimmering_pearl.break")));
	public static final DeferredHolder<SoundEvent, SoundEvent> PLATINUM_BASS_DEATH = SOUNDS.register("platinum_bass_death",
		() -> SoundEvent.createVariableRangeEvent(Util.cr("entity.platinum_bass.death")));
	public static final DeferredHolder<SoundEvent, SoundEvent> PLATINUM_BASS_HURT = SOUNDS.register("platinum_bass_hurt",
		() -> SoundEvent.createVariableRangeEvent(Util.cr("entity.platinum_bass.hurt")));
	public static final DeferredHolder<SoundEvent, SoundEvent> PLATINUM_BASS_FLOP = SOUNDS.register("platinum_bass_flop",
		() -> SoundEvent.createVariableRangeEvent(Util.cr("entity.platinum_bass.flop")));
	public static final DeferredHolder<SoundEvent, SoundEvent> REBOUND_HEAL = SOUNDS.register("rebound_heal",
		() -> SoundEvent.createVariableRangeEvent(Util.cr("effect.rebound.heal")));
	public static final DeferredHolder<SoundEvent, SoundEvent> CORROSION_CORRODE = SOUNDS.register("corrosion_corrode",
		() -> SoundEvent.createVariableRangeEvent(Util.cr("effect.corrosion.corrode")));

	public static void create(IEventBus bus) {
		SOUNDS.register(bus);
	}
}
