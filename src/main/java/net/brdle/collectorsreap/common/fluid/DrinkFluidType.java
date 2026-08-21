package net.brdle.collectorsreap.common.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import java.util.function.Consumer;

public class DrinkFluidType extends FluidType {
	public static final ResourceLocation FLUID_STILL_TEXTURE = ResourceLocation.parse("block/water_still");
	public static final ResourceLocation FLUID_FLOWING_TEXTURE = ResourceLocation.parse("block/water_flow");
	private final int tintColor;

	public DrinkFluidType(int tintColor) {
		super(FluidType.Properties.create()
			.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
			.sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
		);
		this.tintColor = tintColor;
	}

	@Override
	public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
		consumer.accept(new IClientFluidTypeExtensions() {
			@Override
			public ResourceLocation getStillTexture() {
				return FLUID_STILL_TEXTURE;
			}

			@Override
			public ResourceLocation getFlowingTexture() {
				return FLUID_FLOWING_TEXTURE;
			}

			@Override
			public int getTintColor() {
				return tintColor;
			}
		});
	}
}
