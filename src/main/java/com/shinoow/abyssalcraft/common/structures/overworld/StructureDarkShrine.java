package com.shinoow.abyssalcraft.common.structures.overworld;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class StructureDarkShrine extends StructureDarklandsBase {

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {

		PlacementSettings placeSettings = new PlacementSettings().setReplacedBlock(Blocks.STRUCTURE_VOID);

		position = position.down(3);

		MinecraftServer server = worldIn.getMinecraftServer();
		TemplateManager templateManager = worldIn.getSaveHandler().getStructureTemplateManager();

		Template template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "shrine/dark_shrine"));

		template.addBlocksToWorld(worldIn, position, placeSettings);


		return true;
	}

}
