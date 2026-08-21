package net.brdle.collectorsreap.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CRMilkshakeCauldronBlock extends LayeredCauldronBlock {
	public CRMilkshakeCauldronBlock(CauldronInteraction.InteractionMap map) {
		super(Biome.Precipitation.NONE, map, BlockBehaviour.Properties.ofLegacyCopy(Blocks.CAULDRON).requiresCorrectToolForDrops().noOcclusion());
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
		return new ItemStack(Items.CAULDRON);
	}
}
